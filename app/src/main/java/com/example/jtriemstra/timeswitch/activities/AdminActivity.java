package com.example.jtriemstra.timeswitch.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.jtriemstra.timeswitch.R;
import com.example.jtriemstra.timeswitch.models.TimeLog;
import com.example.jtriemstra.timeswitch.models.TimeLogEntry;

import java.util.List;

/**
 * Created by JTriemstra on 12/31/2015.
 */
public class AdminActivity extends ActivityBase {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
    }

    public void deleteAllEntries(View v){
        TimeLog objTimeLogModel = new TimeLog(this);
        objTimeLogModel.deleteAll();
    }

}
