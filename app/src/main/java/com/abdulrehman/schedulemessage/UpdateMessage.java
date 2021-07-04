package com.abdulrehman.schedulemessage;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class UpdateMessage extends AppCompatActivity {

    EditText updateMessage, updateReceiver;
    TextView updateDateView, updateTimeView;
    String sendDay, sendMonth, sendYear, sendMinute, sendHour;
    Button updateButton, deleteButton, updateDateButton, updateTimeButton;
    Message obj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_message);
        System.out.println("Reached the Update Message Activity!");
        updateMessage = findViewById(R.id.update_message_show);
        updateReceiver = findViewById(R.id.update_receiver_show);
        updateDateView = findViewById(R.id.update_viewDate);
        updateTimeView = findViewById(R.id.update_viewTime);
        updateButton = findViewById(R.id.update_button);
        deleteButton = findViewById(R.id.delete_button);
        updateDateButton = findViewById(R.id.updateDate);
        updateTimeButton = findViewById(R.id.updateTime);
        obj = new Message((String) getIntent().getExtras().getString("messageId"),
                (String) getIntent().getExtras().getString("message"),
                (String) getIntent().getExtras().getString("receiver"),
                (String) getIntent().getExtras().getString("day"),
                (String) getIntent().getExtras().getString("month"),
                (String) getIntent().getExtras().getString("year"),
                (String) getIntent().getExtras().getString("hour"),
                (String) getIntent().getExtras().getString("minute"));
        updateMessage.setText(obj.getMessage());
        updateReceiver.setText(obj.getReceiver());
        setDateandTime(obj.getYear(), obj.getMonth(), obj.getDay(), obj.getHour(),obj.getMinute());

        updateDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int yr = cal.get(Calendar.YEAR);
                int mt = cal.get(Calendar.MONTH);
                int dy = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(UpdateMessage.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        sendDay = String.valueOf(dayOfMonth);
                        sendMonth = String.valueOf(month);
                        sendYear = String.valueOf(year);
                        obj.setYear(sendYear);
                        obj.setMonth(sendMonth);
                        obj.setDay(sendDay);
                        Calendar cal1 = Calendar.getInstance();
                        cal1.set(Calendar.YEAR, year);
                        cal1.set(Calendar.MONTH, month);
                        cal1.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                        updateDateView.setText(new android.text.format.DateFormat().format("MMM dd, yyyy", cal1));
                    }
                },yr,mt,dy);
                datePickerDialog.show();
            }
        });
        updateTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int hr = cal.get(Calendar.HOUR);
                int mn = cal.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(UpdateMessage.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        sendHour = String.valueOf(hourOfDay);
                        sendMinute = String.valueOf(minute);
                        obj.setHour(sendHour);
                        obj.setMinute(sendMinute);
                        if(hourOfDay > 12){
                            updateTimeView.setText( "" + checkDigit(hourOfDay-12) + ":" + checkDigit(minute) + " PM");
                        }
                        else{
                            updateTimeView.setText( "" + checkDigit(hourOfDay) + ":" + checkDigit(minute) + " AM");
                        }
                    }
                },hr,mn,false);
                timePickerDialog.show();
            }
        });
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDBHelper myDBHelper = new MyDBHelper(UpdateMessage.this);
                SQLiteDatabase database = myDBHelper.getWritableDatabase();
                ContentValues cv =new ContentValues();
                int id = Integer.parseInt(obj.getMessageID());
                cv.put(MyMessageContract.Messages._MESSAGE,updateMessage.getText().toString());
                cv.put(MyMessageContract.Messages._RECEIVER,updateReceiver.getText().toString());
                cv.put(MyMessageContract.Messages._YEAR,obj.getYear());
                cv.put(MyMessageContract.Messages._MONTH,obj.getMonth());
                cv.put(MyMessageContract.Messages._DAY,obj.getDay());
                cv.put(MyMessageContract.Messages._HOUR,obj.getHour());
                cv.put(MyMessageContract.Messages._MINUTE,obj.getMinute());
                database.update(MyMessageContract.Messages.TABLENAME, cv, MyMessageContract.Messages._ID+"= ?",new String[]{obj.getMessageID()});
                database.close();
                myDBHelper.close();
                finish();
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDBHelper myDBHelper = new MyDBHelper(UpdateMessage.this);
                SQLiteDatabase database = myDBHelper.getWritableDatabase();
                database.delete(MyMessageContract.Messages.TABLENAME, MyMessageContract.Messages._ID+"= ?", new String[]{obj.getMessageID()});
                database.close();
                myDBHelper.close();
                finish();
            }
        });
    }
    void setDateandTime(String yr, String mn, String dy, String hr, String mt){
        Calendar cal1 = Calendar.getInstance();
        int year = Integer.parseInt(yr);
        int month = Integer.parseInt(mn);
        int day = Integer.parseInt(dy);
        int hour = Integer.parseInt(hr);
        int minute = Integer.parseInt(mt);
        cal1.set(Calendar.YEAR, year);
        cal1.set(Calendar.MONTH, month);
        cal1.set(Calendar.DAY_OF_MONTH,day);
        cal1.set(Calendar.HOUR,hour);
        cal1.set(Calendar.MINUTE,minute);
        cal1.set(Calendar.SECOND,0);
        String timeHere;
        if(hour>12){
            timeHere = checkDigit(hour-12)+":"+checkDigit(minute)+" PM";
        }
        else{
            timeHere = checkDigit(hour)+":"+checkDigit(minute)+" AM";
        }
        updateDateView.setText(new android.text.format.DateFormat().format("MMM dd, yyyy", cal1));
        updateTimeView.setText(timeHere);
    }
    public String checkDigit(int number) {
        return number <= 9 ? "0" + number : String.valueOf(number);
    }
}