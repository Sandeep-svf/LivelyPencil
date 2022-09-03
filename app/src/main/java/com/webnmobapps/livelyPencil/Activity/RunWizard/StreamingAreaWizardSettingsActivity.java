package com.webnmobapps.livelyPencil.Activity.RunWizard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.webnmobapps.livelyPencil.R;

public class StreamingAreaWizardSettingsActivity extends AppCompatActivity {

    AppCompatButton streaming_area_wizard_next_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_streaming_area_wizard_settings);

        intis();

        streaming_area_wizard_next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StreamingAreaWizardSettingsActivity.this, TvChannelSettingsWizardActivity.class);
                startActivity(intent);
            }
        });


    }

    private void intis() {
        streaming_area_wizard_next_button = findViewById(R.id.streaming_area_wizard_next_button);
    }
}