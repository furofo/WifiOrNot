package com.example.wifiornot;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.wifiornot.MainActivity;

public class StartActivityOnBootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if(Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            Intent i = new Intent(context, MainActivity.class); //whatever class i want to open
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }
    }
}
