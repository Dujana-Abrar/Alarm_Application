package com.example.alarmapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        WakeLocker.acquire(context);
        Intent intent1 = new Intent(context, AlarmReciever.class);
        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent1);
        Toast.makeText(context, "Alarm is ringing!!", Toast.LENGTH_LONG).show();
        WakeLocker.release();
    }
}
