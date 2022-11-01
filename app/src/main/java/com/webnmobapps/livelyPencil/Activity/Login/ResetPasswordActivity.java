package com.webnmobapps.livelyPencil.Activity.Login;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.webnmobapps.livelyPencil.Model.SmFlaxibleModel;
import com.webnmobapps.livelyPencil.ModelPython.CommonStatusMessageModelPython;
import com.webnmobapps.livelyPencil.R;
import com.webnmobapps.livelyPencil.RetrofitApi.API_Client;
import com.webnmobapps.livelyPencil.utility.StaticKey;

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
    private  String key,userEmail,userPhone, countryCode,otpData ,email,otp,accessToken,finalAccessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        intis();

        SharedPreferences sharedPreferences= getSharedPreferences("AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
        // user_id=sharedPreferences.getString("UserID","");
        accessToken=sharedPreferences.getString("accessToken","");
        finalAccessToken = StaticKey.prefixTokem+accessToken;

       /* key = getIntent().getStringExtra("key");
        userEmail = getIntent().getStringExtra("userEmail");
        userPhone = getIntent().getStringExtra("userPhone");
        countryCode = getIntent().getStringExtra("countryCode");*/


        try {
            email = getIntent().getStringExtra("email");
            otp = getIntent().getStringExtra("otp");

        } catch (Exception exception) {
            exception.printStackTrace();
            Toast.makeText(ResetPasswordActivity.this, "Something went wrong...", Toast.LENGTH_SHORT).show();
        }
        Log.e("intent_data",email+otp+"ok");
        reset_password_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                get_form_data();
                if(validation())
                {
                    // reset password api
                   // reset_password_api();

                    reset_password_api_python();
                    // add API here.....

                }
            }
        });
    }

    private void reset_password_api_python() {

            final ProgressDialog pd = new ProgressDialog(ResetPasswordActivity.this);
            pd.setCancelable(false);
            pd.setMessage("loading...");
            pd.show();


            Call<CommonStatusMessageModelPython> call = API_Client.getClient().COMMON_STATUS_MESSAGE_MODEL_PYTHON_CALL_RESET_PASSWORD(finalAccessToken,userEmail,otp,resetPasswordData);

            call.enqueue(new Callback<CommonStatusMessageModelPython>() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onResponse(Call<CommonStatusMessageModelPython> call, Response<CommonStatusMessageModelPython> response) {
                    pd.dismiss();


                    try {
                        if (response.isSuccessful()) {
                            String message = response.body().getMessage();
                            String success = response.body().getStatus();

                            if (success.equals("true") || success.equals("True")) {
                                Toast.makeText(ResetPasswordActivity.this, message, Toast.LENGTH_SHORT).show();
                                pd.dismiss();
                                Intent intent = new Intent(ResetPasswordActivity.this, LoginActivity.class);
                                startActivity(intent);

                            } else {
                                Toast.makeText(ResetPasswordActivity.this, "This email is not registered with us.", Toast.LENGTH_LONG).show();
                                pd.dismiss();
                            }


                        } else {
                            try {
                                JSONObject jObjError = new JSONObject(response.errorBody().string());
                                Toast.makeText(ResetPasswordActivity.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                                switch (response.code()) {
                                    case 400:
                                        alert_dialog_message("400");
                                        //  Toast.makeText(getApplicationContext(), "The server did not understand the request.", Toast.LENGTH_SHORT).show();
                                        break;
                                    case 401:
                                        alert_dialog_message("401");
                                        // Toast.makeText(getApplicationContext(), "Unauthorized The requested page needs a username and a password.", Toast.LENGTH_SHORT).show();
                                        break;
                                    case 404:
                                        alert_dialog_message("404");
                                        //Toast.makeText(getApplicationContext(), "The server can not find the requested page.", Toast.LENGTH_SHORT).show();
                                        break;
                                    case 500:
                                        alert_dialog_message("500");
                                        //Toast.makeText(getApplicationContext(), "Internal Server Error..", Toast.LENGTH_SHORT).show();
                                        break;
                                    case 503:
                                        alert_dialog_message("503");
                                        // Toast.makeText(getApplicationContext(), "Service Unavailable..", Toast.LENGTH_SHORT).show();
                                        break;
                                    case 504:
                                        alert_dialog_message("504");
                                        //  Toast.makeText(getApplicationContext(), "Gateway Timeout..", Toast.LENGTH_SHORT).show();
                                        break;
                                    case 511:
                                        alert_dialog_message("511");
                                        // Toast.makeText(getApplicationContext(), "Network Authentication Required ..", Toast.LENGTH_SHORT).show();
                                        break;
                                    default:
                                        alert_dialog_message("default");
                                        //Toast.makeText(getApplicationContext(), "unknown error", Toast.LENGTH_SHORT).show();
                                        break;
                                }

                            } catch (Exception e) {
                                Toast.makeText(ResetPasswordActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    } catch (
                            Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<CommonStatusMessageModelPython> call, Throwable t) {
                    Log.e("bhgyrrrthbh", String.valueOf(t));
                    if (t instanceof IOException) {
                        Toast.makeText(ResetPasswordActivity.this, "This is an actual network failure :( inform the user and possibly retry)" + t.getMessage(), Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    } else {
                        Log.e("conversion issue", t.getMessage());
                        Toast.makeText(ResetPasswordActivity.this, "Please Check your Internet Connection...." + t.getMessage(), Toast.LENGTH_SHORT).show();
                        pd.dismiss();
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