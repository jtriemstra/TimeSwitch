package com.example.jtriemstra.timeswitch.activities;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jtriemstra.timeswitch.NotificationFactory;
import com.example.jtriemstra.timeswitch.R;
import com.example.jtriemstra.timeswitch.models.TimeLog;
import com.example.jtriemstra.timeswitch.models.TimeLogEntry;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import android.widget.TimePicker;

/**
 * Created by JTriemstra on 12/26/2015.
 */
public class TodayActivity extends ActivityBase {
    private int m_intSelectedRowID = -1;
    private int m_intNextRowID = -1;


    protected void loadData(){
        TimeLog objTimeLogModel = new TimeLog(this);
        List<TimeLogEntry> lstEntries = objTimeLogModel.listToday();
        Log.d("TodayActivity", "entries list has " + lstEntries.size() + " values");
        Log.d("TodayActivity", "converted entries array has " + lstEntries.toArray(new TimeLogEntry[]{}).length + " values");

        ArrayAdapter<TimeLogEntry> adapter = new ArrayAdapter<TimeLogEntry>(this,
                android.R.layout.simple_list_item_1, lstEntries.toArray(new TimeLogEntry[]{}));
        ListView lstEntriesView = ((ListView) findViewById(R.id.entries));
        lstEntriesView.setAdapter(adapter);

        lstEntriesView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String strJob = ((TextView) view).getText().toString();
                TimeLogEntry objEntry = (TimeLogEntry) parent.getItemAtPosition(position);
                TimeLogEntry objNext = null;
                Log.d("NAServiceMenu", Integer.toString(objEntry.RowID));
                m_intSelectedRowID = objEntry.RowID;

                if (parent.getCount() > position + 1) {
                    objNext = (TimeLogEntry) parent.getItemAtPosition(position + 1);
                    if (objEntry.EndTime.equals(objNext.StartTime)) {
                        m_intNextRowID = objNext.RowID;
                    }
                    else{
                        m_intNextRowID = -1;
                    }
                }
                else {
                    m_intNextRowID = -1;
                }

                showTimePickerDialog(objEntry, objNext);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today);

        loadData();

    }


    public void showTimePickerDialog(TimeLogEntry objEntry, TimeLogEntry objNext) {
        TimePickerFragment newFragment = new TimePickerFragment();
        newFragment.Hour = objEntry.EndHour();
        newFragment.Minute = objEntry.EndMinute();
        newFragment.SelectedEntry = objEntry;
        newFragment.NextEntry = objNext;

        newFragment.show(getFragmentManager(), "timePicker");
    }

    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        public int Hour;
        public int Minute;
        public TimeLogEntry SelectedEntry;
        public TimeLogEntry NextEntry;




        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, Hour, Minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            int NewLocalHour = hourOfDay;
            int NewLocalMinute = minute;

            int intOriginalHour = SelectedEntry.EndHour();
            int intOriginalMinute = SelectedEntry.EndMinute();

            if (NewLocalHour > intOriginalHour) return;

            int intMinutesPushedBack = (NewLocalHour*60 + NewLocalMinute) - (intOriginalHour * 60 + intOriginalMinute);
            Calendar c = Calendar.getInstance();
            c.setTime(SelectedEntry.EndTimeUTC);
            c.add(Calendar.MINUTE, intMinutesPushedBack);

            Log.d("time", SelectedEntry.EndTimeUTC.toString());
            Log.d("time", c.toString());

            TimeLog x = new TimeLog(this.getActivity());
            x.updateEntry(SelectedEntry.RowID, null, c.getTime());
            x.updateEntry(NextEntry.RowID, c.getTime(), null);

            ((TodayActivity) this.getActivity()).loadData();
        }
    }

}
