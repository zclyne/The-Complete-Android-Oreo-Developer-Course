package com.parse.starter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.parse.FindCallback;
import com.parse.ParseException;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        setTitle("Your Feed");

        feedListView = findViewById(R.id.feedsListView);

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
    }
}
