package com.yifan.notesapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class NoteActivity extends AppCompatActivity {

    public EditText editNote;
    public int noteIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        editNote = findViewById(R.id.editNote);

        Intent intent = getIntent();
        noteIndex = intent.getIntExtra("noteIndex", -1);

        if (noteIndex != -1) { // edit an exiting note
            editNote.setText(MainActivity.notesArrayList.get(noteIndex));
        }

        // add on change listener
        editNote.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (noteIndex == -1) { // add a new note
                    MainActivity.notesArrayList.add("");
                    noteIndex = MainActivity.notesArrayList.size() - 1;
                }
                MainActivity.notesArrayList.set(noteIndex, String.valueOf(charSequence));
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // save the note permanently
                try {
                    SharedPreferences sharedPreferences = getSharedPreferences("com.yifan.notesapp", Context.MODE_PRIVATE);
                    String notesListString = ObjectSerializer.serialize(MainActivity.notesArrayList);
                    sharedPreferences.edit().putString("notesArrayList", notesListString).apply();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                MainActivity.arrayAdapter.notifyDataSetChanged(); // update the ListView in MainActivity
            }
        });
    }
}
