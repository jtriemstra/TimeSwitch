package com.example.jtriemstra.timeswitch.models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.jtriemstra.timeswitch.DatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by JTriemstra on 12/27/2015.
 */
public class ModelBase {
    protected static SQLiteDatabase m_objDatabase;
    protected Context m_objContext;

    protected ModelBase(Context objContext){
        m_objContext = objContext;
        createHelper(objContext);
    }
    protected static void createHelper(Context objContext){
        if (m_objDatabase == null) {
            DatabaseHelper objDatabaseHelper = new DatabaseHelper(objContext);
            m_objDatabase = objDatabaseHelper.getWritableDatabase();
        }
    }

    protected static String convertToSqlLiteString(Date dt){
        SimpleDateFormat objFormat = new SimpleDateFormat("yyyy-MM-dd");
        return objFormat.format(dt);
    }

}
