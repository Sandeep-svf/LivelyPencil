package com.webnmobapps.livelyPencil;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.webnmobapps.livelyPencil.Activity.Book.WebviewEditorActivity;
import com.webnmobapps.livelyPencil.Activity.JoinUs.NameEmailActivity;
import com.webnmobapps.livelyPencil.Activity.JoinUs.NameEmailActivity2;
import com.webnmobapps.livelyPencil.Activity.Login.ForgetPasswordActivity;
import com.webnmobapps.livelyPencil.Activity.Login.LoginActivity;
import com.webnmobapps.livelyPencil.Activity.Login.LoginJoinusActivity;
import com.webnmobapps.livelyPencil.Activity.Tutorial.TutorialActivity;
import com.webnmobapps.livelyPencil.Activity.Utility.WelcomeActivity;

public class MainActivity extends AppCompatActivity {
    AppCompatButton joinUsMovement,loginMovement;
    TextView countinue_tour;
    AppCompatTextView forgot_password,terms_privacy_help_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inits();

        terms_privacy_help_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WebviewEditorActivity.class);
                intent.putExtra("key","1");
                startActivity(intent);
            }
        });

        joinUsMovement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               Intent intent = new Intent(MainActivity.this, NameEmailActivity2.class);
                startActivity(intent);
            }
        });

        loginMovement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        countinue_tour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TutorialActivity.class);
                startActivity(intent);
            }
        });

        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ForgetPasswordActivity.class);
                startActivity(intent);
            }
        });

    }

    private void inits() {
        terms_privacy_help_layout = findViewById(R.id.terms_privacy_help_layout);
        joinUsMovement = findViewById(R.id.joinUsMovement);
        loginMovement = findViewById(R.id.loginMovement);
        countinue_tour = findViewById(R.id.countinue_tour);
        forgot_password = findViewById(R.id.forgot_password);
    }
}