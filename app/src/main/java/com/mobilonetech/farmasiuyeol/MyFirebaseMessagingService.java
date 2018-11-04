package com.mobilonetech.farmasiuyeol;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat.Builder;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMsgService";

    public void onMessageReceived(RemoteMessage remoteMessage) {
        String url = (String) remoteMessage.getData().get("URL");
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Content of Meessage: " + remoteMessage.getData());
            sendNotification("Farmasilove.com", "" + remoteMessage.getData(), url);
        }
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Head of Meessage: " + remoteMessage.getNotification().getTitle() + " " + "Content of Meessage: " + remoteMessage.getNotification().getBody());
            sendNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody(), url);
        }
    }

    private void sendNotification(String title, String body, String url) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("url", url);
        intent.putExtra("title", title);
        intent.putExtra("from", "Note");
        Builder notificationBuilder = new Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setVibrate(new long[]{500, 500, 500, 500})
                .setContentIntent(PendingIntent
                        .getActivity(this,
                                0,
                                intent,
                                PendingIntent.FLAG_ONE_SHOT));
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        try {
            RingtoneManager.getRingtone(getApplicationContext(),
                    RingtoneManager.getDefaultUri(2)).play();
        } catch (Exception e) {
            e.printStackTrace();
        }
        notificationManager.notify(0, notificationBuilder.build());
    }
}
