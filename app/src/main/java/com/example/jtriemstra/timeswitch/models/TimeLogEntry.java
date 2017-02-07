package com.example.jtriemstra.timeswitch.models;

import java.util.Date;

/**
 * Created by JTriemstra on 12/25/2015.
 */
public class TimeLogEntry {
    public String Job;
    public String StartTime;
    public String EndTime;
    public Date StartTimeUTC;
    public Date EndTimeUTC;
    public int RowID;

    public int EndHour(){
        return Integer.parseInt(EndTime.split(":")[0]);
    }

    public int EndMinute(){
        return Integer.parseInt(EndTime.split(":")[1]);
    }

    public int StartHour(){
        return Integer.parseInt(StartTime.split(":")[0]);
    }

    public int StartMinute(){
        return Integer.parseInt(StartTime.split(":")[1]);
    }

    @Override
    public String toString(){
        return Job + " (" + StartTime + " - " + EndTime + ")";
    }
}
