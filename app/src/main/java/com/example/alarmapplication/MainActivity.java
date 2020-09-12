package com.example.alarmapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button buttonDatePicker;
    private Button buttonTimePicker;
    Button buttonSet;
    TextView textViewDate;
    TextView textViewTime;

    private AlarmManager alarmManager;
    private PendingIntent alarmIntent;

    private int year, month, day, hour, minute;
    private int alarmYear, alarmMonth, alarmDay, alarmHour, alarmMinute;
    private int n = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<Integer> alarms = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            alarms.add(i);
        }

        RecyclerView recyclerView = findViewById(R.id.recycler);
        recyclerView.setAdapter(new AlarmAdapter(alarms));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

//        buttonDatePicker = findViewById(R.id.buttonDatePick);
//        buttonTimePicker = findViewById(R.id.buttonTimePicker);
//        buttonSet = findViewById(R.id.buttonSet);
//        textViewDate = findViewById(R.id.textViewDate);
//        textViewTime = findViewById(R.id.textViewTime);
//
//        buttonDatePicker.setOnClickListener(this);
//        buttonTimePicker.setOnClickListener(this);
//        buttonSet.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View v)
    {

        if(v == buttonDatePicker)
        {
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(android.widget.DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        textViewDate.setText(dayOfMonth+"-"+monthOfYear+"-"+year);
                        alarmMonth = monthOfYear;
                        alarmYear = year;
                        alarmDay = dayOfMonth;
                    }
                }, year, month, day);
        datePickerDialog.show();
        }

        if(v == buttonTimePicker)
        {
            Calendar calendar = Calendar.getInstance();
            hour = calendar.get(Calendar.HOUR_OF_DAY);
            minute = calendar.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(android.widget.TimePicker view, int hourOfDay, int minute) {
                            textViewTime.setText(hourOfDay+":"+minute);
                            alarmHour = hourOfDay;
                            alarmMinute = minute;
                        }
                    }, hour, minute, false);
            timePickerDialog.show();
        }

        if (v == buttonSet)
        {
            if (textViewTime.getText().toString().isEmpty())
            {
                textViewTime.setError("Please specify the time");
            }
            else {
                setAlarm(alarmYear, alarmMonth, alarmDay, alarmHour, alarmMinute);
            }
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void setAlarm(int year, int month, int day, int hour, int minute)
    {
        Context context = getApplicationContext();

        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, BootReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE,  minute);

        if (android.os.Build.VERSION.SDK_INT >= 23) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis() - (calendar.getTimeInMillis() % 60000), alarmIntent);
        }
        else if (android.os.Build.VERSION.SDK_INT >= 19) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis() - (calendar.getTimeInMillis() % 60000), alarmIntent);
        }
        else
        {
            alarmManager.set(AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis() - (calendar.getTimeInMillis() % 60000), alarmIntent);
        }

        Toast.makeText(getApplicationContext(), "Alarm set for - "+day+"/"+month+"/"+year+"  "+hour+":"+minute,
            Toast.LENGTH_LONG).show();
    }
}