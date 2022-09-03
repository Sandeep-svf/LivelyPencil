package com.webnmobapps.livelyPencil.Activity.Utility;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.webnmobapps.livelyPencil.R;

import java.util.ArrayList;

public class Test_Multiple_Image_Activity extends AppCompatActivity {
    private LinearLayout lnrImages;
    private Button btnAddPhots;
    private Button btnSaveImages;
    private ArrayList<String> imagesPathList;
    private Bitmap yourbitmap;
    private Bitmap resized;
    private final int PICK_IMAGE_MULTIPLE =1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_multiple_image);
        lnrImages = (LinearLayout)findViewById(R.id.lnrImages);
        btnAddPhots = (Button)findViewById(R.id.btnAddPhots);
        btnSaveImages = (Button)findViewById(R.id.btnSaveImages);
        /*btnAddPhots.setOnClickListener((View.OnClickListener) this);
        btnSaveImages.setOnClickListener((View.OnClickListener) this);*/

        btnAddPhots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Test_Multiple_Image_Activity.this,CustomPhotoGalleryActivity.class);
                startActivityForResult(intent,PICK_IMAGE_MULTIPLE);
            }
        });

        btnSaveImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(imagesPathList !=null){
                    if(imagesPathList.size()>1) {
                        Toast.makeText(Test_Multiple_Image_Activity.this, imagesPathList.size() + " no of images are selected", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(Test_Multiple_Image_Activity.this, imagesPathList.size() + " no of image are selected", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(Test_Multiple_Image_Activity.this," no images are selected", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if(requestCode == PICK_IMAGE_MULTIPLE){
                imagesPathList = new ArrayList<String>();
                String[] imagesPath = data.getStringExtra("data").split("\\|");
                try{
                    lnrImages.removeAllViews();
                }catch (Throwable e){
                    e.printStackTrace();
                }
                for (int i=0;i<imagesPath.length;i++){
                    imagesPathList.add(imagesPath[i]);
                    yourbitmap = BitmapFactory.decodeFile(imagesPath[i]);
                    ImageView imageView = new ImageView(this);
                    imageView.setImageBitmap(yourbitmap);
                    imageView.setAdjustViewBounds(true);
                    lnrImages.addView(imageView);
                }
            }
        }

    }

}