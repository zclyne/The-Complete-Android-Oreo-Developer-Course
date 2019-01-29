package com.yifan.timerdemo;

import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        final Handler handler = new Handler();
//
//        Runnable run = new Runnable() {
//            @Override
//            public void run() {
//                Log.i("Hey it's us", "A second passed by");
//
//                handler.postDelayed(this, 1000); // run this function every second
//            }
//        };
//
//        handler.post(run);

        new CountDownTimer(10000, 1000) { // run onTick() every second for 10 seconds
            public void onTick(long millisecondsUntilDone) {
                Log.i("seconds left!", String.valueOf(millisecondsUntilDone / 1000));
            }

            public void onFinish() { // this is run whenever the countdown timer is completely finished
                Log.i("We're done!", "No more countdown");
            }
        }.start();
    }
}
