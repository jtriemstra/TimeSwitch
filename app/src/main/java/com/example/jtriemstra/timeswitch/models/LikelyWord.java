package com.example.jtriemstra.timeswitch.models;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.example.jtriemstra.timeswitch.DatabaseHelper;

import org.apache.commons.codec.language.DoubleMetaphone;
import org.apache.commons.lang3.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by JTriemstra on 12/29/2015.
 */
public class LikelyWord extends ModelBase {
    private static ArrayList<String> m_lstLikelyWords;
    private static ArrayList<String> m_lstActualJobs;

    public static final String TABLE_NAME = "LikelyWord";
    public static final String GO_HOME = "go home";

    public LikelyWord(Context objContext){
        super(objContext);
    }

    private void populateLikelyWords(){

        if (m_lstLikelyWords == null){
            m_lstLikelyWords = new ArrayList<String>();
            Cursor objCursor = m_objDatabase.rawQuery("SELECT * FROM " + TABLE_NAME, null);

            if (objCursor.getCount() > 0) {
                Log.d("WordMatcher", "cursor 1 has values");
                objCursor.moveToFirst();
                do {
                    m_lstLikelyWords.add(objCursor.getString(0));
                }
                while (objCursor.moveToNext());
            }

            m_lstActualJobs = new ArrayList<>();
            objCursor = m_objDatabase.rawQuery("SELECT DISTINCT Job FROM " + DatabaseHelper.TABLENAME_TIMELOG, null);

            if (objCursor.getCount() > 0) {
                Log.d("WordMatcher", "cursor 2 has values");
                objCursor.moveToFirst();
                do {
                    m_lstActualJobs.add(objCursor.getString(0));
                }
                while (objCursor.moveToNext());
            }
        }
    }

    public String chooseBest(ArrayList<String> lstMatches){
        String strReturn = "";
        AlgorithmLog objAlgorithmLog = new AlgorithmLog(this.m_objContext);
        int intMatchIndex = 0;
        String strMatchOriginal = "";
        String strStage = "";
        Correction objCorrectionModel = new Correction(m_objContext);

        Log.d("LikelyWord", "choosing best");
        populateLikelyWords();

        Log.d("LikelyWord", "checking actuals");
        for(String strMatch : lstMatches){
            Log.d("LikelyWord", "current match is " + strMatch);
            for(String strActual : m_lstActualJobs){
                Log.d("LikelyWord", "current actual is " + strActual);
                if (strMatch.equalsIgnoreCase(strActual)){
                    strMatchOriginal = strMatch;
                    strStage = "actuals";
                    strReturn = strMatch;
                }
            }
            intMatchIndex++;
        }

        Log.d("LikelyWord", "checking corrections");
        for(String strMatch : lstMatches) {
            if (objCorrectionModel.containsKey(strMatch)){
                strReturn = objCorrectionModel.get(strMatch);
            }
        }

        Log.d("LikelyWord", "checking likelys");
        if ("".equals(strReturn)) {
            intMatchIndex = 0;
            for (String strMatch : lstMatches) {
                for (String strLikely : m_lstLikelyWords) {
                    Log.d("LikelyWord", "current likely is " + strLikely);
                    if (strMatch.toLowerCase().contains(strLikely.toLowerCase())) {
                        strReturn = strMatch;
                        strMatchOriginal = strMatch;
                        strStage = "likelys";
                    }
                }
                intMatchIndex++;
            }
        }

        if ("".equals(strReturn)) {
            Log.d("LikelyWord", "checking metaphone");
            intMatchIndex = 0;
            DoubleMetaphone objMetaphone = new DoubleMetaphone();
            for (String strMatch : lstMatches) {
                if ("".equals(strReturn)) {
                    Log.d("LikelyWord", "current match is " + strMatch);
                    List<String> lstMatchTokens = tokenizeMatch(strMatch);
                    for (String strToken : lstMatchTokens) {
                        String strEncodedToken = objMetaphone.doubleMetaphone(strToken);
                        Log.d("LikelyWord", "current encoded token is " + strEncodedToken);
                        for (String strLikely : m_lstLikelyWords) {
                            String strEncodedLikely = objMetaphone.doubleMetaphone(strLikely);
                            Log.d("LikelyWord", "current encoded likely is " + strEncodedLikely);
                            //if (StringUtils.getLevenshteinDistance(strEncodedToken, strEncodedLikely) == 0) {
                            if (strEncodedToken.contains(strEncodedLikely)) {
                                strReturn = strMatch.replaceAll(strToken, strLikely);
                                strMatchOriginal = strMatch;
                                strStage = "sounds like";
                                Log.d("LikelyWord", "strReturn is " + strReturn);
                            }
                        }
                    }
                }
                intMatchIndex++;
            }
        }

        if ("".equals(strReturn)) {
            strReturn = lstMatches.get(0);
            strMatchOriginal = strReturn;
            strStage = "fallthrough";
        }

        Log.d("LikelyWord", "checking corrections");


        if (objCorrectionModel.containsKey(strReturn)){
            strReturn = objCorrectionModel.get(strReturn);
            strStage = strStage + " corrected";
        }

        objAlgorithmLog.add(strMatchOriginal, 0, strReturn, strStage);

        return strReturn;
    }

    public  void add(String strNewWord){
        m_objDatabase.execSQL("INSERT INTO " + TABLE_NAME + " VALUES (?);", new String[]{strNewWord});
    }

    public String[] getAll(){
        populateLikelyWords();
        return m_lstLikelyWords.toArray(new String[]{});
    }

    private static List<String> tokenizeMatch(String strPhrase){
        String[] strTokens = strPhrase.split(" ");
        ArrayList<String> lstReturn = new ArrayList<>();
        for (int i=0; i< strTokens.length; i++){
            if (i > 0) lstReturn.add(strTokens[i-1] + strTokens[i]);
            lstReturn.add(strTokens[i]);
        }

        return lstReturn;
    }
}
