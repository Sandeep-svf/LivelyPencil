package com.webnmobapps.livelyPencil.Activity.JoinUs;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

import android.Manifest;
import android.app.Activity;
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
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.webnmobapps.livelyPencil.Activity.Login.LoginActivity;
import com.webnmobapps.livelyPencil.Activity.Utility.ImagePickerActivity;
import com.webnmobapps.livelyPencil.Activity.Utility.Permission;
import com.webnmobapps.livelyPencil.R;

import java.io.File;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class NameEmailActivity2 extends AppCompatActivity {

    AppCompatTextView help_area_layout, back_to_login;
    AppCompatEditText email_edittext, nameEditText, surnameEditText;
    AppCompatButton next_button;
    private String emailData, nameData, surNameData;
    private String regexEmail = "(?:[A-Za-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[A-Za-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[A-Za-z0-9](?:[A-Za-z0-9-]*[A-Za-z0-9])?\\.)+[A-Za-z0-9](?:[A-Za-z0-9-]*[A-Za-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[A-Za-z0-9]:(?:|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
    CircleImageView uesrProfileImage;
    public static final int REQUEST_IMAGE = 10045;
    private static final int MY_CAMERA_REQUEST_CODE = 100;
    private static final int PICK_IMAGE_G = 124578;
    private Uri selectedImageUri;
    private Bitmap thumbnail6;
    private String imageBase64 , key;
    private File profileImage;
    private Uri imageUri6;
    private Uri uri;
    private static final int CAMERA_PIC_REQUEST_R = 34598;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_email2);

        intis();


        back_to_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NameEmailActivity2.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        uesrProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                AlertDialog dialogs;
                
                final LayoutInflater inflater = NameEmailActivity2.this.getLayoutInflater();
                View alertLayout = inflater.inflate(R.layout.test_dialog_xml, null);

                final ImageView close_dialog = alertLayout.findViewById(R.id.close_dialog);
                final ImageView camera_icon = alertLayout.findViewById(R.id.camera_icon);
                final ImageView browse_icon = alertLayout.findViewById(R.id.browse_icon);

                final AlertDialog.Builder alert = new AlertDialog.Builder(NameEmailActivity2.this);

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

        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get form data
                get_form_data();

                if(validation()){
                    // Saving data in shared preferance...
                    SharedPreferences getUserIdData = getApplicationContext().getSharedPreferences("AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = getUserIdData.edit();
                    editor.putString("userName", String.valueOf(nameData));
                    editor.putString("usersurName", String.valueOf(surNameData));
                    editor.putString("userEmail", String.valueOf(emailData));
                    editor.putString("cameraGalleryimageURI", String.valueOf(uri));
                    //editor.putString("userPhone", String.valueOf(userPhone));
                   // editor.putString("countryCode", String.valueOf( ccp.getSelectedCountryCode()));
                   // editor.putString("key", String.valueOf( key));
                    editor.apply();

                    try {
                        Log.e("verifiy_input_data","nameData: "+nameData);
                        Log.e("verifiy_input_data","surNameData: "+surNameData);
                        Log.e("verifiy_input_data","emailData: "+emailData);
                        Log.e("verifiy_input_data","uri: "+uri);
                    }catch (Exception exception){
                        exception.printStackTrace();
                    }

                  // Calling intent
                    Intent intent = new Intent(NameEmailActivity2.this,StreamPageActivity.class);
                    startActivity(intent);
                }
            }
        });

    }

    private void get_form_data() {
        emailData = email_edittext.getText().toString();
        nameData = nameEditText.getText().toString();
        surNameData = surnameEditText.getText().toString();
    }

    private boolean validation() {
        if(nameData.equals("")){
            // error popup
            alert_dialog_message("1");
            return false;
        }else if (surNameData.equals("")){
            alert_dialog_message("2");
            return false;
        }else if(emailData.equals("")){
            alert_dialog_message("3");
            return false;
        }else if (!emailData.matches(regexEmail)){
            alert_dialog_message("4");
            return false;
        }
        return true;
    }

    private void alert_dialog_message(String value) {

        AlertDialog dialogs;

        final LayoutInflater inflater = NameEmailActivity2.this.getLayoutInflater();
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



        final AlertDialog.Builder alert = new AlertDialog.Builder(NameEmailActivity2.this);

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

    private void intis() {
        help_area_layout = findViewById(R.id.help_area_layout);
        back_to_login = findViewById(R.id.back_to_login);
        email_edittext = findViewById(R.id.email_edittext);
        nameEditText = findViewById(R.id.nameEditText);
        surnameEditText = findViewById(R.id.surnameEditText);
        next_button = findViewById(R.id.next_button);
        uesrProfileImage = findViewById(R.id.upload_user_profile_layout);
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void profile_camera_open() {

        PackageManager packageManager = getPackageManager();

        boolean readExternal = Permission.checkPermissionReadExternal(NameEmailActivity2.this);
        boolean writeExternal = Permission.checkPermissionReadExternal2(NameEmailActivity2.this);
        boolean camera = Permission.checkPermissionCamera(NameEmailActivity2.this);

        if (camera && writeExternal && readExternal ) {
            if (packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
           /*     values6 = new ContentValues();
                values6.put(MediaStore.Images.Media.TITLE, "New Picture");
                values6.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
                imageUri6 = getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values6);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri6);
                startActivityForResult(intent, CAMERA_PIC_REQUEST_R);*/

                Intent intent = new Intent(NameEmailActivity2.this, ImagePickerActivity.class);
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
            Toast.makeText(NameEmailActivity2.this, "camera permission required", Toast.LENGTH_LONG).show();
            requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
        }

    }

    private void gallery() {

        boolean readExternal = Permission.checkPermissionReadExternal(NameEmailActivity2.this);
        boolean writeExternal = Permission.checkPermissionReadExternal2(NameEmailActivity2.this);
        boolean camera = Permission.checkPermissionCamera(NameEmailActivity2.this);
        if (readExternal && camera ) {
           /* Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, PICK_IMAGE_G);*/

            Intent intent = new Intent(NameEmailActivity2.this, ImagePickerActivity.class);
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE_G) {

            key = "2";
            Bitmap bitmap = null;

            selectedImageUri = data.getData();
            //Toast.makeText(getApplicationContext(), String.valueOf(selectedImageUri), Toast.LENGTH_SHORT).show();



            String[] projection = {MediaStore.MediaColumns.DATA};
            Cursor cursor = managedQuery(selectedImageUri, projection, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            cursor.moveToFirst();
            profileImage = new File(cursor.getString(column_index));
            Log.e("userImage1", String.valueOf(profileImage));
            String selectedImagePath1 = getPath(selectedImageUri);
            Log.v("Deatils_path","***"+selectedImagePath1);
            Glide.with(NameEmailActivity2.this).load(selectedImagePath1).into(uesrProfileImage);
            Log.e("userImage1", "BB");



        }else if ( requestCode == CAMERA_PIC_REQUEST_R && resultCode == RESULT_OK )
        {
            key = "1";
            //  Toast.makeText(this, "R Code Working", Toast.LENGTH_SHORT).show();
            //  Log.e("imageUri6", String.valueOf(imageUri6));


            try {
                thumbnail6 = MediaStore.Images.Media.getBitmap(
                        NameEmailActivity2.this.getContentResolver(), imageUri6);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //setUserProfile.setImageBitmap(thumbnail);


            File file = new File(getRealPathFromURIs(imageUri6));

            Glide.with(NameEmailActivity2.this)
                    .load(file)
                    .placeholder(R.color.text_color)
                    .into(uesrProfileImage);

            profileImage  = new File(getRealPathFromURIs(imageUri6));
        }else if(requestCode == REQUEST_IMAGE)
        {



            if (resultCode == Activity.RESULT_OK) {
                uri = data.getParcelableExtra("path");

                Log.e("image_poath", "uri: "+ String.valueOf(uri));

                // String sel_path = getpath(uri);
                try {
                    // You can update this bitmap to your server
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);

                    // loading profile image from local cache
                    //loadProfile(uri.toString());
                    File file = new File(uri.getPath());
                    Log.e("file ", "path " + file.getAbsolutePath());

                    Uri uu = Uri.fromFile(file);
                    Log.e("image_poath","uu: "+ String.valueOf(uu));
                    Glide.with(this).load(uu)
                            .into(uesrProfileImage);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        }


    }

   

    public String getRealPathFromURIs(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(contentUri, proj, null, null, null);
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
            cursor = getContentResolver().query(uri, projection, null, null, null);
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
    
}