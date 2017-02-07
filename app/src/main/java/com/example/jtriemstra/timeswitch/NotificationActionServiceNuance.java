package com.example.jtriemstra.timeswitch;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import com.nuance.nmdp.speechkit.Recognizer;
import com.nuance.nmdp.speechkit.Recognition;
import com.nuance.nmdp.speechkit.SpeechError;
import com.nuance.nmdp.speechkit.Prompt;
import com.nuance.nmdp.speechkit.SpeechKit;

import com.example.jtriemstra.timeswitch.models.*;

import java.util.ArrayList;

/**
 * Created by JTriemstra on 12/21/2015.
 */
public class NotificationActionServiceNuance extends Service {
    Recognizer.Listener m_objListener;
    SpeechKit m_objSpeechKit;
    Recognizer m_objRecognizer;
    public static final String TAG = "NASN";
    public static final String START = "start";
    public static final String STOP = "stop";
    private boolean m_blnIsListening;

    public NotificationActionServiceNuance(){
        Log.d(TAG, "constructor");
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        Log.d(TAG, "startCommand received");

        String strAction = intent.getAction();

        if (strAction.equals(START))
            startListening();
        else if (strAction.equals(STOP))
            stopListening();

        return Service.START_NOT_STICKY;
    }

    private void startListening(){
        if (m_objListener == null) m_objListener = new TimeSwitchListener(this);
        if (m_objSpeechKit == null) m_objSpeechKit = SpeechKit.initialize(this, AppInfo.SpeechKitAppId, AppInfo.SpeechKitServer, AppInfo.SpeechKitPort, AppInfo.SpeechKitSsl, AppInfo.SpeechKitApplicationKey);
        m_objSpeechKit.connect();

        // TODO: Keep an eye out for audio prompts not working on the Droid 2 or other 2.2 devices.
        Prompt beep = m_objSpeechKit.defineAudioPrompt(R.raw.beep);
        m_objSpeechKit.setDefaultRecognizerPrompts(beep, Prompt.vibration(100), null, null);

        m_objRecognizer = m_objSpeechKit.createRecognizer(Recognizer.RecognizerType.Dictation, Recognizer.EndOfSpeechDetection.Short, "eng-USA", m_objListener, null);
        NotificationFactory.createOrUpdateNotification(this, "", true);
        m_objRecognizer.start();
    }

    private void stopListening(){
        if (m_objRecognizer != null){
            m_objRecognizer.stopRecording();
        }
    }

    public void onDestroy() {
        Log.d("NAService", "destroy received");

    }

    class TimeSwitchListener implements Recognizer.Listener{
        private Context m_objContext;
        public TimeSwitchListener(Context objContext){
            m_objContext = objContext;
        }
        @Override
        public void onRecordingBegin(Recognizer recognizer)
        {
            Log.d("TimeSwitchListener", "begin");
        }

        @Override
        public void onRecordingDone(Recognizer recognizer)
        {
            Log.d("TimeSwitchListener", "recording done");
            NotificationFactory.createOrUpdateNotification(m_objContext, "", false);
        }

        @Override
        public void onError(Recognizer recognizer, SpeechError error)
        {
            Log.d("TimeSwitchListener", "error");
            ErrorLog objErrorLog = new ErrorLog(m_objContext);
            objErrorLog.insert(error.getErrorDetail());
        }

        @Override
        public void onResults(Recognizer recognizer, Recognition results)
        {
            Log.d("TimeSwitchListener", "result");int count = results.getResultCount();
            ArrayList<String> lstResults = new ArrayList<>();
            for (int i = 0; i < count; i++)
            {
                Log.d("TimeSwitchListener", results.getResult(i).getText());
                lstResults.add(results.getResult(i).getText());
            }
            LikelyWord objLikelyWordModel = new LikelyWord(m_objContext);
            String strJob = objLikelyWordModel.chooseBest(lstResults);
            NotificationFactory.createOrUpdateNotification(m_objContext, strJob, false);
            TimeLog objTimeLogModel = new TimeLog(m_objContext);
            objTimeLogModel.changeJob(strJob);
            //m_objRecognizer.stopRecording();
        }
    }


}
