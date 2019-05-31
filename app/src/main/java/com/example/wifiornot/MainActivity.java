package com.example.wifiornot;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RemoteViews;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {
    private final String CHANNEL_ID = "personal_notifications";
    private final int NOTIFICATION_ID = 001;
    private String notficationText = "You are not on wifi";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        //displayNotification();
        startService();


        Timer timer = new Timer();
        TimerTask task = new Helper();
        Date date = new Date();


       // timer.schedule(task, 0, 1000); // THIS RUNS DISPLAY NOTIFICAITON EVERY SECOND

        System.out.println("Timer running");


    }

    public void startService() {
        Intent serviceIntent = new Intent (this, ExampleService.class);
        System.out.println("service started");
        startService(serviceIntent);
    }


    public void displayNotification() {
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        RemoteViews collapsedView = new RemoteViews(getPackageName(),
                R.layout.notification_collapsed);

        if (mWifi.isConnected()) {
            // Do whatever
            collapsedView.setTextColor(R.id.text_view_collapsed_1, Color.rgb(34,139,34));
            collapsedView.setTextViewText(R.id.text_view_collapsed_1, "You are on Wifi!");
            notficationText = "You are on wifi";
        } else {
            collapsedView.setTextColor(R.id.text_view_collapsed_1, Color.rgb(255,0,0));
            collapsedView.setTextViewText(R.id.text_view_collapsed_1, "You are not on Wifi!");
            notficationText = "You are not on wifi";
        }
        createNotificationChannel();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID);
        builder.setSmallIcon(R.drawable.ic_sms_notification);
        builder.setOngoing(true);
        builder.setCustomContentView(collapsedView);
        builder.setStyle(new NotificationCompat.DecoratedCustomViewStyle());
        builder.setVibrate(new long[]{0, 0});
        builder.setPriority(NotificationCompat.PRIORITY_LOW);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Personal Notification";
            String description = "Include all the personal notifications";
            int importance = NotificationManager.IMPORTANCE_LOW;

            NotificationChannel notficationChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            notficationChannel.enableVibration(false);

            notficationChannel.setDescription(description);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notficationChannel);
        }
    }

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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class Helper extends TimerTask {
        public int i = 0;

        public void run() {
            System.out.println("hello world");
            displayNotification();
        }
    }
}










