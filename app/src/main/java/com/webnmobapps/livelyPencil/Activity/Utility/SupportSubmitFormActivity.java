package com.webnmobapps.livelyPencil.Activity.Utility;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.webnmobapps.livelyPencil.Activity.JoinUs.NameEmailActivity;
import com.webnmobapps.livelyPencil.Activity.Login.LoginActivity;
import com.webnmobapps.livelyPencil.R;

public class SupportSubmitFormActivity extends AppCompatActivity {

    AppCompatTextView login_support_form, joinus_supporet_form, help_pages_support_form;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support_submit_form2);

        intis();

        login_support_form.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SupportSubmitFormActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        joinus_supporet_form.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SupportSubmitFormActivity.this, NameEmailActivity.class);
                startActivity(intent);
            }
        });

        help_pages_support_form.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SupportSubmitFormActivity.this, HelpActivity.class);
                startActivity(intent);
            }
        });

    }

    private void intis() {
        login_support_form = findViewById(R.id.login_support_form);
        joinus_supporet_form = findViewById(R.id.joinus_supporet_form);
        help_pages_support_form = findViewById(R.id.help_pages_support_form);
    }
}