package com.imtiaj.islamiccompanion.PreferenceUtil;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.imtiaj.islamiccompanion.R;

public class AlarmReceiver extends BroadcastReceiver {
    private Uri SOUND_URI;
    @Override
    public void onReceive(Context context, Intent intent) {
        // This method will be called when the alarm is triggered
        Log.d("com.imtiaj.islamiccompanion.AlarmReceiver", "Alarm triggered");

        // Retrieve dynamic content for the notification
        String title = "Your notification title";
        String content = "Your notification content";
        SOUND_URI = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.custom);

        // You can perform any action you want here, such as showing a notification
        showNotification(context, title, content);

    }


    private void showNotification(Context context, String title, String content) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "YourChannelId")
                .setSmallIcon(R.drawable.nicon)
                .setSound(SOUND_URI)
                .setContentTitle(title)
                .setContentText(content)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        // notificationId is a unique int for each notification that you must define
        int notificationId = 123;
        notificationManager.notify(notificationId, builder.build());
    }



}