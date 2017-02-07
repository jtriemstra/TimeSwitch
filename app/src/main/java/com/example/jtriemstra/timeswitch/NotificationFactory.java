package com.example.jtriemstra.timeswitch;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;


/**
 * Created by JTriemstra on 12/23/2015.
 */
public class NotificationFactory {
    public static final int NOTIFICATION_ID = 3;

    public static void createOrUpdateNotification(Context objContext, String strCurrentlyWorkingOn, boolean blnNuanceInProgress){

        android.support.v7.app.NotificationCompat.Builder mBuilder = (android.support.v7.app.NotificationCompat.Builder)
                new android.support.v7.app.NotificationCompat.Builder(objContext)
                        .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                        .setSmallIcon(R.drawable.ic_action_time)
                        .setContentTitle("Working on:")
                        .setContentText(strCurrentlyWorkingOn)
                        .setOngoing(true)
                ;

        if (blnNuanceInProgress){
            PendingIntent objClickIntentNuanceStop = PendingIntent.getService(objContext, 0, (new Intent(objContext, NotificationActionServiceNuance.class)).setAction(NotificationActionServiceNuance.STOP), PendingIntent.FLAG_UPDATE_CURRENT);

            mBuilder.addAction(R.drawable.ic_action_done, "Stop", objClickIntentNuanceStop)
                    .setStyle(new NotificationCompat.MediaStyle()
                    .setShowActionsInCompactView(0));
        }
        else {
            //PendingIntent objClickIntentNuance = PendingIntent.getService(objContext, 0, (new Intent(objContext, NotificationActionServiceNuance.class)).setAction(NotificationActionServiceNuance.START), PendingIntent.FLAG_UPDATE_CURRENT);
            PendingIntent objClickIntentGoogle = PendingIntent.getService(objContext, 0, new Intent(objContext, NotificationActionService.class), PendingIntent.FLAG_UPDATE_CURRENT);
            PendingIntent objClickIntentMenu = PendingIntent.getService(objContext, 0, new Intent(objContext, NotificationActionServiceMenu.class), PendingIntent.FLAG_UPDATE_CURRENT);

            mBuilder.addAction(R.drawable.ic_action_overflow, "Start", objClickIntentMenu)
                    .addAction(R.drawable.ic_action_microphone_g, "Start", objClickIntentGoogle)
                    .setStyle(new NotificationCompat.MediaStyle()
                            .setShowActionsInCompactView(0, 1));
        }



        NotificationManager mNotificationManager =
                (NotificationManager) objContext.getSystemService(Context.NOTIFICATION_SERVICE);

        // mId allows you to update the notification later on.
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }

    public static void cancelNotification(Context objContext){

        NotificationManager mNotificationManager =
                (NotificationManager) objContext.getSystemService(Context.NOTIFICATION_SERVICE);

        // mId allows you to update the notification later on.
        mNotificationManager.cancel(NOTIFICATION_ID);
    }
}
