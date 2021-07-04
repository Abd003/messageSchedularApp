package com.abdulrehman.schedulemessage;

import androidx.annotation.Nullable;

public class Message {
    private String messageID, message, receiver;
    private String day, month, year;
    private String hour, minute;
    private String isSent;

    public Message(String ID, String message, String receiver, String day, String month, String year, String hour, String minute) {
        this.messageID = ID;
        this.message = message;
        this.receiver = receiver;
        this.day = day;
        this.month = month;
        this.year = year;
        this.hour = hour;
        this.minute = minute;
        this.isSent = "NO";
    }

    public String getMessageID() { return messageID; }

    public void setMessageID(String messageID) { this.messageID = messageID; }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getDay() { return day; }

    public void setDay(String day) { this.day = day; }

    public String getMonth() { return month; }

    public void setMonth(String month) { this.month = month; }

    public String getYear() { return year; }

    public void setYear(String year) { this.year = year; }

    public String getHour() { return hour; }

    public void setHour(String hour) { this.hour = hour; }

    public String getMinute() { return minute; }

    public void setMinute(String minute) { this.minute = minute; }

    public String getIsSent() { return isSent; }

    public void setIsSent(String isSent) { this.isSent = isSent; }

    @Override
    public String toString() {
        return "Message{" +
                "messageID='" + messageID + '\'' +
                ", message='" + message + '\'' +
                ", receiver='" + receiver + '\'' +
                ", day='" + day + '\'' +
                ", month='" + month + '\'' +
                ", year='" + year + '\'' +
                ", hour='" + hour + '\'' +
                ", minute='" + minute + '\'' +
                ", isSent='" + isSent + '\'' +
                '}';
    }

    public void copyMessage(Message obj){
        this.setMessage(obj.getMessage());
        this.setReceiver(obj.getReceiver());
        this.setYear(obj.getYear());
        this.setMonth(obj.getMonth());
        this.setDay(obj.getDay());
        this.setHour(obj.getHour());
        this.setMinute(obj.getMinute());
    }
}
