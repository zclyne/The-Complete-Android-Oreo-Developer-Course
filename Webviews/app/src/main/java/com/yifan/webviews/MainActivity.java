package com.yifan.webviews;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WebView webView = findViewById(R.id.webView);

        webView.getSettings().setJavaScriptEnabled(true); // enable javascript
        webView.setWebViewClient(new WebViewClient()); // open websites in our webView, rather than in the browser
//        webView.loadUrl("http://www.google.com");

        // load a raw html into a webView
        webView.loadData("<html><body><h1>Hello World!</h1>This is my cool website<p></p></body></html>", "text/html", "UTF-8");
    }
}
