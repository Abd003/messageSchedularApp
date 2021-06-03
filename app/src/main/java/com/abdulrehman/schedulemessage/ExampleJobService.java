package com.abdulrehman.schedulemessage;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class ExampleJobService extends BroadcastReceiver {
    private static final String TAG = "ExampleJobService";

    @Override
    public void onReceive(Context context, Intent intent) {
        String phoneNumber = (String) intent.getExtras().getString("receiver");
        String smsMessage = (String) intent.getExtras().getString("message");
        System.out.println("Alarm Manager Message is : "+smsMessage+" and Phone number is : "+phoneNumber);
        Intent intent1 = new Intent();
        intent1.setClass(context,sendMessage.class);
        intent1.putExtra("message",smsMessage);
        intent1.putExtra("receiver",phoneNumber);
        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent1);
        return;
    }
}
