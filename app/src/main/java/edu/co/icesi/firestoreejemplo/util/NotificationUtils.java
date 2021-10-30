package edu.co.icesi.firestoreejemplo.util;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import edu.co.icesi.firestoreejemplo.R;
import edu.co.icesi.firestoreejemplo.activity.HomeActivity;
import edu.co.icesi.firestoreejemplo.activity.MainActivity;
import edu.co.icesi.firestoreejemplo.app.AppMoviles;
import edu.co.icesi.firestoreejemplo.model.User;

public class NotificationUtils {

    private static final String CHANNEL_ID="messages";
    private static final String CHANNEL_NAME="Messages";

    private static int notificationID;


    public static void showNotification(String title, String message, PendingIntent pendingIntent){
        NotificationManager ntManager = (NotificationManager) AppMoviles.getGlobalContext().getSystemService(Context.NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            ntManager.createNotificationChannel(channel);
        }



        NotificationCompat.Builder builder = new NotificationCompat.Builder(AppMoviles.getGlobalContext(), CHANNEL_ID)
                .setDefaults(Notification.DEFAULT_ALL)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.outline_verified_white_24dp)
                .setContentIntent(pendingIntent);

        Notification notification = builder.build();
        ntManager.notify(notificationID, notification);
        notificationID++;
    }

}
