package com.yifan.numbershapes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    class Number {
        int number;
        public boolean isTriangular() {
            int x = 1;
            int triangularNumber = 1;
            while (triangularNumber < number) {
                x++;
                triangularNumber += x;
            }
            return triangularNumber == number;
        }
        public boolean isSquare() {
            double squareRoot = Math.sqrt(number);
            return squareRoot == Math.floor(squareRoot);
        }
    }

    public void judge(View view) {
        EditText inputNum = (EditText) findViewById(R.id.inputNum);
        String text;
        if (inputNum.getText().toString().isEmpty()) {// make sure that the string is not empty
            text = "Please enter a number first";
        } else {
            Number myNumber = new Number();
            myNumber.number = Integer.parseInt(inputNum.getText().toString());
            boolean isTriangular = myNumber.isTriangular();
            boolean isSquare = myNumber.isSquare();
            if (isTriangular && isSquare) {
                text = "Is both triangular and square number";
            } else if (isTriangular) {
                text = "Is triangular number";
            } else if (isSquare) {
                text = "Is square number";
            } else {
                text = "Neither";
            }
        }
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
