package com.yifan.multipleactivitydemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    public void goToNext(View view) {
        // move from the 1st activity to the 2nd activity
        Intent intent = new Intent(getApplicationContext(), SecondActivity.class); // move the the SecondActivity class, rather than an instance of this class
        // putExtra() allows user to pass information from one acitivity to another
        intent.putExtra("age", 20);
        startActivity(intent); // move
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
