package com.webnmobapps.livelyPencil.Activity.Login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.webnmobapps.livelyPencil.Activity.JoinUs.NameEmailActivity;
import com.webnmobapps.livelyPencil.R;

public class LoginJoinusActivity extends AppCompatActivity {

    AppCompatButton joinUsMovement,loginMovement;
    AppCompatTextView forgot_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_joinus);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            // edited here
            getWindow().setStatusBarColor(Color.WHITE);

        }

        joinUsMovement = findViewById(R.id.joinUsMovement);
        loginMovement = findViewById(R.id.loginMovement);
        forgot_password = findViewById(R.id.forgot_password);


        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginJoinusActivity.this, ForgetPasswordActivity.class);
                startActivity(intent);
            }
        });


        joinUsMovement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginJoinusActivity.this, NameEmailActivity.class);
                startActivity(intent);
            }
        });

        loginMovement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(LoginJoinusActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}