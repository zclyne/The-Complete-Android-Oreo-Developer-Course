package com.yifan.eggtimer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public boolean isCounting = false;
    public CountDownTimer timer;
    public int countTime = 30; // initially the timer is set to 30 seconds;
    public TextView time;
    public MediaPlayer mediaPlayer;
    public Button startAndStop;
    public SeekBar timeSeekBar;

    public void toggleCount(View view) {
        if (!isCounting) { // start counting
            isCounting = true;
            timer = createTimer(countTime);
            timer.start();
            startAndStop.setText("stop");
            timeSeekBar.setEnabled(false);
        } else { // stop counting
            isCounting = false;
            timer.cancel(); // stop the timer
            countTime = 30;
            time.setText(getTimeString(countTime));
            startAndStop.setText("start");
            timeSeekBar.setEnabled(true);
            timeSeekBar.setProgress(countTime);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timeSeekBar = findViewById(R.id.timeSeekBar);
        time = findViewById(R.id.time);
        startAndStop = findViewById(R.id.startAndStop);
        mediaPlayer = MediaPlayer.create(this, R.raw.sound); // set the audio to play when the timer stops

        timeSeekBar.setMax(600); // max is 10 minutes
        timeSeekBar.setProgress(countTime);

        timeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (!isCounting) {
                    countTime = progress;
                    time.setText(getTimeString(countTime));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public String getTimeString(int timeInSecond) {
        int minute = timeInSecond / 60;
        int second = timeInSecond % 60;
        if (second >= 10) {
            return String.valueOf(minute) + " : " + String.valueOf(second);
        } else {
            return String.valueOf(minute) + " : 0" + String.valueOf(second);
        }
    }

    public CountDownTimer createTimer(int timeInSecond) {
        return new CountDownTimer(1000 * countTime, 1000) {
            public void onTick(long millisecondsUntilDone) {
                time.setText(getTimeString((int) millisecondsUntilDone / 1000));
            }

            public void onFinish() {
                mediaPlayer.start();
                isCounting = false;
                countTime = 30;
                time.setText(getTimeString(countTime));
                startAndStop.setText("start");
                timeSeekBar.setEnabled(true);
                timeSeekBar.setProgress(countTime);
            }
        };
    }
}
