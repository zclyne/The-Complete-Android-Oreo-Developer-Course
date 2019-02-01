package com.yifan.newsreader;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public ListView newsListView;
    public ArrayList<String> newsArrayList = new ArrayList<>();
    public ArrayAdapter<String> arrayAdapter;
    public SQLiteDatabase newsDatabase;

    public void updateListView() {
        Cursor c = newsDatabase.rawQuery("SELECT * FROM news", null);
        int URLIndex = c.getColumnIndex("url");
        int titleIndex = c.getColumnIndex("title");
        if (c != null && c.moveToFirst()) {
            newsArrayList.clear();
            do {
                newsArrayList.add(c.getString(titleIndex));
            } while (c.moveToNext());
            arrayAdapter.notifyDataSetChanged();
        } else {
            Log.i("error", "db is empty");
        }
    }

    public class DownloadTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            String result = "";
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                InputStream in = connection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();
                while (data != -1) {
                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }
                Log.i("URL Content", result);
                JSONArray jsonArray = new JSONArray(result);
                int numberOfItems = jsonArray.length() < 20 ? jsonArray.length() : 20;
                newsDatabase.execSQL("DELETE FROM news");
                for (int i = 0; i < numberOfItems; i++) {
                    String articleId = jsonArray.getString(i); // get article id
                    // get JSON about the article
                    url = new URL("https://hacker-news.firebaseio.com/v0/item/" + articleId + ".json?print=pretty");
                    connection = (HttpURLConnection) url.openConnection();
                    in = connection.getInputStream();
                    reader = new InputStreamReader(in);
                    data = reader.read();
                    String articleInfo = "";
                    while (data != -1) {
                        char current = (char) data;
                        articleInfo += current;
                        data = reader.read();
                    }
                    JSONObject articleJSON = new JSONObject(articleInfo);
                    if (!articleJSON.isNull("title") && !articleJSON.isNull("url")) {
                        String articleTitle = articleJSON.getString("title");
                        String articleURL = articleJSON.getString("url");
                        String sql = "INSERT INTO news (articleId, title, url) VALUES (?, ?, ?)";
                        SQLiteStatement statement = newsDatabase.compileStatement(sql);
                        // bindString() is 1-based counting
                        statement.bindString(1, articleId);
                        statement.bindString(2, articleTitle);
                        statement.bindString(3, articleURL);
                        statement.execute();
                    }
                }

                return result;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            updateListView();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newsListView = findViewById(R.id.newsListView);

        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, newsArrayList);
        newsListView.setAdapter(arrayAdapter);

        newsDatabase = this.openOrCreateDatabase("newsDatabase", MODE_PRIVATE, null);
        newsDatabase.execSQL("CREATE TABLE IF NOT EXISTS news (id INTEGER PRIMARY KEY, articleId INTEGER, title VARCHAR, url VARCHAR)");

//        DownloadTask task = new DownloadTask();
//        try {
//            task.execute("https://hacker-news.firebaseio.com/v0/topstories.json?print=pretty");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor c = newsDatabase.rawQuery("SELECT url FROM news WHERE title = \"" + newsArrayList.get(i) + "\"", null);
                int URLIndex = c.getColumnIndex("url");
                if (c != null && c.moveToFirst()) {
                    String articleURL = c.getString(URLIndex);
                    Intent intent = new Intent(getApplicationContext(), WebViewActivity.class);
                    intent.putExtra("newsURL", articleURL);
                    startActivity(intent);
                }
            }
        });

        updateListView();
    }
}
