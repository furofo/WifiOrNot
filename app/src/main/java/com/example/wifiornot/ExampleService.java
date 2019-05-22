package com.example.wifiornot;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import static com.example.wifiornot.App.CHANNEL_ID;

public class ExampleService extends Service {
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID).setContentTitle
                ("example serivce").setContentTitle("example service")
                .setContentText("hello")
                .setSmallIcon(R.drawable.ic_android)
                .build();


        startForeground(1, notification);
        return START_NOT_STICKY;

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
