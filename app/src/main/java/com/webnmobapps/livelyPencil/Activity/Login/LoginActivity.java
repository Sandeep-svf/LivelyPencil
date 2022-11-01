package com.webnmobapps.livelyPencil.Activity.Login;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.constraintlayout.widget.ConstraintLayout;

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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.webnmobapps.livelyPencil.Activity.JoinUs.NameEmailActivity;
import com.webnmobapps.livelyPencil.Activity.JoinUs.NameEmailActivity2;
import com.webnmobapps.livelyPencil.Activity.UserWall.HomeActivity;
import com.webnmobapps.livelyPencil.Activity.Utility.HelpActivity;
import com.webnmobapps.livelyPencil.Activity.Utility.SupportActivity;
import com.webnmobapps.livelyPencil.MainActivity;
import com.webnmobapps.livelyPencil.Model.LoginModel;
import com.webnmobapps.livelyPencil.ModelPython.LoginModelPython;
import com.webnmobapps.livelyPencil.ModelPython.TokenPython;
import com.webnmobapps.livelyPencil.R;
import com.webnmobapps.livelyPencil.RetrofitApi.API_Client;

import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private String regexEmail = "(?:[A-Za-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[A-Za-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[A-Za-z0-9](?:[A-Za-z0-9-]*[A-Za-z0-9])?\\.)+[A-Za-z0-9](?:[A-Za-z0-9-]*[A-Za-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[A-Za-z0-9]:(?:|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
    AppCompatEditText email_edit_text, password_edit_text;
    private String userEmailPhoneData, userPasswordData, userEmail,userPhone ;
    AppCompatButton login_button;
    LinearLayoutCompat email_edittext123;
    AppCompatTextView joinus, help_pages , support;
    private String emailRegex = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private String device_token;
    AppCompatTextView  with_phone_green_text,with_email_green_text;
    ConstraintLayout active_email_layout,active_phone_layout;
    LinearLayoutCompat phone_edittext;
    AppCompatEditText email_edittext, user_phone_edit_text;
    private  String key = "1";
    AppCompatTextView forgot_password;
    AlertDialog dialogs;
    private String loginMessage;
    private String UserID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inits();

        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                startActivity(intent);
            }
        });

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("sam", "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        device_token = task.getResult().getToken();
                        Log.e("tokenn",""+device_token);
                    }
                });




        with_email_green_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                active_email_layout.setVisibility(View.VISIBLE);
                active_phone_layout.setVisibility(View.GONE);
                phone_edittext.setVisibility(View.GONE);
                email_edittext123.setVisibility(View.VISIBLE);
                key = "1";

                 // Toast.makeText(LoginActivity.this, key, Toast.LENGTH_SHORT).show();
            }
        });

        with_phone_green_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                active_email_layout.setVisibility(View.GONE);
                active_phone_layout.setVisibility(View.VISIBLE);
                phone_edittext.setVisibility(View.VISIBLE);
                email_edittext123.setVisibility(View.GONE);
                key = "2";
                //  Toast.makeText(LoginActivity.this, key, Toast.LENGTH_SHORT).show();
            }
        });


        help_pages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, HelpActivity.class);
                startActivity(intent);
            }
        });

        support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SupportActivity.class);
                startActivity(intent);
            }
        });



        joinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, NameEmailActivity2.class);
                startActivity(intent);
            }
        });



        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getUserFormData();

                if (validation()) {
                    // Login API
                    login_api();
                }

            }
        });

    }


    private boolean validation() {
        if (userPasswordData.equals("")) {
            alert_dialog_message("1");
           // Toast.makeText(LoginActivity.this, "please enter password", Toast.LENGTH_SHORT).show();
            return false;
        }else if (userPasswordData.length() < 2) {
            alert_dialog_message("14521");
            // Toast.makeText(LoginActivity.this, "please enter password", Toast.LENGTH_SHORT).show();
            return false;
        }else if(key.equals("1") )
        {
            if(userEmail.equals(""))
            {
                alert_dialog_message("2");
               // Toast.makeText(LoginActivity.this, "Please enter email", Toast.LENGTH_SHORT).show();
                return false;
            }else if(false)
            {
                //!userEmail.matches(regexEmail)
                alert_dialog_message("3");
               // Toast.makeText(LoginActivity.this, "Please enter valid email", Toast.LENGTH_SHORT).show();
                return false;
            }

        }else if(key.equals("2"))
        {
            if(userPhone.equals(""))
            {
                alert_dialog_message("4");
               // Toast.makeText(LoginActivity.this, "Please enter phone", Toast.LENGTH_SHORT).show();
                return false;
            }else
            {
                String firstDigit = userPhone.substring(0,1);
                if(firstDigit.equals("0") || (userPhone.length() != 10))
                {
                    if(firstDigit.equals("0"))
                    {
                        alert_dialog_message("5");
                        //Toast.makeText(LoginActivity.this, "Please remove zero from start.", Toast.LENGTH_SHORT).show();
                        return false;
                    }else
                    {
                        alert_dialog_message("6");
                       // Toast.makeText(LoginActivity.this, "Please enter 10 digit valid phone number.", Toast.LENGTH_SHORT).show();
                        return false;
                    }

                }
            }
        }

     /*   else if (!userEmailPhoneData.equals("")) {
            if (isInteger(userEmailPhoneData)) {
                if (userEmailPhoneData.length() > 12 || userEmailPhoneData.length() < 7) {
                    Toast.makeText(LoginActivity.this, "please enter valid phone number", Toast.LENGTH_SHORT).show();
                    return false;
                }
            } else {
                if (!userEmailPhoneData.matches(emailRegex)) {
                    Toast.makeText(LoginActivity.this, "please enter valid email", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }

        }*/
        return true;
    }

    private void alert_dialog_message(String value) {

        final LayoutInflater inflater = LoginActivity.this.getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.test_dialog_xml_otp, null);

        final ImageView close_dialog = alertLayout.findViewById(R.id.close_dialog);
        final TextView error_message = alertLayout.findViewById(R.id.error_message);





        if(value.equals("1"))
        {
            error_message.setText(getResources().getString(R.string.Please_enter_password2));
        }else if(value.equals("2"))
        {
            error_message.setText(getResources().getString(R.string.please_enter_email));
        }else if(value.equals("3"))
        {
            error_message.setText(getResources().getString(R.string.please_enter_valid_email));
        }else if(value.equals("4"))
        {
            error_message.setText(getResources().getString(R.string.please_enter_phone));
        }else if(value.equals("5"))
        {
            error_message.setText(getResources().getString(R.string.Please_remove_zero_from_start));
        }else if(value.equals("6"))
        {
            error_message.setText(getResources().getString(R.string.please_enter_10_digit_valid));
        }else if(value.equals("7"))
        {
            error_message.setText(loginMessage);
        }else if(value.equals("400"))
        {
            error_message.setText(getResources().getString(R.string.case_4_0_0));
        }else if(value.equals("401"))
        {
            error_message.setText(getResources().getString(R.string.case_4_0_1));
        }else if(value.equals("404"))
        {
            error_message.setText(getResources().getString(R.string.case_4_0_4));
        }else if(value.equals("500"))
        {
            error_message.setText(getResources().getString(R.string.case_5_0_0));
        }else if(value.equals("503"))
        {
            error_message.setText(getResources().getString(R.string.case_5_0_3));
        }else if(value.equals("504"))
        {
            error_message.setText(getResources().getString(R.string.case_5_0_4));
        }else if(value.equals("511"))
        {
            error_message.setText(getResources().getString(R.string.case_5_1_1));
        }else if(value.equals("504"))
        {
            error_message.setText(getResources().getString(R.string.case_5_0_4));
        }else if(value.equals("default"))
        {
            error_message.setText(getResources().getString(R.string.default_api_error));
        }else if(value.equals("14521"))
        {
            error_message.setText(getResources().getString(R.string.password_must_be));
        }



        final AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this);

        alert.setView(alertLayout);
        alert.setCancelable(false);

        dialogs = alert.create();
        dialogs.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogs.show();
        dialogs.setCanceledOnTouchOutside(true);
       // dialogs.getWindow().setBackgroundDrawableResource(R.color.black_transparent_50);
       // dialogs.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));



        close_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialogs.dismiss();
            }
        });
    }


    private void login_api() {
        String userEmailPhoneData2 = "";

        if(key.equals("1"))
        {
            userEmailPhoneData2 =userEmail;
        }else if(key.equals("2"))
        {

            userEmailPhoneData2 =userPhone;
        }

        Log.e("userEmailPhoneData2",userEmailPhoneData2+"ok");
        Log.e("userEmailPhoneData2",userPhone+"ok");
        Log.e("userEmailPhoneData2",userEmail+"ok");
        Log.e("userEmailPhoneData2",userPasswordData+"ok");


        final ProgressDialog pd = new ProgressDialog(LoginActivity.this);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();


        Call<LoginModelPython> call = API_Client.getClient().login(email_edittext.getText().toString(),password_edit_text.getText().toString()
            ,"fdhsg" );

        call.enqueue(new Callback<LoginModelPython>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<LoginModelPython> call, Response<LoginModelPython> response) {
                pd.dismiss();


                try {
                    if (response.isSuccessful()) {
                        String message = response.body().getMessage();
                        String success = response.body().getStatus();
                        loginMessage = message;



                        if (success.equals("true") || success.equals("True")) {

                            LoginModelPython loginModel = response.body();
                            TokenPython tokenPython = loginModel.getToken();

                            String accessToken = tokenPython.getAccess();
                            String refreshToken = tokenPython.getRefresh();
                            UserID = "15";
                            Log.e("UserID",UserID);


                            SharedPreferences getUserIdData = getSharedPreferences("AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = getUserIdData.edit();
                            editor.putString("UserID", String.valueOf(UserID));
                            editor.putString("accessToken", String.valueOf(accessToken));
                            editor.apply();

                           // alert_dialog_message("7");


                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // To clean up all activities
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                             Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();


                        } else {
                            alert_dialog_message("7");
                            pd.dismiss();
                        }


                    } else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(getApplicationContext(), jObjError.getString("message"), Toast.LENGTH_LONG).show();

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
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (
                        Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<LoginModelPython> call, Throwable t) {
                Log.e("bhgyrrrthbh", String.valueOf(t));
                if (t instanceof IOException) {
                    Toast.makeText(getApplicationContext(), "This is an actual network failure :( inform the user and possibly retry)" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                } else {
                    Log.e("conversion issue", t.getMessage());
                    Toast.makeText(getApplicationContext(), "Please Check your Internet Connection...." + t.getMessage(), Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            }
        });


    }

    private void getUserFormData() {
        userPhone = user_phone_edit_text.getText().toString();
        String mysz2 = email_edittext.getText().toString();
        userEmail = mysz2.replaceAll("\\s","");
        userEmailPhoneData = email_edit_text.getText().toString();
        userPasswordData = password_edit_text.getText().toString();
    }

    private void inits() {
        forgot_password = findViewById(R.id.forgot_password);
        email_edittext123 = findViewById(R.id.email_edittext123);
        support = findViewById(R.id.support);
        help_pages = findViewById(R.id.help_pages);
        joinus = findViewById(R.id.joinus);
        email_edit_text = findViewById(R.id.email_edit_text);
        password_edit_text = findViewById(R.id.password_edit_text);
        login_button = findViewById(R.id.login_button);


        user_phone_edit_text = findViewById(R.id.user_phone_edit_text);
        phone_edittext = findViewById(R.id.phone_edittext);
        active_phone_layout = findViewById(R.id.active_phone_layout);
        with_phone_green_text = findViewById(R.id.with_phone_green_text);
        support = findViewById(R.id.support);
        active_email_layout = findViewById(R.id.active_email_layout);
        email_edittext = findViewById(R.id.email_edittext);
        help_pages = findViewById(R.id.help_pages);
        with_email_green_text = findViewById(R.id.with_email_green_text);
        joinus = findViewById(R.id.joinus);

    }
    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return false;
        } catch(NullPointerException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }
}