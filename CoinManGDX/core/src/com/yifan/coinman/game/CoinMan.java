package com.yifan.coinman.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.Random;

public class CoinMan extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background; // Texture is a way to add picture to the app
    Texture[] man;
    int manState = 0; // this variable is used to make the man look like that he is running
    int pause = 0; // this variable is used to give pause to our game
    float gravity = 0.2f;
    float velocity = 0;
    int manY = 0; // the man's y position
    Rectangle manRectangle;
    ArrayList<Integer> coinXs = new ArrayList<Integer>();
    ArrayList<Integer> coinYs = new ArrayList<Integer>();
    ArrayList<Rectangle> coinRectangles = new ArrayList<Rectangle>();
    Texture coin;
    int coinCount;
    ArrayList<Integer> bombXs = new ArrayList<Integer>();
    ArrayList<Integer> bombYs = new ArrayList<Integer>();
    ArrayList<Rectangle> bombRectangles = new ArrayList<Rectangle>();
    Texture bomb;
    int bombCount;
    Random random;
    int score = 0;
    BitmapFont font; // this is used to show the score on the screen
    int gameState = 0; // to represent what state the game is in
    Texture dizzy;

	@Override
	public void create () { // this function is called when we set things up for the first time
		batch = new SpriteBatch(); // draw everything to the screen
        background = new Texture("bg.png");
        man = new Texture[4]; // we have 4 images of the man altogether
        man[0] = new Texture("frame-1.png");
        man[1] = new Texture("frame-2.png");
        man[2] = new Texture("frame-3.png");
        man[3] = new Texture("frame-4.png");
        // set the man's initial Y position
        manY = Gdx.graphics.getHeight() / 2;
        // setup the coins and bombs
        coin = new Texture("coin.png");
        bomb = new Texture("bomb.png");
        random = new Random();
        // setup BitmapFont
        font = new BitmapFont();
        font.setColor(Color.WHITE);
        font.getData().setScale(10); // set the size of this BitmapFont
        dizzy = new Texture("dizzy-1.png");
	}

	public void makeCoin() {
	    float height = random.nextFloat() * Gdx.graphics.getHeight();
	    coinYs.add((int) height);
	    coinXs.add(Gdx.graphics.getWidth());
    }

    public void makeBomb() {
	    float height = random.nextFloat() * Gdx.graphics.getHeight();
	    bombYs.add((int) height);
	    bombXs.add(Gdx.graphics.getWidth());
    }

	@Override
	public void render () { // this function is called over and over until we close the app
        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); // starts at (0, 0) and takes the full width and height

        if (gameState == 1) {
            // game is live

            // setup coins
            if (coinCount < 100) {
                coinCount++;
            } else {
                coinCount = 0;
                makeCoin(); // put another coin on the screen
            }
            coinRectangles.clear();
            for (int i = 0;i < coinXs.size(); i++) {
                batch.draw(coin, coinXs.get(i), coinYs.get(i));
                coinXs.set(i, coinXs.get(i) - 4); // make coins move from right to left
                coinRectangles.add(new Rectangle(coinXs.get(i), coinYs.get(i), coin.getWidth(), coin.getHeight())); // add a rectangle to the coin
            }

            // setup bombs
            if (bombCount < 250) {
                bombCount++;
            } else {
                bombCount = 0;
                makeBomb(); // put another coin on the screen
            }
            bombRectangles.clear();
            for (int i = 0;i < bombXs.size(); i++) {
                batch.draw(bomb, bombXs.get(i), bombYs.get(i));
                bombXs.set(i, bombXs.get(i) - 8); // make coins move from right to left
                bombRectangles.add(new Rectangle(bombXs.get(i), bombYs.get(i), bomb.getWidth(), bomb.getHeight()));
            }
            // when the screen is tapped
            if (Gdx.input.justTouched()) {
                velocity = -10; // when velocity is negative, the man is jumping up
            }

            // we have to add the man after we draw the background, otherwise the man would be covered by the background
            if (pause < 8) {
                pause++;
            } else {
                pause = 0;
                manState = (manState + 1) % 4;
            }

            // calculate velocity
            velocity += gravity;
            manY -= velocity; // when velocity > 0, the man is falling down to the ground, else he is jumping up

            if (manY <= 0) {
                manY = 0; // stay at the bottom
            }

            // center the man in the screen
            batch.draw(man[manState], Gdx.graphics.getWidth() / 2 - man[manState].getWidth() / 2, manY);

            // detect collision
            manRectangle = new Rectangle(Gdx.graphics.getWidth() / 2 - man[manState].getWidth() / 2, manY, man[manState].getWidth(), man[manState].getHeight()); // setup the rectangle for the man
            for (int i = 0; i < coinRectangles.size(); i++) { // collide with a coin
                if (Intersector.overlaps(manRectangle, coinRectangles.get(i))) {
                    score++;
                    // get rid of the coin
                    coinRectangles.remove(i);
                    coinXs.remove(i);
                    coinYs.remove(i);
                    break; // leave the for loop
                }
            }
            for (int i = 0; i < bombRectangles.size(); i++) { // collide with a bomb
                if (Intersector.overlaps(manRectangle, bombRectangles.get(i))) {
                    Gdx.app.log("Bomb!", "Collision!");
                    gameState = 2;
                }
            }

            // display the score
            font.draw(batch, String.valueOf(score), 100, 200);

        } else if (gameState == 0) {

            // center the man in the screen
            batch.draw(man[manState], Gdx.graphics.getWidth() / 2 - man[manState].getWidth() / 2, manY);

            // display information
            font.draw(batch, "Tap to start!", 100, 200);

            // waiting, looking for a touch to start the game
            if (Gdx.input.justTouched()) {
                gameState = 1;
            }

        } else if (gameState == 2) {

            // show the dizzy man image
            batch.draw(dizzy, Gdx.graphics.getWidth() / 2 - man[manState].getWidth() / 2, manY);

            // display information
            font.draw(batch, "You lose!", 100, 200);

            // game over, reset everything
            if (Gdx.input.justTouched()) {
                gameState = 1;
                manY = Gdx.graphics.getHeight() / 2;
                score = 0;
                velocity = 0;
                coinXs.clear();
                coinYs.clear();
                coinRectangles.clear();
                coinCount = 0;
                bombXs.clear();
                bombYs.clear();
                bombRectangles.clear();
                bombCount = 0;
            }
        }

        batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
