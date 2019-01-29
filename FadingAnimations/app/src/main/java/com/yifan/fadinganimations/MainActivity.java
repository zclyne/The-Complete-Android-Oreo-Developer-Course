package com.yifan.fadinganimations;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    public void fade(View view) {
        ImageView bartImageView = (ImageView) findViewById(R.id.bartImageView);
//        ImageView homerImageView = (ImageView) findViewById(R.id.homerImageView);
//        bartImageView.animate().alpha(1 - bartImageView.getAlpha()).setDuration(2000);
//        homerImageView.animate().alpha(1 - homerImageView.getAlpha()).setDuration(2000);
//        bartImageView.animate().translationYBy(1000).setDuration(2000); // move it down by 1000dp
//        bartImageView.animate().translationXBy(-1000).setDuration(2000); // move it left by 1000dp
//        bartImageView.animate().rotation(180).setDuration(2000); // rotate the image by 180 degrees clockwise
//        bartImageView.animate().rotation(1800).alpha(0).setDuration(2000); // rotate the image 10 times clockwise and fade to transparent
        bartImageView.animate().scaleX(0.5f).scaleY(0.5f).setDuration(2000); // shrink the image by half
        // scaleX and scaleY accepts float rather than double, so we need to put an 'f' after the number 0.5 to represent a float
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView bartImageView = (ImageView) findViewById(R.id.bartImageView);
        bartImageView.setX(-1000);
        bartImageView.animate().translationXBy(1000).rotation(3600).setDuration(1000);
    }
}
