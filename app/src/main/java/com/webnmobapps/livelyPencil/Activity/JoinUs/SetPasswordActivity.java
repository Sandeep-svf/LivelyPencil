package com.webnmobapps.livelyPencil.Activity.JoinUs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import com.webnmobapps.livelyPencil.R;

public class SetPasswordActivity extends AppCompatActivity {

    AppCompatButton confirm_button;
    AppCompatEditText userPassword, userConfirmPassword;
    private String userPasswordData, userConfirmPasswordData;
    AlertDialog dialogs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_password);

        inits();

        confirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getUserFormData();


                if (validation()) {
                    SharedPreferences getUserIdData = getApplicationContext().getSharedPreferences("AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = getUserIdData.edit();
                    editor.putString("userPasswordData", String.valueOf(userPasswordData));
                    editor.apply();

                    Intent intent = new Intent(SetPasswordActivity.this, PasscodeValidationActivity.class);
                    startActivity(intent);

                }
            }
        });
    }


    private boolean validation() {

        if (userPasswordData.equals("")) {
            alert_dialog_message("1");
            //Toast.makeText(SetPasswordActivity.this, "Please enter password", Toast.LENGTH_SHORT).show();
            return false;
        } else if (userPasswordData.length() < 6) {
            alert_dialog_message("2");
            //Toast.makeText(SetPasswordActivity.this, "Minimum password length should be atleast six digit", Toast.LENGTH_SHORT).show();
            return false;
        } else if (userConfirmPasswordData.equals("")) {
            alert_dialog_message("3");
            //Toast.makeText(SetPasswordActivity.this, "Please enter confirm password", Toast.LENGTH_SHORT).show();
            return false;
        }else
        if (!userPasswordData.equals(userConfirmPasswordData)) {
            alert_dialog_message("4");
           // Toast.makeText(SetPasswordActivity.this, "Password did not match", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
    private void alert_dialog_message(String value) {

        final LayoutInflater inflater = SetPasswordActivity.this.getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.test_dialog_xml_otp, null);

        final ImageView close_dialog = alertLayout.findViewById(R.id.close_dialog);
        final TextView error_message = alertLayout.findViewById(R.id.error_message);


        if(value.equals("1"))
        {
            error_message.setText(getResources().getString(R.string.please_enter_password));
        }else if(value.equals("2"))
        {
            error_message.setText(getResources().getString(R.string.minimum_password_length));
        }else if(value.equals("3"))
        {
            error_message.setText(getResources().getString(R.string.please_enter_confirm_password));
        }else if(value.equals("4"))
        {
            error_message.setText(getResources().getString(R.string.password_did_not_match));
        }




        final AlertDialog.Builder alert = new AlertDialog.Builder(SetPasswordActivity.this);

        alert.setView(alertLayout);
        alert.setCancelable(false);

        dialogs = alert.create();
        dialogs.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogs.show();
        dialogs.setCanceledOnTouchOutside(true);


        close_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialogs.dismiss();
            }
        });
    }
    private void getUserFormData() {
        userPasswordData = userPassword.getText().toString();
        userConfirmPasswordData = userConfirmPassword.getText().toString();
    }

    private void inits() {
        confirm_button = findViewById(R.id.confirm_button);
        userPassword = findViewById(R.id.Stream_name_title);
        userConfirmPassword = findViewById(R.id.Stream_name_title2);
    }

}