package com.webnmobapps.livelyPencil.Activity.Utility;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

import com.webnmobapps.livelyPencil.R;

public class VideoPlayerActivity extends AppCompatActivity {

    private  String videoUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);


        videoUrl = getIntent().getStringExtra("videoUrl");

        Log.e("videoUrl123","ghgjhghk"+videoUrl);

        Uri uri = Uri.parse("videoUrl"); //Declare your url here.

        VideoView mVideoView  = (VideoView)findViewById(R.id.mVideoView);
        MediaController mediaController = new MediaController(VideoPlayerActivity.this);
        mediaController.setAnchorView(mVideoView);
        mVideoView.setMediaController(mediaController);
        mVideoView.setVideoURI(uri);
        mVideoView.requestFocus();

        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener()
        {
            @Override
            public void onPrepared(MediaPlayer mp)
            {
                mVideoView.start();
            }
        });

    }
}