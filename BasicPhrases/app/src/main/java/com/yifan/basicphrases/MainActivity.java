package com.yifan.basicphrases;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;

public class MainActivity extends AppCompatActivity {

    public MediaPlayer mediaPlayer;

    public void speak(View view) {
        Button btnTapped = (Button) view;
        int tag = Integer.parseInt(btnTapped.getTag().toString());
        if (tag == 0)
            mediaPlayer = MediaPlayer.create(this, R.raw.doyouspeakenglish);
        else if (tag == 1)
            mediaPlayer = MediaPlayer.create(this, R.raw.goodevening);
        else if (tag == 2)
            mediaPlayer = MediaPlayer.create(this, R.raw.hello);
        else if (tag == 3)
            mediaPlayer = MediaPlayer.create(this, R.raw.howareyou);
        else if (tag == 4)
            mediaPlayer = MediaPlayer.create(this, R.raw.ilivein);
        else if (tag == 5)
            mediaPlayer = MediaPlayer.create(this, R.raw.mynameis);
        else if (tag == 6)
            mediaPlayer = MediaPlayer.create(this, R.raw.please);
        else
            mediaPlayer = MediaPlayer.create(this, R.raw.welcome);
        mediaPlayer.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
