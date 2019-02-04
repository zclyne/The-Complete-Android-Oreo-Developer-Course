package com.parse.starter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class UserFeedActivity extends AppCompatActivity {

    public LinearLayout linLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_feed);

        linLayout = (LinearLayout) findViewById(R.id.linLayout);

        Intent intent = getIntent();
        String username = intent.getStringExtra("username");

        setTitle(username + "'s Photos");

        ParseQuery<ParseObject> query = new ParseQuery<>("Image");

        query.whereEqualTo("username", username); // get all images posted by the user that was clicked on
        query.orderByDescending("createdAt"); // descending order in time when the images are posted
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null && objects.size() > 0) {
                    for (ParseObject object : objects) {
                        ParseFile file = object.getParseFile("image"); // get the image
                        file.getDataInBackground(new GetDataCallback() {
                            @Override
                            public void done(byte[] data, ParseException e) { // show the image with an ImageView inside the LinearLayout
                                if (e == null && data != null) {
                                    Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                                    ImageView imageView = new ImageView(getApplicationContext());
                                    imageView.setLayoutParams(new ViewGroup.LayoutParams(
                                            ViewGroup.LayoutParams.MATCH_PARENT,
                                            ViewGroup.LayoutParams.WRAP_CONTENT
                                    ));
                                    imageView.setImageBitmap(bitmap);
                                    linLayout.addView(imageView);
                                }
                            }
                        });
                    }
                }
            }
        });
    }
}
