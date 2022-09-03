package com.webnmobapps.livelyPencil.Activity.RunWizard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.webnmobapps.livelyPencil.R;

public class ContactInfoWizardSettingsActivity extends AppCompatActivity {

    AppCompatButton contact_info_wizard_next_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_info_wizard_settings);

        inits();

        contact_info_wizard_next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ContactInfoWizardSettingsActivity.this, AboutYourselfWizardActivity.class);
                startActivity(intent);
            }
        });

    }

    private void inits() {
        contact_info_wizard_next_button = findViewById(R.id.contact_info_wizard_next_button);
    }
}