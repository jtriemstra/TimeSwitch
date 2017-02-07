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
    public ErrorLog(Context objContext){
        super(objContext);
    }

    public void insert(String s){
        m_objDatabase.execSQL("INSERT INTO " + DatabaseHelper.TABLENAME_ERRORS + " VALUES(?, datetime('now'));", new String[]{s});
    }

    public List<String> getLast10(){
        ArrayList<String> lstReturn = new ArrayList();
        Cursor objCursor = m_objDatabase.rawQuery("SELECT * FROM " + DatabaseHelper.TABLENAME_ERRORS + " ORDER BY TimeOccurred DESC LIMIT 10", null);
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
