package com.example.jtriemstra.timeswitch.models;

import android.content.Context;

import com.example.jtriemstra.timeswitch.DatabaseHelper;

/**
 * Created by JTriemstra on 2/7/2016.
 */
public class AlgorithmLog extends ModelBase {
    public AlgorithmLog(Context objContext){
        super(objContext);
    }

    public void add(String strHeard, int intRank, String strResult, String strStage){
        m_objDatabase.execSQL("INSERT INTO " + DatabaseHelper.TABLENAME_ALGOLOG + " VALUES (?,?,?,?);", new String[]{strHeard, Integer.toString(intRank), strResult, strStage});
    }

}
