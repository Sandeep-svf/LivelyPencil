package com.webnmobapps.livelyPencil.Activity.Book;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.webnmobapps.livelyPencil.R;

public class WebviewEditorActivity extends AppCompatActivity {


    WebView web;
    private final String  url = "http://69.49.235.253:8001/";
    private String bookId,finalUrl;
    private String key="";
    AppCompatImageView back_arrow;
    private AppCompatTextView tital;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview_editor);

        tital = findViewById(R.id.tital);

        key = getIntent().getStringExtra("key");
        //get id
        bookId = getIntent().getStringExtra("bookid");

        try {
            Log.e("nnnnn",bookId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(key.equals("1")){
            finalUrl = "https://help.livelypencil.com";
            tital.setText("Help");
        }else{
            tital.setText("Content Editor");
            finalUrl = url+bookId;
        }

        web = findViewById(R.id.editor_webview);
        back_arrow = findViewById(R.id.back_arrow);


        // linl ->  http://69.49.235.253:8001/
        //http://69.49.235.253:8001/

        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


       // web.getSettings().setLoadsImagesAutomatically(true);

        web.getSettings().setJavaScriptEnabled(true);
        web.getSettings().setDomStorageEnabled(true);

       // web.getSettings().setBuiltInZoomControls(true);

        //   web.getSettings().setSupportZoom(true);
        //web.getSettings().setLoadWithOverviewMode(true);

       // web.getSettings().setUseWideViewPort(true);

       // web.getSettings().setAllowContentAccess(true);

        web.getSettings().setPluginState(WebSettings.PluginState.ON);
        web.getSettings().setMediaPlaybackRequiresUserGesture(false);
        web.setLayerType(View.LAYER_TYPE_NONE, null);

        web.setWebViewClient(new WebViewClient());
        web.loadUrl(finalUrl);


    }
}