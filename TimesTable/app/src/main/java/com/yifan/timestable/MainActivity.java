package com.yifan.timestable;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SeekBar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public SeekBar seekBar;
    public ListView timesTableListView;
    public ArrayList<String> timesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBar = findViewById(R.id.timeSeekBar);
        timesTableListView = findViewById(R.id.timesTableListView);
        timesList = new ArrayList<>();

        seekBar.setMax(20);
        seekBar.setProgress(1); // initially the seekBar starts at 1
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, timesList);
        timesTableListView.setAdapter(arrayAdapter);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int min = 1;
                int timesTableNumber;
                if (progress < min) {
                    timesTableNumber = min;
                    seekBar.setProgress(min);
                } else {
                    timesTableNumber = progress;
                }
                // refresh timesList
                timesList.clear();
                for (int i = 1; i < 11; i++) {
                    timesList.add(Integer.toString(i * timesTableNumber));
                }
                arrayAdapter.notifyDataSetChanged(); // this function is used to display the refreshed timesList on the listView
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
