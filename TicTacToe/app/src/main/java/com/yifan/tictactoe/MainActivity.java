package com.yifan.tictactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int activePlayer = 0; // 0 for yellow and 1 for red
    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2}; // 2 for empty
    int[][] winningPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};
    boolean gameOver = false;

    public void dropIn(View view) {
        ImageView counter = (ImageView) view;
        int tappedCounter = Integer.parseInt(counter.getTag().toString());
        if (!gameOver && gameState[tappedCounter] == 2) { // empty
            counter.setTranslationY(-1500);
            if (activePlayer == 0) {
                counter.setImageResource(R.drawable.yellow);
            } else {
                counter.setImageResource(R.drawable.red);
            }
            gameState[tappedCounter] = activePlayer;
            counter.animate().translationYBy(1500).setDuration(300);
            for (int[] winningPosition : winningPositions) { // check if the game is over
                if (gameState[winningPosition[0]] == gameState[winningPosition[1]] && gameState[winningPosition[0]] == gameState[winningPosition[2]] && gameState[winningPosition[0]] != 2) { // one player has won
                    String winner = activePlayer == 0 ? "Yellow" : "Red";
                    String winningText = winner + " has won!";
                    Toast.makeText(getApplicationContext(), winningText, Toast.LENGTH_SHORT).show();
                    gameOver = true; // the game is over
                    Button playAgainButton = (Button) findViewById(R.id.playAgainButton);
                    TextView winnerTextView = (TextView) findViewById(R.id.winnerTextView);
                    winnerTextView.setText(winningText);
                    winnerTextView.setVisibility(View.VISIBLE); // set the text to be visible
                    playAgainButton.setVisibility(View.VISIBLE); // set the button to be visible
                }
            }
            activePlayer = 1 - activePlayer;
        } else {
            Toast.makeText(getApplicationContext(), "This position has already been occupied, choose again!", Toast.LENGTH_SHORT).show();
        }
    }

    public void playAgain(View view) {
        Button playAgainButton = (Button) findViewById(R.id.playAgainButton);
        TextView winnerTextView = (TextView) findViewById(R.id.winnerTextView);
        winnerTextView.setVisibility(View.INVISIBLE); // set the text to be invisible
        playAgainButton.setVisibility(View.INVISIBLE); // set the button to be invisible
        android.support.v7.widget.GridLayout gridLayout = (android.support.v7.widget.GridLayout) findViewById(R.id.gridLayout); // don't use just GridLayout, otherwise the app will crash
        for (int i = 0; i < gridLayout.getChildCount(); i++) { // loop through all objects in the gridLayout
            ImageView counter = (ImageView) gridLayout.getChildAt(i); // get the ImageView inside the gridLayout // get the ImageView inside the gridLayout
            counter.setImageDrawable(null); // remove the red and yellow circle from the board
        }
        for (int i = 0; i < gameState.length; i++) { // reset gameState
            gameState[i] = 2;
        }
        gameOver = false;
        activePlayer = 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
