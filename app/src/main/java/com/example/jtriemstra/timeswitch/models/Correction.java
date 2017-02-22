package com.example.jtriemstra.timeswitch.models;

import android.content.Context;
import android.database.Cursor;

import com.example.jtriemstra.timeswitch.DatabaseHelper;

import java.util.HashMap;

/**
 * Created by JTriemstra on 1/2/2016.
 */
public class Correction extends ModelBase {
    public static final String TABLE_NAME = "Corrections";

    private HashMap<String, String> m_hshStoredCorrections;

    public Correction(Context objContext){
        super(objContext);
        m_hshStoredCorrections = getAll();
    }

    public void add(String strHeard, String strActual){
        m_objDatabase.execSQL("INSERT INTO " + TABLE_NAME + " VALUES (?,?);", new String[]{strHeard, strActual});
        m_objDatabase.execSQL("UPDATE " + TABLE_NAME + " SET Job = ? WHERE Job = ? COLLATE NOCASE", new String[]{strActual, strHeard});
    }

    public boolean containsKey(String strKey){
        return m_hshStoredCorrections.containsKey(strKey.toLowerCase());
    }

    public String get(String strKey){
        return m_hshStoredCorrections.get(strKey.toLowerCase());
    }

    private HashMap<String, String> getAll(){
        HashMap<String, String> hshReturn = new HashMap<>();
        Cursor objCursor = m_objDatabase.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        if (objCursor.getCount() > 0){
            objCursor.moveToFirst();
            do{
                hshReturn.put(objCursor.getString(0).toLowerCase(), objCursor.getString(1));
            }
            while(objCursor.moveToNext());
        }
        return hshReturn;
    }
}
