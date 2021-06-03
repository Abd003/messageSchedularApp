package com.abdulrehman.schedulemessage;

import android.provider.BaseColumns;

public class MyMessageContract {
    private MyMessageContract(){

    }
    public static int DB_VERSION=1;
    public static String DB_NAME="mymessagesdb.db";

    public static class Messages implements BaseColumns {
        public static String TABLENAME="messages";
        public static String _MESSAGE="message";
        public static String _RECEIVER="receiver";
        public static String _DAY="day";
        public static String _MONTH="month";
        public static String _YEAR="year";
        public static String _HOUR="hour";
        public static String _MINUTE="minute";
        public static String _ISSENT="NO";
    }

}
