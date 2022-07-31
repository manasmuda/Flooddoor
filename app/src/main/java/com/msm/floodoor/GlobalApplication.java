package com.msm.floodoor;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;

public class GlobalApplication extends Application {

    public static AWSHelper awsHelper;

    @Override
    public void onCreate() {
        super.onCreate();
        awsHelper=AWSHelper.createInstance(this);
        FirebaseApp.initializeApp(this);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            String channelName = "Emergency Alert";
            String channelDescription = "Notifications on flood alerts";
            String CHANEL_ID = "EA";

            NotificationChannel notificationChannel = new NotificationChannel(CHANEL_ID,channelName, NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setDescription(channelDescription);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }
}
