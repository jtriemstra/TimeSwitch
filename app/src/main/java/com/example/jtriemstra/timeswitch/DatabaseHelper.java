package com.example.jtriemstra.timeswitch;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.jtriemstra.timeswitch.models.AlgorithmLog;
import com.example.jtriemstra.timeswitch.models.Correction;
import com.example.jtriemstra.timeswitch.models.ErrorLog;
import com.example.jtriemstra.timeswitch.models.LikelyWord;
import com.example.jtriemstra.timeswitch.models.RuntimeConfig;
import com.example.jtriemstra.timeswitch.models.TimeLog;

/**
 * Created by JTriemstra on 12/24/2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 7;

    private static final String m_strSQL1 ="CREATE TABLE " + TimeLog.TABLE_NAME + " (Job TEXT, StartTime INT, EndTime INT, Elapsed INT);";
    private static final String m_strSQL2 ="CREATE TABLE " + LikelyWord.TABLE_NAME + " (Job TEXT);";
    private static final String m_strSQL3 ="CREATE TABLE " + Correction.TABLE_NAME + " (Heard TEXT, Actual TEXT);";
    private static final String m_strSQL4 ="CREATE TABLE " + ErrorLog.TABLE_NAME + " (Message TEXT, TimeOccurred INT);";
    private static final String m_strSQL5 ="CREATE TABLE " + AlgorithmLog.TABLE_NAME + " (Heard TEXT, Rank INT, Result TEXT, Stage TEXT);";
    private static final String m_strSQL6 ="CREATE TABLE " + RuntimeConfig.TABLE_NAME + " (Key TEXT, Value TEXT);";
    private static final String m_strSQL6b="INSERT INTO " + RuntimeConfig.TABLE_NAME + " VALUES('" + com.example.jtriemstra.timeswitch.models.RuntimeConfig.SPEECH_PROVIDER_KEY + "', '" + NotificationActionServiceGoogle.PROVIDER_NAME + "');";

    public DatabaseHelper(Context objContext){
        super(objContext, "TimeLog", null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(m_strSQL1);
        db.execSQL(m_strSQL2);
        db.execSQL(m_strSQL3);
        db.execSQL(m_strSQL4);
        db.execSQL(m_strSQL5);
        db.execSQL(m_strSQL6);
        db.execSQL(m_strSQL6b);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2){
            db.execSQL(m_strSQL2);
        }

        if (oldVersion < 3){
            db.execSQL(m_strSQL3);
        }

        if (oldVersion < 5) {
            db.execSQL(m_strSQL4);
        }

        if (oldVersion < 6) {
            db.execSQL(m_strSQL5);
        }

        if (oldVersion < 7) {
            db.execSQL(m_strSQL6);
            db.execSQL(m_strSQL6b);
        }
    }
}
