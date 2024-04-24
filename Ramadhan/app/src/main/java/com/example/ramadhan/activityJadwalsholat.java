package com.example.ramadhan;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import kotlin.Suppress;

public class activityJadwalsholat extends AppCompatActivity {
    private TextView textClock, textDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jadwalsholat);
        textClock = findViewById(R.id.clock);
        textDate = findViewById(R.id.date);

        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                Date date = new Date();
                @SuppressLint("SimpleDateFormat")
                DateFormat clockFormat = new SimpleDateFormat("HH:mm:ss");

                @SuppressLint("SimpleDateFormat")
                DateFormat dateFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy");

                textClock.setText(clockFormat.format(new Date()));
                textDate.setText(dateFormat.format(new Date()));

                handler.postDelayed(this,1000);
            }
        });
    }
}