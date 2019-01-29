package com.yifan.demoapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public void clickFunction(View view) {
        Log.i("Info", "Button pressed!");
    }

    public void click2(View view) {
        EditText myTextField = (EditText) findViewById(R.id.myTextField); // findViewById returns a view, and we need to convert it into EditText
        Log.i("btn2", myTextField.getText().toString());
    }

    public void login(View view) {
        EditText username = (EditText) findViewById(R.id.username);
        EditText password = (EditText) findViewById(R.id.password);
        Log.i("userInfo", "The username is: " + username.getText().toString() + "; the password is " + password.getText().toString());
        Toast.makeText(this, "Hi there, " + username.getText().toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
