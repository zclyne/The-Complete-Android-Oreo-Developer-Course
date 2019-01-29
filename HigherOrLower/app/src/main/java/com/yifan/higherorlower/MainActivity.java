package com.yifan.higherorlower;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public int num;

    public void guessNum(View view) {
        EditText guessRes = (EditText) findViewById(R.id.guessRes);
        int inputNum = Integer.parseInt(guessRes.getText().toString());
        String text;
        if (inputNum > num) {
            text = "Too high";
        } else if (inputNum < num) {
            text = "Too low";
        } else {
            text = "Right answer";
        }
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
        Log.i("btn", "btn clicked, the text is " + text);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        num = (int) (Math.random() * 20 + 1);
    }
}
