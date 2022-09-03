package com.webnmobapps.livelyPencil.Activity.UserWall;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.webnmobapps.livelyPencil.Activity.Share.PostWallActivity;
import com.webnmobapps.livelyPencil.Activity.Share.ToEmailActivity;
import com.webnmobapps.livelyPencil.Activity.Share.ToFriendActivity;
import com.webnmobapps.livelyPencil.R;

public class ShareStreamPageByReadresActivity extends AppCompatActivity {

    ConstraintLayout to_email_layout, to_friend_layout, post_wall_layout;
    AppCompatImageView back_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_stream_page_by_readres);

        inits();

        post_wall_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ShareStreamPageByReadresActivity.this, PostWallActivity.class);
                startActivity(intent);
            }
        });

        to_friend_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ShareStreamPageByReadresActivity.this, ToFriendActivity.class);
                startActivity(intent);
            }
        });

        to_email_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ShareStreamPageByReadresActivity.this, ToEmailActivity.class);
                startActivity(intent);
            }
        });


        back_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void inits() {
        to_email_layout = findViewById(R.id.to_email_layout);
        to_friend_layout = findViewById(R.id.to_friend_layout);
        post_wall_layout = findViewById(R.id.post_wall_layout);
        back_layout = findViewById(R.id.back_layout);
    }
}