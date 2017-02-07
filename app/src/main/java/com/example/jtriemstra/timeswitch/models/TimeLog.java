package com.example.jtriemstra.timeswitch.models;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.example.jtriemstra.timeswitch.DatabaseHelper;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by JTriemstra on 12/24/2015.
 */
public class TimeLog extends ModelBase {
    public TimeLog(Context objContext){
        super(objContext);
    }

    public void changeJob(String strJob){
        m_objDatabase.execSQL("UPDATE " + DatabaseHelper.TABLENAME_TIMELOG + " SET EndTime = julianday('now'), Elapsed = cast((julianday('now') - julianday(StartTime)) * 24 * 60 * 60 as INT) WHERE EndTime IS NULL");
        if (!LikelyWord.GO_HOME.equalsIgnoreCase(strJob)) {
            m_objDatabase.execSQL("INSERT INTO " + DatabaseHelper.TABLENAME_TIMELOG + " VALUES (?, julianday('now'), null, null)", new String[]{strJob});
        }
    }

    public List<TimeLogEntry> listToday(){
        Log.d("TimeLog", "entering listToday");
        ArrayList<TimeLogEntry> lstReturn = new ArrayList<TimeLogEntry>();
        Cursor objCursor = m_objDatabase.rawQuery("SELECT Job, time(StartTime, 'localtime'), time(EndTime, 'localtime'), ROWID, datetime(StartTime), datetime(EndTime) FROM " + DatabaseHelper.TABLENAME_TIMELOG + " WHERE date(StartTime, 'localtime') = date('now', 'localtime');", null);
        //Cursor objCursor = m_objDatabase.rawQuery("SELECT Job, strftime('%Y-%m-%d', julianday('now')), strftime('%Y-%m-%d', julianday('now', '+1 day')) FROM TimeLog ;--WHERE StartTime BETWEEN julianday('now') AND julianday('now', '+1 day');", null);
        if (objCursor.getCount() > 0) {
            Log.d("TimeLog", "cursor has values");
            objCursor.moveToFirst();
            do {
                TimeLogEntry objCurrentEntry = new TimeLogEntry();
                objCurrentEntry.Job = !objCursor.isNull(0) ? objCursor.getString(0) : "";
                objCurrentEntry.StartTime = !objCursor.isNull(1) ? objCursor.getString(1) : "";
                objCurrentEntry.EndTime = !objCursor.isNull(2) ? objCursor.getString(2) : "";
                objCurrentEntry.RowID = !objCursor.isNull(3) ? objCursor.getInt(3) : -1;




                DateFormat iso8601Format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String strStart = (!objCursor.isNull(4) ? objCursor.getString(4) : "");
                String strEnd = (!objCursor.isNull(5) ? objCursor.getString(5) : "");

                try {
                    objCurrentEntry.StartTimeUTC = iso8601Format.parse(strStart);
                    objCurrentEntry.EndTimeUTC = iso8601Format.parse(strEnd);
                }
                catch (Exception e){
                    Log.d("TimeLog", "error parsing time");
                    Log.d("TimeLog", e.getMessage());
                }


                Log.d("TimeLog", "Start at " + objCurrentEntry.StartTimeUTC.toString());
                lstReturn.add(objCurrentEntry);
            }
            while (objCursor.moveToNext());
        }
        Log.d("TimeLog", "returning list with " + lstReturn.size() + " values");
        return lstReturn;
    }

    public void deleteAll(){
        m_objDatabase.execSQL("DELETE FROM " + DatabaseHelper.TABLENAME_TIMELOG);
    }

    public List<String> listJobs(){
        ArrayList<String> lstReturn = new ArrayList<>();
        Cursor objCursor = m_objDatabase.rawQuery("SELECT DISTINCT Job FROM " + DatabaseHelper.TABLENAME_TIMELOG + " WHERE Job NOT IN (SELECT Actual FROM " + DatabaseHelper.TABLENAME_CORRECTIONS + ");", null);
        if (objCursor.getCount() > 0){
            objCursor.moveToFirst();
            do {
                lstReturn.add(objCursor.getString(0));
            }
            while (objCursor.moveToNext());
        }
        return lstReturn;
    }

    public List<String> listRecentJobs(){
        ArrayList<String> lstReturn = new ArrayList<>();
        Cursor objCursor = m_objDatabase.rawQuery("SELECT Job FROM (SELECT Job, max(julianday(StartTime)) AS MaxTime FROM " + DatabaseHelper.TABLENAME_TIMELOG + " GROUP BY Job) ORDER BY MaxTime DESC;", null);
        if (objCursor.getCount() > 0){
            objCursor.moveToFirst();
            do {
                lstReturn.add(objCursor.getString(0));
            }
            while (objCursor.moveToNext());
        }
        Log.d("TimeLog", "returning list with " + lstReturn.size() + " values");
        return lstReturn;
    }

    public void updateEntry(int intRowID, Date dtNewStart, Date dtNewEnd){
        DateFormat iso8601Format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strColumn = (dtNewStart == null ? "EndTime" : "StartTime");
        String strDate;
        if (dtNewStart == null) {
            strDate = iso8601Format.format(dtNewEnd);
        } else {
            strDate = iso8601Format.format(dtNewStart);
        }
        m_objDatabase.execSQL("UPDATE " + DatabaseHelper.TABLENAME_TIMELOG + " SET " + strColumn + " = ?1 WHERE ROWID=?2", new String[] {strDate, Integer.toString(intRowID)});
    }
}
