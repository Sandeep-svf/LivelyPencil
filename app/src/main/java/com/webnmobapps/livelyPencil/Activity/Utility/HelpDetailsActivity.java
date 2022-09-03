package com.webnmobapps.livelyPencil.Activity.Utility;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.webnmobapps.livelyPencil.R;

public class HelpDetailsActivity extends AppCompatActivity {

    AppCompatTextView heading_details_text_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_details);
        inits();



    }

    private void inits() {
        heading_details_text_view = findViewById(R.id.heading_details_text_view);
    }
}