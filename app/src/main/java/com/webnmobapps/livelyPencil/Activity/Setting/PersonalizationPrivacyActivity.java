package com.webnmobapps.livelyPencil.Activity.Setting;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;

import android.app.ProgressDialog;
import android.content.Context;
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

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.webnmobapps.livelyPencil.Model.PPSettingsModel;
import com.webnmobapps.livelyPencil.Model.Personal_Information_Settings_Model;
import com.webnmobapps.livelyPencil.Model.PersonalizationPrivacyModel;
import com.webnmobapps.livelyPencil.Model.Record.PPSettingsRessult;
import com.webnmobapps.livelyPencil.R;
import com.webnmobapps.livelyPencil.RetrofitApi.API_Client;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PersonalizationPrivacyActivity extends AppCompatActivity {

    SwitchMaterial profile_switchMaterial, friend_request_switchMaterials, messaging_switchMaterials,
            mail_box_switchMaterial, relationship_switchMaterial, tv_channel_switchMaterial, radio_channel_switchMaterial,
            photos_switchMaterial, activities_switchMaterial, email_switchMaterial, phone_number_switchMaterial,
            web_url_switchMaterial,about_me_switchMaterial,my_static_switchMaterial;
    private String key, status, user_id;
    private String profileApiData, friendApiData, messageApiData, mailboxApiData, relationshipsApiData,tVChannelApiData, radioChannelApiData,
            photosApiData, activitiesApiData, emailApiData, phoneApiData, webUrlApiData, aboutMeApiData, myStaticApiData, idApiData;


    List<PPSettingsRessult> ppSettingsRessultList = new ArrayList<>();

    AppCompatImageView back_arrow;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personalization_privacy);

        inits();

        SharedPreferences sharedPreferences= getSharedPreferences("AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
        user_id=sharedPreferences.getString("UserID","");


        pp_settings_details_api();

        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        my_static_switchMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                key = "MyStatics";
                if(!my_static_switchMaterial.isChecked())
                {
                    status = "0";
                }else
                {
                    status = "1";
                }

                Log.e("key_status",key+" "+status);
                personalization_privacy_api(key,status);
            }
        });


        about_me_switchMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                key = "Aboutme";
                if(!about_me_switchMaterial.isChecked())
                {
                    status = "0";
                }else
                {
                    status = "1";
                }

                personalization_privacy_api(key,status);
            }
        });

        web_url_switchMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                key = "WebUrl";
                if(!web_url_switchMaterial.isChecked())
                {
                    status = "0";
                }else
                {
                    status = "1";
                }

                personalization_privacy_api(key,status);
            }
        });


        phone_number_switchMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                key = "Phone";
                if(!phone_number_switchMaterial.isChecked())
                {
                    status = "0";
                }else
                {
                    status = "1";
                }

                personalization_privacy_api(key,status);
            }
        });

        email_switchMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                key = "Email";
                if(!email_switchMaterial.isChecked())
                {
                    status = "0";
                }else
                {
                    status = "1";
                }

                personalization_privacy_api(key,status);
            }
        });

        activities_switchMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                key = "Activities";
                if(!activities_switchMaterial.isChecked())
                {
                    status = "0";
                }else
                {
                    status = "1";
                }

                personalization_privacy_api(key,status);
            }
        });

        photos_switchMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                key = "Photos";
                if(!photos_switchMaterial.isChecked())
                {
                    status = "0";
                }else
                {
                    status = "1";
                }

                personalization_privacy_api(key,status);
            }
        });

        radio_channel_switchMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                key = "RadioChannel";
                if(!radio_channel_switchMaterial.isChecked())
                {
                    status = "0";
                }else
                {
                    status = "1";
                }

                personalization_privacy_api(key,status);
            }
        });


        tv_channel_switchMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                key = "TVChannel";
                if(!tv_channel_switchMaterial.isChecked())
                {
                    status = "0";
                }else
                {
                    status = "1";
                }

                personalization_privacy_api(key,status);
            }
        });


        relationship_switchMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                key = "Relationships";
                if(!relationship_switchMaterial.isChecked())
                {
                    status = "0";
                }else
                {
                    status = "1";
                }

                personalization_privacy_api(key,status);
            }
        });


        mail_box_switchMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                key = "Mailbox";
                if(!mail_box_switchMaterial.isChecked())
                {
                    status = "0";
                }else
                {
                    status = "1";
                }

                personalization_privacy_api(key,status);
            }
        });


        messaging_switchMaterials.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                key = "Message";
                if(!messaging_switchMaterials.isChecked())
                {
                    status = "0";
                }else
                {
                    status = "1";
                }

                personalization_privacy_api(key,status);
            }
        });

        friend_request_switchMaterials.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                key = "Friend";
                if(!friend_request_switchMaterials.isChecked())
                {
                    status = "0";
                }else
                {
                    status = "1";
                }

                personalization_privacy_api(key,status);
            }
        });

        profile_switchMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                key = "profile";
               if(!profile_switchMaterial.isChecked())
               {
                   status = "0";
               }else
               {
                   status = "1";
               }

                personalization_privacy_api(key,status);
            }
        });


    }

    private void pp_settings_details_api() {

        final ProgressDialog pd = new ProgressDialog(PersonalizationPrivacyActivity.this);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();



        Call<PPSettingsModel> call = API_Client.getClient().ppSettingsDetails(user_id);

        call.enqueue(new Callback<PPSettingsModel>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<PPSettingsModel> call, Response<PPSettingsModel> response) {
                pd.dismiss();


                try {
                    if (response.isSuccessful()) {
                        String message = response.body().getMessage();
                        String success = response.body().getSuccess();



                        if (success.equals("true") || success.equals("True")) {


                            ppSettingsRessultList = response.body().getRecord();

                            profileApiData = String.valueOf(ppSettingsRessultList.get(0).getProfile());
                            friendApiData = String.valueOf(ppSettingsRessultList.get(0).getFriend());
                            messageApiData = String.valueOf(ppSettingsRessultList.get(0).getMessage());
                            mailboxApiData = String.valueOf(ppSettingsRessultList.get(0).getMailbox());
                            relationshipsApiData = String.valueOf(ppSettingsRessultList.get(0).getRelationships());
                            tVChannelApiData = String.valueOf(ppSettingsRessultList.get(0).gettVChannel());
                            radioChannelApiData = String.valueOf(ppSettingsRessultList.get(0).getRadioChannel());
                            photosApiData = String.valueOf(ppSettingsRessultList.get(0).getPhotos());
                            activitiesApiData = String.valueOf(ppSettingsRessultList.get(0).getActivities());
                            emailApiData = String.valueOf(ppSettingsRessultList.get(0).getEmail());
                            phoneApiData = String.valueOf(ppSettingsRessultList.get(0).getPhone());
                            webUrlApiData = String.valueOf(ppSettingsRessultList.get(0).getWebUrl());
                            aboutMeApiData = String.valueOf(ppSettingsRessultList.get(0).getAboutme());
                            myStaticApiData = String.valueOf(ppSettingsRessultList.get(0).getMyStatics());



                            if(myStaticApiData.equalsIgnoreCase("1"))
                            {
                                my_static_switchMaterial.setChecked(true);
                            }else
                            {
                                my_static_switchMaterial.setChecked(false);
                            }

                            if(aboutMeApiData.equalsIgnoreCase("1"))
                            {
                                about_me_switchMaterial.setChecked(true);
                            }else
                            {
                                about_me_switchMaterial.setChecked(false);
                            }

                            if(webUrlApiData.equalsIgnoreCase("1"))
                            {
                                web_url_switchMaterial.setChecked(true);
                            }else
                            {
                                web_url_switchMaterial.setChecked(false);
                            }

                            if(phoneApiData.equalsIgnoreCase("1"))
                            {
                                phone_number_switchMaterial.setChecked(true);
                            }else
                            {
                                phone_number_switchMaterial.setChecked(false);
                            }


                            if(emailApiData.equalsIgnoreCase("1"))
                            {
                                email_switchMaterial.setChecked(true);
                            }else
                            {
                                email_switchMaterial.setChecked(false);
                            }


                            if(activitiesApiData.equalsIgnoreCase("1"))
                            {
                                activities_switchMaterial.setChecked(true);
                            }else
                            {
                                activities_switchMaterial.setChecked(false);
                            }


                            if(photosApiData.equalsIgnoreCase("1"))
                            {
                                photos_switchMaterial.setChecked(true);
                            }else
                            {
                                photos_switchMaterial.setChecked(false);
                            }

                            if(radioChannelApiData.equalsIgnoreCase("1"))
                            {
                                radio_channel_switchMaterial.setChecked(true);
                            }else
                            {
                                radio_channel_switchMaterial.setChecked(false);
                            }

                            if(tVChannelApiData.equalsIgnoreCase("1"))
                            {
                                tv_channel_switchMaterial.setChecked(true);
                            }else
                            {
                                tv_channel_switchMaterial.setChecked(false);
                            }


                            if(relationshipsApiData.equalsIgnoreCase("1"))
                            {
                                relationship_switchMaterial.setChecked(true);
                            }else
                            {
                                relationship_switchMaterial.setChecked(false);
                            }


                            if(mailboxApiData.equalsIgnoreCase("1"))
                            {
                                mail_box_switchMaterial.setChecked(true);
                            }else
                            {
                                mail_box_switchMaterial.setChecked(false);
                            }


                            if(messageApiData.equalsIgnoreCase("1"))
                            {
                                messaging_switchMaterials.setChecked(true);
                            }else
                            {
                                messaging_switchMaterials.setChecked(false);
                            }


                            if(friendApiData.equalsIgnoreCase("1"))
                            {
                                friend_request_switchMaterials.setChecked(true);
                            }else
                            {
                                friend_request_switchMaterials.setChecked(false);
                            }


                            if(profileApiData.equalsIgnoreCase("1"))
                            {
                                profile_switchMaterial.setChecked(true);
                            }else
                            {
                                profile_switchMaterial.setChecked(false);
                            }


                           // Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

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
            public void onFailure(Call<PPSettingsModel> call, Throwable t) {
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

    private void personalization_privacy_api(String key, String status) {

        final ProgressDialog pd = new ProgressDialog(PersonalizationPrivacyActivity.this);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();

        Log.e("rfewrwerwe",key);
        Log.e("rfewrwerwe",status);

        Call<PersonalizationPrivacyModel> call = API_Client.getClient().personalizationPrivacy(user_id,key,status);

        call.enqueue(new Callback<PersonalizationPrivacyModel>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<PersonalizationPrivacyModel> call, Response<PersonalizationPrivacyModel> response) {
                pd.dismiss();


                try {
                    if (response.isSuccessful()) {
                        String message = response.body().getMessage();
                        String success = response.body().getSuccess();



                        if (success.equals("true") || success.equals("True")) {

                            // Set default value for genser and relationship



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
            public void onFailure(Call<PersonalizationPrivacyModel> call, Throwable t) {
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
        AlertDialog dialogs;

        final LayoutInflater inflater = PersonalizationPrivacyActivity.this.getLayoutInflater();
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
        }else  if(value.equals("400"))
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

        final AlertDialog.Builder alert = new AlertDialog.Builder(PersonalizationPrivacyActivity.this);

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
        back_arrow = findViewById(R.id.back_arrow);
        my_static_switchMaterial = findViewById(R.id.my_static_switchMaterial);
        about_me_switchMaterial = findViewById(R.id.about_me_switchMaterial);
        phone_number_switchMaterial = findViewById(R.id.phone_number_switchMaterial);
        email_switchMaterial = findViewById(R.id.email_switchMaterial);
        activities_switchMaterial = findViewById(R.id.activities_switchMaterial);
        photos_switchMaterial = findViewById(R.id.photos_switchMaterial);
        web_url_switchMaterial = findViewById(R.id.web_url_switchMaterial);
        profile_switchMaterial = findViewById(R.id.profile_switchMaterial);
        friend_request_switchMaterials = findViewById(R.id.friend_request_switchMaterials);
        messaging_switchMaterials = findViewById(R.id.messaging_switchMaterials);
        mail_box_switchMaterial = findViewById(R.id.mail_box_switchMaterial);
        relationship_switchMaterial = findViewById(R.id.relationship_switchMaterial);
        tv_channel_switchMaterial = findViewById(R.id.tv_channel_switchMaterial);
        radio_channel_switchMaterial = findViewById(R.id.radio_channel_switchMaterial);


    }
}