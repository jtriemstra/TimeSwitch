package com.example.jtriemstra.timeswitch;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by JTriemstra on 12/24/2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 6;
    public static final String TABLENAME_TIMELOG = "TimeLog";
    public static final String TABLENAME_LIKELYWORDS = "LikelyWords";
    public static final String TABLENAME_CORRECTIONS = "Corrections";
    public static final String TABLENAME_ERRORS = "Errors";
    public static final String TABLENAME_ALGOLOG = "AlgorithmLog";

    public DatabaseHelper(Context objContext){
        super(objContext, "TimeLog", null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        String strSQL = "CREATE TABLE " + TABLENAME_TIMELOG + " (Job TEXT, StartTime INT, EndTime INT, Elapsed INT);";
        db.execSQL(strSQL);

        strSQL = "CREATE TABLE " + TABLENAME_LIKELYWORDS + " (Job TEXT);";
        db.execSQL(strSQL);

        strSQL = "CREATE TABLE " + TABLENAME_CORRECTIONS + " (Heard TEXT, Actual TEXT);";
        db.execSQL(strSQL);

        strSQL = "CREATE TABLE " + TABLENAME_ERRORS + " (Message TEXT, TimeOccurred INT);";
        db.execSQL(strSQL);

        strSQL = "CREATE TABLE " + TABLENAME_ALGOLOG + " (Heard TEXT, Rank INT, Result TEXT, Stage TEXT);";
        db.execSQL(strSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2){
            String strSQL = "CREATE TABLE " + TABLENAME_LIKELYWORDS + " (Job TEXT);";
            db.execSQL(strSQL);
        }

        if (oldVersion < 3){
            String strSQL = "CREATE TABLE " + TABLENAME_CORRECTIONS + " (Heard TEXT, Actual TEXT);";
            db.execSQL(strSQL);
        }

        if (oldVersion < 5) {
            String strSQL = "CREATE TABLE " + TABLENAME_ERRORS + " (Message TEXT, TimeOccurred INT);";
            db.execSQL(strSQL);
        }

        if (oldVersion < 6) {
            String strSQL = "CREATE TABLE " + TABLENAME_ALGOLOG + " (Heard TEXT, Rank INT, Result TEXT, Stage TEXT);";
            db.execSQL(strSQL);
        }
    }
}
