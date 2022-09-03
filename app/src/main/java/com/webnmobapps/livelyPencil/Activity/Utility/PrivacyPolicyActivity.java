package com.webnmobapps.livelyPencil.Activity.Utility;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.webnmobapps.livelyPencil.R;

public class PrivacyPolicyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);

        WebView web = (WebView) findViewById(R.id.privacy_policies_webView);

        web.setWebViewClient(new WebViewClient());

        web.getSettings().setLoadsImagesAutomatically(true);

        web.getSettings().setJavaScriptEnabled(true);

        web.getSettings().setBuiltInZoomControls(true);

        web.getSettings().setSupportZoom(true);

        web.getSettings().setLoadWithOverviewMode(true);

        web.getSettings().setUseWideViewPort(true);

        web.getSettings().setAllowContentAccess(true);

        web.loadUrl("https://mobileandwebsitedevelopment.com/LivelyPencil/privacypolicy");
        web.setWebViewClient(new WebViewClient());


    }
}