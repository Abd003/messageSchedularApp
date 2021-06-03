package com.abdulrehman.schedulemessage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;

public class MyRvAdapter extends RecyclerView.Adapter<MyRvAdapter.MyViewHolder> {
    List<Message> ls;
    Context c;

    public MyRvAdapter(List<Message> ls, Context c) {
        this.ls = ls;
        this.c = c;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemrow= LayoutInflater.from(c).inflate(R.layout.row,parent,false);
        return new  MyViewHolder(itemrow);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.message.setText(ls.get(position).getMessage());
        holder.receiver.setText(ls.get(position).getReceiver());
        Calendar cal1 = Calendar.getInstance();
        int yr = Integer.parseInt(ls.get(position).getYear());
        int mn = Integer.parseInt(ls.get(position).getMonth());
        int dy = Integer.parseInt(ls.get(position).getDay());
        int hr = Integer.parseInt(ls.get(position).getHour());
        int mt = Integer.parseInt(ls.get(position).getMinute());
        cal1.set(Calendar.YEAR, yr);
        cal1.set(Calendar.MONTH, mn);
        cal1.set(Calendar.DAY_OF_MONTH,dy);
        cal1.set(Calendar.HOUR,hr);
        cal1.set(Calendar.MINUTE,mt);
        cal1.set(Calendar.SECOND,0);
        System.out.println("RVAdapter Time = " + dy+"-"+mn+"-"+yr+"-"+hr+"-"+mt);
        String timeHere;
        if(hr>12){
            timeHere = checkDigit(hr-12)+":"+checkDigit(mt)+" PM";
        }
        else{
            timeHere = checkDigit(hr)+":"+checkDigit(mt)+" AM";
        }
        System.out.println("RVAdapter Time = " +timeHere);
        holder.setSendDate.setText(new android.text.format.DateFormat().format("MMM dd, yyyy", cal1));
        holder.setSendTime.setText(timeHere);
        holder.linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                MyDBHelper myDBHelper=new MyDBHelper(c);
                SQLiteDatabase database=myDBHelper.getReadableDatabase();
                database.delete(MyMessageContract.Messages.TABLENAME,MyMessageContract.Messages._ID+"= ?",new String[]{ls.get(position).getMessageID()});
                database.close();
                myDBHelper.close();
                ls.remove(position);
                notifyDataSetChanged();
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return ls.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView message, receiver, setSendDate,setSendTime;
        LinearLayout linearLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.message);
            receiver = itemView.findViewById(R.id.receiver);
            setSendDate = itemView.findViewById(R.id.setSendDate);
            setSendTime = itemView.findViewById(R.id.setSendTime);
            linearLayout = itemView.findViewById(R.id.ll);
        }
    }
    public String checkDigit(int number) {
        return number <= 9 ? "0" + number : String.valueOf(number);
    }
}
