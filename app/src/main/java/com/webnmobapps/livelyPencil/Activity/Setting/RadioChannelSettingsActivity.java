package com.webnmobapps.livelyPencil.Activity.Setting;

import static com.webnmobapps.livelyPencil.RetrofitApi.API_Client.BASE_LOGO_IMAGE;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.AppCompatTextView;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.webnmobapps.livelyPencil.Activity.Utility.ImagePickerActivity;
import com.webnmobapps.livelyPencil.Activity.Utility.Permission;
import com.webnmobapps.livelyPencil.Model.EditRadioSettingsModel;
import com.webnmobapps.livelyPencil.Model.EditTVSettingModel;
import com.webnmobapps.livelyPencil.Model.GroupAgeModel;
import com.webnmobapps.livelyPencil.Model.IntrestListModel;
import com.webnmobapps.livelyPencil.Model.RadioSettingsModel;
import com.webnmobapps.livelyPencil.Model.Record.GroupAgeResult;
import com.webnmobapps.livelyPencil.Model.Record.IntrestListModelRecord;
import com.webnmobapps.livelyPencil.Model.Record.RadioSettingsResult;
import com.webnmobapps.livelyPencil.Model.Record.TvSettingsResult;
import com.webnmobapps.livelyPencil.Model.TvSettingsModel;
import com.webnmobapps.livelyPencil.R;
import com.webnmobapps.livelyPencil.RetrofitApi.API_Client;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RadioChannelSettingsActivity extends AppCompatActivity {
    AppCompatImageView back_arrow, radio_cover_image;
    AppCompatButton radio_save_change_button;
    AppCompatTextView radio_cover_edit_layout,radio_logo_edit_layout;
    CircleImageView radio_logo_image;
    AppCompatEditText radio_other_tag;
    AppCompatSpinner radio_topic_spin, radio_age_group_spin;
    AlertDialog dialogs;
    private String imageKey;
    public static final int REQUEST_IMAGE = 100;
    private static final int MY_CAMERA_REQUEST_CODE = 100;
    private Uri uri;
    private File radioLogoSettingsImage, radioCoverSettingsImage;
    private String user_id, topic_string,age_string, radio_id ;
    List<RadioSettingsResult> radioSettingsResultList = new ArrayList<>();
    List<String> topic_name_list = new ArrayList<>();
    List<String> groupNameAgeList = new ArrayList<>();
    List<GroupAgeResult> groupAgeResultList = new ArrayList<>();
    List<IntrestListModelRecord> IntrtestListData = new ArrayList<>();
    private String topic_sp_id, topicData,ageGroupData, otherTagData;
    private String age_group_sp_id, age_group_data;
    private MultipartBody.Part tvSettingCoverImage, tvSettingLogoImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio_channel_settings);

        inits();

        SharedPreferences sharedPreferences= getSharedPreferences("AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
        user_id=sharedPreferences.getString("UserID","");

        radio_details_api();
        selectIntrestListApi();
        group_age_api();



        radio_save_change_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                get_form_data();
                if(validation())
                {
                    edit_radio_setting_api();
                }

            }
        });

        radio_logo_edit_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                imageKey = "1";
                // Toast.makeText(TVChannelSettingsActivity.this, "Working ########", Toast.LENGTH_SHORT).show();

                final LayoutInflater inflater = RadioChannelSettingsActivity.this.getLayoutInflater();
                View alertLayout = inflater.inflate(R.layout.test_dialog_xml, null);

                final ImageView close_dialog = alertLayout.findViewById(R.id.close_dialog);
                final ImageView camera_icon = alertLayout.findViewById(R.id.camera_icon);
                final ImageView browse_icon = alertLayout.findViewById(R.id.browse_icon);

                final AlertDialog.Builder alert = new AlertDialog.Builder(RadioChannelSettingsActivity.this);

                alert.setView(alertLayout);
                alert.setCancelable(false);

                dialogs = alert.create();
                dialogs.show();
                dialogs.setCanceledOnTouchOutside(true);


                close_dialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogs.dismiss();
                    }
                });

                camera_icon.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onClick(View view) {
                        profile_camera_open();
                        dialogs.dismiss();
                    }
                });


                browse_icon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        gallery();
                        dialogs.dismiss();
                    }
                });

            }
        });


        radio_cover_edit_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                imageKey = "2";

                final LayoutInflater inflater = RadioChannelSettingsActivity.this.getLayoutInflater();
                View alertLayout = inflater.inflate(R.layout.test_dialog_xml, null);

                final ImageView close_dialog = alertLayout.findViewById(R.id.close_dialog);
                final ImageView camera_icon = alertLayout.findViewById(R.id.camera_icon);
                final ImageView browse_icon = alertLayout.findViewById(R.id.browse_icon);

                final AlertDialog.Builder alert = new AlertDialog.Builder(RadioChannelSettingsActivity.this);

                alert.setView(alertLayout);
                alert.setCancelable(false);

                dialogs = alert.create();
                dialogs.show();
                dialogs.setCanceledOnTouchOutside(true);



                close_dialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogs.dismiss();
                    }
                });

                camera_icon.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onClick(View view) {
                        profile_camera_open();
                        dialogs.dismiss();
                    }
                });


                browse_icon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        gallery();
                        dialogs.dismiss();
                    }
                });

            }
        });


        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        radio_topic_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //  Toast.makeText(getActivity(), "Country Spinner Working **********", Toast.LENGTH_SHORT).show();

                String item = radio_topic_spin.getSelectedItem().toString();
                if (item.equals(getResources().getString(R.string.select_topic)))
                {

                    // int spinnerPosition = dAdapter.getPosition(compareValue);
                    // spinner_category.setSelection(Integer.parseInt("Select Category"));
                }   else
                {

                    topic_sp_id = String.valueOf(IntrtestListData.get(i).getId());
                    topic_string = String.valueOf(IntrtestListData.get(i).getStatus());
                    /*Log.e("LIST_ID_City",topic_sp_id+"ID");
                    Log.e("LIST_ID_City",topic_name);*/
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {


            }
        });


        radio_age_group_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //  Toast.makeText(getActivity(), "Country Spinner Working **********", Toast.LENGTH_SHORT).show();

                String item = radio_age_group_spin.getSelectedItem().toString();
                if (item.equals(getResources().getString(R.string.select_age_group)))
                {

                    // int spinnerPosition = dAdapter.getPosition(compareValue);
                    // spinner_category.setSelection(Integer.parseInt("Select Category"));
                }   else
                {

                    age_group_sp_id = String.valueOf(groupAgeResultList.get(i).getId());
                    age_group_data = String.valueOf(groupAgeResultList.get(i).getAgediff());
                  /*  Log.e("LIST_ID_City",topic_sp_id+"ID");
                    Log.e("LIST_ID_City",topic_name);*/
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {


            }
        });



    }

    private void get_form_data() {
        topicData = topic_string;
        ageGroupData = age_group_data;
        otherTagData = radio_other_tag.getText().toString();

    }

    private boolean validation() {

        if(otherTagData.equals(""))
        {
            Toast.makeText(RadioChannelSettingsActivity.this, "Please enter radio tag", Toast.LENGTH_SHORT).show();
            return  false;
        }

        return  true;
    }

    private void edit_radio_setting_api() {

        final ProgressDialog pd = new ProgressDialog(RadioChannelSettingsActivity.this);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();



        if( radioCoverSettingsImage== null)
        {

            tvSettingCoverImage = MultipartBody.Part.createFormData("radiocover", "", RequestBody.create(MediaType.parse("image/*"), ""));
        }else
        {
            Toast.makeText(RadioChannelSettingsActivity.this, "Logo Image Found", Toast.LENGTH_SHORT).show();
            tvSettingCoverImage = MultipartBody.Part.createFormData("radiocover", radioCoverSettingsImage.getName(), RequestBody.create(MediaType.parse("image/*"), radioCoverSettingsImage));
        }

        if(radioLogoSettingsImage == null)
        {
            tvSettingLogoImage = MultipartBody.Part.createFormData("radiologo", "", RequestBody.create(MediaType.parse("image/*"), ""));
        }else
        {
            tvSettingLogoImage = MultipartBody.Part.createFormData("radiologo", radioLogoSettingsImage.getName(), RequestBody.create(MediaType.parse("image/*"), radioLogoSettingsImage));
        }


        Log.e("edit_radio_data","radio_id: "+radio_id);
        Log.e("edit_radio_data","topicData: "+topicData);
        Log.e("edit_radio_data","otherTagData: "+otherTagData);
        Log.e("edit_radio_data","age_group_data: "+age_group_data);



        RequestBody  tv_id_RB = RequestBody.create(MediaType.parse("text/plain"),  radio_id);
        RequestBody  topicData_RB = RequestBody.create(MediaType.parse("text/plain"),  topicData);
        RequestBody otherTagData_RB = RequestBody.create(MediaType.parse("text/plain"),  otherTagData);
        RequestBody age_group_data_RB = RequestBody.create(MediaType.parse("text/plain"),  age_group_data);

        Call<EditRadioSettingsModel> call = API_Client.getClient().editRadioSetting(tv_id_RB,
                topicData_RB,
                age_group_data_RB,
                otherTagData_RB,
                tvSettingLogoImage,
                tvSettingCoverImage);

        call.enqueue(new Callback<EditRadioSettingsModel>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<EditRadioSettingsModel> call, Response<EditRadioSettingsModel> response) {
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
            public void onFailure(Call<EditRadioSettingsModel> call, Throwable t) {
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


    private void group_age_api() {

        final ProgressDialog pd = new ProgressDialog(RadioChannelSettingsActivity.this);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();


        Call<GroupAgeModel> call = API_Client.getClient().groupAge();

        call.enqueue(new Callback<GroupAgeModel>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<GroupAgeModel> call, Response<GroupAgeModel> response) {
                pd.dismiss();


                try {
                    if (response.isSuccessful()) {
                        String message = response.body().getMessage();
                        String success = response.body().getSuccess();
                        if (success.equals("true") || success.equals("True")) {



                            groupNameAgeList.clear();
                            groupAgeResultList = response.body().getRecord();

                            GroupAgeResult groupAgeResult =new GroupAgeResult();
                            groupAgeResult.setAgediff(getResources().getString(R.string.select_age_group));
                            groupAgeResult.setId(0);
                            groupAgeResultList.add(0,groupAgeResult);
                            for (int j=0;j<groupAgeResultList.size();j++)
                            {
                                String category_name = groupAgeResultList.get(j).getAgediff();
                                //    Toast.makeText(getActivity(), (CharSequence) countryList.get(j), Toast.LENGTH_SHORT).show();
                                if(category_name!=null)
                                {
                                    groupNameAgeList.add(category_name);
                                }
                                Log.e("city_name_list",groupNameAgeList.size()+"S");
                            }

                            spinnerAdapter dAdapter = new spinnerAdapter(getApplicationContext(), R.layout.custom_spinner_two, groupNameAgeList);
                            dAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            dAdapter.addAll(groupNameAgeList);
                            dAdapter.add(getResources().getString(R.string.select_age_group));
                            radio_age_group_spin.setAdapter(dAdapter);
                            radio_age_group_spin.setSelection(dAdapter.getCount());

                            Log.e("age_string", age_string);

                            if (age_string.equals("")) {
                            } else {
                                int spinnerPosition = dAdapter.getPosition(age_string);
                                radio_age_group_spin.setSelection(spinnerPosition);
                            }


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
            public void onFailure(Call<GroupAgeModel> call, Throwable t) {
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

    private void selectIntrestListApi() {

        // show till load api data

        final ProgressDialog pd = new ProgressDialog(RadioChannelSettingsActivity.this);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();

        Call<IntrestListModel> call = API_Client.getClient().IntrestList(
        );

        call.enqueue(new Callback<IntrestListModel>() {
            @Override
            public void onResponse(Call<IntrestListModel> call, Response<IntrestListModel> response) {
                pd.dismiss();
                try {
                    //if api response is successful ,taking message and success
                    if (response.isSuccessful()) {
                        String message = response.body().getMessage();
                        String success = String.valueOf(response.body().getSuccess());

                        if (success.equals("true") || success.equals("True")) {

                            topic_name_list.clear();
                            IntrtestListData = response.body().getIntrestListModelRecord();

                            IntrestListModelRecord intrestListRecord =new IntrestListModelRecord();
                            intrestListRecord.setStatus(getResources().getString(R.string.select_topic));
                            intrestListRecord.setId(0);
                            IntrtestListData.add(0,intrestListRecord);
                            for (int j=0;j<IntrtestListData.size();j++)
                            {
                                String category_name = IntrtestListData.get(j).getStatus();
                                //    Toast.makeText(getActivity(), (CharSequence) countryList.get(j), Toast.LENGTH_SHORT).show();
                                if(category_name!=null)
                                {
                                    topic_name_list.add(category_name);
                                }
                                Log.e("city_name_list",topic_name_list.size()+"S");
                            }

                            spinnerAdapter dAdapter = new spinnerAdapter(getApplicationContext(), R.layout.custom_spinner_two, topic_name_list);
                            dAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            dAdapter.addAll(topic_name_list);
                            dAdapter.add(getResources().getString(R.string.select_topic));
                            radio_topic_spin.setAdapter(dAdapter);
                            radio_topic_spin.setSelection(dAdapter.getCount());

                            if (topic_string.equals("")) {
                            } else {
                                int spinnerPosition = dAdapter.getPosition(topic_string);
                                radio_topic_spin.setSelection(spinnerPosition);
                            }

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
            public void onFailure(Call<IntrestListModel> call, Throwable t) {
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

    private void radio_details_api() {
        final ProgressDialog pd = new ProgressDialog(RadioChannelSettingsActivity.this);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();



        Call<RadioSettingsModel> call = API_Client.getClient().radioSettingDetails(user_id);

        call.enqueue(new Callback<RadioSettingsModel>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<RadioSettingsModel> call, Response<RadioSettingsModel> response) {
                pd.dismiss();


                try {
                    if (response.isSuccessful()) {
                        String message = response.body().getMessage();
                        String success = response.body().getSuccess();
                        if (success.equals("true") || success.equals("True")) {

                            radioSettingsResultList = response.body().getRecord();

                            String temp = BASE_LOGO_IMAGE+radioSettingsResultList.get(0).getRadiologo();
                            Log.e("tv_logo",": "+temp);
                            Glide.with(RadioChannelSettingsActivity.this).load(temp).placeholder(R.drawable.ic_launcher_background).into(radio_logo_image);
                            Glide.with(RadioChannelSettingsActivity.this).load(BASE_LOGO_IMAGE+radioSettingsResultList.get(0).getRadiocover()).placeholder(R.drawable.ic_launcher_background).into(radio_cover_image);

                            radio_other_tag.setText(radioSettingsResultList.get(0).getOtherTags());
                            topic_string = radioSettingsResultList.get(0).getTopic();
                            age_string = radioSettingsResultList.get(0).getAgegroup();
                            age_group_data = age_string;
                            radio_id = String.valueOf(radioSettingsResultList.get(0).getId());

                            Log.e("age_topic_string",age_string);
                            Log.e("age_topic_string",topic_string);


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
            public void onFailure(Call<RadioSettingsModel> call, Throwable t) {
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

        final LayoutInflater inflater = RadioChannelSettingsActivity.this.getLayoutInflater();
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

        final AlertDialog.Builder alert = new AlertDialog.Builder(RadioChannelSettingsActivity.this);

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


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void profile_camera_open() {

        PackageManager packageManager = getPackageManager();

        boolean readExternal = Permission.checkPermissionReadExternal(RadioChannelSettingsActivity.this);
        boolean writeExternal = Permission.checkPermissionReadExternal2(RadioChannelSettingsActivity.this);
        boolean camera = Permission.checkPermissionCamera(RadioChannelSettingsActivity.this);

        if (camera && writeExternal && readExternal ) {
            if (packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {

                Intent intent = new Intent(RadioChannelSettingsActivity.this, ImagePickerActivity.class);
                intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_IMAGE_CAPTURE);

                // setting aspect ratio
                intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
                intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
                intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);

                // setting maximum bitmap width and height
                intent.putExtra(ImagePickerActivity.INTENT_SET_BITMAP_MAX_WIDTH_HEIGHT, true);
                intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_WIDTH, 1000);
                intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_HEIGHT, 1000);

                startActivityForResult(intent, REQUEST_IMAGE);


            }
        } else {
            Toast.makeText(RadioChannelSettingsActivity.this, "camera permission required", Toast.LENGTH_LONG).show();
            requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
        }

    }

    private void gallery() {

        boolean readExternal = Permission.checkPermissionReadExternal(RadioChannelSettingsActivity.this);
        boolean writeExternal = Permission.checkPermissionReadExternal2(RadioChannelSettingsActivity.this);
        boolean camera = Permission.checkPermissionCamera(RadioChannelSettingsActivity.this);
        if (readExternal && camera ) {

            Intent intent = new Intent(RadioChannelSettingsActivity.this, ImagePickerActivity.class);
            intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_GALLERY_IMAGE);

            // setting aspect ratio
            intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
            intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
            intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);
            startActivityForResult(intent, REQUEST_IMAGE);

        }else
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
            }
        }
    }


    private void inits() {

        radio_age_group_spin = findViewById(R.id.radio_age_group_spin);
        radio_topic_spin = findViewById(R.id.radio_topic_spin);
        radio_other_tag = findViewById(R.id.radio_other_tag);
        radio_logo_edit_layout = findViewById(R.id.radio_logo_edit_layout);
        radio_logo_image = findViewById(R.id.radio_logo_image);
        radio_cover_edit_layout = findViewById(R.id.radio_cover_edit_layout);
        radio_save_change_button = findViewById(R.id.radio_save_change_button);
        radio_cover_image = findViewById(R.id.radio_cover_image);
        back_arrow = findViewById(R.id.back_arrow);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_IMAGE) {

           // Toast.makeText(RadioChannelSettingsActivity.this, "imageKey: "+imageKey, Toast.LENGTH_SHORT).show();

            if (resultCode == Activity.RESULT_OK) {
                uri = data.getParcelableExtra("path");
                // String sel_path = getpath(uri);
                try {
                    // You can update this bitmap to your server
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);

                    // loading profile image from local cache
                    //loadProfile(uri.toString());

                    if(imageKey.equals("1"))
                    {
                        radioLogoSettingsImage = new File(uri.getPath());
                        Log.e("file ", "path " + radioLogoSettingsImage.getAbsolutePath());

                        Uri uu = Uri.fromFile(radioLogoSettingsImage);
                        Glide.with(RadioChannelSettingsActivity.this).
                                load(radioLogoSettingsImage).
                                placeholder(R.drawable.ic_launcher_background).
                                into(radio_logo_image);
                    }else if(imageKey.equals("2"))
                    {
                        radioCoverSettingsImage = new File(uri.getPath());
                        Uri uu = Uri.fromFile(radioCoverSettingsImage);
                        Glide.with(RadioChannelSettingsActivity.this).
                                load(radioCoverSettingsImage).
                                placeholder(R.drawable.ic_launcher_background).
                                into(radio_cover_image);
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public class spinnerAdapter extends ArrayAdapter<String>
    {
        private spinnerAdapter(Context context, int textViewResourceId, List<String> smonking)
        {
            super(context, textViewResourceId);
        }

        @Override
        public int getCount()
        {
            int count = super.getCount();
            return count > 0 ? count - 1 : count;
        }
    }

}