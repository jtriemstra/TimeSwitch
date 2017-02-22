package com.example.jtriemstra.timeswitch.models;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;

import com.example.jtriemstra.timeswitch.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JTriemstra on 1/25/2016.
 */
public class ErrorLog extends ModelBase {
    public static final String TABLE_NAME = "Errors";

    public ErrorLog(Context objContext){
        super(objContext);
    }

    public void insert(String s){
        m_objDatabase.execSQL("INSERT INTO " + TABLE_NAME + " VALUES(?, datetime('now'));", new String[]{s});
    }

    public List<String> getLast10(){
        ArrayList<String> lstReturn = new ArrayList();
        Cursor objCursor = m_objDatabase.rawQuery("SELECT * FROM " + TABLE_NAME + " ORDER BY TimeOccurred DESC LIMIT 10", null);
        if (objCursor.getCount() > 0) {
            Log.d("ErrorLog", "cursor has values");
            objCursor.moveToFirst();
            do {
                lstReturn.add(objCursor.getString(0));
            }
            while (objCursor.moveToNext());
        }
        return lstReturn;
    }
}
