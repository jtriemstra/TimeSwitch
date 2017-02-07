package com.example.jtriemstra.timeswitch.activities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jtriemstra.timeswitch.R;
import com.example.jtriemstra.timeswitch.activitymodels.JobWeek;
import com.example.jtriemstra.timeswitch.models.Correction;
import com.example.jtriemstra.timeswitch.models.LikelyWord;
import com.example.jtriemstra.timeswitch.models.TimeLog;

import java.sql.Time;

/**
 * Created by JTriemstra on 12/26/2015.
 */
public class CorrectionsActivity extends ActivityBase  {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_corrections);

        TimeLog objTimeLogModel = new TimeLog(this);
        ListView objJobs = (ListView) findViewById(R.id.jobs);
        CorrectionsArrayAdapter objAdapter = new CorrectionsArrayAdapter(this, objTimeLogModel.listJobs().toArray(new String[]{}));
        objJobs.setAdapter(objAdapter);
    }


    public class CorrectionsArrayAdapter extends ArrayAdapter<String>{
        private Context m_objContext;
        private String[] m_strHeards;

        public CorrectionsArrayAdapter(Context context, String[] values){
            super(context, R.layout.list_correction, values);
            m_objContext = context;
            m_strHeards = values;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) m_objContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.list_correction, parent, false);
            final TextView textView = (TextView) rowView.findViewById(R.id.heard);
            final EditText objEditText = (EditText) rowView.findViewById(R.id.actual);

            textView.setText(m_strHeards[position]);

            Button objSaveButton = (Button) rowView.findViewById(R.id.saveCorrection);
            objSaveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Correction objCorrectionModel = new Correction(m_objContext);
                    objCorrectionModel.add(textView.getText().toString(), objEditText.getText().toString());
                }
            });

            return rowView;
        }
    }
}
