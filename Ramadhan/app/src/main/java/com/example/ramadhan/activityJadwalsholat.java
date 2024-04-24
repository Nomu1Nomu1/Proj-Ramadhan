package com.example.ramadhan;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ramadhan.model.ModelJadwal;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import api.ApiService;
import api.ApiUrl;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class activityJadwalsholat extends AppCompatActivity {
    private TextView tv_lokasi_value, tv_fajr_value, tv_dhuhr_value, tv_asr_value, tv_magrib_value, tv_isha_value;

    private FloatingActionButton refresh;
    private ProgressDialog progressDialog;


    private TextView textClock, textDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jadwalsholat);
        textClock = findViewById(R.id.clock);
        textDate = findViewById(R.id.date);
        tv_lokasi_value = findViewById(R.id.tv_lokasi_value);
        tv_fajr_value = findViewById(R.id.tv_fajr_value);
        tv_dhuhr_value = findViewById(R.id.tv_dhuhr_value);
        tv_asr_value = findViewById(R.id.tv_asr_value);
        tv_magrib_value = findViewById(R.id.tv_magrib_value);
        tv_isha_value = findViewById(R.id.tv_isha_value);
        refresh = findViewById(R.id.refresh);

        getJadwal();

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getJadwal();

            }
        });




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
    private void getJadwal (){

        progressDialog = new ProgressDialog(activityJadwalsholat.this);
        progressDialog.setMessage("Loading");
        progressDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiUrl.URL_ROOT_HTTP)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        Call<ModelJadwal> call = apiService.getJadwal();

        call.enqueue(new Callback<ModelJadwal>() {
            @Override
            public void onResponse(Call<ModelJadwal> call, Response<ModelJadwal> response) {

                progressDialog.dismiss();

                if(response.isSuccessful()){
                    tv_lokasi_value.setText(response.body().getCity()+","+response.body().getItems().get(0).getDateFor());
                    tv_fajr_value.setText(response.body().getItems().get(0).getFajr());
                    tv_dhuhr_value.setText(response.body().getItems().get(0).getDhuhr());
                    tv_asr_value.setText(response.body().getItems().get(0).getAsr());
                    tv_magrib_value.setText(response.body().getItems().get(0).getMaghrib());
                    tv_isha_value.setText(response.body().getItems().get(0).getIsha());


                }
            }

            @Override
            public void onFailure(Call<ModelJadwal> call, Throwable t) {

                progressDialog.dismiss();
                Toast.makeText(activityJadwalsholat.this,"Coba Lagi",Toast.LENGTH_SHORT).show();

            }
        });
    }
}