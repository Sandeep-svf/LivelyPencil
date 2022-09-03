package com.webnmobapps.livelyPencil.Activity.Setting;

import static com.webnmobapps.livelyPencil.RetrofitApi.API_Client.BASE_LOGO_IMAGE;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.hbb20.CountryCodePicker;
import com.webnmobapps.livelyPencil.Activity.JoinUs.NameEmailActivity;
import com.webnmobapps.livelyPencil.Activity.JoinUs.PasscodeValidationActivity;
import com.webnmobapps.livelyPencil.Activity.JoinUs.ProfilePhotoActivity;
import com.webnmobapps.livelyPencil.Model.ContactInformationModel;
import com.webnmobapps.livelyPencil.Model.EditContactSettingModel;
import com.webnmobapps.livelyPencil.Model.Record.ContactInformationResult;
import com.webnmobapps.livelyPencil.Model.Record.TvSettingsResult;
import com.webnmobapps.livelyPencil.Model.TvSettingsModel;
import com.webnmobapps.livelyPencil.R;
import com.webnmobapps.livelyPencil.RetrofitApi.API_Client;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import in.aabhasjindal.otptextview.OtpTextView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactInformationActivity extends AppCompatActivity {

    AppCompatImageView back_arrow;
    CountryCodePicker ccp;
    AlertDialog dialogs;
    OtpTextView otpData;
    AppCompatTextView phone_number_approved_verify_layout, email_approved_verify_layout;
    AppCompatEditText contact_email_edit_text, contact_number_edit_text, contact_web_edit_text;
    AppCompatButton contact_save_change_button, verify_otp_button;
    private String user_id, OTPData,id, emialData, numberData,webUrlData, countryCode, mobileNumber , phoneDataApi ;
    List<ContactInformationResult> contactInformationResultList = new ArrayList<>();
    private String regexEmail = "(?:[A-Za-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[A-Za-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[A-Za-z0-9](?:[A-Za-z0-9-]*[A-Za-z0-9])?\\.)+[A-Za-z0-9](?:[A-Za-z0-9-]*[A-Za-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[A-Za-z0-9]:(?:|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

    // Firebase
    public String phoneVerificationId;
    public FirebaseAuth mAuth;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_information);

        inits();


        FirebaseApp.initializeApp(ContactInformationActivity.this);
        context = ContactInformationActivity.this;
        FirebaseApp.getApps(context);
        mAuth = FirebaseAuth.getInstance();

        SharedPreferences sharedPreferences= getSharedPreferences("AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
        user_id=sharedPreferences.getString("UserID","");
        countryCode = String.valueOf( ccp.getSelectedCountryCode());

        // API
        contact_details_api();


        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        phone_number_approved_verify_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                get_form_data();

                if(validation_otp())
                {
                    if(phone_number_approved_verify_layout.getText().toString().equalsIgnoreCase("Verify"))
                    {

                       // Toast.makeText(ContactInformationActivity.this, "OTP Method Running ....", Toast.LENGTH_SHORT).show();

                        Log.e("otp_data",countryCode + numberData);


                        sendVerificationCode("+"+countryCode + numberData);


                    }else
                    {
                        Toast.makeText(ContactInformationActivity.this, "Phone Number Already Verified", Toast.LENGTH_SHORT).show();

                    }
                }


            }
        });

        contact_number_edit_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




            }
        });


        contact_number_edit_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {



            }

            @SuppressLint("ResourceAsColor")
            @Override
            public void afterTextChanged(Editable editable) {

                if(phoneDataApi.equals(contact_number_edit_text.getText().toString()))
                {
                    phone_number_approved_verify_layout.setText(R.string.approved);
                    phone_number_approved_verify_layout.setTextColor(getResources().getColor(R.color.settingEditBackgroundColor));
                    Log.e("fdsdf", "old number ########" +phone_number_approved_verify_layout.getText().toString());

                }else
                {
                    phone_number_approved_verify_layout.setText(R.string.verify);
                    phone_number_approved_verify_layout.setTextColor(getResources().getColor(R.color.red));
                    Log.e("fdsdf", "Not match @@@@@@@@@@"+phone_number_approved_verify_layout.getText().toString());
                }

            }
        });




        contact_save_change_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                get_form_data();


                if(validation())
                {
                    edit_contact_seting_api();
                }

            }
        });

    }

    private boolean validation_otp() {

        if(numberData.equals(""))
        {
            Toast.makeText(ContactInformationActivity.this, "Please Mobile Number Email", Toast.LENGTH_SHORT).show();
            return  false;
        }else if(ccp.getSelectedCountryCode().equalsIgnoreCase(""))
        {
            Toast.makeText(ContactInformationActivity.this, "Please Select Country Code", Toast.LENGTH_SHORT).show();
            return  false;
        }else if((numberData.length() < 8) || (numberData.length() > 13) )
        {
            Toast.makeText(ContactInformationActivity.this, "Please Enter Number Between 8 To 13 Digits", Toast.LENGTH_SHORT).show();
            return  false;
        }
        return  true;
    }

    private void sendVerificationCode(String mobileNumber) {

        // this method is used for getting
        // OTP on user phone number.
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                mobileNumber, // first parameter is user's mobile number
                60, // second parameter is time limit for OTP
                // verification which is 60 seconds in our case.
                TimeUnit.SECONDS, // third parameter is for initializing units
                // for time period which is in seconds in our case.
                TaskExecutors.MAIN_THREAD, // this task will be excuted on Main thread.
                mCallBack // we are calling callback method when we recieve OTP for
                // auto verification of user.
        );

    }


    private PhoneAuthProvider.OnVerificationStateChangedCallbacks

            // initializing our callbacks for on
            // verification callback method.
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        // below method is used when
        // OTP is sent from Firebase
        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            // when we receive the OTP it
            // contains a unique id which
            // we are storing in our string
            // which we have already created.
           /* Intent intent=new Intent(getApplicationContext(),Verification_Activity.class);
            startActivity(intent);*/
            phoneVerificationId = s;
            //viewFlipper.setDisplayedChild(1);
            aleret_dialog();


        }

        // this method is called when user
        // receive OTP from Firebase.
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            // below line is used for getting OTP code
            // which is sent in phone auth credentials.
            final String code = phoneAuthCredential.getSmsCode();
            signInWithPhoneAuthCredential(phoneAuthCredential);
            // checking if the code
            // is null or not.

            if (code != null) {
                //   Forget_Login();
                // if the code is not null then
                // we are setting that code to
                // our OTP edittext field.
//                edtOTP.setText(code);

                // after setting this code
                // to OTP edittext field we
                // are calling our verifycode method.
//                verifyCode(code);
            }
        }

        // this method is called when firebase doesn't
        // sends our OTP code due to any error or issue.
        @Override
        public void onVerificationFailed(FirebaseException e) {
            // displaying error message with firebase exception.

            Toast.makeText(ContactInformationActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            Toast.makeText(ContactInformationActivity.this,"OTP Verification Failed", Toast.LENGTH_LONG).show();
        }
    };

    private void aleret_dialog() {

        final LayoutInflater inflater = ContactInformationActivity.this.getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.otp_dialog_xml, null);

         otpData = alertLayout.findViewById(R.id.submit_otp);
        verify_otp_button = alertLayout.findViewById(R.id.verify_otp_button);

        final AlertDialog.Builder alert = new AlertDialog.Builder(ContactInformationActivity.this);

        alert.setView(alertLayout);
        alert.setCancelable(false);

        dialogs = alert.create();
        dialogs.show();
        dialogs.setCanceledOnTouchOutside(true);



        verify_otp_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(otpData.getOTP().equalsIgnoreCase(""))
                {
                    Toast.makeText(ContactInformationActivity.this, "Please Enter OTP", Toast.LENGTH_SHORT).show();
                }else if(!(otpData.getOTP().length() == 6))
                {
                    Toast.makeText(ContactInformationActivity.this, "Please Enter Six Digit Valid OTP", Toast.LENGTH_SHORT).show();

                }else
                {
                    OTPData = otpData.getOTP();
                    Toast.makeText(ContactInformationActivity.this, OTPData, Toast.LENGTH_SHORT).show();
                    dialogs.dismiss();
                    verifyCode();
                }

            }
        });




    }

    private void verifyCode() {

            PhoneAuthCredential credential =
                    PhoneAuthProvider.getCredential(phoneVerificationId, OTPData);
            signInWithPhoneAuthCredential(credential);

    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(ContactInformationActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            phoneDataApi = numberData;
                            Toast.makeText(ContactInformationActivity.this, "OTP Verification Successful", Toast.LENGTH_SHORT).show();
                            phone_number_approved_verify_layout.setText(R.string.approved);
                            phone_number_approved_verify_layout.setTextColor(getResources().getColor(R.color.settingEditBackgroundColor));


                        } else {
                            Toast.makeText(ContactInformationActivity.this, "OTP Verification Failed", Toast.LENGTH_SHORT).show();
                            if (task.getException() instanceof
                                    FirebaseAuthInvalidCredentialsException) {
                            }
                        }
                    }
                });
    }


    private boolean validation() {
        if(emialData.equals(""))
        {
            Toast.makeText(ContactInformationActivity.this, "Please Enter Email", Toast.LENGTH_SHORT).show();
            return  false;
        }else if (!emialData.matches(regexEmail))
        {
            Toast.makeText(ContactInformationActivity.this, "Please Enter Valid Email Address", Toast.LENGTH_SHORT).show();
            return  false;
        }else if(numberData.equals(""))
        {
            Toast.makeText(ContactInformationActivity.this, "Please Enter Mobile Number", Toast.LENGTH_SHORT).show();
            return  false;
        }else if(phone_number_approved_verify_layout.getText().toString().equalsIgnoreCase("Verify"))
        {
            Toast.makeText(ContactInformationActivity.this, "Please Verify Mobile Number", Toast.LENGTH_SHORT).show();
            return  false;
        } else if(webUrlData.equals(""))
        {
            Toast.makeText(ContactInformationActivity.this, "Please Enter Web Url", Toast.LENGTH_SHORT).show();
            return  false;
        }else if(ccp.getSelectedCountryCode().equalsIgnoreCase(""))
        {
            Toast.makeText(ContactInformationActivity.this, "Please Select Country Code", Toast.LENGTH_SHORT).show();
            return  false;
        }else if(phone_number_approved_verify_layout.getText().toString().equalsIgnoreCase("Verify"))
        {
            Toast.makeText(ContactInformationActivity.this, "Please Verify Your Phone Number", Toast.LENGTH_SHORT).show();
            return  false;
        }

        return  true;
    }

    private void get_form_data() {

        emialData = contact_email_edit_text.getText().toString();
        numberData = contact_number_edit_text.getText().toString();
        webUrlData = contact_web_edit_text.getText().toString();

    }

    private void edit_contact_seting_api() {
        final ProgressDialog pd = new ProgressDialog(ContactInformationActivity.this);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();

        Log.e("edit_contact",emialData);
        Log.e("edit_contact",numberData);
        Log.e("edit_contact",webUrlData);


        Call<EditContactSettingModel> call = API_Client.getClient().editContactInformation(user_id,
                emialData,
                numberData,
                webUrlData);

        call.enqueue(new Callback<EditContactSettingModel>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<EditContactSettingModel> call, Response<EditContactSettingModel> response) {
                pd.dismiss();


                try {
                    if (response.isSuccessful()) {
                        String message = response.body().getMessage();
                        String success = response.body().getSuccess();
                        if (success.equals("true") || success.equals("True")) {

                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

                        } else {
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
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
            public void onFailure(Call<EditContactSettingModel> call, Throwable t) {
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

    private void contact_details_api() {

        final ProgressDialog pd = new ProgressDialog(ContactInformationActivity.this);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();



        Call<ContactInformationModel> call = API_Client.getClient().contactInformation(user_id);

        call.enqueue(new Callback<ContactInformationModel>() {
            @SuppressLint("ResourceAsColor")
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<ContactInformationModel> call, Response<ContactInformationModel> response) {
                pd.dismiss();


                try {
                    if (response.isSuccessful()) {
                        String message = response.body().getMessage();
                        String success = response.body().getSuccess();
                        if (success.equals("true") || success.equals("True")) {

                            contactInformationResultList = response.body().getRecord();

                            phoneDataApi = contactInformationResultList.get(0).getPhone();
                            contact_email_edit_text.setText(contactInformationResultList.get(0).getEmail());
                            contact_number_edit_text.setText(contactInformationResultList.get(0).getPhone());
                            contact_web_edit_text.setText(contactInformationResultList.get(0).getUrl());
                            id = String.valueOf(contactInformationResultList.get(0).getId());

                            phone_number_approved_verify_layout.setText(R.string.approved);


                        } else {
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
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
            public void onFailure(Call<ContactInformationModel> call, Throwable t) {
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

    private void alert_dialog_message(String value) {

        final LayoutInflater inflater = ContactInformationActivity.this.getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.test_dialog_xml_otp, null);

        final ImageView close_dialog = alertLayout.findViewById(R.id.close_dialog);
        final TextView error_message = alertLayout.findViewById(R.id.error_message);

        if(value.equals("400"))
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

        final AlertDialog.Builder alert = new AlertDialog.Builder(ContactInformationActivity.this);

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

    private void inits() {

        phone_number_approved_verify_layout = findViewById(R.id.phone_number_approved_verify_layout);
        email_approved_verify_layout = findViewById(R.id.email_approved_verify_layout);
        ccp = findViewById(R.id.ccp2);
        contact_save_change_button = findViewById(R.id.contact_save_change_button);
        contact_web_edit_text = findViewById(R.id.contact_web_edit_text);
        contact_number_edit_text = findViewById(R.id.contact_number_edit_text);
        contact_email_edit_text = findViewById(R.id.contact_email_edit_text);
        back_arrow = findViewById(R.id.back_arrow);
    }
}