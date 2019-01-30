package com.yifan.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = this.getSharedPreferences("com.yifan.sharedpreferences", Context.MODE_PRIVATE);

        // save a string permanently

        // the 1st parameter is the name of the string, rather than the string itself
        // the 2nd parameter is the real string that we want to save
        sharedPreferences.edit().putString("username", "nick").apply(); // put a string into sharedPreferences permanently
        // the next time we open this app, it's ok to comment out the line above, and we can still get "nick" in Logcat
        // the 2nd parameter is the default value in case cannot find a result
        String username = sharedPreferences.getString("username", ""); // get a string with name "username" from sharedPreferences
        Log.i("username", username);



        // save an array permanently
//        ArrayList<String> friends = new ArrayList<>();
//        friends.add("Fido");
//        friends.add("Sean");
//        friends.add("Sara");
//        // we cannot directly save an array, so we have to serialize it first
//        try {
//            sharedPreferences.edit().putString("friends", ObjectSerializer.serialize(friends)).apply();
//            Log.i("friends", ObjectSerializer.serialize(friends));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        ArrayList<String> newFriends = new ArrayList<>();
        try {
            newFriends = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("friends", ObjectSerializer.serialize(new ArrayList<String>())));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.i("new friends", newFriends.toString());
    }
}
