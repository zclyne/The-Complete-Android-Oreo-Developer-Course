package com.parse.starter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FeedActivity extends AppCompatActivity {

    ListView feedListView;
    List<Map<String, String> > tweetData;
    ImageView feedImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        setTitle("Your Feed");

        feedListView = findViewById(R.id.feedsListView);
        feedImageView = findViewById(R.id.feedImageView);

        tweetData = new ArrayList<>();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Tweet");
        query.whereContainedIn("username", ParseUser.getCurrentUser().getList("isFollowing")); // get the tweets of the users that the current user is following
        query.orderByDescending("createdAt");
        query.setLimit(20);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null && objects.size() > 0) {
                    for (ParseObject tweet : objects) {
                        String content = tweet.getString("tweet");
                        String username = tweet.getString("username");
                        Map<String, String> tweetInfo = new HashMap<>();
                        tweetInfo.put("content", content);
                        tweetInfo.put("username", username);
                        tweetData.add(tweetInfo);
                    }
                    // simple_list_item_2 allows us to use both the item and the subitem
                    // the two items in the int array correspond to item and subitem
                    SimpleAdapter simpleAdapter = new SimpleAdapter(FeedActivity.this, tweetData, android.R.layout.simple_list_item_2, new String[] {"content", "username"}, new int[] {android.R.id.text1, android.R.id.text2});
                    feedListView.setAdapter(simpleAdapter);
                }
            }
        });

        feedListView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // when clicked on a specific tweet, get the image and show it in the imageView
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String contentClicked = tweetData.get(i).get("content");
                String usernameClicked = tweetData.get(i).get("username");
                ParseQuery<ParseObject> query = ParseQuery.getQuery("Tweet");
                query.whereEqualTo("username", usernameClicked);
                query.whereEqualTo("tweet", contentClicked);
                Log.i("usernameClicked", usernameClicked);
                Log.i("contentClicked", contentClicked);
                query.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {
                        if (e == null && objects.size() > 0) {
                            ParseFile file = objects.get(0).getParseFile("image"); // get the image
                            if (file != null) {
                                file.getDataInBackground(new GetDataCallback() {
                                    @Override
                                    public void done(byte[] data, ParseException e) { // show the image with an ImageView inside the LinearLayout
                                        if (e == null && data != null) {
                                            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                                            feedImageView.setImageBitmap(bitmap);
                                        }
                                    }
                                });
                            } else {
                                Toast.makeText(getApplicationContext(), "This tweet doesn't have an image with it.", Toast.LENGTH_SHORT).show();
                                feedImageView.setColorFilter(Color.WHITE);
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "This tweet doesn't have an image with it.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}
