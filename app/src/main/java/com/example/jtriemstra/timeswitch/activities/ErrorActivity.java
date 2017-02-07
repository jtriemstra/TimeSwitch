package com.example.jtriemstra.timeswitch.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.jtriemstra.timeswitch.R;
import com.example.jtriemstra.timeswitch.models.ErrorLog;
import com.example.jtriemstra.timeswitch.models.TimeLog;
import com.example.jtriemstra.timeswitch.models.TimeLogEntry;

import java.util.List;

/**
 * Created by JTriemstra on 12/26/2015.
 */
public class ErrorActivity extends ActivityBase {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_errors);

        ErrorLog objErrorLogModel = new ErrorLog(this);
        List<String> lstEntries = objErrorLogModel.getLast10();
        Log.d("TodayActivity", "entries list has " + lstEntries.size() + " values");
        Log.d("TodayActivity", "converted entries array has " + lstEntries.toArray(new String[]{}).length + " values");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, lstEntries.toArray(new String[]{}));
        ((ListView) findViewById(R.id.errors)).setAdapter(adapter);

    }

}
