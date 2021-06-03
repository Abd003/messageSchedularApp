package com.abdulrehman.schedulemessage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayoutStates;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 1;
    RecyclerView rv;
    FloatingActionButton add;
    List<Message> messages;
    MyRvAdapter adapter;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        add = findViewById(R.id.floatingActionButton);
        rv =findViewById(R.id.recyclerView);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MainActivity.this,AddMessage.class),REQUEST_CODE);
            }
        });
        messages = new ArrayList<>();
        adapter = new MyRvAdapter(messages, this);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
        rv.setLayoutManager(lm);
        rv.setAdapter(adapter);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(REQUEST_CODE == 1){
            if(resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    //Toast.makeText(MainActivity.this, "Data Received", Toast.LENGTH_SHORT).show();
                    String message = (String) data.getExtras().getString("message");
                    String receiver = (String) data.getExtras().getString("receiver");
                    String sendDay = (String) data.getExtras().get("day");
                    String sendMonth = (String) data.getExtras().get("month");
                    String sendYear = (String) data.getExtras().get("year");
                    String sendHour = (String) data.getExtras().get("hour");
                    String sendMinute = (String) data.getExtras().get("minute");
                    //messages.add(new Message(message,receiver,sendDay,sendMonth,sendYear,sendHour,sendMinute));
                    MyDBHelper myDBHelper = new MyDBHelper(MainActivity.this);
                    SQLiteDatabase database =myDBHelper.getWritableDatabase();
                    ContentValues cv =new ContentValues();
                    cv.put(MyMessageContract.Messages._MESSAGE,message);
                    cv.put(MyMessageContract.Messages._RECEIVER,receiver);
                    cv.put(String.valueOf(MyMessageContract.Messages._DAY), sendDay);
                    cv.put(String.valueOf(MyMessageContract.Messages._MONTH),sendMonth);
                    cv.put(String.valueOf(MyMessageContract.Messages._YEAR),sendYear);
                    cv.put(String.valueOf(MyMessageContract.Messages._HOUR),sendHour);
                    cv.put(String.valueOf(MyMessageContract.Messages._MINUTE),sendMinute);
                    cv.put(String.valueOf(MyMessageContract.Messages._ISSENT),false);
                    double res=database.insert(MyMessageContract.Messages.TABLENAME,null,cv);
                    database.close();
                    myDBHelper.close();
                    System.out.println("Main Activity Time = " + sendDay+"-"+sendMonth+"-"+sendYear+"-"+sendHour+"-"+sendMinute);
                    checkTime(message,receiver,sendDay, sendMonth,sendYear,sendHour,sendMinute);
                    adapter.notifyDataSetChanged();
                }
            }
        }
    }

    private void checkTime(String message, String receiver, String sendDay, String sendMonth, String sendYear, String sendHour, String sendMinute) {
        Calendar calendar = Calendar.getInstance();
        int yr = Integer.parseInt(sendYear);
        int mn = Integer.parseInt(sendMonth);
        int dy = Integer.parseInt(sendDay);
        int hr = Integer.parseInt(sendHour);
        int mt = Integer.parseInt(sendMinute);
        calendar.set(yr,mn,dy,hr,mt,0);
        setAlarm(calendar.getTimeInMillis(), message, receiver);
    }

    private void setAlarm(long timeInMillis, String message, String receiver) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, ExampleJobService.class);
        intent.putExtra("message",message);
        intent.putExtra("receiver", receiver);
        final int id = (int) System.currentTimeMillis();
        System.out.println("Main Activity Message is : "+message+" and Phone number is : "+receiver);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,id,intent,PendingIntent.FLAG_ONE_SHOT);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP,timeInMillis,pendingIntent);
        Log.d("Main Activity", "Alarm is set");
    }

    private void getData() {
        MyDBHelper myDBHelper=new MyDBHelper(MainActivity.this);
        SQLiteDatabase database=myDBHelper.getReadableDatabase();
        String[] projection=new String[]{
                MyMessageContract.Messages._ID,
                MyMessageContract.Messages._MESSAGE,
                MyMessageContract.Messages._RECEIVER,
                MyMessageContract.Messages._DAY,
                MyMessageContract.Messages._MONTH,
                MyMessageContract.Messages._YEAR,
                MyMessageContract.Messages._HOUR,
                MyMessageContract.Messages._MINUTE,
                MyMessageContract.Messages._ISSENT,
        };
        Cursor c=database.query(MyMessageContract.Messages.TABLENAME,projection,null,null,null,null,null);
        messages.clear();
        while (c.moveToNext()){
            messages.add(
                    new Message(
                            c.getString(c.getColumnIndex(MyMessageContract.Messages._ID)),
                            c.getString(c.getColumnIndex(MyMessageContract.Messages._MESSAGE)),
                            c.getString(c.getColumnIndex(MyMessageContract.Messages._RECEIVER)),
                            c.getString(c.getColumnIndex(MyMessageContract.Messages._DAY)),
                            c.getString(c.getColumnIndex(MyMessageContract.Messages._MONTH)),
                            c.getString(c.getColumnIndex(MyMessageContract.Messages._YEAR)),
                            c.getString(c.getColumnIndex(MyMessageContract.Messages._HOUR)),
                            c.getString(c.getColumnIndex(MyMessageContract.Messages._MINUTE))
                    )
            );
        }
        adapter.notifyDataSetChanged();
    }
    @Override
    protected void onResume() {
        super.onResume();
        getData();
        //for(int i = 0;i<messages.size();i++) {
            //checkTime(messages.get(i).getMessage(),messages.get(i).getReceiver(),messages.get(i).getDay(), messages.get(i).getMonth(), messages.get(i).getYear(), messages.get(i).getHour(), messages.get(i).getMinute());
        //}
    }
}