package com.webnmobapps.livelyPencil.Activity.Setting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.webnmobapps.livelyPencil.R;

public class SettingsActivity extends AppCompatActivity {

    AppCompatTextView personal_information_layout, contact_information_layout, language_settings_layout,game_setting_layout
    ,api_setting_layout,stream_setting_layout,stream_page_setting_layout,share_setting_layout
            ,tv_channel_setting_layout,radio_channel_setting_layout,ads_setting_layout,personalization_privacy_layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        inits();

        personal_information_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // calling personal_information_layout activity
                Intent intent = new Intent (SettingsActivity.this, PersonalInformationActivity.class);
                startActivity(intent);
            }
        });

        contact_information_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // calling personal_information_layout activity
                Intent intent2 = new Intent (SettingsActivity.this, ContactInformationActivity.class);
                startActivity(intent2);
            }
        });



        language_settings_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // calling language_settings_layout activity
                Intent intent3 = new Intent (SettingsActivity.this, LanguageSettingsActivity.class);
                startActivity(intent3);
            }
        });




        game_setting_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // calling game_setting_layout activity
                Intent intent4 = new Intent (SettingsActivity.this, GameSettingsActivity.class);
                startActivity(intent4);
            }
        });

        api_setting_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // calling APISettingsActivity activity
                Intent intent5 = new Intent (SettingsActivity.this, APISettingsActivity.class);
                startActivity(intent5);

            }
        });


        stream_setting_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // calling StreamSettingsActivity activity
                Intent intent6 = new Intent (SettingsActivity.this, StreamSettingsActivity.class);
                startActivity(intent6);
            }
        });



        stream_page_setting_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // calling StreamPageSettingsActivity activity
                Intent intent7 = new Intent (SettingsActivity.this, StreamPageSettingsActivity.class);
                startActivity(intent7);
            }
        });



        share_setting_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // calling APISettingsActivity activity
                Intent intent8 = new Intent (SettingsActivity.this, ShareSettingsActivity.class);
                startActivity(intent8);

            }
        });


        tv_channel_setting_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // calling TVChannelSettingsActivity activity
                Intent intent9 = new Intent (SettingsActivity.this, TVChannelSettingsActivity.class);
                startActivity(intent9);
            }
        });



        radio_channel_setting_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // calling RadioChannelSettingsActivity activity
                Intent intent10 = new Intent (SettingsActivity.this, RadioChannelSettingsActivity.class);
                startActivity(intent10);
            }
        });



        ads_setting_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // calling ADS_Settings_Activity activity
                Intent intent11 = new Intent (SettingsActivity.this, ADS_Settings_Activity.class);
                startActivity(intent11);
            }
        });

        personalization_privacy_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // calling ADS_Settings_Activity activity
                Intent intent11 = new Intent (SettingsActivity.this, PersonalizationPrivacyActivity.class);
                startActivity(intent11);
            }
        });


    }

    private void inits() {
        personal_information_layout = findViewById(R.id.personal_information_layout);
        contact_information_layout = findViewById(R.id.contact_information_layout);
        language_settings_layout = findViewById(R.id.language_settings_layout);
        api_setting_layout = findViewById(R.id.api_setting_layout);
        stream_setting_layout = findViewById(R.id.stream_setting_layout);
        stream_page_setting_layout = findViewById(R.id.stream_page_setting_layout);
        share_setting_layout = findViewById(R.id.share_setting_layout);
        tv_channel_setting_layout = findViewById(R.id.tv_channel_setting_layout);
        radio_channel_setting_layout = findViewById(R.id.radio_channel_setting_layout);
        game_setting_layout = findViewById(R.id.game_setting_layout);
        ads_setting_layout = findViewById(R.id.ads_setting_layout);
        personalization_privacy_layout = findViewById(R.id.personalization_privacy_layout);
    }
}