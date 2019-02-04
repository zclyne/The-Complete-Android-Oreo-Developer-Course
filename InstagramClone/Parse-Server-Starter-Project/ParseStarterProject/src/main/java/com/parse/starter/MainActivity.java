/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnKeyListener {

    public boolean signUpModeActive = true; // sign up mode or login mode
    public TextView loginTextView;
    public EditText usernameEditText;
    public EditText passwordEditText;
    public ImageView logoImageView;
    public RelativeLayout backgroundLayout;

    public void showUserList() {
        Intent intent = new Intent(getApplicationContext(), UserListActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {

        // when the user presses "enter", login or sign up
        if (i == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN) { // is tells us what kind of key is pressed
            signUpClicked(view);
        }
        return false;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.loginTextView) { // if the loginTextView is clicked
            Button signUpButton = (Button) findViewById(R.id.signUpButton);
            if (signUpModeActive) { // go to login mode
                signUpModeActive = false;
                signUpButton.setText("Login");
                loginTextView.setText("or, Sign up");
            } else { // go to sign up mode
                signUpModeActive = true;
                signUpButton.setText("Sign Up");
                loginTextView.setText("or, Login");
            }
        } else if(view.getId() == R.id.backgroundLayout || view.getId() == R.id.logoImageView) { // logoImageView or backgroundLayout is clicked
            // hide keyboard
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    public void signUpClicked(View view) {
        if (usernameEditText.getText().toString().matches("") || passwordEditText.getText().toString().matches("")) {
            Toast.makeText(this, "A username and a password are required.", Toast.LENGTH_SHORT).show();
        } else {
            if (signUpModeActive) { // sign up
                ParseUser user = new ParseUser();
                user.setUsername(usernameEditText.getText().toString());
                user.setPassword(passwordEditText.getText().toString());
                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            Log.i("Sign Up", "Success");
                            showUserList();
                        } else {
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                });
            } else { // login
                ParseUser.logInInBackground(usernameEditText.getText().toString(), passwordEditText.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser parseUser, ParseException e) {
                        if (parseUser != null) { // login successfully
                            Log.i("Login", "ok!");
                            showUserList();
                        } else {
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Instagram Clone");

        loginTextView = (TextView) findViewById(R.id.loginTextView);
        usernameEditText = (EditText) findViewById(R.id.usernameEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        logoImageView = (ImageView) findViewById(R.id.logoImageView);
        backgroundLayout = (RelativeLayout) findViewById(R.id.backgroundLayout);

        loginTextView.setOnClickListener(this);

        passwordEditText.setOnKeyListener(this);

        logoImageView.setOnClickListener(this);
        backgroundLayout.setOnClickListener(this);

        if (ParseUser.getCurrentUser() != null) { // already logged in
            showUserList();
        }

        ParseAnalytics.trackAppOpenedInBackground(getIntent());
    }
}