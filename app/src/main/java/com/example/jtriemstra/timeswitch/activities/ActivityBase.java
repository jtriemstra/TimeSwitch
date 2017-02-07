package com.example.jtriemstra.timeswitch.activities;

import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

import com.example.jtriemstra.timeswitch.R;

/**
 * Created by JTriemstra on 1/2/2016.
 */
public class ActivityBase extends Activity {
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch(id){
            case R.id.action_settings:
                return true;
            case R.id.action_today:
                Intent objIntentForToday = new Intent(this, TodayActivity.class);
                startActivity(objIntentForToday);
                return true;
            case R.id.action_report_week:
                Intent objIntentForWeekReport = new Intent(this, ReportWeekActivity.class);
                startActivity(objIntentForWeekReport);
                return true;
            case R.id.likely_words:
                Intent objIntentForLikelyWords = new Intent(this, LikelyWordsActivity.class);
                startActivity(objIntentForLikelyWords);
                return true;
            case R.id.admin:
                Intent objIntentForAdmin = new Intent(this, AdminActivity.class);
                startActivity(objIntentForAdmin);
                return true;
            case R.id.corrections:
                Intent objIntentForCorrections = new Intent(this, CorrectionsActivity.class);
                startActivity(objIntentForCorrections);
                return true;
            case R.id.errors:
                Intent objIntentForErrors = new Intent(this, ErrorActivity.class);
                startActivity(objIntentForErrors);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
