package com.example.jtriemstra.timeswitch.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.jtriemstra.timeswitch.NotificationFactory;
import com.example.jtriemstra.timeswitch.R;


public class MainActivity extends ActivityBase {
    private static final int NOTIFICATION_ID = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void createNotification(View view) {
        Log.d("MainActivity", "Button clicked");

        NotificationFactory.createOrUpdateNotification(this, "", false);
    }

    public void cancelNotification(View view) {
        Log.d("MainActivity", "Button clicked");

        NotificationFactory.cancelNotification(this);
    }
}
