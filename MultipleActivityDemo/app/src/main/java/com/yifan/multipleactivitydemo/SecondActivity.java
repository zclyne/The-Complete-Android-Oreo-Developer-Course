package com.yifan.multipleactivitydemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {

    public void goBack(View view) {
        // not good method
//        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//        startActivity(intent);
        finish(); // this function also makes the user go to MainActivity
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        // get the information passed from MainActivity
        Intent intent = getIntent();
        int age = intent.getIntExtra("age", 0);
        Toast.makeText(getApplicationContext(), "Age is " + String.valueOf(age), Toast.LENGTH_SHORT).show();
    }
}
