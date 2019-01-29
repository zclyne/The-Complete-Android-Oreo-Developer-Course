package com.yifan.currencyconverter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public void convert(View view) {
        EditText money = findViewById(R.id.money);
        double money_dollar = Double.parseDouble(money.getText().toString());
        double money_yuan = money_dollar * 7;
        Toast.makeText(getApplicationContext(), "￥" + String.format("%.2f", money_yuan), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
