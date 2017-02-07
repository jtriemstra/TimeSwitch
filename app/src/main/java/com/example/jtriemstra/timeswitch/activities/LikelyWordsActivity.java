package com.example.jtriemstra.timeswitch.activities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.jtriemstra.timeswitch.R;
import com.example.jtriemstra.timeswitch.activitymodels.JobWeek;
import com.example.jtriemstra.timeswitch.models.LikelyWord;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by JTriemstra on 12/26/2015.
 */
public class LikelyWordsActivity extends ActivityBase  {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_likely_words);

        LikelyWord objLikelyWordModel = new LikelyWord(this);
        ListView objWords = (ListView) findViewById(R.id.words);
        ArrayAdapter<String> objAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, objLikelyWordModel.getAll());
        objWords.setAdapter(objAdapter);
    }

    public void addWord(View v){
        EditText objNewWord = (EditText) findViewById(R.id.newWord);
        LikelyWord objLikelyWordModel = new LikelyWord(this);
        objLikelyWordModel.add(objNewWord.getText().toString());
    }




}
