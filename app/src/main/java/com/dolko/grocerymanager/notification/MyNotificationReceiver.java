package com.dolko.grocerymanager.notification;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.dolko.grocerymanager.R;

public class MyNotificationReceiver extends BroadcastReceiver {
    private static final String CHANNEL_ID = "MyNotificationChannel";

    @Override
    public void onReceive(Context context, Intent intent) {
        // Get the title and text of the notification from the intent extras
        String title = intent.getStringExtra("notification_title");
        String text = intent.getStringExtra("notification_text");

        // Create a notification builder
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher_adaptive_fore)
                .setContentTitle(title)
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        // Show the notification
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {return;}
        notificationManager.notify(0, builder.build());
    }
}

