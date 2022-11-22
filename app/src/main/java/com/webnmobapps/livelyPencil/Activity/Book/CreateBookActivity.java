package com.webnmobapps.livelyPencil.Activity.Book;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;

import androidx.constraintlayout.widget.ConstraintLayout;

import android.Manifest;
import android.app.Activity;
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


import com.webnmobapps.livelyPencil.Activity.JoinUs.SelectIntrestActivity;
import com.webnmobapps.livelyPencil.Activity.UserWall.HomeActivity;
import com.webnmobapps.livelyPencil.Activity.Utility.ImagePickerActivity;
import com.webnmobapps.livelyPencil.Activity.Utility.Permission;
import com.webnmobapps.livelyPencil.ModelPython.CommonStatusMessageModelPython;
import com.webnmobapps.livelyPencil.ModelPython.CommonStatusMessageModelPython;
import com.webnmobapps.livelyPencil.ModelPython.TokenPython;
import com.webnmobapps.livelyPencil.R;
import com.webnmobapps.livelyPencil.RetrofitApi.API_Client;
import com.webnmobapps.livelyPencil.utility.StaticKey;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateBookActivity extends AppCompatActivity {

    AppCompatImageView stream_page_Image, stream_user_image;
    AppCompatButton confirm_button2, confirm_button, final_confirm_button;
    AlertDialog dialogs;
    ConstraintLayout  need_to_hide;
    AppCompatEditText Stream_name_title, bookDescription;
    public static final int REQUEST_IMAGE = 100;
    private Uri uri;
    SwitchMaterial private_public;



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
    private String bookNameData,bookDescriptionData;
    private String finalAccessToken,accessToken,user_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_book);
        inits();


        SharedPreferences sharedPreferences= CreateBookActivity.this.getSharedPreferences("AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
        user_id=sharedPreferences.getString("UserID","");
        accessToken=sharedPreferences.getString("accessToken","");
        finalAccessToken = StaticKey.prefixTokem+accessToken;




        stream_page_Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final LayoutInflater inflater = CreateBookActivity.this.getLayoutInflater();
                View alertLayout = inflater.inflate(R.layout.test_dialog_xml, null);

                final ImageView close_dialog = alertLayout.findViewById(R.id.close_dialog);
                final ImageView camera_icon = alertLayout.findViewById(R.id.camera_icon);
                final ImageView browse_icon = alertLayout.findViewById(R.id.browse_icon);

                final AlertDialog.Builder alert = new AlertDialog.Builder(CreateBookActivity.this);

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
                //  Toast.makeText(CreateBookActivity.this, "Please select stream cover image.", Toast.LENGTH_SHORT).show();
                alert_dialog_message("6");
            }
        });

        confirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(validation()) {

                    getUserFormData();
                    // calling APIs here.....
                   // create_book_api();

                    Intent intent =new Intent(CreateBookActivity.this, SelectIntrestActivity.class);
                    intent.putExtra("key","1");
                    startActivity(intent);
                    
                }
            }
        });
    }



    
    
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void profile_camera_open() {

        PackageManager packageManager = getPackageManager();

        boolean readExternal = Permission.checkPermissionReadExternal(CreateBookActivity.this);
        boolean writeExternal = Permission.checkPermissionReadExternal2(CreateBookActivity.this);
        boolean camera = Permission.checkPermissionCamera(CreateBookActivity.this);

        if (camera && writeExternal && readExternal ) {
            if (packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {



                Intent intent = new Intent(CreateBookActivity.this, ImagePickerActivity.class);
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
            Toast.makeText(CreateBookActivity.this, "camera permission required", Toast.LENGTH_LONG).show();
            requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
        }

    }

    private void gallery() {

        boolean readExternal = Permission.checkPermissionReadExternal(CreateBookActivity.this);
        boolean writeExternal = Permission.checkPermissionReadExternal2(CreateBookActivity.this);
        boolean camera = Permission.checkPermissionCamera(CreateBookActivity.this);
        if (readExternal && camera ) {
      /*      Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, PICK_IMAGE_G);*/


            Intent intent = new Intent(CreateBookActivity.this, ImagePickerActivity.class);
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
            Glide.with(CreateBookActivity.this).load(selectedImagePath1).into(stream_user_image);
            Log.e("userImage1", "BB");

            confirm_button.setVisibility(View.GONE);
            confirm_button2.setVisibility(View.VISIBLE);

        }else if ( requestCode == CAMERA_PIC_REQUEST_R && resultCode == RESULT_OK )
        {
            key = "1";
            //  Toast.makeText(this, "R Code Working", Toast.LENGTH_SHORT).show();


            try {
                thumbnail6 = MediaStore.Images.Media.getBitmap(
                        CreateBookActivity.this.getContentResolver(), imageUri7);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //setUserProfile.setImageBitmap(thumbnail);


            File file = new File(getRealPathFromURIs(imageUri7));

            Glide.with(CreateBookActivity.this)
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
                    profileImage = streamImage;

                    Uri uu = Uri.fromFile(streamImage);
                    Glide.with(this).load(uu)
                            .into(stream_user_image);


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
    
    private  boolean validation() {



        if(Stream_name_title.getText().toString().equals(""))
        {
            alert_dialog_message("5");
            return  false;
        } else if(streamImage == null)
        {
            alert_dialog_message("6");
            return  false;
        } else if(bookDescription.getText().toString().equals(""))
        {
            alert_dialog_message("8");
            return false;
        }

        return true;
    }

    private void alert_dialog_message(String value) {

        final LayoutInflater inflater = CreateBookActivity.this.getLayoutInflater();
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
        }else if(value.equals("8"))
        {
            error_message.setText(getResources().getString(R.string.book_description));
        }else if(value.equals("5"))
        {
            error_message.setText(getResources().getString(R.string.enter_book_name));
        }else if(value.equals("6"))
        {
            error_message.setText(getResources().getString(R.string.select_book_image));
        }




        final AlertDialog.Builder alert = new AlertDialog.Builder(CreateBookActivity.this);

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
        bookNameData = Stream_name_title.getText().toString();
        bookDescriptionData = bookDescription.getText().toString();
    }

    private void inits() {
        stream_page_Image = findViewById(R.id.stream_page_Image_cb);
        confirm_button2 = findViewById(R.id.confirm_button2_cb);
        confirm_button = findViewById(R.id.confirm_button_cb);
        stream_user_image = findViewById(R.id.stream_user_image_cb);
     //   private_public = findViewById(R.id.private_public_cb);
        Stream_name_title = findViewById(R.id.Stream_name_title_cb);
        need_to_hide = findViewById(R.id.need_to_hide_cb);
      //  private_public_text = findViewById(R.id.private_public_text_cb);
        bookDescription = findViewById(R.id.passwordEditText_cb);
      //  confirm_password_EditText = findViewById(R.id.confirm_password_EditText_cb);

    }
}