package com.yifan.downloadingwebcontent;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    // to download contents from the internet, we should use a background thread
    // the main thread is used to deal with the UI stuffs

    public class DownloadTask extends AsyncTask<String, Void, String> { // everything within DownloadTask happens background
        // the two String here means we can pass in a string and pass out a string
        // we pass in a url, and we get the html
        @Override
        protected String doInBackground(String... urls) { // protected allows everything in the package to access this method, String... allow us to pass in any number of strings as we like, and it works like an array

            String result = "";
            URL url;
            HttpURLConnection urlConnection = null; // HttpURLConnection is like a browser

            try {
                Log.i("url", urls[0]);
                url = new URL(urls[0]); // convert the string to an URL
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();

                while (data != -1) { // -1 means the reader arrives at the last thing
                    char current = (char) data;
                    result += current; // read the data one character after another
                    data = reader.read();
                }
                return result;
            } catch (Exception e) {
                e.printStackTrace();
                return "Failed";
            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DownloadTask task = new DownloadTask();

        String result = null;

        try {
            result = task.execute("http://www.google.com").get(); // execute() passes the urls into doInBackground()
            // add .get() to return the String to result
            Log.i("resultHTML", result);
        } catch (Exception e) {
           e.printStackTrace();
        }
    }
}
