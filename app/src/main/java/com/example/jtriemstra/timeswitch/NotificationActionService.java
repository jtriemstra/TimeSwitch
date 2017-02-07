package com.example.jtriemstra.timeswitch;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;

import com.example.jtriemstra.timeswitch.models.ErrorLog;
import com.example.jtriemstra.timeswitch.models.TimeLog;
import com.example.jtriemstra.timeswitch.models.LikelyWord;

import java.util.ArrayList;

/**
 * Created by JTriemstra on 12/21/2015.
 */
public class NotificationActionService extends Service {
    private SpeechRecognizer m_objSpeechRecognizer;
    public static final String TAG = "MainActivity";
    //public static final String NOTIFICATION_KEY = "com.example.jtriemstra.timeswitch.NAS";

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        Log.d("NAService", "start received");

        Intent objRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        objRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
        objRecognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,"com.example.jtriemstra.timeswitch");
        objRecognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS,5);

        m_objSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        m_objSpeechRecognizer.setRecognitionListener(new listener(this));

        m_objSpeechRecognizer.startListening(objRecognizerIntent);

        //stopSelf();

        return Service.START_NOT_STICKY;
    }

    public void onDestroy (){
        Log.d("NAService", "destroy received");
        m_objSpeechRecognizer.destroy();
    }

    class listener implements RecognitionListener
    {
        private Context m_objContext;
        public listener(Context objContext){m_objContext = objContext;}
        public void onReadyForSpeech(Bundle params)
        {
            Log.d(TAG, "onReadyForSpeech");
        }
        public void onBeginningOfSpeech()
        {
            Log.d(TAG, "onBeginningOfSpeech");
        }
        public void onRmsChanged(float rmsdB)
        {
            Log.d(TAG, "onRmsChanged");
        }
        public void onBufferReceived(byte[] buffer)
        {
            Log.d(TAG, "onBufferReceived");
        }
        public void onEndOfSpeech()
        {
            Log.d(TAG, "onEndofSpeech");
        }
        public void onError(int error)
        {
            Log.d(TAG, "error " + error);

            ErrorLog objErrorLog = new ErrorLog(m_objContext);
            objErrorLog.insert(Integer.toString(error));
        }
        public void onResults(Bundle results)
        {
            Log.d(TAG, "onResults " + results);
            ArrayList data = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            /*for (int i = 0; i < data.size(); i++)
            {
                Log.d(TAG, "result " + data.get(i));
            }*/

            /*float[] scores = results.getFloatArray(SpeechRecognizer.CONFIDENCE_SCORES);
            for(int i=0; i<scores.length; i++){
                Log.d(TAG, "score " + Float.toString(scores[i]));
            }*/
            LikelyWord objLikelyWordModel = new LikelyWord(m_objContext);
            String strJob = objLikelyWordModel.chooseBest(data);
            NotificationFactory.createOrUpdateNotification(m_objContext, strJob, false);
            TimeLog objTimeLogModel = new TimeLog(m_objContext);
            objTimeLogModel.changeJob(strJob);

        }
        public void onPartialResults(Bundle partialResults)
        {
            Log.d(TAG, "onPartialResults");
        }
        public void onEvent(int eventType, Bundle params)
        {
            Log.d(TAG, "onEvent " + eventType);
        }
    }
}
