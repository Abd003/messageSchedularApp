package com.abdulrehman.schedulemessage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;

public class sendMessage extends AppCompatActivity {

    String sendNumber, sendMessage;
    private final int SEND_SMS_PERMISSION_REQUEST_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("Reached the Send Message Activity!");
        sendNumber = (String) getIntent().getExtras().getString("receiver");
        sendMessage = (String) getIntent().getExtras().getString("message");
        System.out.println("Send Message Activity Message is : "+sendMessage+" and Phone number is : "+sendNumber);
        if(sendNumber == null || sendNumber.length() == 0 || sendMessage == null || sendMessage.length() == 0){
            return;
        }
        if(!checkPermission(Manifest.permission.SEND_SMS)){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS},SEND_SMS_PERMISSION_REQUEST_CODE);
        }
        if(checkPermission(Manifest.permission.SEND_SMS)) {
            //MediaPlayer mediaPlayer = MediaPlayer.create(context, Settings.System.DEFAULT_RINGTONE_URI);
            //mediaPlayer.start();
            //SmsManager smsManager = SmsManager.getDefault();
            //smsManager.sendTextMessage(sendNumber, null, sendMessage, null, null);
            Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                vibrator.vibrate(1000);
            }
            Toast.makeText(sendMessage.this,"Message Sent!",Toast.LENGTH_LONG).show();
            System.out.println("Message Sent!");
        }
        else{
            Toast.makeText(sendMessage.this, "Permission Denied",Toast.LENGTH_SHORT).show();
        }
        finish();
    }

    public boolean checkPermission(String permission){
        int check = ContextCompat.checkSelfPermission(this, permission);
        return (check== PackageManager.PERMISSION_GRANTED);
    }
}