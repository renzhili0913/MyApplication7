package com.example.qrcode;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

public class WebActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_item);
        Intent intent=getIntent();
        String result = intent.getStringExtra("result");
        WebView webView = findViewById(R.id.webview);
        webView.loadUrl(result);
       // webView.loadDataWithBaseURL(null,result,"Text/html","utf-8",null);
    }
}
