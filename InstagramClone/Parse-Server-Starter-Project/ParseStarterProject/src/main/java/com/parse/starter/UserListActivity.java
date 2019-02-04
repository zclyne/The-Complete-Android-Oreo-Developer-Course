package com.parse.starter;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class UserListActivity extends AppCompatActivity {

    public ListView listView;
    public ArrayList<String> usernames;
    public ArrayAdapter<String> arrayAdapter;

    public void getPhoto() {
        // use intent to get to another activity provided by android studio that allows the user to choose an image inside the device
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) { // this functions is called after we get the result from startActivityForResult()
        super.onActivityResult(requestCode, resultCode, data);

        Uri selectedImage = data.getData();

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage); // get the selected image
                Toast.makeText(UserListActivity.this, "Successfully selected an image.", Toast.LENGTH_SHORT).show();

                // Parse the photo we selected and upload it to the server
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                ParseFile file = new ParseFile("image4.png", byteArray); // the 1st parameter is the name
                ParseObject object = new ParseObject("Image");
                object.put("image", file);
                object.put("username", ParseUser.getCurrentUser().getUsername()); // whoever the image belongs to
                object.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            Toast.makeText(UserListActivity.this, "Image has been shared!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(UserListActivity.this, "There has been an issue uploading the image. :(", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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

    // set up the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.share_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.share) { // tap on share, import a new photo
            // check permission
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            } else {
                getPhoto();
            }
        } else if (item.getItemId() == R.id.logout) {
            ParseUser.logOut();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        // set title
        setTitle("User Feed");

        listView = (ListView) findViewById(R.id.listView);
        usernames = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, usernames);

        // show all usernames in the listView
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereNotEqualTo("username", ParseUser.getCurrentUser().getUsername()); // don't show the current user's name in the list
        query.addAscendingOrder("username");
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> list, ParseException e) {
                if (e == null) {
                    if (list.size() > 0) {
                        for (ParseUser user : list) {
                            usernames.add(user.getUsername());
                        }
                        listView.setAdapter(arrayAdapter);
                    }
                } else {
                    e.printStackTrace();
                }
            }
        });

        // set onClickListener for listView items
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), UserFeedActivity.class);
                intent.putExtra("username", usernames.get(i));
                startActivity(intent);
            }
        });
    }
}
