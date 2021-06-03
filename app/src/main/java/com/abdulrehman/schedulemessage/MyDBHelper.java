package com.abdulrehman.schedulemessage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyDBHelper extends SQLiteOpenHelper {
    String CREATE_MESSAGE_TABLE="CREATE TABLE "+
            MyMessageContract.Messages.TABLENAME+" ("+
            MyMessageContract.Messages._ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            MyMessageContract.Messages._MESSAGE +" TEXT NOT NULL, "+
            MyMessageContract.Messages._RECEIVER +" TEXT NOT NULL, "+
            MyMessageContract.Messages._DAY +" TEXT NOT NULL, "+
            MyMessageContract.Messages._MONTH +" TEXT NOT NULL, "+
            MyMessageContract.Messages._YEAR +" TEXT NOT NULL, "+
            MyMessageContract.Messages._HOUR +" TEXT NOT NULL, "+
            MyMessageContract.Messages._MINUTE +" TEXT NOT NULL, "+
            MyMessageContract.Messages._ISSENT +" TEXT NOT NULL);";
    String DROP_MESSAGE_TABLE="DROP TABLE IF EXISTS "+MyMessageContract.Messages.TABLENAME;
    public MyDBHelper(@Nullable Context context) {
        super(context, MyMessageContract.DB_NAME, null, MyMessageContract.DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_MESSAGE_TABLE);

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_MESSAGE_TABLE);
        onCreate(db);
    }
}
