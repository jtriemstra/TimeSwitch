package com.example.jtriemstra.timeswitch.activitymodels;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.example.jtriemstra.timeswitch.DatabaseHelper;
import com.example.jtriemstra.timeswitch.models.ModelBase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JTriemstra on 12/27/2015.
 */
public class JobWeek extends ModelBase {
    public String Job;
    public double[] Hours;

    public JobWeek(Context objContext){
        super(objContext);
    }

    public List<JobWeek> getOneWeek(String strUtcMonday){
        Log.d("TimeLog", "entering getOneWeek for " + strUtcMonday);
        ArrayList<JobWeek> lstReturn = new ArrayList<JobWeek>();
        Cursor objCursor = m_objDatabase.rawQuery("SELECT Job, " +
                                                    "date(StartTime, 'localtime'), " +
                                                    "SUM(julianday(EndTime) - julianday(StartTime)) * 24, " +
                                                    "CAST((julianday(StartTime) - julianday(?1)) AS INT) " +
                                                    "FROM " + DatabaseHelper.TABLENAME_TIMELOG + " " +
                                                    "WHERE date(StartTime) BETWEEN date(?1) AND date(?1, '+6 day') " +
                                                    "GROUP BY Job, date(StartTime, 'localtime') " +
                                                    "ORDER BY Job, date(StartTime, 'localtime');",
                                                new String[] {strUtcMonday });

        if (objCursor.getCount() > 0) {
            Log.d("TimeLog", "cursor has values");
            objCursor.moveToFirst();
            String strCurrentJob = "";
            JobWeek objCurrentEntry = null;
            JobWeek objTotalEntries = new JobWeek(m_objContext);
            objTotalEntries.Job  = "TOTAL";
            objTotalEntries.Hours = new double[]{0,0,0,0,0,0,0};
            do {
                Log.d("JobWeek", " | " + objCursor.getString(0) + " | " + objCursor.getString(3) + " | " + objCursor.getString(1));
                if (!strCurrentJob.equals(objCursor.getString(0))){
                    strCurrentJob = objCursor.getString(0);
                    objCurrentEntry = new JobWeek(m_objContext);
                    objCurrentEntry.Job = objCursor.getString(0);
                    objCurrentEntry.Hours = new double[]{0,0,0,0,0,0,0};
                    lstReturn.add(objCurrentEntry);
                }
                objCurrentEntry.Hours[objCursor.getInt(3)] = Math.round(objCursor.getDouble(2) * 100.0) / 100.0;
                objTotalEntries.Hours[objCursor.getInt(3)] += Math.round(objCursor.getDouble(2) * 100.0) / 100.0;
                //objCurrentEntry.StartTime = !objCursor.isNull(1) ? objCursor.getFloat(1) : "";


            }
            while (objCursor.moveToNext());
            lstReturn.add(0, objTotalEntries);
        }
        Log.d("TimeLog", "returning list with " + lstReturn.size() + " values");
        return lstReturn;
    }

   /* public static List<JobWeek> getTestWeek(Context objContext, String strUtcMonday){
        ArrayList<JobWeek> lstReturn = new ArrayList<>();

        JobWeek a = new JobWeek();
        a.Job = "Keebler";
        a.Hours = new double[] {1.5, 0, 2, 3.5, 1, 0, 0};

        JobWeek b = new JobWeek();
        b.Job = "Open for Breakfast";
        b.Hours = new double[] {.5, 0, 0, 3.5, 1, 0, 0};

        JobWeek c = new JobWeek();
        c.Job = "KFR";
        c.Hours = new double[] {6, 0, 2, .5, 1, 0, 0};

        JobWeek d = new JobWeek();
        d.Job = "APAC";
        d.Hours = new double[] {1.5, 0, 2, 3.5, 1, 0, 0};

        for(int i=0; i<5; i++) {
            JobWeek e = new JobWeek();
            e.Job = "APAC";
            e.Hours = new double[]{1.5, 0, 2, 3.5, 1, 0, 0};
            lstReturn.add(e);
        }


        lstReturn.add(a);
        lstReturn.add(b);
        lstReturn.add(d);
        lstReturn.add(c);

        return lstReturn;
    }*/
}
