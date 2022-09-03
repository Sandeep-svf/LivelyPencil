package com.webnmobapps.livelyPencil.Activity.Setting;

import static com.webnmobapps.livelyPencil.RetrofitApi.API_Client.BASE_COVER_IMAGE;
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
import androidx.appcompat.widget.LinearLayoutCompat;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
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
import com.webnmobapps.livelyPencil.Activity.JoinUs.SelectIntrestActivity;
import com.webnmobapps.livelyPencil.Activity.Utility.ImagePickerActivity;
import com.webnmobapps.livelyPencil.Activity.Utility.Permission;
import com.webnmobapps.livelyPencil.Adapter.SelectIntrestAdapter;
import com.webnmobapps.livelyPencil.Model.EditStreamSettingModel;
import com.webnmobapps.livelyPencil.Model.EditTVSettingModel;
import com.webnmobapps.livelyPencil.Model.GroupAgeModel;
import com.webnmobapps.livelyPencil.Model.IntrestListModel;
import com.webnmobapps.livelyPencil.Model.Record.CityListResult;
import com.webnmobapps.livelyPencil.Model.Record.GroupAgeResult;
import com.webnmobapps.livelyPencil.Model.Record.IntrestListModelRecord;
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
import me.relex.circleindicator.CircleIndicator;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TVChannelSettingsActivity extends AppCompatActivity {
    AppCompatImageView back_arrow, tv_cover_image;
    CircleImageView tv_logo_image;
    AppCompatEditText tag_edit_text;
    AppCompatButton tv_save_change_button;
    AppCompatTextView tv_logo_edit_layout, tv_cover_edit_layout;
    private String user_id, topic_sp_id,topic_name,topic_string,age_string, age_group_sp_id, age_group_data;
    List<TvSettingsResult> tvSettingsResultList = new ArrayList<>();
    List<GroupAgeResult> groupAgeResultList = new ArrayList<>();
    List<IntrestListModelRecord> IntrtestListData = new ArrayList<>();
    List<String> topic_name_list = new ArrayList<>();
    List<String> groupNameAgeList = new ArrayList<>();
    AppCompatSpinner topic_spin, age_group_spin;
    private String topicData, ageGroupData, otherTagData;
    AlertDialog dialogs;
    public static final int REQUEST_IMAGE = 100;
    private static final int MY_CAMERA_REQUEST_CODE = 100;
    private Uri uri;
    private File tvLogoSettingsImage, tvCoverSettingsImage;
    private String imageKey, tv_id;
    private MultipartBody.Part tvSettingCoverImage, tvSettingLogoImage ;
    CircleIndicator Indicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tvchannel_settings);
        inits();

        // Manual Circle Indicatior........
        Indicator = findViewById(R.id.pi_indicator);
        Indicator.createIndicators(3,1);
        Indicator.animatePageSelected(0);

        SharedPreferences sharedPreferences= getSharedPreferences("AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
        user_id=sharedPreferences.getString("UserID","");

        tv_details_api();
        selectIntrestListApi();
        group_age_api();


        tv_save_change_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                get_form_data();
                if(validation())
                {
                    edit_tv_settings_api();
                }
            }
        });

        tv_cover_edit_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageKey = "2";

                final LayoutInflater inflater = TVChannelSettingsActivity.this.getLayoutInflater();
                View alertLayout = inflater.inflate(R.layout.test_dialog_xml, null);

                final ImageView close_dialog = alertLayout.findViewById(R.id.close_dialog);
                final ImageView camera_icon = alertLayout.findViewById(R.id.camera_icon);
                final ImageView browse_icon = alertLayout.findViewById(R.id.browse_icon);

                final AlertDialog.Builder alert = new AlertDialog.Builder(TVChannelSettingsActivity.this);

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

        tv_logo_edit_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                imageKey = "1";
               // Toast.makeText(TVChannelSettingsActivity.this, "Working ########", Toast.LENGTH_SHORT).show();

                final LayoutInflater inflater = TVChannelSettingsActivity.this.getLayoutInflater();
                View alertLayout = inflater.inflate(R.layout.test_dialog_xml, null);

                final ImageView close_dialog = alertLayout.findViewById(R.id.close_dialog);
                final ImageView camera_icon = alertLayout.findViewById(R.id.camera_icon);
                final ImageView browse_icon = alertLayout.findViewById(R.id.browse_icon);

                final AlertDialog.Builder alert = new AlertDialog.Builder(TVChannelSettingsActivity.this);

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



        topic_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //  Toast.makeText(getActivity(), "Country Spinner Working **********", Toast.LENGTH_SHORT).show();

                String item = topic_spin.getSelectedItem().toString();
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


        age_group_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //  Toast.makeText(getActivity(), "Country Spinner Working **********", Toast.LENGTH_SHORT).show();

                String item = age_group_spin.getSelectedItem().toString();
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

    private void group_age_api() {

        final ProgressDialog pd = new ProgressDialog(TVChannelSettingsActivity.this);
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
                            age_group_spin.setAdapter(dAdapter);
                            age_group_spin.setSelection(dAdapter.getCount());

                            Log.e("age_string", age_string);

                            if (age_string.equals("")) {
                            } else {
                                int spinnerPosition = dAdapter.getPosition(age_string);
                                age_group_spin.setSelection(spinnerPosition);
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

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void profile_camera_open() {

        PackageManager packageManager = getPackageManager();

        boolean readExternal = Permission.checkPermissionReadExternal(TVChannelSettingsActivity.this);
        boolean writeExternal = Permission.checkPermissionReadExternal2(TVChannelSettingsActivity.this);
        boolean camera = Permission.checkPermissionCamera(TVChannelSettingsActivity.this);

        if (camera && writeExternal && readExternal ) {
            if (packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
              /*  values7 = new ContentValues();
                values7.put(MediaStore.Images.Media.TITLE, "New Picture");
                values7.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
                imageUri7 = getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values7);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri7);
                startActivityForResult(intent, CAMERA_PIC_REQUEST_R);*/


                Intent intent = new Intent(TVChannelSettingsActivity.this, ImagePickerActivity.class);
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
            Toast.makeText(TVChannelSettingsActivity.this, "camera permission required", Toast.LENGTH_LONG).show();
            requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
        }

    }

    private void gallery() {

        boolean readExternal = Permission.checkPermissionReadExternal(TVChannelSettingsActivity.this);
        boolean writeExternal = Permission.checkPermissionReadExternal2(TVChannelSettingsActivity.this);
        boolean camera = Permission.checkPermissionCamera(TVChannelSettingsActivity.this);
        if (readExternal && camera ) {
      /*      Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, PICK_IMAGE_G);*/


            Intent intent = new Intent(TVChannelSettingsActivity.this, ImagePickerActivity.class);
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

    private void edit_tv_settings_api() {

        final ProgressDialog pd = new ProgressDialog(TVChannelSettingsActivity.this);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();



        if(tvCoverSettingsImage == null)
        {
            tvSettingCoverImage = MultipartBody.Part.createFormData("tvcover", "", RequestBody.create(MediaType.parse("image/*"), ""));
        }else
        {
            tvSettingCoverImage = MultipartBody.Part.createFormData("tvcover", tvCoverSettingsImage.getName(), RequestBody.create(MediaType.parse("image/*"), tvCoverSettingsImage));
        }

        if(tvLogoSettingsImage == null)
        {
            tvSettingLogoImage = MultipartBody.Part.createFormData("tvlogo", "", RequestBody.create(MediaType.parse("image/*"), ""));
        }else
        {
            tvSettingLogoImage = MultipartBody.Part.createFormData("tvlogo", tvLogoSettingsImage.getName(), RequestBody.create(MediaType.parse("image/*"), tvLogoSettingsImage));
        }


        Log.e("edit_tv_data","tv_id: "+tv_id);
        Log.e("edit_tv_data","topicData: "+topicData);
        Log.e("edit_tv_data","otherTagData: "+otherTagData);
        Log.e("edit_tv_data","age_group_data: "+age_group_data);



        RequestBody  tv_id_RB = RequestBody.create(MediaType.parse("text/plain"),  tv_id);
        RequestBody  topicData_RB = RequestBody.create(MediaType.parse("text/plain"),  topicData);
        RequestBody otherTagData_RB = RequestBody.create(MediaType.parse("text/plain"),  otherTagData);
        RequestBody age_group_data_RB = RequestBody.create(MediaType.parse("text/plain"),  age_group_data);

        Call<EditTVSettingModel> call = API_Client.getClient().editTvSetting(tv_id_RB,
                topicData_RB,
                age_group_data_RB,
                otherTagData_RB,
                tvSettingLogoImage,
                tvSettingCoverImage);

        call.enqueue(new Callback<EditTVSettingModel>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<EditTVSettingModel> call, Response<EditTVSettingModel> response) {
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
            public void onFailure(Call<EditTVSettingModel> call, Throwable t) {
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

    private boolean validation() {
        if(otherTagData.equals(""))
        {
            Toast.makeText(TVChannelSettingsActivity.this, "Please Enter Tag", Toast.LENGTH_SHORT).show();
            return  false;
        }
        return true;
    }

    private void get_form_data() {
        topicData = topic_string;
        ageGroupData = age_group_data;
        otherTagData = tag_edit_text.getText().toString();
    }

    private void selectIntrestListApi() {

        // show till load api data

        final ProgressDialog pd = new ProgressDialog(TVChannelSettingsActivity.this);
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
                            topic_spin.setAdapter(dAdapter);
                            topic_spin.setSelection(dAdapter.getCount());

                            if (topic_string.equals("")) {
                            } else {
                                int spinnerPosition = dAdapter.getPosition(topic_string);
                                topic_spin.setSelection(spinnerPosition);
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
    private void tv_details_api() {

        final ProgressDialog pd = new ProgressDialog(TVChannelSettingsActivity.this);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();



        Call<TvSettingsModel> call = API_Client.getClient().tvSettingDetails(user_id);

        call.enqueue(new Callback<TvSettingsModel>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<TvSettingsModel> call, Response<TvSettingsModel> response) {
                pd.dismiss();


                try {
                    if (response.isSuccessful()) {
                        String message = response.body().getMessage();
                        String success = response.body().getSuccess();
                        if (success.equals("true") || success.equals("True")) {

                            tvSettingsResultList = response.body().getRecord();

                            String temp = BASE_LOGO_IMAGE+tvSettingsResultList.get(0).getTvlogo();
                            Log.e("tv_logo",": "+temp);
                            Glide.with(TVChannelSettingsActivity.this).load(temp).placeholder(R.drawable.ic_launcher_background).into(tv_logo_image);
                            Glide.with(TVChannelSettingsActivity.this).load(BASE_LOGO_IMAGE+tvSettingsResultList.get(0).getTvcover()).placeholder(R.drawable.ic_launcher_background).into(tv_cover_image);

                           /* tag_edit_text.setText(tvSettingsResultList.get(0).getOtherTags());
                            topic_string = tvSettingsResultList.get(0).getTopic();
                            age_string = tvSettingsResultList.get(0).getAgegroup();
                            tv_id = String.valueOf(tvSettingsResultList.get(0).getId());*/

                            /*Log.e("age_topic_string",age_string);
                            Log.e("age_topic_string",topic_string);*/


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
            public void onFailure(Call<TvSettingsModel> call, Throwable t) {
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

        final LayoutInflater inflater = TVChannelSettingsActivity.this.getLayoutInflater();
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

        final AlertDialog.Builder alert = new AlertDialog.Builder(TVChannelSettingsActivity.this);

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
        tv_cover_edit_layout = findViewById(R.id.tv_cover_edit_layout);
        tv_logo_edit_layout = findViewById(R.id.tv_logo_edit_layout);
        tv_logo_image = findViewById(R.id.tv_logo_image);
        tv_cover_image = findViewById(R.id.tv_cover_image);
        tag_edit_text = findViewById(R.id.tag_edit_text);
        topic_spin = findViewById(R.id.topic_spin);
        tv_save_change_button = findViewById(R.id.tv_save_change_button);
        age_group_spin = findViewById(R.id.age_group_spin);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_IMAGE) {

          //  Toast.makeText(TVChannelSettingsActivity.this, "imageKey: "+imageKey, Toast.LENGTH_SHORT).show();

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
                        tvLogoSettingsImage = new File(uri.getPath());
                        Log.e("file ", "path " + tvLogoSettingsImage.getAbsolutePath());

                        Uri uu = Uri.fromFile(tvLogoSettingsImage);
                        Glide.with(TVChannelSettingsActivity.this).
                                load(tvLogoSettingsImage).
                                placeholder(R.drawable.ic_launcher_background).
                                into(tv_logo_image);
                    }else if(imageKey.equals("2"))
                    {
                        tvCoverSettingsImage = new File(uri.getPath());
                        Uri uu = Uri.fromFile(tvCoverSettingsImage);
                        Glide.with(TVChannelSettingsActivity.this).
                                load(tvCoverSettingsImage).
                                placeholder(R.drawable.ic_launcher_background).
                                into(tv_cover_image);
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }



}