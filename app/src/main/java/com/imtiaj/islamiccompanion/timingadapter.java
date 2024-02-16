package com.imtiaj.islamiccompanion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class timingadapter extends RecyclerView.Adapter<timingadapter.MyViewHolder> {
    private Context context;
    private List<timing> TimingList;

    public timingadapter(Context context) {
        this.context = context;
        TimingList = new ArrayList<>();
    }

    public  void add(timing Timing){
        TimingList.add(Timing);
        notifyDataSetChanged();
    }

    public  void clear(){
        TimingList.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.design,parent,false);


        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        timing Timing=TimingList.get(position);
        holder.bindViews(Timing);
        if (Timing.isCurrentDate()) {
            // Apply a different style for the current date
            holder.itemView.setBackgroundResource(R.drawable.border);
            holder.itemView.post(new Runnable() {
                @Override
                public void run() {
                    holder.itemView.getParent().requestChildFocus(holder.itemView, holder.itemView);
                }
            });

        }
        else {

            holder.itemView.setBackgroundResource(R.drawable.border2);


        }
    }

    @Override
    public int getItemCount() {

        return TimingList.size();
    }

    public timing getItem(int position) {
        return TimingList.get(position);
    }

    public static class  MyViewHolder extends RecyclerView.ViewHolder{
        private TextView fajr,dohr,asr,magrib,isha,tahajjut,sunset,sunrise,imsak,ifter,weekday,hijri,navor,dohrRange,sunsetRange,date;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            fajr=itemView.findViewById(R.id.fajr1);
            dohr=itemView.findViewById(R.id.dohr1);
            asr=itemView.findViewById(R.id.asr1);
            magrib=itemView.findViewById(R.id.magrib1);
            isha=itemView.findViewById(R.id.isha1);
            tahajjut=itemView.findViewById(R.id.tahaj);
            sunset=itemView.findViewById(R.id.sunset);
            sunrise=itemView.findViewById(R.id.sunrise);
            imsak=itemView.findViewById(R.id.sehri);
            ifter=itemView.findViewById(R.id.ifter);
            weekday=itemView.findViewById(R.id.week);
            navor=itemView.findViewById(R.id.nvor);
            dohrRange=itemView.findViewById(R.id.ndupur);
            sunsetRange=itemView.findViewById(R.id.nsonda);
            hijri=itemView.findViewById(R.id.hijri);

            date=itemView.findViewById(R.id.cdate);



        }


        public void bindViews(timing timing) {
            fajr.setText(timing.getFajr());
            dohr.setText(timing.getDhuhr());
            asr.setText(timing.getAsr());
            magrib.setText(timing.getMagrib());
            isha.setText(timing.getIsha());
            tahajjut.setText(timing.getTahajjut());
            sunset.setText(timing.getSunset());
            sunrise.setText(timing.getSunrise());
            imsak.setText(timing.getImsak());
            ifter.setText(timing.getSunset());
            weekday.setText(timing.getWeekday());
            hijri.setText(timing.getHijri());
            navor.setText(timing.getNavor());
            dohrRange.setText(timing.getDohrRange());
            sunsetRange.setText(timing.getSunsetRange());
            date.setText(timing.getDate());


        }
    }

}