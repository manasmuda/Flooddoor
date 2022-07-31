package com.msm.floodoor;

import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyNotificationServices extends FirebaseMessagingService {

    private int notificationId=0;

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.i("abcde  dtdty",remoteMessage.getMessageId());

        notificationId++;

        Intent intent=new Intent(getApplicationContext(), WarningActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("river",remoteMessage.getData().get("river"));
        intent.putExtra("month",remoteMessage.getData().get("month"));
        //Log.i("abcde  dtdty",new String(remoteMessage.getRawData()));
        //Log.i("abcde",remoteMessage.getData().get("message"));
        PendingIntent pendingIntent = PendingIntent.getActivity(this,notificationId,intent,PendingIntent.FLAG_ONE_SHOT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"EA")
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setSmallIcon(R.drawable.icon_circle)
                .setAutoCancel(true)
                .setContentTitle("Floods Alert")
                .setContentIntent(pendingIntent)
                .setContentText(remoteMessage.getData().get("message"));


        NotificationManagerCompat notificationManagerCompat= NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(notificationId,builder.build());
    }
}
