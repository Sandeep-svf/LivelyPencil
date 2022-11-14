package com.webnmobapps.livelyPencil.Activity.NewChangePhase;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
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

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder;
import com.webnmobapps.livelyPencil.Activity.Book.CreateBookActivity;
import com.webnmobapps.livelyPencil.Activity.Book.WebviewEditorActivity;
import com.webnmobapps.livelyPencil.Activity.JoinUs.NameEmailActivity;
import com.webnmobapps.livelyPencil.Activity.JoinUs.NameEmailActivity2;
import com.webnmobapps.livelyPencil.Activity.Login.LoginJoinusActivity;
import com.webnmobapps.livelyPencil.Activity.UserWall.HomeActivity;
import com.webnmobapps.livelyPencil.Activity.Utility.ImagePickerActivity;
import com.webnmobapps.livelyPencil.Activity.Utility.Permission;
import com.webnmobapps.livelyPencil.Fragment.TopMenu.RadioFragment;
import com.webnmobapps.livelyPencil.ModelPython.CommonStatusMessageModelPython;
import com.webnmobapps.livelyPencil.ModelPython.NotificationSettingModel;
import com.webnmobapps.livelyPencil.ModelPython.NotificationModelSettingData;
import com.webnmobapps.livelyPencil.ModelPython.RoleSettingDataModel;
import com.webnmobapps.livelyPencil.ModelPython.RoleSettingModel;
import com.webnmobapps.livelyPencil.ModelPython.SettingModel;
import com.webnmobapps.livelyPencil.ModelPython.SettingModelData;
import com.webnmobapps.livelyPencil.ModelPython.UserProfileData;
import com.webnmobapps.livelyPencil.ModelPython.UserProfileModel;
import com.webnmobapps.livelyPencil.R;
import com.webnmobapps.livelyPencil.RetrofitApi.API_Client;
import com.webnmobapps.livelyPencil.utility.StaticKey;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsFragment extends Fragment implements  com.tsongkha.spinnerdatepicker.DatePickerDialog.OnDateSetListener {

    private static final int REQUEST_IMAGE = 100;
    private static final int MY_CAMERA_REQUEST_CODE = 100;

    AppCompatImageView support_center_image;
    ConstraintLayout chang_user_profile_layout;
    AppCompatEditText name_editText, surname_editText,streamname_editText,country_name_editText;
    AppCompatTextView date_spin, month_spin, year_spin,change_username_text_layout;
    SwitchMaterial stream_privacy_setting_switchmaterial, notification_privacy_setting_switchmaterial;
    private String dayData, monthData, yearData;
    private Context  context;
    private String finalAccessToken,accessToken;
    private String notificatinStatus, roleStatus;
    CircleImageView usere_profile_circle_image_view;
    AppCompatTextView user_name_data_text;
    AlertDialog dialogs;

    AppCompatButton save_change_button;
    AppCompatTextView terms_privacy_help_layout;

    private String userName, userSurName, streamName, dob, appUserName, country, userId;


    private Uri uri;
    private static final int PICK_IMAGE_G = 12547;
    public static final int RESULT_OK = -1;
    private Uri img;
    private File profileImage, streamImage;
    private ContentValues values7;
    private Uri imageUri7;
    private static final int CAMERA_PIC_REQUEST_R = 25418;
    private Bitmap thumbnail6;
    private String imageBase64 , key;
    private Uri selectedImageUri;
    private String temp;
    private AppCompatButton logout_setting_button;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_settings, container, false);
        intis(view);
        context = getActivity();


        SharedPreferences sharedPreferences= getActivity().getSharedPreferences("AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
        // user_id=sharedPreferences.getString("UserID","");
        accessToken=sharedPreferences.getString("accessToken","");
        finalAccessToken = StaticKey.prefixTokem+accessToken;
        
        // notification settings get API
        notification_settring_api();
        role_setting_api();
        user_profle_api();

        terms_privacy_help_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WebviewEditorActivity.class);
                intent.putExtra("key","1");
                startActivity(intent);
            }
        });

        logout_setting_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.logout_dialog);
                LinearLayout noDialogLogout = dialog.findViewById(R.id.noDialogLogout);
                LinearLayout yesDialogLogout = dialog.findViewById(R.id.yesDialogLogout);


                dialog.show();
                Window window = dialog.getWindow();
                window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                yesDialogLogout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //relaseMediaPlayer();
                        //geting userID data


                        SharedPreferences getUserIdData = getActivity().getSharedPreferences("AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = getUserIdData.edit();
                        editor.putString("UserID", "");


                        editor.apply();
                        Intent intent = new Intent(getActivity(), LoginJoinusActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // To clean up all activities
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.putExtra("finish", true);
                        startActivity(intent);

//                        logout_api();
                    }

                });

                noDialogLogout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                 /*       RadioFragment radioFragment = new RadioFragment();
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        //  ((ConstraintLayout)findViewById(R.id.fragment_contaner)).removeAllViews();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
                        fragmentTransaction.replace(R.id.fragment_contaner, radioFragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();*/



                        dialog.dismiss();
                    }
                });
            }
        });

        change_username_text_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //  open popup
                final LayoutInflater inflater =getActivity().getLayoutInflater();
                View alertLayout = inflater.inflate(R.layout.test_dialog_xml2, null);

                 AppCompatEditText userNameEditText = alertLayout.findViewById(R.id.userNameEditText);
                 AppCompatButton userNameSaveButton  = alertLayout.findViewById(R.id.userNameSaveButton);
                 ImageView close_dialog = alertLayout.findViewById(R.id.close_dialog);

                final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

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

                userNameSaveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        temp = userNameEditText.getText().toString();

                        Log.e("check_username",temp+"ok");
                        get_form_data("2");

                        settings_update_api();
                        dialogs.dismiss();
                    }
                });





            }
        });


        save_change_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_form_data("1");
                if(validation()){
                    // update APIs
                    settings_update_api();
                }
            }
        });


        chang_user_profile_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final LayoutInflater inflater =getActivity().getLayoutInflater();
                View alertLayout = inflater.inflate(R.layout.test_dialog_xml, null);

                final ImageView close_dialog = alertLayout.findViewById(R.id.close_dialog);
                final ImageView camera_icon = alertLayout.findViewById(R.id.camera_icon);
                final ImageView browse_icon = alertLayout.findViewById(R.id.browse_icon);

                final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

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
        
        
        stream_privacy_setting_switchmaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                role_change_settings_api();
            }
        });

        notification_privacy_setting_switchmaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notification_change_settings_api();
            }
        });

        date_spin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intisialization_method(v);

            }
        });

        month_spin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intisialization_method(v);
            }
        });

        year_spin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intisialization_method(v);
            }
        });



        return view;
    }

    private boolean validation() {
        if(userName.equals("")){
            alert_dialog_message("1");
            return false;

        }else if(userSurName.equals("")){
            alert_dialog_message("2");
            return false;

        }else if(streamName.equals("")){
            alert_dialog_message("3");
            return false;

        }else if(country.equals("")){
            alert_dialog_message("4");
            return false;

        }


        return true;
    }
    private void alert_dialog_message(String value) {

        final LayoutInflater inflater = getActivity().getLayoutInflater();
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
            error_message.setText(getResources().getString(R.string.please_enter_stream));
        }else if(value.equals("4"))
        {
            error_message.setText(getResources().getString(R.string.please_enter_country));
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



        final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

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
    private void settings_update_api(){

        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();

        MultipartBody.Part userProifleImage;

     //   filePart1 = MultipartBody.Part.createFormData("nationalId", "", RequestBody.create(MediaType.parse("image/*"), ""));


        if(profileImage == null){
            userProifleImage = MultipartBody.Part.createFormData("image", "", RequestBody.create(MediaType.parse("image/*"), ""));

        }else{
            userProifleImage = MultipartBody.Part.createFormData("image", profileImage.getName(), RequestBody.create(MediaType.parse("image/*"), profileImage));

        }


        RequestBody bookNameDataRB = RequestBody.create(MediaType.parse("text/plain"), userName);
        RequestBody userIdDataRB = RequestBody.create(MediaType.parse("text/plain"), userId);
        RequestBody userSurNameRB = RequestBody.create(MediaType.parse("text/plain"), userSurName);
        RequestBody dobDataRB = RequestBody.create(MediaType.parse("text/plain"), dob);
        RequestBody appUserNameDataRB = RequestBody.create(MediaType.parse("text/plain"), appUserName);
        RequestBody countryDataRB = RequestBody.create(MediaType.parse("text/plain"), country);
        RequestBody streamNameDataRB = RequestBody.create(MediaType.parse("text/plain"), streamName);



        Call<SettingModel> call = API_Client.getClient().SETTING_MODEL_CALL_UPDATE(finalAccessToken,userIdDataRB,
                appUserNameDataRB, userSurNameRB, userSurNameRB,streamNameDataRB,dobDataRB,countryDataRB,userProifleImage);

        call.enqueue(new Callback<SettingModel>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<SettingModel> call, Response<SettingModel> response) {
                pd.dismiss();


                try {
                    if (response.isSuccessful()) {
                        String message = response.body().getMessage();
                        String success = response.body().getStatus();

                        if (success.equals("true") || success.equals("True")) {
                            Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                            user_profle_api();
                            pd.dismiss();

                        } else {
                          //  alert_dialog_message("7");
                            pd.dismiss();
                        }


                    } else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(getActivity(), jObjError.getString("message"), Toast.LENGTH_LONG).show();

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
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (
                        Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<SettingModel> call, Throwable t) {
                Log.e("bhgyrrrthbh", String.valueOf(t));
                if (t instanceof IOException) {
                    Toast.makeText(getActivity(), "This is an actual network failure :( inform the user and possibly retry)" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                } else {
                    Log.e("conversion issue", t.getMessage());
                    Toast.makeText(getActivity(), "Please Check your Internet Connection...." + t.getMessage(), Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            }
        });


    }

    private void get_form_data(String key) {
        userName = name_editText.getText().toString();
        userSurName = surname_editText.getText().toString();
        streamName = streamname_editText.getText().toString();
        country = country_name_editText.getText().toString();
        if(key.equals("1")){
            appUserName = user_name_data_text.getText().toString();

        }else
        {
            appUserName = temp;
        }
        Log.e("check_username",appUserName+"final_value");
       
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void profile_camera_open() {

        PackageManager packageManager = getActivity().getPackageManager();

        boolean readExternal = Permission.checkPermissionReadExternal(getActivity());
        boolean writeExternal = Permission.checkPermissionReadExternal2(getActivity());
        boolean camera = Permission.checkPermissionCamera(getActivity());

        if (camera && writeExternal && readExternal ) {
            if (packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {



                Intent intent = new Intent(getActivity(), ImagePickerActivity.class);
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
            Toast.makeText(getActivity(), "camera permission required", Toast.LENGTH_LONG).show();
            requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
        }

    }

    private void gallery() {

        boolean readExternal = Permission.checkPermissionReadExternal(getActivity());
        boolean writeExternal = Permission.checkPermissionReadExternal2(getActivity());
        boolean camera = Permission.checkPermissionCamera(getActivity());
        if (readExternal && camera ) {
      /*      Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, PICK_IMAGE_G);*/


            Intent intent = new Intent(getActivity(), ImagePickerActivity.class);
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


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


         if ( requestCode == CAMERA_PIC_REQUEST_R && resultCode == RESULT_OK )
        {
            key = "1";
            //  Toast.makeText(this, "R Code Working", Toast.LENGTH_SHORT).show();


            try {
                thumbnail6 = MediaStore.Images.Media.getBitmap(
                        getActivity().getContentResolver(), imageUri7);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //setUserProfile.setImageBitmap(thumbnail);


            File file = new File(getRealPathFromURIs(imageUri7));

            Glide.with(getActivity())
                    .load(file)
                    .placeholder(R.color.text_color)
                    .into(usere_profile_circle_image_view);



            profileImage  = new File(getRealPathFromURIs(imageUri7));
        } else if(requestCode == REQUEST_IMAGE)
        {


            if (resultCode == Activity.RESULT_OK) {
                uri = data.getParcelableExtra("path");



                // String sel_path = getpath(uri);
                try {
                    // You can update this bitmap to your server
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);

                    // loading profile image from local cache
                    //loadProfile(uri.toString());
                    streamImage = new File(uri.getPath());
                    Log.e("file ", "path " + streamImage.getAbsolutePath());
                    profileImage = streamImage;

                    Uri uu = Uri.fromFile(streamImage);
                    Glide.with(this).load(uu)
                            .into(usere_profile_circle_image_view);


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }


    }
    public String getRealPathFromURIs(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    public String getPath(Uri uri)
    {
        Cursor cursor=null;
        try {
            String[] projection = {MediaStore.Images.Media.DATA};
            cursor = getActivity().getContentResolver().query(uri, projection, null, null, null);
            if (cursor == null) return null;
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();

            return cursor.getString(column_index);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return "";
    }

    private void user_profle_api() {




            final ProgressDialog pd = new ProgressDialog(getActivity());
            pd.setCancelable(false);
            pd.setMessage("loading...");
            pd.show();





            Call<SettingModel> call = API_Client.getClient().SETTING_MODEL_CALL(finalAccessToken);

            call.enqueue(new Callback<SettingModel>() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onResponse(Call<SettingModel> call, Response<SettingModel> response) {
                    pd.dismiss();


                    try {
                        if (response.isSuccessful()) {
                            String message = response.body().getMessage();
                            String success = response.body().getStatus();

                            if (success.equals("true") || success.equals("True")) {
                                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();


                                SettingModel settingModel = response.body();
                                SettingModelData settingModelData = settingModel.getData();

                                name_editText.setText(settingModelData.getFirstName());
                                surname_editText.setText(settingModelData.getLastName());
                                streamname_editText.setText(settingModelData.getStreamTitle());

                                userId = String.valueOf(settingModelData.getId());
                                appUserName = settingModelData.getUsername();
                                user_name_data_text.setText(appUserName);

                                Glide.with(getActivity()).load(API_Client.BASE_IMAGE+settingModelData.getImage())
                                        .placeholder(R.drawable.ic_launcher_background)
                                        .into(usere_profile_circle_image_view);
                                try {
                                    country_name_editText.setText(settingModelData.getCountry());
                                } catch (Exception exception) {
                                    exception.printStackTrace();

                                }

                                String dobRawData,year,month,day;

                                dobRawData = settingModelData.getBirthDate();
                                dob = settingModelData.getBirthDate();

                                try {
                                    String[] parts = dobRawData.split("-");
                                    year = parts[0];
                                    month = parts[1];
                                    day = parts[2];

                                    date_spin.setText(day);
                                    month_spin.setText(month);
                                    year_spin.setText(year);
                                } catch (Exception exception) {
                                    exception.printStackTrace();
                                }

                               /* UserProfileModel userProfileModel = response.body();
                                UserProfileData userProfileData = userProfileModel.getUserProfileData();


                                try {
                                    name_editText.setText(userProfileData.getFirstName());
                                    surname_editText.setText(userProfileData.getLastName());
                                    streamname_editText.setText(userProfileData.getStreamTitle());
                                    Glide.with(getActivity()).load(API_Client.BASE_IMAGE+userProfileData.getImage())
                                            .placeholder(R.drawable.ic_launcher_background)
                                            .into(usere_profile_circle_image_view);
                                    country_name_editText.setText("India");
                                } catch (Exception exception) {
                                    exception.printStackTrace();
                                    Toast.makeText(getActivity(), "Something went wrong, while loading user profile data form APIs.", Toast.LENGTH_SHORT).show();
                                }*/

                                pd.dismiss();
                            } else {
                                Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                                pd.dismiss();
                            }


                        } else {
                            try {
                                JSONObject jObjError = new JSONObject(response.errorBody().string());
                                Toast.makeText(getActivity(), jObjError.getString("message"), Toast.LENGTH_LONG).show();
                                switch (response.code()) {
                                    case 400:
                                        Toast.makeText(getActivity(), "The server did not understand the request.", Toast.LENGTH_SHORT).show();
                                        break;
                                    case 401:
                                        Toast.makeText(getActivity(), "Unauthorized The requested page needs a username and a password.", Toast.LENGTH_SHORT).show();
                                        break;
                                    case 404:
                                        Toast.makeText(getActivity(), "The server can not find the requested page.", Toast.LENGTH_SHORT).show();
                                        break;
                                    case 500:
                                        Toast.makeText(getActivity(), "Internal Server Error..", Toast.LENGTH_SHORT).show();
                                        break;
                                    case 503:
                                        Toast.makeText(getActivity(), "Service Unavailable..", Toast.LENGTH_SHORT).show();
                                        break;
                                    case 504:
                                        Toast.makeText(getActivity(), "Gateway Timeout..", Toast.LENGTH_SHORT).show();
                                        break;
                                    case 511:
                                        Toast.makeText(getActivity(), "Network Authentication Required ..", Toast.LENGTH_SHORT).show();
                                        break;
                                    default:
                                        Toast.makeText(getActivity(), "unknown error", Toast.LENGTH_SHORT).show();
                                        break;
                                }

                            } catch (Exception e) {
                                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    } catch (
                            Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<SettingModel> call, Throwable t) {
                    Log.e("bhgyrrrthbh", String.valueOf(t));
                    if (t instanceof IOException) {
                        Toast.makeText(getActivity(), "This is an actual network failure :( inform the user and possibly retry)" + t.getMessage(), Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    } else {
                        Log.e("conversion issue", t.getMessage());
                        Toast.makeText(getActivity(), "Please Check your Internet Connection...." + t.getMessage(), Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }
                }
            });

        }

    private void role_change_settings_api() {



            final ProgressDialog pd = new ProgressDialog(getActivity());
            pd.setCancelable(false);
            pd.setMessage("loading...");
            pd.show();

            String roleStatus="";

            if(stream_privacy_setting_switchmaterial.isChecked()){
                roleStatus = "1";
            }else{
                roleStatus = "0";
            }



            Call<CommonStatusMessageModelPython> call = API_Client.getClient().ROLE_CHANGE_SETTINGS_CALL(finalAccessToken,roleStatus);

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
                                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                                role_setting_api();
                                pd.dismiss();
                            } else {
                                Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                                pd.dismiss();
                            }


                        } else {
                            try {
                                JSONObject jObjError = new JSONObject(response.errorBody().string());
                                Toast.makeText(getActivity(), jObjError.getString("message"), Toast.LENGTH_LONG).show();
                                switch (response.code()) {
                                    case 400:
                                        Toast.makeText(getActivity(), "The server did not understand the request.", Toast.LENGTH_SHORT).show();
                                        break;
                                    case 401:
                                        Toast.makeText(getActivity(), "Unauthorized The requested page needs a username and a password.", Toast.LENGTH_SHORT).show();
                                        break;
                                    case 404:
                                        Toast.makeText(getActivity(), "The server can not find the requested page.", Toast.LENGTH_SHORT).show();
                                        break;
                                    case 500:
                                        Toast.makeText(getActivity(), "Internal Server Error..", Toast.LENGTH_SHORT).show();
                                        break;
                                    case 503:
                                        Toast.makeText(getActivity(), "Service Unavailable..", Toast.LENGTH_SHORT).show();
                                        break;
                                    case 504:
                                        Toast.makeText(getActivity(), "Gateway Timeout..", Toast.LENGTH_SHORT).show();
                                        break;
                                    case 511:
                                        Toast.makeText(getActivity(), "Network Authentication Required ..", Toast.LENGTH_SHORT).show();
                                        break;
                                    default:
                                        Toast.makeText(getActivity(), "unknown error", Toast.LENGTH_SHORT).show();
                                        break;
                                }

                            } catch (Exception e) {
                                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
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
                        Toast.makeText(getActivity(), "This is an actual network failure :( inform the user and possibly retry)" + t.getMessage(), Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    } else {
                        Log.e("conversion issue", t.getMessage());
                        Toast.makeText(getActivity(), "Please Check your Internet Connection...." + t.getMessage(), Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }
                }
            });

        }

    private void notification_change_settings_api() {


            final ProgressDialog pd = new ProgressDialog(getActivity());
            pd.setCancelable(false);
            pd.setMessage("loading...");
            pd.show();

            String notificationStatus="";

        if(notification_privacy_setting_switchmaterial.isChecked()){
            notificationStatus = "1";
        }else{
            notificationStatus = "0";
        }



            Call<CommonStatusMessageModelPython> call = API_Client.getClient().NOTIFICATION_MODEL_CHANGE_SETTINGS_CALL(finalAccessToken,notificationStatus);

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
                                    Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                                    notification_settring_api();
                                    pd.dismiss();
                            } else {
                                Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                                pd.dismiss();
                            }


                        } else {
                            try {
                                JSONObject jObjError = new JSONObject(response.errorBody().string());
                                Toast.makeText(getActivity(), jObjError.getString("message"), Toast.LENGTH_LONG).show();
                                switch (response.code()) {
                                    case 400:
                                        Toast.makeText(getActivity(), "The server did not understand the request.", Toast.LENGTH_SHORT).show();
                                        break;
                                    case 401:
                                        Toast.makeText(getActivity(), "Unauthorized The requested page needs a username and a password.", Toast.LENGTH_SHORT).show();
                                        break;
                                    case 404:
                                        Toast.makeText(getActivity(), "The server can not find the requested page.", Toast.LENGTH_SHORT).show();
                                        break;
                                    case 500:
                                        Toast.makeText(getActivity(), "Internal Server Error..", Toast.LENGTH_SHORT).show();
                                        break;
                                    case 503:
                                        Toast.makeText(getActivity(), "Service Unavailable..", Toast.LENGTH_SHORT).show();
                                        break;
                                    case 504:
                                        Toast.makeText(getActivity(), "Gateway Timeout..", Toast.LENGTH_SHORT).show();
                                        break;
                                    case 511:
                                        Toast.makeText(getActivity(), "Network Authentication Required ..", Toast.LENGTH_SHORT).show();
                                        break;
                                    default:
                                        Toast.makeText(getActivity(), "unknown error", Toast.LENGTH_SHORT).show();
                                        break;
                                }

                            } catch (Exception e) {
                                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
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
                        Toast.makeText(getActivity(), "This is an actual network failure :( inform the user and possibly retry)" + t.getMessage(), Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    } else {
                        Log.e("conversion issue", t.getMessage());
                        Toast.makeText(getActivity(), "Please Check your Internet Connection...." + t.getMessage(), Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }
                }
            });

        }

        private void role_setting_api(){


                final ProgressDialog pd = new ProgressDialog(getActivity());
                pd.setCancelable(false);
                pd.setMessage("loading...");
                pd.show();

                Call<RoleSettingModel> call = API_Client.getClient().ROLE_SETTING_MODEL_CALL(finalAccessToken);

                call.enqueue(new Callback<RoleSettingModel>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onResponse(Call<RoleSettingModel> call, Response<RoleSettingModel> response) {
                        pd.dismiss();


                        try {
                            if (response.isSuccessful()) {
                                String message = response.body().getMessage();
                                String success = response.body().getStatus();

                                if (success.equals("true") || success.equals("True")) {
                                    RoleSettingModel roleSettingModel = response.body();
                                    RoleSettingDataModel roleSettingDataModel = roleSettingModel.getRoleSettingDataModel();

                                    roleStatus = roleSettingDataModel.getRole();

                                    Log.e("notificatinStatus",notificatinStatus+"ok");

                                    // 0-> public
                                    // 1-> private

                                    if(roleStatus.equals("0")){
                                        stream_privacy_setting_switchmaterial.setChecked(false);
                                        stream_privacy_setting_switchmaterial.setText("Private");
                                    }else if(roleStatus.equals("1")){
                                        stream_privacy_setting_switchmaterial.setChecked(true);
                                        stream_privacy_setting_switchmaterial.setText("Public");

                                    }else{
                                        Toast.makeText(getActivity(), "Something went wrong while loading notification settings data", Toast.LENGTH_SHORT).show();
                                    }




                                } else {
                                    Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                                    pd.dismiss();
                                }


                            } else {
                                try {
                                    JSONObject jObjError = new JSONObject(response.errorBody().string());
                                    Toast.makeText(getActivity(), jObjError.getString("message"), Toast.LENGTH_LONG).show();
                                    switch (response.code()) {
                                        case 400:
                                            Toast.makeText(getActivity(), "The server did not understand the request.", Toast.LENGTH_SHORT).show();
                                            break;
                                        case 401:
                                            Toast.makeText(getActivity(), "Unauthorized The requested page needs a username and a password.", Toast.LENGTH_SHORT).show();
                                            break;
                                        case 404:
                                            Toast.makeText(getActivity(), "The server can not find the requested page.", Toast.LENGTH_SHORT).show();
                                            break;
                                        case 500:
                                            Toast.makeText(getActivity(), "Internal Server Error..", Toast.LENGTH_SHORT).show();
                                            break;
                                        case 503:
                                            Toast.makeText(getActivity(), "Service Unavailable..", Toast.LENGTH_SHORT).show();
                                            break;
                                        case 504:
                                            Toast.makeText(getActivity(), "Gateway Timeout..", Toast.LENGTH_SHORT).show();
                                            break;
                                        case 511:
                                            Toast.makeText(getActivity(), "Network Authentication Required ..", Toast.LENGTH_SHORT).show();
                                            break;
                                        default:
                                            Toast.makeText(getActivity(), "unknown error", Toast.LENGTH_SHORT).show();
                                            break;
                                    }

                                } catch (Exception e) {
                                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        } catch (
                                Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<RoleSettingModel> call, Throwable t) {
                        Log.e("bhgyrrrthbh", String.valueOf(t));
                        if (t instanceof IOException) {
                            Toast.makeText(getActivity(), "This is an actual network failure :( inform the user and possibly retry)" + t.getMessage(), Toast.LENGTH_SHORT).show();
                            pd.dismiss();
                        } else {
                            Log.e("conversion issue", t.getMessage());
                            Toast.makeText(getActivity(), "Please Check your Internet Connection...." + t.getMessage(), Toast.LENGTH_SHORT).show();
                            pd.dismiss();
                        }
                    }
                });

            }

    private void notification_settring_api() {
  
            final ProgressDialog pd = new ProgressDialog(getActivity());
            pd.setCancelable(false);
            pd.setMessage("loading...");
            pd.show();




            Call<NotificationSettingModel> call = API_Client.getClient().NOTIFICATION_MODEL_SETTINGS_CALL(finalAccessToken);

            call.enqueue(new Callback<NotificationSettingModel>() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onResponse(Call<NotificationSettingModel> call, Response<NotificationSettingModel> response) {
                    pd.dismiss();


                    try {
                        if (response.isSuccessful()) {
                            String message = response.body().getMessage();
                            String success = response.body().getStatus();

                            if (success.equals("true") || success.equals("True")) {
                                NotificationSettingModel notificationSettingModel = response.body();
                                NotificationModelSettingData notificationModelSettingData = notificationSettingModel.getData();

                                notificatinStatus = notificationModelSettingData.getNotification();

                                Log.e("notificatinStatus",notificatinStatus+"ok");

                                // 0-> open
                                // 1-> close

                                if(notificatinStatus.equals("0")){
                                    notification_privacy_setting_switchmaterial.setChecked(false);
                                    notification_privacy_setting_switchmaterial.setText("Close");
                                }else if(notificatinStatus.equals("1")){
                                    notification_privacy_setting_switchmaterial.setChecked(true);
                                    notification_privacy_setting_switchmaterial.setText("Open");

                                }else{
                                    Toast.makeText(getActivity(), "Something went wrong while loading notification settings data", Toast.LENGTH_SHORT).show();
                                }

                               


                            } else {
                                Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                                pd.dismiss();
                            }


                        } else {
                            try {
                                JSONObject jObjError = new JSONObject(response.errorBody().string());
                                Toast.makeText(getActivity(), jObjError.getString("message"), Toast.LENGTH_LONG).show();
                                switch (response.code()) {
                                    case 400:
                                        Toast.makeText(getActivity(), "The server did not understand the request.", Toast.LENGTH_SHORT).show();
                                        break;
                                    case 401:
                                        Toast.makeText(getActivity(), "Unauthorized The requested page needs a username and a password.", Toast.LENGTH_SHORT).show();
                                        break;
                                    case 404:
                                        Toast.makeText(getActivity(), "The server can not find the requested page.", Toast.LENGTH_SHORT).show();
                                        break;
                                    case 500:
                                        Toast.makeText(getActivity(), "Internal Server Error..", Toast.LENGTH_SHORT).show();
                                        break;
                                    case 503:
                                        Toast.makeText(getActivity(), "Service Unavailable..", Toast.LENGTH_SHORT).show();
                                        break;
                                    case 504:
                                        Toast.makeText(getActivity(), "Gateway Timeout..", Toast.LENGTH_SHORT).show();
                                        break;
                                    case 511:
                                        Toast.makeText(getActivity(), "Network Authentication Required ..", Toast.LENGTH_SHORT).show();
                                        break;
                                    default:
                                        Toast.makeText(getActivity(), "unknown error", Toast.LENGTH_SHORT).show();
                                        break;
                                }

                            } catch (Exception e) {
                                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    } catch (
                            Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<NotificationSettingModel> call, Throwable t) {
                    Log.e("bhgyrrrthbh", String.valueOf(t));
                    if (t instanceof IOException) {
                        Toast.makeText(getActivity(), "This is an actual network failure :( inform the user and possibly retry)" + t.getMessage(), Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    } else {
                        Log.e("conversion issue", t.getMessage());
                        Toast.makeText(getActivity(), "Please Check your Internet Connection...." + t.getMessage(), Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }
                }
            });

        }

    private void intisialization_method(View v) {
        String temp = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));

        new SpinnerDatePickerDialogBuilder()
                .context(getActivity())
                .callback(this)
                .spinnerTheme(R.style.NumberPickerStyle)
                .showTitle(true)
                .showDaySpinner(true)
                .defaultDate(2000, 0, 1)
                .maxDate(Integer.parseInt(temp), 0, 1)
                .minDate(1950, 0, 1)
                .build()
                .show();
    }

    private void intis(View view) {
        logout_setting_button = view.findViewById(R.id.logout_setting_button);
        terms_privacy_help_layout = view.findViewById(R.id.terms_privacy_help_layout);
        save_change_button = view.findViewById(R.id.save_change_button);
        user_name_data_text = view.findViewById(R.id.user_name_data_text);
        change_username_text_layout = view.findViewById(R.id.change_username_text_layout);
        support_center_image = view.findViewById(R.id.support_center_image);
        chang_user_profile_layout = view.findViewById(R.id.chang_user_profile_layout);
        name_editText = view.findViewById(R.id.name_editText);
        surname_editText = view.findViewById(R.id.surname_editText);
        streamname_editText = view.findViewById(R.id.streamname_editText);
        country_name_editText = view.findViewById(R.id.country_name_editText);
        date_spin = view.findViewById(R.id.date_spin);
        month_spin = view.findViewById(R.id.month_spin);
        usere_profile_circle_image_view = view.findViewById(R.id.usere_profile_circle_image_view);
        year_spin = view.findViewById(R.id.year_spin);
        stream_privacy_setting_switchmaterial = view.findViewById(R.id.stream_privacy_setting_switchmaterial);
        notification_privacy_setting_switchmaterial = view.findViewById(R.id.notification_privacy_setting_switchmaterial);
    }


    @Override
    public void onDateSet(com.tsongkha.spinnerdatepicker.DatePicker  view, int year, int monthOfYear, int dayOfMonth) {
        monthOfYear = ++monthOfYear;
        Log.e("check","working.*&###########"+year+monthOfYear+dayOfMonth);
        if(monthOfYear == 0)
        {
            month_spin.setText("Jan");
            monthData = "Jan";
        }

        if(monthOfYear == 1)
        {
            month_spin.setText("Feb");
            monthData = "Feb";
        }

        if(monthOfYear == 2)
        {
            month_spin.setText("Mar");
            monthData = "Mar";
        }

        if(monthOfYear == 3)
        {
            month_spin.setText("Apr");
            monthData = "Apr";
        }

        if(monthOfYear == 4)
        {
            month_spin.setText("May");
            monthData = "May";
        }

        if(monthOfYear == 5)
        {
            month_spin.setText("Jun");
            monthData = "Jun";
        }

        if(monthOfYear == 6)
        {
            month_spin.setText("Jul");
            monthData = "Jul";
        }

        if(monthOfYear == 7)
        {
            month_spin.setText("Aug");
            monthData = "Aug";
        }

        if(monthOfYear == 8)
        {
            month_spin.setText("Sep");
            monthData = "Sep";
        }

        if(monthOfYear == 9)
        {
            month_spin.setText("Oct");
            monthData = "Oct";
        }

        if(monthOfYear == 10)
        {
            month_spin.setText("Nov");
            monthData = "Nov";
        }

        if(monthOfYear == 11)
        {
            month_spin.setText("Dec");
            monthData = "Dec";
        }


        year_spin.setText(String.valueOf(year));
        date_spin.setText(String.valueOf(dayOfMonth));
        yearData = String.valueOf(year);



        Log.e("dfssdfsdfsd", String.valueOf(dayOfMonth));
        Log.e("dfssdfsdfsd", String.valueOf(monthData));
        Log.e("dfssdfsdfsd", String.valueOf(year));

        dob = year+"-"+String.valueOf(monthOfYear)+"-"+dayOfMonth;

    }


}