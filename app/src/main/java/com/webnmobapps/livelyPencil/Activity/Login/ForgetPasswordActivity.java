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

import android.app.Activity;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.hbb20.CountryCodePicker;
import com.webnmobapps.livelyPencil.Activity.JoinUs.NameEmailActivity;
import com.webnmobapps.livelyPencil.Activity.JoinUs.NameEmailActivity2;
import com.webnmobapps.livelyPencil.Activity.JoinUs.PasscodeValidationActivity;
import com.webnmobapps.livelyPencil.Activity.JoinUs.ProfilePhotoActivity;
import com.webnmobapps.livelyPencil.Activity.JoinUs.SelectIntrestActivity;
import com.webnmobapps.livelyPencil.Activity.JoinUs.SetPasswordActivity;
import com.webnmobapps.livelyPencil.Activity.Utility.HelpActivity;
import com.webnmobapps.livelyPencil.Activity.Utility.SupportActivity;
import com.webnmobapps.livelyPencil.Model.CheckUserModel;
import com.webnmobapps.livelyPencil.Model.RegisterModel;
import com.webnmobapps.livelyPencil.Model.SmFlaxibleModel;
import com.webnmobapps.livelyPencil.R;
import com.webnmobapps.livelyPencil.RetrofitApi.API_Client;

import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import in.aabhasjindal.otptextview.OtpTextView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgetPasswordActivity extends AppCompatActivity {
    ConstraintLayout with_phone, with_email;
    AppCompatTextView  with_phone_green_text,with_email_green_text;
    ConstraintLayout active_email_layout,active_phone_layout;
    LinearLayoutCompat phone_edittext;
    AppCompatEditText email_edittext, user_phone_edit_text;
    private  String key = "1";
    AppCompatButton login_button;
    LinearLayoutCompat email_edittext222;
    AppCompatTextView text_with_email, text_with_phone,login, joinus, support, help_pages;
    private String userPhone, userEmail;
    String countryCode;
    CountryCodePicker country_code_picker;
    AlertDialog dialogs;
    private String regexEmail = "(?:[A-Za-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[A-Za-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[A-Za-z0-9](?:[A-Za-z0-9-]*[A-Za-z0-9])?\\.)+[A-Za-z0-9](?:[A-Za-z0-9-]*[A-Za-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[A-Za-z0-9]:(?:|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            // edited here
            getWindow().setStatusBarColor(Color.WHITE);

        }

        intis();




        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                get_form_data();

                Log.e("check_data", userEmail);
                Log.e("check_data", userPhone);

                if(validation())
                {
                    //forget_password_api();

                   // check_user_api();
                    Intent intent = new Intent(ForgetPasswordActivity.this, ForgetPaawordPasscodeActivity.class);
                    intent.putExtra("userEmail",userEmail);
                    startActivity(intent);

                }
            }
        });

        with_email_green_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                active_email_layout.setVisibility(View.VISIBLE);
                active_phone_layout.setVisibility(View.GONE);
                phone_edittext.setVisibility(View.GONE);
                email_edittext222.setVisibility(View.VISIBLE);
                key = "1";

                //Toast.makeText(ForgetPasswordActivity.this, key, Toast.LENGTH_SHORT).show();
            }
        });

        with_phone_green_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                active_email_layout.setVisibility(View.GONE);
                active_phone_layout.setVisibility(View.VISIBLE);
                phone_edittext.setVisibility(View.VISIBLE);
                email_edittext222.setVisibility(View.GONE);
                key = "2";
               // country_code_picker.setDefaultCountryUsingNameCode("TR");
                //country_code_picker.setDefaultCountryUsingPhoneCode(+90);
                // Toast.makeText(ForgetPasswordActivity.this, key, Toast.LENGTH_SHORT).show();
            }
        });


        help_pages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ForgetPasswordActivity.this, HelpActivity.class);
                startActivity(intent);
            }
        });

        support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ForgetPasswordActivity.this, SupportActivity.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ForgetPasswordActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        joinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ForgetPasswordActivity.this, NameEmailActivity2.class);
                startActivity(intent);
            }
        });

     /*   text_with_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                with_email.setVisibility(View.GONE);
                text_with_email.setVisibility(View.VISIBLE);
                with_phone.setVisibility(View.VISIBLE);
                text_with_phone.setVisibility(View.GONE);
                with_phone.setBackground(getResources().getDrawable(R.drawable.forget_password_border_line));

            }
        });

        text_with_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                with_phone.setBackground(getResources().getDrawable(R.drawable.forget_password_border_line));
                with_phone.setVisibility(View.VISIBLE);
                text_with_phone.setVisibility(View.VISIBLE);
                text_with_email.setVisibility(View.GONE);
                with_email.setVisibility(View.VISIBLE);
            }
        });*/
    }


    private void check_user_api() {
        String emailPhone;



        // show till load api data

        final ProgressDialog pd = new ProgressDialog(ForgetPasswordActivity.this);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();

        Call<CheckUserModel> call = API_Client.getClient().checkUser(userEmail);



        call.enqueue(new Callback<CheckUserModel>() {
            @Override
            public void onResponse(Call<CheckUserModel> call, Response<CheckUserModel> response) {
                pd.dismiss();
                try {
                    //if api response is successful ,taking message and success
                    if (response.isSuccessful()) {
                        String message = response.body().getMessage();
                        String success = String.valueOf(response.body().getSuccess());

                        if (success.equals("true") || success.equals("True")) {
                           // Toast.makeText(getApplicationContext(), "Email or phone already register", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(ForgetPasswordActivity.this, ForgetPaawordPasscodeActivity.class);
                            intent.putExtra("userEmail",userEmail);
                            startActivity(intent);
                        } else {
                          /*  Intent intent = new Intent(NameEmailActivity.this, SetPasswordActivity.class);
                            startActivity(intent);*/
                            alert_dialog_message("6");
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
            public void onFailure(Call<CheckUserModel> call, Throwable t) {
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

    private boolean validation ()
    {
        if(key.equals("1") )
        {
            if(userEmail.equals(""))
            {
                alert_dialog_message("1");
                //Toast.makeText(ForgetPasswordActivity.this, "Please enter email", Toast.LENGTH_SHORT).show();
                return false;
            }else if(!userEmail.matches(regexEmail))
            {
                alert_dialog_message("2");
             //   Toast.makeText(ForgetPasswordActivity.this, "Please enter valid email", Toast.LENGTH_SHORT).show();
                return false;
            }

        }else if(key.equals("2")) {
            if (userPhone.equals("")) {
                alert_dialog_message("3");
                //Toast.makeText(ForgetPasswordActivity.this, "Please enter phone", Toast.LENGTH_SHORT).show();
                return false;
            } else {
                String firstDigit = userPhone.substring(0, 1);
                if (firstDigit.equals("0") || (userPhone.length() != 10)) {
                    if (firstDigit.equals("0")) {
                       // Toast.makeText(ForgetPasswordActivity.this, "Please remove zero from start.", Toast.LENGTH_SHORT).show();
                        alert_dialog_message("4");
                        return false;
                    } else {
                       // Toast.makeText(ForgetPasswordActivity.this, "Please enter 10 digit valid phone number.", Toast.LENGTH_SHORT).show();
                        alert_dialog_message("5");
                        return false;
                    }

                }
            }
        }
        return  true;
    }

    private void alert_dialog_message(String value) {

        final LayoutInflater inflater = ForgetPasswordActivity.this.getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.test_dialog_xml_otp, null);

        final ImageView close_dialog = alertLayout.findViewById(R.id.close_dialog);
        final TextView error_message = alertLayout.findViewById(R.id.error_message);





        if(value.equals("1"))
        {
            error_message.setText(getResources().getString(R.string.Please_enter_email));
        }else if(value.equals("2"))
        {
            error_message.setText(getResources().getString(R.string.please_enter_valid_email));
        }else if(value.equals("3"))
        {
            error_message.setText(getResources().getString(R.string.please_enter_phone));
        }else if(value.equals("4"))
        {
            error_message.setText(getResources().getString(R.string.Please_remove_zero_from_start));
        }else if(value.equals("5"))
        {
            error_message.setText(getResources().getString(R.string.please_enter_10_digit_valid));
        }else if(value.equals("6"))
        {
            error_message.setText(getResources().getString(R.string.did_not_find_account));
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
        }



        final AlertDialog.Builder alert = new AlertDialog.Builder(ForgetPasswordActivity.this);

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

    private void get_form_data() {

        userPhone = user_phone_edit_text.getText().toString();
        String mysz2 = email_edittext.getText().toString();
        userEmail = mysz2.replaceAll("\\s","");
        countryCode = country_code_picker.getSelectedCountryCode();

    }

    private void intis() {
        email_edittext222 = findViewById(R.id.email_edittext222);
        text_with_phone = findViewById(R.id.text_with_phone);
     //   text_with_email = findViewById(R.id.text_with_email);
        country_code_picker = findViewById(R.id.country_code_picker);
        with_phone = findViewById(R.id.with_phone);
        with_email = findViewById(R.id.with_email);
        joinus = findViewById(R.id.joinus);
        login = findViewById(R.id.login);
        support = findViewById(R.id.support);
        login_button = findViewById(R.id.login_button);
        help_pages = findViewById(R.id.help_pages);



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


}