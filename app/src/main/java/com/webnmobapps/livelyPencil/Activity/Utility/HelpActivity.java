package com.webnmobapps.livelyPencil.Activity.Utility;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.webnmobapps.livelyPencil.R;

public class HelpActivity extends AppCompatActivity {

    AppCompatTextView one, two, three, four, five, six;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);



        inits();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            // edited here
            getWindow().setStatusBarColor(Color.WHITE);

        }
        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HelpActivity.this, HelpDetailsActivity.class);
                startActivity(intent);
            }
        });

        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HelpActivity.this, HelpDetailsActivity.class);
                startActivity(intent);
            }
        });


        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HelpActivity.this, HelpDetailsActivity.class);
                startActivity(intent);
            }
        });
        four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HelpActivity.this, HelpDetailsActivity.class);
                startActivity(intent);
            }
        });

        five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HelpActivity.this, HelpDetailsActivity.class);
                startActivity(intent);
            }
        });
        six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HelpActivity.this, HelpDetailsActivity.class);
                startActivity(intent);
            }
        });


    }

    private void inits() {
        six = findViewById(R.id.six);
        five = findViewById(R.id.five);
        four = findViewById(R.id.four);
        three = findViewById(R.id.three);
        two = findViewById(R.id.two);
        one = findViewById(R.id.one);
    }



}