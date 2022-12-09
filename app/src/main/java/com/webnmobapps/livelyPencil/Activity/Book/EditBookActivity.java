package com.webnmobapps.livelyPencil.Activity.Book;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

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
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.tabs.TabLayout;
import com.webnmobapps.livelyPencil.Activity.JoinUs.NameEmailActivity;
import com.webnmobapps.livelyPencil.Activity.Utility.ImagePickerActivity;
import com.webnmobapps.livelyPencil.Activity.Utility.Permission;
import com.webnmobapps.livelyPencil.Adapter.FFAdapter;
import com.webnmobapps.livelyPencil.ModelPython.CommonStatusMessageModelPython;
import com.webnmobapps.livelyPencil.ModelPython.CommonStatusMessageModelPython;
import com.webnmobapps.livelyPencil.R;
import com.webnmobapps.livelyPencil.RetrofitApi.API_Client;
import com.webnmobapps.livelyPencil.utility.GridSpacingItemDecoration;
import com.webnmobapps.livelyPencil.utility.StaticKey;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditBookActivity extends AppCompatActivity {

    private String bookCoverImage,bookDescription,bookName,bookId;
    AppCompatEditText book_name_edit_text,book_desc_edit_text;
    private AppCompatImageView book_cover_image,back_arrow;
    CircleImageView add_stream_image;
    AlertDialog dialogs;


    private Bitmap thumbnail6;
    AppCompatImageView streamCoverImage;
    public static final int REQUEST_IMAGE = 100;
    private Uri uri;
    private static final int MY_CAMERA_REQUEST_CODE = 100;
    private static final int PICK_IMAGE_G = 12547;
    public static final int RESULT_OK = -1;
    private File profileImage, streamImage;
    private static final int CAMERA_PIC_REQUEST_R = 25418;
    private Uri selectedImageUri;
    private Uri imageUri7;
    AppCompatButton update_button;
    private String accessToken, finalAccessToken;
    private SwitchMaterial book_status_flag;
    private String streamPagePrivacy;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_book);

        back_arrow = findViewById(R.id.back_arrow);
        book_status_flag = findViewById(R.id.book_status_flag);
        update_button = findViewById(R.id.update_button);
        add_stream_image = findViewById(R.id.add_stream_image);
        book_desc_edit_text = findViewById(R.id.passwordEditText_cb);
        book_name_edit_text = findViewById(R.id.Stream_name_title_cb);
        streamCoverImage = findViewById(R.id.stream_user_image_cb);

        SharedPreferences sharedPreferences= getSharedPreferences("AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
        // user_id=sharedPreferences.getString("UserID","");
        accessToken=sharedPreferences.getString("accessToken","");
        finalAccessToken = StaticKey.prefixTokem+accessToken;


        try {
            bookName = getIntent().getStringExtra("bookName");
            bookDescription = getIntent().getStringExtra("bookDescription");
            bookCoverImage = getIntent().getStringExtra("bookCoverImage");
            bookId = getIntent().getStringExtra("bookId");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            book_name_edit_text.setText(bookName);
            book_desc_edit_text.setText(bookDescription);
            Glide.with(EditBookActivity.this)
                    .load(API_Client.BASE_IMAGE+bookCoverImage)
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(streamCoverImage);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(EditBookActivity.this, "Something went wrong... while loading book details.", Toast.LENGTH_SHORT).show();
        }



        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        book_status_flag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(book_status_flag.isChecked()) book_status_flag.setText("Public");
                if(! book_status_flag.isChecked()) book_status_flag.setText("Private");
            }
        });

        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validation()){

                    if(book_status_flag.isChecked())
                    {
                        streamPagePrivacy = "1";
                    }else
                    {
                        streamPagePrivacy = "0";
                    }

                    update_book_api();
                }
            }
        });


        add_stream_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final LayoutInflater inflater = EditBookActivity.this.getLayoutInflater();
                View alertLayout = inflater.inflate(R.layout.test_dialog_xml, null);

                final ImageView close_dialog = alertLayout.findViewById(R.id.close_dialog);
                final ImageView camera_icon = alertLayout.findViewById(R.id.camera_icon);
                final ImageView browse_icon = alertLayout.findViewById(R.id.browse_icon);

                final AlertDialog.Builder alert = new AlertDialog.Builder(EditBookActivity.this);

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

    private void update_book_api() {

        final ProgressDialog pd = new ProgressDialog(EditBookActivity.this);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();


        MultipartBody.Part filePart;

        if (profileImage == null) {
            filePart = MultipartBody.Part.createFormData("book_image", "", RequestBody.create(MediaType.parse("book_image/*"), ""));
            Log.e("Photo", String.valueOf(profileImage)+"null");

        } else {
            filePart = MultipartBody.Part.createFormData("book_image", profileImage.getName(), RequestBody.create(MediaType.parse("book_image/*"), profileImage));
            Log.e("Photo", String.valueOf(profileImage)+"value");
        }

        RequestBody bookNameRB = RequestBody.create(MediaType.parse("text/plain"), book_name_edit_text.getText().toString());
        RequestBody bookDescRB = RequestBody.create(MediaType.parse("text/plain"),book_desc_edit_text.getText().toString());
        RequestBody streamPagePrivacyRB = RequestBody.create(MediaType.parse("text/plain"),streamPagePrivacy);

        Call<CommonStatusMessageModelPython> call = API_Client.getClient().EDIT_BBOK_COMMON_STATUS_MESSAGE_MODEL_PYTHON_CALL("my-book-detail/"+bookId+"/"
                ,finalAccessToken
        ,bookNameRB
        ,bookDescRB
        ,streamPagePrivacyRB
        ,filePart);

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

                            Toast.makeText(EditBookActivity.this, message, Toast.LENGTH_LONG).show();
                            pd.dismiss();
                            Intent intent = new Intent(EditBookActivity.this, BookListActivity.class);
                            startActivity(intent);

                        } else {
                            Toast.makeText(EditBookActivity.this, message, Toast.LENGTH_LONG).show();
                            pd.dismiss();
                        }


                    } else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(EditBookActivity.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                            switch (response.code()) {
                                case 400:
                                    Toast.makeText(EditBookActivity.this, "The server did not understand the request.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 401:
                                    Toast.makeText(EditBookActivity.this, "Unauthorized The requested page needs a username and a password.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 404:
                                    Toast.makeText(EditBookActivity.this, "The server can not find the requested page.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 500:
                                    Toast.makeText(EditBookActivity.this, "Internal Server Error..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 503:
                                    Toast.makeText(EditBookActivity.this, "Service Unavailable..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 504:
                                    Toast.makeText(EditBookActivity.this, "Gateway Timeout..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 511:
                                    Toast.makeText(EditBookActivity.this, "Network Authentication Required ..", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(EditBookActivity.this, "unknown error", Toast.LENGTH_SHORT).show();
                                    break;
                            }

                        } catch (Exception e) {
                            Toast.makeText(EditBookActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
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
                    Toast.makeText(EditBookActivity.this, "This is an actual network failure :( inform the user and possibly retry)" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                } else {
                    Log.e("conversion issue", t.getMessage());
                    Toast.makeText(EditBookActivity.this, "Please Check your Internet Connection...." + t.getMessage(), Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            }
        });

    }

    private boolean validation() {

        if(book_name_edit_text.getText().toString().equals("")){
            alert_dialog_message("1");
           return false;
        }else if(book_desc_edit_text.getText().toString().equals("")){
            alert_dialog_message("2");
            return false;
        }

        return true;
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void profile_camera_open() {

        PackageManager packageManager = getPackageManager();

        boolean readExternal = Permission.checkPermissionReadExternal(EditBookActivity.this);
        boolean writeExternal = Permission.checkPermissionReadExternal2(EditBookActivity.this);
        boolean camera = Permission.checkPermissionCamera(EditBookActivity.this);

        if (camera && writeExternal && readExternal ) {
            if (packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {



                Intent intent = new Intent(EditBookActivity.this, ImagePickerActivity.class);
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
            Toast.makeText(EditBookActivity.this, "camera permission required", Toast.LENGTH_LONG).show();
            requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
        }

    }

    private void gallery() {

        boolean readExternal = Permission.checkPermissionReadExternal(EditBookActivity.this);
        boolean writeExternal = Permission.checkPermissionReadExternal2(EditBookActivity.this);
        boolean camera = Permission.checkPermissionCamera(EditBookActivity.this);
        if (readExternal && camera ) {
      /*      Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, PICK_IMAGE_G);*/


            Intent intent = new Intent(EditBookActivity.this, ImagePickerActivity.class);
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





        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE_G) {


            Bitmap bitmap = null;

            selectedImageUri = data.getData();
            //Toast.makeText(EditBookActivity.this, String.valueOf(selectedImageUri), Toast.LENGTH_SHORT).show();



            String[] projection = {MediaStore.MediaColumns.DATA};
            Cursor cursor = EditBookActivity.this.managedQuery(selectedImageUri, projection, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            cursor.moveToFirst();
            profileImage = new File(cursor.getString(column_index));
            Log.e("userImage1", String.valueOf(profileImage));
            String selectedImagePath1 = getPath(selectedImageUri);
            Log.v("Deatils_path","***"+selectedImagePath1);
            Glide.with(EditBookActivity.this).load(selectedImagePath1).into(streamCoverImage);
            Log.e("userImage1", "BB");



        }else if ( requestCode == CAMERA_PIC_REQUEST_R && resultCode == RESULT_OK )
        {

            //  Toast.makeText(this, "R Code Working", Toast.LENGTH_SHORT).show();


            try {
                thumbnail6 = MediaStore.Images.Media.getBitmap(
                        EditBookActivity.this.getContentResolver(), imageUri7);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //setUserProfile.setImageBitmap(thumbnail);


            File file = new File(getRealPathFromURIs(imageUri7));

            Glide.with(EditBookActivity.this)
                    .load(file)
                    .placeholder(R.color.text_color)
                    .into(streamCoverImage);

            profileImage  = new File(getRealPathFromURIs(imageUri7));
        } else if(requestCode == REQUEST_IMAGE)
        {


            if (resultCode == Activity.RESULT_OK) {
                uri = data.getParcelableExtra("path");



                // String sel_path = getpath(uri);
                // You can update this bitmap to your server


                // loading profile image from local cache
                //loadProfile(uri.toString());
                streamImage = new File(uri.getPath());
                Log.e("file ", "path " + streamImage.getAbsolutePath());
                profileImage = streamImage;

                Uri uu = Uri.fromFile(streamImage);
                Glide.with(this).load(uu)
                        .into(streamCoverImage);

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


    private void alert_dialog_message(String value) {

        final LayoutInflater inflater = EditBookActivity.this.getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.test_dialog_xml_otp, null);

        final ImageView close_dialog = alertLayout.findViewById(R.id.close_dialog);
        final TextView error_message = alertLayout.findViewById(R.id.error_message);


        if(value.equals("1"))
        {
            error_message.setText(getResources().getString(R.string.please_enter_book_cover));
        }else if(value.equals("2"))
        {
            error_message.setText(getResources().getString(R.string.please_enter_book_desc));
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



        final AlertDialog.Builder alert = new AlertDialog.Builder(EditBookActivity.this);

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
}