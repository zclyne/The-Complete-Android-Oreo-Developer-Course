package com.yifan.showingandhidinguielements;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public void toggle(View view) {
        TextView text = findViewById(R.id.text);
        if (text.getVisibility() == View.VISIBLE) {
            text.setVisibility(View.INVISIBLE);
        } else {
            text.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
