package com.webnmobapps.livelyPencil.Activity.Setting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.webnmobapps.livelyPencil.R;

public class LanguageSettingsActivity extends AppCompatActivity {

    AppCompatSpinner language_spin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_settings);
        inits();


    }

    private void inits() {
        language_spin = findViewById(R.id.language_spin);
    }
}