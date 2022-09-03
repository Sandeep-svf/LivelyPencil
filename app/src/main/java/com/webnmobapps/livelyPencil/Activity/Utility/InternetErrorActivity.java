package com.webnmobapps.livelyPencil.Activity.Utility;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import com.webnmobapps.livelyPencil.R;

import java.util.ArrayList;
import java.util.List;

public class InternetErrorActivity extends AppCompatActivity {

    Button retry_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internet_error);

        retry_button = findViewById(R.id.retry_button);

        retry_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(InternetErrorActivity.this, WelcomeActivity.class);
                startActivity(intent);
            }
        });



    }
}