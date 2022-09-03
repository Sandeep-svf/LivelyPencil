package com.webnmobapps.livelyPencil.Activity.JoinUs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.content.Intent;
import android.widget.Toast;

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
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.webnmobapps.livelyPencil.Model.CheckUserModel;
import com.webnmobapps.livelyPencil.Model.SmFlaxibleModel;
import com.webnmobapps.livelyPencil.R;
import com.webnmobapps.livelyPencil.RetrofitApi.API_Client;

import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import in.aabhasjindal.otptextview.OtpTextView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PasscodeValidationActivity extends AppCompatActivity {

    androidx.appcompat.widget.AppCompatButton confirm_button;
    androidx.appcompat.widget.AppCompatTextView skip_layout;
    OtpTextView otpText;
    private String userOtpData, userEmail, userPhone, device_token, countryCode,key;
    Context context;

    // Firebase
    public String phoneVerificationId;
    public FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passcode_validation);
        inits();


        FirebaseApp.initializeApp(PasscodeValidationActivity.this);
        context = PasscodeValidationActivity.this;
        FirebaseApp.getApps(context);


        mAuth = FirebaseAuth.getInstance();

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("ritik", "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        device_token = task.getResult().getToken();
                        Log.e("tokenn", "" + device_token);
                    }
                });

        //geting userID data
        SharedPreferences getUserIdData = getSharedPreferences("AUTHENTICATION_FILE_NAME", MODE_PRIVATE);
        userEmail = getUserIdData.getString("userEmail", "");
        userPhone = getUserIdData.getString("userPhone", "");
        countryCode = getUserIdData.getString("countryCode", "");
        key = getUserIdData.getString("key", "");


        if(key.equals("1"))
        {
            email_verification_api();

        }else if(key.equals("2"))
        {
            sendVerificationCode("+"+countryCode + userPhone);
        }

        confirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getUserFormData();

                if(key.equals("1"))
                {
                    confirmation_email_api();
                }else if(key.equals("2"))
                {
                    verifyCode();
                }



            }
        });

        skip_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(PasscodeValidationActivity.this, ProfilePhotoActivity.class);
                startActivity(intent);
            }
        });

    }

    private void confirmation_email_api() {
        // show till load api data
        final ProgressDialog pd = new ProgressDialog(PasscodeValidationActivity.this);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();

        Call<SmFlaxibleModel> call = API_Client.getClient().emailVerificationOtp(userEmail,userOtpData);

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
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(PasscodeValidationActivity.this, ProfilePhotoActivity.class);
                            startActivity(intent);
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

    private void email_verification_api() {

        // show till load api data
        final ProgressDialog pd = new ProgressDialog(PasscodeValidationActivity.this);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();

        Call<SmFlaxibleModel> call = API_Client.getClient().emailVerification(userEmail,"12345");



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
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
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

    private void getUserFormData() {
        userOtpData = otpText.getOTP();
    }


    private void inits() {

        confirm_button = findViewById(R.id.confirm_button);
        skip_layout = findViewById(R.id.skip_layout);
        otpText = findViewById(R.id.userFirstName);
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

            Intent intent = new Intent(PasscodeValidationActivity.this, NameEmailActivity.class);
            startActivity(intent);
            Toast.makeText(PasscodeValidationActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            Toast.makeText(PasscodeValidationActivity.this,"Please enter valid number", Toast.LENGTH_LONG).show();
        }
    };

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(PasscodeValidationActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {


                            Toast.makeText(PasscodeValidationActivity.this, "OTP Verification Successful", Toast.LENGTH_SHORT).show();

                           Intent intent = new Intent(PasscodeValidationActivity.this, ProfilePhotoActivity.class);
                             startActivity(intent);

                        } else {
                            Toast.makeText(PasscodeValidationActivity.this, "Please enter valid OTP", Toast.LENGTH_SHORT).show();
                            if (task.getException() instanceof
                                    FirebaseAuthInvalidCredentialsException) {
                            }
                        }
                    }
                });
    }


    private void verifyCode() {
        String code = userOtpData;
        if ((!code.equals("")) && (code.length() == 6)) {

            PhoneAuthCredential credential =
                    PhoneAuthProvider.getCredential(phoneVerificationId, code);
            signInWithPhoneAuthCredential(credential);
        } else if (code.length() != 6) {
            Toast.makeText(this, "Please enter six digit valid otp", Toast.LENGTH_SHORT).show();

        }
    }

}