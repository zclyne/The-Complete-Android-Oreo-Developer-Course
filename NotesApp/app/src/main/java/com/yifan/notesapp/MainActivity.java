package com.yifan.notesapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public ListView notesListView;
    static public ArrayList<String> notesArrayList;
    static public ArrayAdapter<String> arrayAdapter;

    // add menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.addNote) {
            // switch to the second Activity
            Intent intent = new Intent(getApplicationContext(), NoteActivity.class);
            startActivity(intent);
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notesListView = findViewById(R.id.notesListView);

        SharedPreferences sharedPreferences = getSharedPreferences("com.yifan.notesapp", Context.MODE_PRIVATE);

        // get notesArrayList
        try {
            notesArrayList = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("notesArrayList", ObjectSerializer.serialize(new ArrayList<String>())));
        } catch(Exception e) {
            e.printStackTrace();
        }

        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, notesArrayList);
        notesListView.setAdapter(arrayAdapter);

        // short click, edit note
        notesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), NoteActivity.class);
                intent.putExtra("noteIndex", i);
                startActivity(intent);
            }
        });

        // long click, delete note
        notesListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int noteIndexToDelete = i;
                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Confirm")
                        .setMessage("Do you really want to delete this note?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                notesArrayList.remove(noteIndexToDelete);
                                arrayAdapter.notifyDataSetChanged();
                                // save the note permanently
                                try {
                                    SharedPreferences sharedPreferences = getSharedPreferences("com.yifan.notesapp", Context.MODE_PRIVATE);
                                    String notesListString = ObjectSerializer.serialize(MainActivity.notesArrayList);
                                    sharedPreferences.edit().putString("notesArrayList", notesListString).apply();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                Toast.makeText(getApplicationContext(), "Successfully deleted the note.", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("NO", null)
                        .show();
                return true; // return value here means whether or not you should allow this long press to happen
            }
        });
    }
}
