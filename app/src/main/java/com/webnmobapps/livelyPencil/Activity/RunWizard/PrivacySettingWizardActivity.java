package com.webnmobapps.livelyPencil.Activity.RunWizard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.webnmobapps.livelyPencil.R;

public class PrivacySettingWizardActivity extends AppCompatActivity {

    AppCompatButton nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_setting_wizard);

        intis();

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PrivacySettingWizardActivity.this, ThankYouWizardActivity.class);
                startActivity(intent);
            }
        });

    }

    private void intis() {
        nextButton = findViewById(R.id.privacy_wizard_next_button);
    }
}