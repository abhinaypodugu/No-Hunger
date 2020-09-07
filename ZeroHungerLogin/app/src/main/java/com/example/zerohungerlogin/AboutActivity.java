package com.example.zerohungerlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;

import java.util.Random;

public class AboutActivity extends AppCompatActivity {
TextView textview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

//        CountDownTimer cd =new CountDownTimer() {
//            @Override
//            public void onTick(long l) {
//                Random random = new Random();
//                int mColor = 0xff000000 | random.nextInt(0xffffff);
//
//            }
//
//            @Override
//            public void onFinish() {
//
//            }
//        };
    }
}