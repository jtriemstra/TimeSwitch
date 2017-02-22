package com.example.jtriemstra.timeswitch.models;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.example.jtriemstra.timeswitch.DatabaseHelper;
import com.example.jtriemstra.timeswitch.NotificationActionServiceGoogle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JTriemstra on 1/25/2016.
 */
public class RuntimeConfig extends ModelBase {
    public static final String SPEECH_PROVIDER_KEY = "SpeechProvider";
    public static final String TABLE_NAME = "RuntimeConfig";

    public RuntimeConfig(Context objContext){
        super(objContext);
    }

    public String getSpeechProvider(){
        String strReturn = getValueByKey(SPEECH_PROVIDER_KEY);
        if (strReturn == null) strReturn = NotificationActionServiceGoogle.PROVIDER_NAME;
        return strReturn;
    }

    private String getValueByKey(String strKey){
        Cursor objCursor = m_objDatabase.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE Key = '" + strKey + "';", null);
        if (objCursor.getCount() > 0) {
            Log.d("RuntimeConfig", "cursor has values");
            objCursor.moveToFirst();
            return objCursor.getString(0);
        }
        else {
            return null;
        }
    }

    private void setValueByKey(String strKey, String strValue){
        m_objDatabase.execSQL("INSERT INTO " + TABLE_NAME + " VALUES(?, ?);", new String[]{strKey, strValue});
    }
}
