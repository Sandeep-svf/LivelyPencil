package com.webnmobapps.livelyPencil.Activity.JoinUs;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.content.Intent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hbb20.CountryCodePicker;
import com.webnmobapps.livelyPencil.Activity.Login.ForgetPasswordActivity;
import com.webnmobapps.livelyPencil.Activity.Login.LoginActivity;
import com.webnmobapps.livelyPencil.Activity.Utility.HelpActivity;
import com.webnmobapps.livelyPencil.Activity.Utility.SupportActivity;
import com.webnmobapps.livelyPencil.Model.CheckUserModel;
import com.webnmobapps.livelyPencil.R;
import com.webnmobapps.livelyPencil.RetrofitApi.API_Client;

import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NameEmailActivity extends AppCompatActivity {
    androidx.appcompat.widget.AppCompatButton next_button;
    CountryCodePicker ccp;
    private String key = "1" ;
    LinearLayoutCompat phone_edittext;
    AppCompatTextView joinus, help_pages, support, with_phone_green_text,with_email_green_text;
    ConstraintLayout email_edittext_layout3, email_edittext_layout2,active_email_layout,active_phone_layout;
    AppCompatEditText userFirstName, userSurname, user_phone_edit_text, user_phone_email_edit_text3,email_edittext;
    private String userEmail,userPhone, usersurName, userName;
    private String emailRegex = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private String regexInt = "^[0-9]*$";
    private String regexEmail = "(?:[A-Za-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[A-Za-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[A-Za-z0-9](?:[A-Za-z0-9-]*[A-Za-z0-9])?\\.)+[A-Za-z0-9](?:[A-Za-z0-9-]*[A-Za-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[A-Za-z0-9]:(?:|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
    AlertDialog dialogs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_email);


      /*  View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
*/



        inits();

       // Toast.makeText(NameEmailActivity.this, key, Toast.LENGTH_SHORT).show();

        with_email_green_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                active_email_layout.setVisibility(View.VISIBLE);
                active_phone_layout.setVisibility(View.GONE);
                phone_edittext.setVisibility(View.GONE);
                email_edittext.setVisibility(View.VISIBLE);
                key = "1";
                userPhone="";
              //  Toast.makeText(NameEmailActivity.this, key, Toast.LENGTH_SHORT).show();
            }
        });

        with_phone_green_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userEmail="";
                active_email_layout.setVisibility(View.GONE);
                active_phone_layout.setVisibility(View.VISIBLE);
                phone_edittext.setVisibility(View.VISIBLE);
                email_edittext.setVisibility(View.GONE);
                key = "2";
              //  Toast.makeText(NameEmailActivity.this, key, Toast.LENGTH_SHORT).show();
            }
        });



        help_pages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NameEmailActivity.this, HelpActivity.class);
                startActivity(intent);
            }
        });

        support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NameEmailActivity.this, SupportActivity.class);
                startActivity(intent);
            }
        });



        joinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NameEmailActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                get_form_data();

                Log.e("country_code",   ccp.getSelectedCountryCode());


                if (validation()) {
                    check_user_api();
                }

            }
        });

     /*   user_phone_email_edit_text2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if(user_phone_email_edit_text2.getText().toString().length() != 0)
                {

                    Toast.makeText(NameEmailActivity.this, user_phone_email_edit_text2.getText().toString(), Toast.LENGTH_SHORT).show();
                    if (user_phone_email_edit_text2.getText().toString().matches(regexInt)) {
                        //  Ram...
                        ccp.setVisibility(View.VISIBLE);
                        email_edittext_layout2.setVisibility(View.VISIBLE);
                        email_edittext_layout3.setVisibility(View.GONE);
                        String temp2 = user_phone_email_edit_text3.getText().toString();


                    } else if (!user_phone_email_edit_text2.getText().toString().matches(regexEmail)) {

                        ccp.setVisibility(View.GONE);
                        email_edittext_layout2.setVisibility(View.GONE);
                        email_edittext_layout3.setVisibility(View.VISIBLE);
                        String temp = user_phone_email_edit_text2.getText().toString();
                        user_phone_email_edit_text3.setText(temp);
                    }
                }
            }
        });


        user_phone_email_edit_text3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });*/

    }

    private boolean validation() {

        Log.e("dfsdfsd", String.valueOf(userPhone.length()));



        if (userName.equals("")) {

            alert_dialog_message("1");
            //Toast.makeText(NameEmailActivity.this, "Please enter name", Toast.LENGTH_SHORT).show();
            return false;
        } else if (usersurName.equals("")) {
            alert_dialog_message("2");
           // Toast.makeText(NameEmailActivity.this, "Please enter surname", Toast.LENGTH_SHORT).show();
            return false;
        }else if(key.equals("1") )
        {
            if(userEmail.equals(""))
            {
                alert_dialog_message("3");
                //Toast.makeText(NameEmailActivity.this, "Please enter email", Toast.LENGTH_SHORT).show();
                return false;
            }else if(!userEmail.matches(regexEmail))
            {
                alert_dialog_message("4");
                //Toast.makeText(NameEmailActivity.this, "Please enter valid email", Toast.LENGTH_SHORT).show();
                return false;
            }

        }else if(key.equals("2"))
        {
            if(userPhone.equals(""))
            {
                alert_dialog_message("5");
               // Toast.makeText(NameEmailActivity.this, "Please enter phone", Toast.LENGTH_SHORT).show();
                return false;
            }else
            {
                String firstDigit = userPhone.substring(0,1);
                if(firstDigit.equals("0") || (userPhone.length() != 10))
                {
                    if(firstDigit.equals("0"))
                    {
                        alert_dialog_message("6");
                       // Toast.makeText(NameEmailActivity.this, "Please remove zero from start.", Toast.LENGTH_SHORT).show();
                        return false;
                    }else
                    {
                        alert_dialog_message("7");
                       // Toast.makeText(NameEmailActivity.this, "Please enter 10 digit valid phone number.", Toast.LENGTH_SHORT).show();
                        return false;
                    }

                }
            }
        }else
        if (!userEmail.matches(regexEmail)) {
            alert_dialog_message("4");
          //  Toast.makeText(NameEmailActivity.this, "please enter valid email", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void alert_dialog_message(String value) {

        final LayoutInflater inflater = NameEmailActivity.this.getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.test_dialog_xml_otp, null);

        final ImageView close_dialog = alertLayout.findViewById(R.id.close_dialog);
        final TextView error_message = alertLayout.findViewById(R.id.error_message);


        if(value.equals("1"))
        {
            error_message.setText(getResources().getString(R.string.please_enter_name));
        }else if(value.equals("2"))
        {
            error_message.setText(getResources().getString(R.string.please_enter_surname));
        }else if(value.equals("3"))
        {
            error_message.setText(getResources().getString(R.string.please_enter_email));
        }else if(value.equals("4"))
        {
            error_message.setText(getResources().getString(R.string.please_enter_valid_email));
        }else if(value.equals("5"))
        {
            error_message.setText(getResources().getString(R.string.please_enter_phone));
        }else if(value.equals("6"))
        {
            error_message.setText(getResources().getString(R.string.Please_remove_zero_from_start));
        }else if(value.equals("7"))
        {
            error_message.setText(getResources().getString(R.string.please_enter_10_digit_valid));
        }else if(value.equals("12222"))
        {
            error_message.setText(getResources().getString(R.string.already_register));
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



        final AlertDialog.Builder alert = new AlertDialog.Builder(NameEmailActivity.this);

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

        userName = userFirstName.getText().toString();
        usersurName = userSurname.getText().toString();
        userPhone = user_phone_edit_text.getText().toString();
        userEmail = email_edittext.getText().toString();
    }

    private void inits() {

        phone_edittext = findViewById(R.id.phone_edittext);
        active_phone_layout = findViewById(R.id.active_phone_layout);
        with_phone_green_text = findViewById(R.id.with_phone_green_text);
        support = findViewById(R.id.support);
        active_email_layout = findViewById(R.id.active_email_layout);
        email_edittext = findViewById(R.id.email_edittext);
        help_pages = findViewById(R.id.help_pages);
        with_email_green_text = findViewById(R.id.with_email_green_text);
        joinus = findViewById(R.id.joinus);
        next_button = findViewById(R.id.next_button);
        userFirstName = findViewById(R.id.nameEditText);
        userSurname = findViewById(R.id.surnameEditText);
        user_phone_edit_text = findViewById(R.id.user_phone_edit_text);
        user_phone_email_edit_text3 = findViewById(R.id.user_phone_email_edit_text3);
        email_edittext_layout2 = findViewById(R.id.email_edittext_layout2);
        email_edittext_layout3 = findViewById(R.id.email_edittext_layout3);
        ccp = findViewById(R.id.ccp);
    }


    private void check_user_api() {
        String emailPhone;
        if(key.equals("1"))
        {
             emailPhone = userEmail;
        }else
        {
            emailPhone = userPhone;
        }


        // show till load api data

        final ProgressDialog pd = new ProgressDialog(NameEmailActivity.this);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();

        Call<CheckUserModel> call = API_Client.getClient().checkUser(emailPhone);

        Log.e("userData","userName: "+userName);
        Log.e("userData","usersurName: "+usersurName);
        Log.e("userData","userEmail: "+userEmail);
        Log.e("userData","userPhone: "+userPhone);
        Log.e("userData","country code : "+ccp.getSelectedCountryCode());

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
                            alert_dialog_message("12222");
                            //Toast.makeText(getApplicationContext(), "Email or phone already register", Toast.LENGTH_LONG).show();
                            if(key.equals("1"))
                            {
                                userPhone = "";
                            }else
                            {
                                userEmail = "";
                            }

                        } else {

                            SharedPreferences getUserIdData = getApplicationContext().getSharedPreferences("AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = getUserIdData.edit();
                            editor.putString("userName", String.valueOf(userName));
                            editor.putString("usersurName", String.valueOf(usersurName));
                            editor.putString("userEmail", String.valueOf(userEmail));
                            editor.putString("userPhone", String.valueOf(userPhone));
                            editor.putString("countryCode", String.valueOf( ccp.getSelectedCountryCode()));
                            editor.putString("key", String.valueOf( key));

                            editor.apply();

                            Intent intent = new Intent(NameEmailActivity.this, SetPasswordActivity.class);
                            startActivity(intent);

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


    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        } catch (NullPointerException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }
}