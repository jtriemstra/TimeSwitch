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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.jtriemstra.timeswitch.R;
import com.example.jtriemstra.timeswitch.activitymodels.JobWeek;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by JTriemstra on 12/26/2015.
 */
public class ReportWeekActivity extends ActivityBase implements AdapterView.OnItemSelectedListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_week);

        loadMondays();

       /* List<TimeLogEntry> lstEntries = TimeLog.listToday(this);
        Log.d("TodayActivity", "entries list has " + lstEntries.size() + " values");
        Log.d("TodayActivity", "converted entries array has " + lstEntries.toArray(new TimeLogEntry[]{}).length + " values");
        ArrayAdapter<TimeLogEntry> adapter = new ArrayAdapter<TimeLogEntry>(this,
                android.R.layout.simple_list_item_1, lstEntries.toArray(new TimeLogEntry[]{}));
        ((ListView) findViewById(R.id.entries)).setAdapter(adapter);*/

    }

    private void loadMondays(){
        ArrayList<String> lstMondays = new ArrayList<String>();
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);

        c.add(Calendar.DAY_OF_MONTH, -35);

        for (int i=1; i<=6; i++){
            lstMondays.add(df.format(c.getTime()));
            c.add(Calendar.DAY_OF_MONTH, 7);
        }

        Spinner objMondays = (Spinner) findViewById(R.id.mondays);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, lstMondays.toArray(new String[]{}));
        objMondays.setOnItemSelectedListener(this);
        objMondays.setAdapter(adapter);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position,
                               long id) {
        Log.d("ReportWeekActivity", "item selected");
        String strMonday = parent.getItemAtPosition(position).toString();
        Date dtMonday;
        SimpleDateFormat objParseFormat = new SimpleDateFormat("yyyy-MM-dd");
        try{
            dtMonday = objParseFormat.parse(strMonday);

            Calendar c = Calendar.getInstance();
            c.setTime(dtMonday);

            SimpleDateFormat objUtcFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            objUtcFormat.setTimeZone(TimeZone.getTimeZone(("UTC")));
            String strUtcMonday = objUtcFormat.format(c.getTime());

            JobWeek objJobWeekModel = new JobWeek(this);
            List<JobWeek> lstJobs = objJobWeekModel.getOneWeek(strUtcMonday);
            //List<JobWeek> lstJobs = JobWeek.getTestWeek(this, strUtcMonday);

            ListView objEntries = (ListView) findViewById(R.id.entries);
            ReportWeekArrayAdapter objAdapter = new ReportWeekArrayAdapter(this, lstJobs.toArray(new JobWeek[]{}));
            objEntries.setAdapter(objAdapter);
        }
        catch(Exception e){
            Log.d("ReportWeekActivity", "error in onItemSelected", e);
        }



    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
        Log.d("ReportWeekActivity", "nothing selected");
    }


    public class ReportWeekArrayAdapter extends ArrayAdapter<JobWeek>{
        private Context m_objContext;
        private JobWeek[] m_objEntries;

        public ReportWeekArrayAdapter(Context context, JobWeek[] values){
            super(context, R.layout.list_report_week, values);
            m_objContext = context;
            m_objEntries = values;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) m_objContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.list_report_week, parent, false);
            TextView textView = (TextView) rowView.findViewById(R.id.job);

            textView.setText(m_objEntries[position].Job);

            for (int i=0; i<m_objEntries[position].Hours.length; i++){
                TextView objHourText = new TextView(m_objContext);
                objHourText.setText(Double.toString(m_objEntries[position].Hours[i]));
                objHourText.setLayoutParams(new LinearLayout.LayoutParams(0, 50, 0.15f));
                LinearLayout objDays = (LinearLayout) rowView.findViewById(R.id.days);
                objDays.addView(objHourText);
            }

            return rowView;
        }
    }
}
