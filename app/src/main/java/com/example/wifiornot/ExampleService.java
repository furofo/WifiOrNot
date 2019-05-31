package com.example.wifiornot;

import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.wifiornot.App.CHANNEL_ID;

public class ExampleService extends Service {
    private String notificationText = "You are not on Wifi";

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        displayForeground();
        Timer timer = new Timer();
        TimerTask task = new ExampleService.Helper();


        timer.schedule(task, 0, 1000);
        return START_NOT_STICKY;

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void displayForeground() {
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        RemoteViews collapsedView = new RemoteViews(getPackageName(),
                R.layout.notification_collapsed);

        if (mWifi.isConnected()) {
            // Do whatever
            notificationText = "You are on wifi";
            collapsedView.setTextColor(R.id.text_view_collapsed_1, Color.rgb(34,139,34));
            collapsedView.setTextViewText(R.id.text_view_collapsed_1, "You are on Wifi!");
        } else {
            collapsedView.setTextColor(R.id.text_view_collapsed_1, Color.rgb(255,0,0));
            collapsedView.setTextViewText(R.id.text_view_collapsed_1, "You are not on Wifi!");
            notificationText = "You are not on wifi";
        }


        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setCustomContentView(collapsedView)
                .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                .setSmallIcon(R.drawable.ic_android)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .build();


        startForeground(1, notification);
    }

    class Helper extends TimerTask {
        public int i = 0;

        public void run() {
            System.out.println("hello world");
            displayForeground();
        }

    }
}

