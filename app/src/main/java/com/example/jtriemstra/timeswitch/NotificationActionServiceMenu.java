package com.example.jtriemstra.timeswitch;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.IBinder;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jtriemstra.timeswitch.models.ErrorLog;
import com.example.jtriemstra.timeswitch.models.LikelyWord;
import com.example.jtriemstra.timeswitch.models.TimeLog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JTriemstra on 12/21/2015.
 */
public class NotificationActionServiceMenu extends Service {
    public static final String TAG = "NotificationServiceMenu";
    //public static final String NOTIFICATION_KEY = "com.example.jtriemstra.timeswitch.NAS";

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        Log.d(TAG, "start received");

        final WindowManager mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        final View mView = View.inflate(getApplicationContext(), R.layout.fragment_jobs, null);
        ListView lstJobs = (ListView) mView.findViewById(R.id.jobs);
        Button btnClose = (Button) mView.findViewById(R.id.close);

        TimeLog objTimeLogModel = new TimeLog(this);
        List<String> lstJobNames = objTimeLogModel.listRecentJobs();
        lstJobNames.remove(0);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_list_item_1, android.R.id.text1, lstJobNames.toArray(new String[]{}));

        // Assign adapter to ListView
        lstJobs.setAdapter(adapter);
        lstJobs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String strJob = ((TextView) view).getText().toString();
                Log.d(TAG, strJob);
                TimeLog objTimeLogModel = new TimeLog(getApplicationContext());

                objTimeLogModel.changeJob(strJob);
                NotificationFactory.createOrUpdateNotification(getApplicationContext(), strJob, false);
                mWindowManager.removeView(mView);
            }
        });

        WindowManager.LayoutParams mLayoutParams = new WindowManager.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, 0, 0,
                WindowManager.LayoutParams.TYPE_SYSTEM_ERROR,
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                        | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                        | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
/* | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON */,
                PixelFormat.RGBA_8888);

        mWindowManager.addView(mView, mLayoutParams);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "close button click");
                mWindowManager.removeView(mView);
            }
        });

        return Service.START_NOT_STICKY;
    }

    public void onDestroy (){
        Log.d(TAG, "destroy received");
        //m_objSpeechRecognizer.destroy();
    }


}
