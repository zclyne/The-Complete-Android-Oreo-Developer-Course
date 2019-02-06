package com.yifan.autofun;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] friends = {"Nick", "Rick", "Sara", "James", "Nina", "Noel", "Nire"};

        AutoCompleteTextView autoCompleteTextView = findViewById(R.id.autoCompleteTextView);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, friends);

        autoCompleteTextView.setAdapter(arrayAdapter);
        autoCompleteTextView.setThreshold(1); // we only need to type in one letter to get the candidates, and the default is 2

    }
}
