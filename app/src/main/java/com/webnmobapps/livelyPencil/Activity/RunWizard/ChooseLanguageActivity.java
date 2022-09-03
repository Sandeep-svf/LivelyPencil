package com.webnmobapps.livelyPencil.Activity.RunWizard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.webnmobapps.livelyPencil.Activity.RunWizardActivity.RunWizardSettingsActivity;
import com.webnmobapps.livelyPencil.Activity.Setting.PersonalInformationActivity;
import com.webnmobapps.livelyPencil.R;

import java.io.File;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import me.relex.circleindicator.CircleIndicator;

public class ChooseLanguageActivity extends AppCompatActivity {

    AppCompatButton choose_language_start_button;
    AppCompatImageView bg_image;
    CircleImageView userProfile;
    CircleIndicator Indicator;
    ConstraintLayout PI_birth_layout;
    private String usreStreamPageCoverImage;
    private File userStreamCoverImageFile, usrProfieImageFile;
    private String cameraGalleryimageURI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_language);
        inits();
        
       /*
        */


        SharedPreferences getUserIdData = getSharedPreferences("AUTHENTICATION_FILE_NAME", MODE_PRIVATE);
        usreStreamPageCoverImage = getUserIdData.getString("usreStreamPageCoverImage", "");
        cameraGalleryimageURI = getUserIdData.getString("cameraGalleryimageURI", "");


        // set stream image
        getStreamCoverImage();
        getProfileImage();


        choose_language_start_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("check","Running..........Choose language settigs activity");
                Intent intent = new Intent(ChooseLanguageActivity.this, PersonalInformationWizardActivity.class);
                startActivity(intent);
            }
        });
    }
    private void getProfileImage() {

        try {
            // You can update this bitmap to your server
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(cameraGalleryimageURI));

            // loading profile image from local cache
            //loadProfile(uri.toString());
            usrProfieImageFile = new File( Uri.parse(cameraGalleryimageURI).getPath());
            Log.e("file ", "path " + usrProfieImageFile.getAbsolutePath());

            Uri uu = Uri.fromFile(usrProfieImageFile);
            Log.e("image_poath","uu: "+ String.valueOf(uu));
           /* Glide.with(this).load(uu)
                    .into(uesrProfileImage);*/

            Glide.with(ChooseLanguageActivity.this).load(usrProfieImageFile).into(userProfile);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    private void getStreamCoverImage ()
    {
        try {
            // You can update this bitmap to your server
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(usreStreamPageCoverImage));

            // loading profile image from local cache
            //loadProfile(uri.toString());
            userStreamCoverImageFile = new File(Uri.parse(usreStreamPageCoverImage).getPath());
            Log.e("file ", "path " + userStreamCoverImageFile.getAbsolutePath());

            Uri uu = Uri.fromFile(userStreamCoverImageFile);
         /*   Glide.with(this).load(uu)
                    .into(stream_user_image);*/

            Glide.with(ChooseLanguageActivity.this).load(userStreamCoverImageFile).into(bg_image);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void inits() {
        choose_language_start_button = findViewById(R.id.choose_language_start_button);
        userProfile = findViewById(R.id.userProfile);
        bg_image = findViewById(R.id.bg_image);
        PI_birth_layout = findViewById(R.id.PI_birth_layout);

    }
}