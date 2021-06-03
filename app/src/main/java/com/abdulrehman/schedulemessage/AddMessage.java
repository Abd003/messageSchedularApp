package com.abdulrehman.schedulemessage;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.sql.Time;
import java.util.Calendar;

public class AddMessage extends AppCompatActivity {

    EditText message, receiver;
    TextView date, time;
    String sendDay, sendMonth, sendYear, sendMinute, sendHour;
    Button add, selectDate, selectTime;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_message);

        message = findViewById(R.id.message);
        receiver = findViewById(R.id.receiver);
        date = findViewById(R.id.viewDate);
        time = findViewById(R.id.viewTime);
        add = findViewById(R.id.add);
        selectDate = findViewById(R.id.selectDate);
        selectTime = findViewById(R.id.selectTime);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddMessage.this, MainActivity.class);
                intent.putExtra("message",message.getText().toString());
                intent.putExtra("receiver",receiver.getText().toString());
                intent.putExtra("day",sendDay);
                intent.putExtra("month",sendMonth);
                intent.putExtra("year",sendYear);
                intent.putExtra("hour",sendHour);
                intent.putExtra("minute",sendMinute);
                System.out.println("Add Message Time is : " + sendDay+"-"+sendMonth+"-"+sendYear+"-"+sendHour+"-"+sendMinute);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });

        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int yr = cal.get(Calendar.YEAR);
                int mt = cal.get(Calendar.MONTH);
                int dy = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddMessage.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        sendDay = String.valueOf(dayOfMonth);
                        sendMonth = String.valueOf(month);
                        sendYear = String.valueOf(year);

                        Calendar cal1 = Calendar.getInstance();
                        cal1.set(Calendar.YEAR, year);
                        cal1.set(Calendar.MONTH, month);
                        cal1.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                        date.setText(new android.text.format.DateFormat().format("MMM dd, yyyy", cal1));
                    }
                },yr,mt,dy);
                datePickerDialog.show();
            }
        });

        selectTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int hr = cal.get(Calendar.HOUR);
                int mn = cal.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(AddMessage.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        sendHour = String.valueOf(hourOfDay);
                        sendMinute = String.valueOf(minute);
                        if(hourOfDay > 12){
                            time.setText( "" + checkDigit(hourOfDay-12) + ":" + checkDigit(minute) + " PM");
                        }
                        else{
                            time.setText( "" + checkDigit(hourOfDay) + ":" + checkDigit(minute) + " AM");
                        }
                    }
                },hr,mn,false);
                timePickerDialog.show();
            }
        });
    }
    public String checkDigit(int number) {
        return number <= 9 ? "0" + number : String.valueOf(number);
    }
}