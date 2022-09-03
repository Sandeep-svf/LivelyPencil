package com.webnmobapps.livelyPencil.Activity.RunWizard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.webnmobapps.livelyPencil.R;

import java.util.stream.Stream;

public class AboutYourselfWizardActivity extends AppCompatActivity {

    AppCompatButton about_yourself_wizard_next_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_yourself_wizard);

        intis();

        about_yourself_wizard_next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AboutYourselfWizardActivity.this, StreamingAreaWizardSettingsActivity.class);
                startActivity(intent);
            }
        });
    }

    private void intis() {
        about_yourself_wizard_next_button = findViewById(R.id.about_yourself_wizard_next_button);
    }
}