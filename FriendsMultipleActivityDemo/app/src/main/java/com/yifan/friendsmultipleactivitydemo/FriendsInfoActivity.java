package com.yifan.friendsmultipleactivitydemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class FriendsInfoActivity extends AppCompatActivity {

    public void goBack(View view) {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_info);

        Intent intent = getIntent();
        String friendName = intent.getStringExtra("friendName");
        Toast.makeText(getApplicationContext(), friendName, Toast.LENGTH_SHORT).show();
    }
}
