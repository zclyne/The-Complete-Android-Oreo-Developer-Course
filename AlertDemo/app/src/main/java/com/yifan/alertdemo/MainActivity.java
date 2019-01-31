package com.yifan.alertdemo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public void setLanguage() {
        SharedPreferences sharedPreferences = this.getSharedPreferences("com.yifan.alertdemo", Context.MODE_PRIVATE);
        String language = sharedPreferences.getString("language", "english"); // default is english
        TextView textView = findViewById(R.id.textView);
        switch (language) {
            case "english":
                textView.setText("Hello World!");
                break;
            case "chinese":
                textView.setText("你好，世界！");
                break;
            default:
                textView.setText("Hello World!");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // connect to the menu
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        SharedPreferences sharedPreferences = this.getSharedPreferences("com.yifan.alertdemo", Context.MODE_PRIVATE);

        switch(item.getItemId()) {
            case R.id.english:
                sharedPreferences.edit().putString("language", "english").apply();
                Toast.makeText(MainActivity.this, "Successfully set the language to English.", Toast.LENGTH_SHORT).show();
                break;
            case R.id.chinese:
                sharedPreferences.edit().putString("language", "chinese").apply();
                Toast.makeText(MainActivity.this, "Successfully set the language to Chinese.", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        setLanguage();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final SharedPreferences sharedPreferences = this.getSharedPreferences("com.yifan.alertdemo", Context.MODE_PRIVATE);

        String language = sharedPreferences.getString("language", "");

        if (language.equals("")) { // using the app for the first time
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Choose Language")
                    .setMessage("Which language do you prefer?")
                    .setPositiveButton("English", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            sharedPreferences.edit().putString("language", "english").apply();
                            Toast.makeText(MainActivity.this, "Successfully set the language to English.", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("Chinese", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            sharedPreferences.edit().putString("language", "chinese").apply();
                            Toast.makeText(MainActivity.this, "Successfully set the language to Chinese.", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .show();
        }

        setLanguage();
    }
}
