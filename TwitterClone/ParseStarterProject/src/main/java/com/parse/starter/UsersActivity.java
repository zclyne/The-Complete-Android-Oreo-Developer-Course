package com.parse.starter;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class UsersActivity extends AppCompatActivity {

    ListView usersListView;
    ArrayList<String> users = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = new MenuInflater(this);
        menuInflater.inflate(R.menu.tweet_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.tweet) { // post a new tweet
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Send a Tweet");
            final EditText tweetEditText = new EditText(this);
            builder.setView(tweetEditText);
            builder.setPositiveButton("Send", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Log.i("Info", tweetEditText.getText().toString());
                    ParseObject tweet = new ParseObject("Tweet");
                    tweet.put("tweet", tweetEditText.getText().toString());
                    tweet.put("username", ParseUser.getCurrentUser().getUsername());
                    tweet.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                Toast.makeText(UsersActivity.this, "Tweet sent!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(UsersActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Log.i("Info", "I don't wanna tweet!");
                    dialogInterface.cancel(); // close the AlertDialog window
                }
            });
            builder.show();
        } else if (item.getItemId() == R.id.logout) { // logout
            ParseUser.logOut();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.viewFeed) {
            Intent intent = new Intent(getApplicationContext(), FeedActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        setTitle("User List");

        usersListView = findViewById(R.id.usersListView);
        usersListView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE); // allow the user to add marks

        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_checked, users);
        usersListView.setAdapter(arrayAdapter);

        usersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CheckedTextView checkedTextView = (CheckedTextView) view;
                if (checkedTextView.isChecked()) {
                    Log.i("Info", "Checked!");
                    // add the user that is clicked to the current user's following list
                    ParseUser.getCurrentUser().add("isFollowing", users.get(i));
                } else {
                    Log.i("Info", "NOT Checked!");
                    // remove the user that is clicked from the current user's following list
                    ParseUser.getCurrentUser().getList("isFollowing").remove(users.get(i));
                    List tempUsers = ParseUser.getCurrentUser().getList("isFollowing");
                    ParseUser.getCurrentUser().remove("isFollowing");
                    ParseUser.getCurrentUser().put("isFollowing", tempUsers);
                }
                ParseUser.getCurrentUser().saveInBackground();
            }
        });

        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereNotEqualTo("username", ParseUser.getCurrentUser().getUsername()); // look for all users except for the current user
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if (e == null && objects.size() > 0) {
                    for(ParseUser user : objects) { // add all the users to the ArrayList
                        users.add(user.getUsername());
                    }
                    arrayAdapter.notifyDataSetChanged(); // refresh the listView

                    for (String username : users) { // check if the user is followed by the current user
                        if (ParseUser.getCurrentUser().getList("isFollowing").contains(username)) {
                            usersListView.setItemChecked(users.indexOf(username), true); // if is followed, check this user
                        }
                    }
                }
            }
        });
    }
}
