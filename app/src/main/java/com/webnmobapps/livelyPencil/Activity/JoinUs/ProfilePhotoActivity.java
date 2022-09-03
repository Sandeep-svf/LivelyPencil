package com.webnmobapps.livelyPencil.Activity.JoinUs;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

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
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.webnmobapps.livelyPencil.Activity.Utility.ImagePickerActivity;
import com.webnmobapps.livelyPencil.Activity.Utility.Permission;
import com.webnmobapps.livelyPencil.R;

import java.io.File;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfilePhotoActivity extends AppCompatActivity {
    CircleImageView uesrProfileImage;
    AppCompatButton confirm_button, confirm_button2;
    AlertDialog dialogs;
    public static final int REQUEST_IMAGE = 100;


    private static final int MY_CAMERA_REQUEST_CODE = 100;
    private static final int PICK_IMAGE_G = 124578;
    public static final int RESULT_OK = -1;
    private Uri img;
    private File profileImage;
    private ContentValues values6;
    private Uri imageUri6;
    private Uri uri;
    private static final int CAMERA_PIC_REQUEST_R = 34598;
    private Bitmap thumbnail6;
    private String imageBase64 , key;
    private Uri selectedImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_photo);

        inits();


        confirm_button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences getUserIdData = getSharedPreferences("AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = getUserIdData.edit();
                editor.putString("cameraGalleryimageURI", String.valueOf(uri));
                editor.apply();

                Intent intent = new Intent(ProfilePhotoActivity.this,StreamPageActivity.class);
                startActivity(intent);
            }
        });

        uesrProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final LayoutInflater inflater = ProfilePhotoActivity.this.getLayoutInflater();
                View alertLayout = inflater.inflate(R.layout.test_dialog_xml, null);

                final ImageView close_dialog = alertLayout.findViewById(R.id.close_dialog);
                final ImageView camera_icon = alertLayout.findViewById(R.id.camera_icon);
                final ImageView browse_icon = alertLayout.findViewById(R.id.browse_icon);

                final AlertDialog.Builder alert = new AlertDialog.Builder(ProfilePhotoActivity.this);

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

        confirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ProfilePhotoActivity.this, "Please select profile image.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void profile_camera_open() {

        PackageManager packageManager = getPackageManager();

        boolean readExternal = Permission.checkPermissionReadExternal(ProfilePhotoActivity.this);
        boolean writeExternal = Permission.checkPermissionReadExternal2(ProfilePhotoActivity.this);
        boolean camera = Permission.checkPermissionCamera(ProfilePhotoActivity.this);

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

                Intent intent = new Intent(ProfilePhotoActivity.this, ImagePickerActivity.class);
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
            Toast.makeText(ProfilePhotoActivity.this, "camera permission required", Toast.LENGTH_LONG).show();
            requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
        }

    }

    private void gallery() {

        boolean readExternal = Permission.checkPermissionReadExternal(ProfilePhotoActivity.this);
        boolean writeExternal = Permission.checkPermissionReadExternal2(ProfilePhotoActivity.this);
        boolean camera = Permission.checkPermissionCamera(ProfilePhotoActivity.this);
        if (readExternal && camera ) {
           /* Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, PICK_IMAGE_G);*/

            Intent intent = new Intent(ProfilePhotoActivity.this, ImagePickerActivity.class);
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
            Glide.with(ProfilePhotoActivity.this).load(selectedImagePath1).into(uesrProfileImage);
            Log.e("userImage1", "BB");

            confirm_button.setVisibility(View.GONE);
            confirm_button2.setVisibility(View.VISIBLE);

        }else if ( requestCode == CAMERA_PIC_REQUEST_R && resultCode == RESULT_OK )
        {
            key = "1";
            //  Toast.makeText(this, "R Code Working", Toast.LENGTH_SHORT).show();
          //  Log.e("imageUri6", String.valueOf(imageUri6));


            try {
                thumbnail6 = MediaStore.Images.Media.getBitmap(
                        ProfilePhotoActivity.this.getContentResolver(), imageUri6);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //setUserProfile.setImageBitmap(thumbnail);


            File file = new File(getRealPathFromURIs(imageUri6));

            Glide.with(ProfilePhotoActivity.this)
                    .load(file)
                    .placeholder(R.color.text_color)
                    .into(uesrProfileImage);

            confirm_button.setVisibility(View.GONE);
            confirm_button2.setVisibility(View.VISIBLE);

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

                    confirm_button.setVisibility(View.GONE);
                    confirm_button2.setVisibility(View.VISIBLE);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        }


    }

    private void inits() {
        uesrProfileImage = findViewById(R.id.uesrProfileImage);
        confirm_button = findViewById(R.id.confirm_button);
        confirm_button2 = findViewById(R.id.confirm_button2);
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