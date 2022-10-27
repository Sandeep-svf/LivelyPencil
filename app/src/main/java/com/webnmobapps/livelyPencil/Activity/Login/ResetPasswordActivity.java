package com.webnmobapps.livelyPencil.Activity.Login;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.webnmobapps.livelyPencil.Model.SmFlaxibleModel;
import com.webnmobapps.livelyPencil.R;
import com.webnmobapps.livelyPencil.RetrofitApi.API_Client;

import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetPasswordActivity extends AppCompatActivity {

    AppCompatEditText confirm_reset_password_data, reset_password_data;
    AppCompatButton reset_password_button;
    String resetPasswordData, confirmResetPasswordData;
    AlertDialog dialogs;
    private  String key,userEmail,userPhone, countryCode,otpData ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        intis();

        key = getIntent().getStringExtra("key");
        userEmail = getIntent().getStringExtra("userEmail");
        userPhone = getIntent().getStringExtra("userPhone");
        countryCode = getIntent().getStringExtra("countryCode");


        reset_password_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                get_form_data();
                if(validation())
                {
                    // reset password api
                   // reset_password_api();

                    // add API here.....
                    Intent intent = new Intent(ResetPasswordActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void reset_password_api() {

            String userName = "";

            // show till load api data
            final ProgressDialog pd = new ProgressDialog(ResetPasswordActivity.this);
            pd.setCancelable(false);
            pd.setMessage("loading...");
            pd.show();

            if(key.equals("1"))
            {
                userName = userEmail;
            }else if(key.equals("2"))
            {
                userName = userPhone;
            }

            Log.e("check_data","userName: "+userName);
            Log.e("check_data","resetPasswordData: "+resetPasswordData);


            Call<SmFlaxibleModel> call = API_Client.getClient().chnage_password(userName,resetPasswordData);

            call.enqueue(new Callback<SmFlaxibleModel>() {
                @Override
                public void onResponse(Call<SmFlaxibleModel> call, Response<SmFlaxibleModel> response) {
                    pd.dismiss();
                    try {
                        //if api response is successful ,taking message and success
                        if (response.isSuccessful()) {
                            String message = response.body().getMessage();
                            String success = String.valueOf(response.body().getSuccess());

                            if (success.equals("true") || success.equals("True")) {
                                alert_dialog_message("4");
                            } else {
                                pd.dismiss();
                            }

                        } else {
                            try {
                                JSONObject jObjError = new JSONObject(response.errorBody().string());
                                Toast.makeText(getApplicationContext(), jObjError.getString("message"), Toast.LENGTH_LONG).show();
                                switch (response.code()) {
                                    case 400:
                                        Toast.makeText(getApplicationContext(), "The server did not understand the request.", Toast.LENGTH_SHORT).show();
                                        break;
                                    case 401:
                                        Toast.makeText(getApplicationContext(), "Unauthorized The requested page needs a username and a password.", Toast.LENGTH_SHORT).show();
                                        break;
                                    case 404:
                                        Toast.makeText(getApplicationContext(), "The server can not find the requested page.", Toast.LENGTH_SHORT).show();
                                        break;
                                    case 500:
                                        Toast.makeText(getApplicationContext(), "Internal Server Error..", Toast.LENGTH_SHORT).show();
                                        break;
                                    case 503:
                                        Toast.makeText(getApplicationContext(), "Service Unavailable..", Toast.LENGTH_SHORT).show();
                                        break;
                                    case 504:
                                        Toast.makeText(getApplicationContext(), "Gateway Timeout..", Toast.LENGTH_SHORT).show();
                                        break;
                                    case 511:
                                        Toast.makeText(getApplicationContext(), "Network Authentication Required ..", Toast.LENGTH_SHORT).show();
                                        break;
                                    default:
                                        Toast.makeText(getApplicationContext(), "unknown error", Toast.LENGTH_SHORT).show();
                                        break;
                                }

                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    } catch (
                            Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<SmFlaxibleModel> call, Throwable t) {
                    Log.e("conversion issue", t.getMessage());

                    if (t instanceof IOException) {
                        Toast.makeText(getApplicationContext(), "This is an actual network failure :( inform the user and possibly retry)", Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    } else {
                        Log.e("conversion issue", t.getMessage());
                        Toast.makeText(getApplicationContext(), "Please Check your Internet Connection...." + t.getMessage(), Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }
                }
            });

        }


    private boolean validation() {
        if(resetPasswordData.equals(""))
        {
            alert_dialog_message("1");
            return false;
        }else if(resetPasswordData.length() < 6)
        {
            alert_dialog_message("12154");
            return false;
        }else if(confirmResetPasswordData.equals(""))
        {
            alert_dialog_message("2");
            return  false;
        }else if(!resetPasswordData.equals( confirmResetPasswordData))
        {
            alert_dialog_message("3");
            return  false;
        }
        return  true;
    }

    private void get_form_data() {
        resetPasswordData = reset_password_data.getText().toString();
        confirmResetPasswordData = confirm_reset_password_data.getText().toString();

        Log.e("check_password_data","resetPasswordData: "+resetPasswordData);
        Log.e("check_password_data","confirmResetPasswordData: "+confirmResetPasswordData);

    }

    private void intis() {
        confirm_reset_password_data = findViewById(R.id.confirm_reset_password_data);
        reset_password_data = findViewById(R.id.reset_password_data);
        reset_password_button = findViewById(R.id.reset_password_button);
    }
    private void alert_dialog_message(String value) {

        final LayoutInflater inflater = ResetPasswordActivity.this.getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.test_dialog_xml_otp, null);

        final ImageView close_dialog = alertLayout.findViewById(R.id.close_dialog);
        final TextView error_message = alertLayout.findViewById(R.id.error_message);





        if(value.equals("1"))
        {
            error_message.setText(getResources().getString(R.string.Please_enter_password));
        }else if(value.equals("2"))
        {
            error_message.setText(getResources().getString(R.string.please_enter_confirm_password));
        }else if(value.equals("3"))
        {
            error_message.setText(getResources().getString(R.string.password_not_match));
        }else if(value.equals("4"))
        {
            error_message.setText(getResources().getString(R.string.successfully_updated));
        }else if(value.equals("12154"))
        {
            error_message.setText(getResources().getString(R.string.minimum_password_length));
        }

        if(value.equals("4"))
        {
            Glide.with(ResetPasswordActivity.this).load(R.drawable.login_message_logo).into(close_dialog);
        }else
        {
            Glide.with(ResetPasswordActivity.this).load(R.drawable.close_icon).into(close_dialog);

        }

        final AlertDialog.Builder alert = new AlertDialog.Builder(ResetPasswordActivity.this);

        alert.setView(alertLayout);
        alert.setCancelable(false);

        dialogs = alert.create();
        dialogs.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogs.show();
        dialogs.setCanceledOnTouchOutside(true);


        close_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(value.equals("4"))
                {
                    Intent intent = new Intent(ResetPasswordActivity.this, LoginActivity.class);
                    startActivity(intent);
                    dialogs.dismiss();
                }else
                {
                    dialogs.dismiss();
                }

            }
        });
    }
}