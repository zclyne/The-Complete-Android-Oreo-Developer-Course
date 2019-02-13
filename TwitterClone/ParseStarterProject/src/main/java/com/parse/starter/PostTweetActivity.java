package com.parse.starter;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;

public class PostTweetActivity extends AppCompatActivity {

    EditText tweetEditText;
    ImageView imageView;
    Button chooseImageButton, postButton;
    ParseFile imageFile = null;

    public void getPhoto() {
        // use intent to get to another activity provided by android studio that allows the user to choose an image inside the device
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 1);
    }

    public void chooseImage(View view) {
        // check permission
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        } else {
            getPhoto();
        }
    }

    public void post(View view) {
        // get the tweet content
        ParseObject tweet = new ParseObject("Tweet");
        tweet.put("tweet", tweetEditText.getText().toString());
        tweet.put("username", ParseUser.getCurrentUser().getUsername());
        tweet.put("image", imageFile);
        tweet.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Toast.makeText(PostTweetActivity.this, "Successfully posted a tweet!", Toast.LENGTH_SHORT).show();
                    // go back to FeedActivity
                    Intent intent = new Intent(getApplicationContext(), FeedActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(PostTweetActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) { // got the permission
                getPhoto();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) { // this functions is called after we get the result from startActivityForResult()
        super.onActivityResult(requestCode, resultCode, data);

        Uri selectedImage = data.getData();

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage); // get the selected image
                Toast.makeText(PostTweetActivity.this, "Successfully selected an image.", Toast.LENGTH_SHORT).show();

                // store the image we selected with a ParseFile
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                imageFile = new ParseFile("image.png", byteArray); // the 1st parameter is the name
                imageView.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_tweet);

        setTitle("Post A New Tweet");

        tweetEditText = findViewById(R.id.tweetEditText);
        imageView = findViewById(R.id.imageView);
        chooseImageButton = findViewById(R.id.chooseImageButton);
        postButton = findViewById(R.id.postButton);
    }
}
