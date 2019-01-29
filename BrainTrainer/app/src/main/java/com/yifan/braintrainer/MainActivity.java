package com.yifan.braintrainer;

import android.os.CountDownTimer;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayout;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    public ConstraintLayout gameLayout;
    public Button goButton;
    public Button playAgainButton;
    public GridLayout gridLayout;
    public TextView sumTextView;
    public TextView resultTextView;
    public TextView scoreTextView;
    public TextView timerTextView;
    public ArrayList<Integer> answers = new ArrayList<>();
    public Button button0;
    public Button button1;
    public Button button2;
    public Button button3;
    public int a, b, curNumOfQuestion, numOfRightAnswer;
    public boolean gameOver = false;
    public CountDownTimer timer;

    public void start(View view) {
        goButton.setVisibility(View.INVISIBLE);
        gameLayout.setVisibility(View.VISIBLE);
        refreshQuestion();
        timer.start();
    }

    public void chooseAnswer(View view) {
        if (!gameOver) {
            Button tappedButton = (Button) view;
            int chosenAnswer = Integer.parseInt(tappedButton.getText().toString());
            if (chosenAnswer == a + b) { // right answer
                resultTextView.setText("Correct!");
                numOfRightAnswer++;
            } else { // wrong answer
                resultTextView.setText("Wrong:(");
            }
            curNumOfQuestion++;
            refreshScoreTextView(); // update score
            refreshQuestion(); // turn to the next answer
        }
    }

    public void playAgain(View view) {
        curNumOfQuestion = 0;
        numOfRightAnswer = 0;
        gameOver = false;
        refreshScoreTextView();
        refreshQuestion();
        playAgainButton.setVisibility(View.INVISIBLE);
        resultTextView.setText("");
        timer.start();
    }

    public void gameOver() {
        gameOver = true;
        resultTextView.setText("Game Over!");
        playAgainButton.setVisibility(View.VISIBLE);
    }

    public void refreshQuestion() {
        Random rand = new Random();
        a = rand.nextInt(21); // pick a random integer within [0, 20]
        b = rand.nextInt(21);
        sumTextView.setText(String.valueOf(a) + " + " + String.valueOf(b));
        int locationOfCorrectAnswer = rand.nextInt(4);
        answers.clear(); // clear the previous answers

        for (int i = 0; i < 4; i++) {
            if (i == locationOfCorrectAnswer) {
                answers.add(a + b);
            } else {
                int randAnswer = rand.nextInt(41);
                while (randAnswer == a + b) { // make sure that randAnswer != a + b
                    randAnswer = rand.nextInt(41);
                }
                answers.add(randAnswer);
            }
        }
        button0.setText(String.valueOf(answers.get(0)));
        button1.setText(String.valueOf(answers.get(1)));
        button2.setText(String.valueOf(answers.get(2)));
        button3.setText(String.valueOf(answers.get(3)));
    }

    public void refreshScoreTextView() {
        String text = String.valueOf(numOfRightAnswer) + "/" + String.valueOf(curNumOfQuestion);
        scoreTextView.setText(text);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameLayout = findViewById(R.id.gameLayout);
        goButton = findViewById(R.id.goButton);
        playAgainButton = findViewById(R.id.playAgainButton);
        gridLayout = findViewById(R.id.gridLayout);
        sumTextView = findViewById(R.id.sumTextView);
        resultTextView = findViewById(R.id.resultTextView);
        scoreTextView = findViewById(R.id.scoreTextView);
        timerTextView = findViewById(R.id.timerTextView);
        button0 = findViewById(R.id.button0);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);

        timer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerTextView.setText(String.valueOf(millisUntilFinished / 1000) + "s");
            }

            @Override
            public void onFinish() {
                timerTextView.setText("0s");
                gameOver();
            }
        };
    }
}
