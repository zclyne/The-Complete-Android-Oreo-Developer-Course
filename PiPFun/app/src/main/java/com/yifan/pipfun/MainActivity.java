package com.yifan.pipfun;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public void goPip(View view) { // go to picture in picture
        enterPictureInPictureMode();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onPictureInPictureModeChanged(boolean isInPictureInPictureMode, Configuration newConfig) { // this function is called when we change into or out of pip mode
        super.onPictureInPictureModeChanged(isInPictureInPictureMode, newConfig);

        TextView textView = findViewById(R.id.textView);
        Button pipButton = findViewById(R.id.pipButton);

        if (isInPictureInPictureMode) { // going into pip mode
            pipButton.setVisibility(View.INVISIBLE); // get rid of the button
            getSupportActionBar().hide(); // get rid of the action bar
            textView.setText("$10,043.53"); // change the text of textView
        } else { // going out of pip mode
            pipButton.setVisibility(View.VISIBLE);
            getSupportActionBar().show();
            textView.setText("Hello World!");// change the text of textView
        }
    }
}
