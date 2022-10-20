package com.webnmobapps.livelyPencil.Activity.Book;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.webnmobapps.livelyPencil.R;

public class WebviewEditorActivity extends AppCompatActivity {


    WebView web;
    private final String  url = "http://69.49.235.253:8001/3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview_editor);

        web = findViewById(R.id.editor_webview);

        // linl ->  http://69.49.235.253:8001/
        //http://69.49.235.253:8001/

        web.setWebViewClient(new WebViewClient());

        web.getSettings().setLoadsImagesAutomatically(true);

        web.getSettings().setJavaScriptEnabled(true);

        web.getSettings().setBuiltInZoomControls(true);

        //   web.getSettings().setSupportZoom(true);
        web.getSettings().setLoadWithOverviewMode(true);

        web.getSettings().setUseWideViewPort(true);

        web.getSettings().setAllowContentAccess(true);

        web.loadUrl(url);
        web.setWebViewClient(new WebViewClient());

    }
}