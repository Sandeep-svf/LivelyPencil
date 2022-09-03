package com.webnmobapps.livelyPencil.Activity.JoinUs;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.SwitchCompat;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.Manifest;
import android.app.Activity;
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
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.webnmobapps.livelyPencil.Activity.Utility.ImagePickerActivity;
import com.webnmobapps.livelyPencil.Activity.Utility.Permission;
import com.webnmobapps.livelyPencil.R;

import java.io.File;
import java.io.IOException;

public class StreamPageActivity extends AppCompatActivity {

    AppCompatImageView stream_page_Image, stream_user_image;
    AppCompatButton confirm_button2, confirm_button, final_confirm_button;
    AlertDialog dialogs;
    FrameLayout fram_layout_stream_page;
    ConstraintLayout layout_first, need_to_hide;
    SwitchMaterial switchMaterial;
    SwitchCompat final_switch_compact;
    AppCompatEditText Stream_name_title, final_stream_title_name;
    public static final int REQUEST_IMAGE = 100;
    private Uri uri;
    private String userStreamNameData , streamPagePrivacy, userPassword , confirmUserPassword;
    SwitchMaterial private_public;
    AppCompatTextView private_public_text;



    private static final int MY_CAMERA_REQUEST_CODE = 100;
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

    AppCompatEditText passwordEditText, confirm_password_EditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stream_page);
        inits();

        private_public.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(private_public.isChecked()) private_public.setText("Public");
                if(! private_public.isChecked()) private_public.setText("Private");
            }
        });

        stream_page_Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final LayoutInflater inflater = StreamPageActivity.this.getLayoutInflater();
                View alertLayout = inflater.inflate(R.layout.test_dialog_xml, null);

                final ImageView close_dialog = alertLayout.findViewById(R.id.close_dialog);
                final ImageView camera_icon = alertLayout.findViewById(R.id.camera_icon);
                final ImageView browse_icon = alertLayout.findViewById(R.id.browse_icon);

                final AlertDialog.Builder alert = new AlertDialog.Builder(StreamPageActivity.this);

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
        confirm_button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  Toast.makeText(StreamPageActivity.this, "Please select stream cover image.", Toast.LENGTH_SHORT).show();
                alert_dialog_message("6");
            }
        });

        confirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(validation()) {


                    getUserFormData();

                    SharedPreferences getUserIdData = getApplicationContext().getSharedPreferences("AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = getUserIdData.edit();
                    editor.putString("usreStreamPageCoverImage", String.valueOf(uri));
                    editor.putString("streamPagePrivacy", String.valueOf(streamPagePrivacy));
                    editor.putString("userStreamNameData", String.valueOf(userStreamNameData));
                    editor.putString("userPasswordData", String.valueOf(userPassword));
                    editor.apply();

                    Intent intent = new Intent(StreamPageActivity.this, SelectIntrestActivity.class);
                    startActivity(intent);
                }
            }
        });

    }

    private  boolean validation() {

         String p1, p2;
         p1 = passwordEditText.getText().toString();
         p2 = confirm_password_EditText.getText().toString();

         Log.e("DATA",p1+":"+p2);

        if(Stream_name_title.getText().toString().equals(""))
        {
            alert_dialog_message("5");
            return  false;
        } else if(streamImage == null)
        {
            alert_dialog_message("6");
            return  false;
        }else if(passwordEditText.getText().toString().equals("")){
            alert_dialog_message("1");
            return false;
        }else if (passwordEditText.getText().toString().length() < 6) {
            alert_dialog_message("2");
            //Toast.makeText(SetPasswordActivity.this, "Minimum password length should be atleast six digit", Toast.LENGTH_SHORT).show();
            return false;
        }else if(confirm_password_EditText.getText().toString().equals("")){
            alert_dialog_message("3");
            return false;
        }else if (!p1.equals(p2)) {
            alert_dialog_message("4");
            // Toast.makeText(SetPasswordActivity.this, "Password did not match", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void alert_dialog_message(String value) {

        final LayoutInflater inflater = StreamPageActivity.this.getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.test_dialog_xml_otp, null);

        final ImageView close_dialog = alertLayout.findViewById(R.id.close_dialog);
        final TextView error_message = alertLayout.findViewById(R.id.error_message);


        if(value.equals("1"))
        {
            error_message.setText(getResources().getString(R.string.please_enter_password));
        }else if(value.equals("2"))
        {
            error_message.setText(getResources().getString(R.string.minimum_password_length));
        }else if(value.equals("3"))
        {
            error_message.setText(getResources().getString(R.string.please_enter_confirm_password));
        }else if(value.equals("4"))
        {
            error_message.setText(getResources().getString(R.string.password_did_not_match));
        }else if(value.equals("5"))
        {
            error_message.setText(getResources().getString(R.string.enter_stream_name));
        }else if(value.equals("6"))
        {
            error_message.setText(getResources().getString(R.string.select_stream_image));
        }




        final AlertDialog.Builder alert = new AlertDialog.Builder(StreamPageActivity.this);

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

    private void getUserFormData() {
        userStreamNameData = Stream_name_title.getText().toString();
        userPassword = passwordEditText.getText().toString();
        confirmUserPassword = confirm_password_EditText.getText().toString();
        if(private_public.isChecked())
        {
            streamPagePrivacy = "1";
        }else
        {
            streamPagePrivacy = "0";
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void profile_camera_open() {

        PackageManager packageManager = getPackageManager();

        boolean readExternal = Permission.checkPermissionReadExternal(StreamPageActivity.this);
        boolean writeExternal = Permission.checkPermissionReadExternal2(StreamPageActivity.this);
        boolean camera = Permission.checkPermissionCamera(StreamPageActivity.this);

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


                Intent intent = new Intent(StreamPageActivity.this, ImagePickerActivity.class);
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
            Toast.makeText(StreamPageActivity.this, "camera permission required", Toast.LENGTH_LONG).show();
            requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
        }

    }

    private void gallery() {

        boolean readExternal = Permission.checkPermissionReadExternal(StreamPageActivity.this);
        boolean writeExternal = Permission.checkPermissionReadExternal2(StreamPageActivity.this);
        boolean camera = Permission.checkPermissionCamera(StreamPageActivity.this);
        if (readExternal && camera ) {
      /*      Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, PICK_IMAGE_G);*/


            Intent intent = new Intent(StreamPageActivity.this, ImagePickerActivity.class);
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
            Glide.with(StreamPageActivity.this).load(selectedImagePath1).into(stream_user_image);
            Log.e("userImage1", "BB");

            confirm_button.setVisibility(View.GONE);
            confirm_button2.setVisibility(View.VISIBLE);

        }else if ( requestCode == CAMERA_PIC_REQUEST_R && resultCode == RESULT_OK )
        {
            key = "1";
            //  Toast.makeText(this, "R Code Working", Toast.LENGTH_SHORT).show();


            try {
                thumbnail6 = MediaStore.Images.Media.getBitmap(
                        StreamPageActivity.this.getContentResolver(), imageUri7);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //setUserProfile.setImageBitmap(thumbnail);


            File file = new File(getRealPathFromURIs(imageUri7));

            Glide.with(StreamPageActivity.this)
                    .load(file)
                    .placeholder(R.color.text_color)
                    .into(stream_user_image);

            confirm_button.setVisibility(View.GONE);
            confirm_button2.setVisibility(View.VISIBLE);

            profileImage  = new File(getRealPathFromURIs(imageUri7));
        } else if(requestCode == REQUEST_IMAGE)
        {


            if (resultCode == Activity.RESULT_OK) {
                uri = data.getParcelableExtra("path");

                if(uri.equals(""))
                {
                    need_to_hide.setVisibility(View.VISIBLE);
                }else
                {
                    need_to_hide.setVisibility(View.GONE);
                    confirm_button2.setVisibility(View.GONE);
                    confirm_button.setVisibility(View.VISIBLE);
                }

                // String sel_path = getpath(uri);
                try {
                    // You can update this bitmap to your server
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);

                    // loading profile image from local cache
                    //loadProfile(uri.toString());
                    streamImage = new File(uri.getPath());
                    Log.e("file ", "path " + streamImage.getAbsolutePath());

                    Uri uu = Uri.fromFile(streamImage);
                    Glide.with(this).load(uu)
                            .into(stream_user_image);


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }


    }

    private void inits() {
        stream_page_Image = findViewById(R.id.stream_page_Image);
        confirm_button2 = findViewById(R.id.confirm_button2);
        confirm_button = findViewById(R.id.confirm_button);
        stream_user_image = findViewById(R.id.stream_user_image);
        private_public = findViewById(R.id.private_public);
        Stream_name_title = findViewById(R.id.Stream_name_title);
        need_to_hide = findViewById(R.id.need_to_hide);
        private_public_text = findViewById(R.id.private_public_text);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirm_password_EditText = findViewById(R.id.confirm_password_EditText);

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