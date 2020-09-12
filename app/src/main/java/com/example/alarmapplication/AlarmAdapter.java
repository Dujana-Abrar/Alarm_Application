package com.example.alarmapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.AlarmViewHolder>{

    private ArrayList<Integer> alarmData;

    public AlarmAdapter(ArrayList<Integer> arrayList) {
        alarmData = arrayList;
    }

    @NonNull
    @Override
    public AlarmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_alarm, parent, false);
        return new AlarmViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlarmViewHolder holder, int position) {
        ((TextView) holder.linearLayout.findViewById(R.id.dosageValue)).setText("" + alarmData.get(position));
    }

    @Override
    public int getItemCount() {
        return alarmData.size();
    }

    public class AlarmViewHolder extends RecyclerView.ViewHolder
    {
        LinearLayout linearLayout;
        public AlarmViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.timeCard);
        }
    }
}
