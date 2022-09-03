package com.webnmobapps.livelyPencil.Activity.Setting;

import static com.webnmobapps.livelyPencil.RetrofitApi.API_Client.BASE_COVER_IMAGE;
import static com.webnmobapps.livelyPencil.RetrofitApi.API_Client.BASE_IMAGE;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.webnmobapps.livelyPencil.Activity.JoinUs.StreamPageActivity;
import com.webnmobapps.livelyPencil.Activity.Utility.ImagePickerActivity;
import com.webnmobapps.livelyPencil.Activity.Utility.Permission;
import com.webnmobapps.livelyPencil.Model.EditStreamSettingModel;
import com.webnmobapps.livelyPencil.Model.PersonalizationPrivacyModel;
import com.webnmobapps.livelyPencil.Model.Record.StreamSettingResult;
import com.webnmobapps.livelyPencil.Model.StreamSettingModel;
import com.webnmobapps.livelyPencil.R;
import com.webnmobapps.livelyPencil.RetrofitApi.API_Client;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StreamSettingsActivity extends AppCompatActivity {

    AppCompatImageView back_arrow, stream_cover_image;
    AppCompatEditText usernameEditText, stremNameEditText, stream_about_edit_text;
    AppCompatTextView streamCoverImageEditLayout;
    AppCompatButton stream_settings_save_change_button;
    private String user_id;
    List<StreamSettingResult> streamSettingResultList = new ArrayList<>();
    AlertDialog dialogs;
    public static final int REQUEST_IMAGE = 100;
    public static final int REQUEST_IMAGE2 = 1464;
    private static final int MY_CAMERA_REQUEST_CODE = 100;
    private static final int PICK_IMAGE_G = 12547;
    public static final int RESULT_OK = -1;
    private File streamSettingsImage = null, apiImage;
    private Uri uri;
    private String userName, streamName , aboutStream;
    MultipartBody.Part streamSettingCoverImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stream_settings);

        inits();

        SharedPreferences sharedPreferences= getSharedPreferences("AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
        user_id=sharedPreferences.getString("UserID","");

        stream_setting_details_api();

        stream_settings_save_change_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                get_form_data();
                if(validation())
                {
                    edit_stream_setting_api();
                }

            }
        });

        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        streamCoverImageEditLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final LayoutInflater inflater = StreamSettingsActivity.this.getLayoutInflater();
                View alertLayout = inflater.inflate(R.layout.test_dialog_xml, null);

                final ImageView close_dialog = alertLayout.findViewById(R.id.close_dialog);
                final ImageView camera_icon = alertLayout.findViewById(R.id.camera_icon);
                final ImageView browse_icon = alertLayout.findViewById(R.id.browse_icon);

                final AlertDialog.Builder alert = new AlertDialog.Builder(StreamSettingsActivity.this);

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

    }

    private void edit_stream_setting_api() {
        final ProgressDialog pd = new ProgressDialog(StreamSettingsActivity.this);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();


        if(streamSettingsImage == null)
        {
            streamSettingCoverImage = MultipartBody.Part.createFormData("streamcoverimage", "", RequestBody.create(MediaType.parse("image/*"), ""));
        }else
        {
            streamSettingCoverImage = MultipartBody.Part.createFormData("streamcoverimage", streamSettingsImage.getName(), RequestBody.create(MediaType.parse("image/*"), streamSettingsImage));
        }


        RequestBody user_id_RB = RequestBody.create(MediaType.parse("text/plain"), user_id);
        RequestBody userName_RB = RequestBody.create(MediaType.parse("text/plain"), userName);
        RequestBody streamName_RB = RequestBody.create(MediaType.parse("text/plain"), streamName);
        RequestBody aboutStream_RB = RequestBody.create(MediaType.parse("text/plain"), aboutStream);


        Call<EditStreamSettingModel> call = API_Client.getClient().editStreamSetting(user_id_RB,
                userName_RB,
                streamName_RB,
                aboutStream_RB,
                streamSettingCoverImage);

        call.enqueue(new Callback<EditStreamSettingModel>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<EditStreamSettingModel> call, Response<EditStreamSettingModel> response) {
                pd.dismiss();


                try {
                    if (response.isSuccessful()) {
                        String message = response.body().getMessage();
                        String success = response.body().getSuccess();



                        if (success.equals("true") || success.equals("True")) {
                            stream_setting_details_api();
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
            public void onFailure(Call<EditStreamSettingModel> call, Throwable t) {
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
        if(userName.equals(""))
        {
            Toast.makeText(StreamSettingsActivity.this, "Please Enter Username", Toast.LENGTH_SHORT).show();
            return false;
        }else if(streamName.equals(""))
        {
            Toast.makeText(StreamSettingsActivity.this, "Please Enter StreamName", Toast.LENGTH_SHORT).show();
            return false;
        }else if(aboutStream.equals(""))
        {
            Toast.makeText(StreamSettingsActivity.this, "Please Enter About Stream", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void get_form_data() {
        userName = usernameEditText.getText().toString();
        streamName = stremNameEditText.getText().toString();
        aboutStream = stream_about_edit_text.getText().toString();

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void profile_camera_open() {

        PackageManager packageManager = getPackageManager();

        boolean readExternal = Permission.checkPermissionReadExternal(StreamSettingsActivity.this);
        boolean writeExternal = Permission.checkPermissionReadExternal2(StreamSettingsActivity.this);
        boolean camera = Permission.checkPermissionCamera(StreamSettingsActivity.this);

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


                Intent intent = new Intent(StreamSettingsActivity.this, ImagePickerActivity.class);
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
            Toast.makeText(StreamSettingsActivity.this, "camera permission required", Toast.LENGTH_LONG).show();
            requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
        }

    }

    private void gallery() {

        boolean readExternal = Permission.checkPermissionReadExternal(StreamSettingsActivity.this);
        boolean writeExternal = Permission.checkPermissionReadExternal2(StreamSettingsActivity.this);
        boolean camera = Permission.checkPermissionCamera(StreamSettingsActivity.this);
        if (readExternal && camera ) {
      /*      Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, PICK_IMAGE_G);*/


            Intent intent = new Intent(StreamSettingsActivity.this, ImagePickerActivity.class);
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

    private void stream_setting_details_api() {

        final ProgressDialog pd = new ProgressDialog(StreamSettingsActivity.this);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();


        Call<StreamSettingModel> call = API_Client.getClient().streamSettingDetails(user_id);

        call.enqueue(new Callback<StreamSettingModel>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<StreamSettingModel> call, Response<StreamSettingModel> response) {
                pd.dismiss();


                try {
                    if (response.isSuccessful()) {
                        String message = response.body().getMessage();
                        String success = response.body().getSuccess();



                        if (success.equals("true") || success.equals("True")) {

                            streamSettingResultList = response.body().getRecord();
                             apiImage = new File(BASE_COVER_IMAGE + streamSettingResultList.get(0).getStreamcoverimage());
                            usernameEditText.setText(streamSettingResultList.get(0).getUsername());
                            stremNameEditText.setText(streamSettingResultList.get(0).getStreamtitle());
                            Glide.with(StreamSettingsActivity.this).load(BASE_COVER_IMAGE+streamSettingResultList.get(0).getStreamcoverimage()).into(stream_cover_image);
                            stream_about_edit_text.setText(String.valueOf(streamSettingResultList.get(0).getStreamaboutme()));
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
            public void onFailure(Call<StreamSettingModel> call, Throwable t) {
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


        final LayoutInflater inflater = StreamSettingsActivity.this.getLayoutInflater();
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

        final AlertDialog.Builder alert = new AlertDialog.Builder(StreamSettingsActivity.this);

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
        usernameEditText = findViewById(R.id.usernameEditText);
        stremNameEditText = findViewById(R.id.stremNameEditText);
        streamCoverImageEditLayout = findViewById(R.id.streamCoverImageEditLayout);
        stream_cover_image = findViewById(R.id.stream_cover_image);
        stream_about_edit_text = findViewById(R.id.stream_about_edit_text);
        stream_settings_save_change_button = findViewById(R.id.stream_settings_save_change_button);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_IMAGE) {


            if (resultCode == Activity.RESULT_OK) {
                uri = data.getParcelableExtra("path");
                // String sel_path = getpath(uri);
                try {
                    // You can update this bitmap to your server
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);

                    // loading profile image from local cache
                    //loadProfile(uri.toString());
                    streamSettingsImage = new File(uri.getPath());
                    Log.e("file ", "path " + streamSettingsImage.getAbsolutePath());

                    Uri uu = Uri.fromFile(streamSettingsImage);
                    Glide.with(StreamSettingsActivity.this).load(streamSettingsImage)
                            .into(stream_cover_image);


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}