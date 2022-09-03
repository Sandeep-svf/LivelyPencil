package com.webnmobapps.livelyPencil.Activity.Share;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.webnmobapps.livelyPencil.Adapter.CommentUserWallSubPostAdapter;
import com.webnmobapps.livelyPencil.Adapter.LiveProfileUserWallAdapter;
import com.webnmobapps.livelyPencil.Model.CommentUserWallSubPostModel;
import com.webnmobapps.livelyPencil.Model.LiveProfileUserWall;
import com.webnmobapps.livelyPencil.R;

import java.util.ArrayList;

public class PostWallActivity extends AppCompatActivity {

    RecyclerView rcvLiveProfileUserWallSubPos, rcvCommentUserWallSubPost;
    LiveProfileUserWallAdapter liveProfileUserWallAdapter;
    CommentUserWallSubPostAdapter commentUserWallSubPostAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_wall);

        rcvLiveProfileUserWallSubPos = findViewById(R.id.rcvLiveProfileUserWallSubPos);
        rcvCommentUserWallSubPost = findViewById(R.id.rcvCommentUserWallSubPost);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        liveProfileUserWallAdapter = new LiveProfileUserWallAdapter(dataqueue1112(), PostWallActivity.this);
        rcvLiveProfileUserWallSubPos.setAdapter(liveProfileUserWallAdapter);
        rcvLiveProfileUserWallSubPos.setLayoutManager(linearLayoutManager);

        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager2.setOrientation(RecyclerView.VERTICAL);
        commentUserWallSubPostAdapter = new CommentUserWallSubPostAdapter(dataqueue1113(), PostWallActivity.this);
        rcvCommentUserWallSubPost.setAdapter(commentUserWallSubPostAdapter);
        rcvCommentUserWallSubPost.setLayoutManager(linearLayoutManager2);

    }

    private ArrayList<CommentUserWallSubPostModel> dataqueue1113() {
        ArrayList<CommentUserWallSubPostModel> holder = new ArrayList<CommentUserWallSubPostModel>();
        CommentUserWallSubPostModel commentUserWallSubPostModel = new CommentUserWallSubPostModel();

        commentUserWallSubPostModel = new CommentUserWallSubPostModel();
        commentUserWallSubPostModel.setProfile(R.drawable.test_profile);
        commentUserWallSubPostModel.setAboutPage("What are this page about ?");
        commentUserWallSubPostModel.setTime("Aselylist");
        commentUserWallSubPostModel.setTimeAgo(":3 hours ago");
        commentUserWallSubPostModel.setComment("24k");
        commentUserWallSubPostModel.setLike("74k");
        holder.add(commentUserWallSubPostModel);


        commentUserWallSubPostModel = new CommentUserWallSubPostModel();
        commentUserWallSubPostModel.setProfile(R.drawable.test_profile);
        commentUserWallSubPostModel.setAboutPage("What are this page about ?");
        commentUserWallSubPostModel.setTime("Aselylist");
        commentUserWallSubPostModel.setTimeAgo(":3 hours ago");
        commentUserWallSubPostModel.setComment("84k");
        commentUserWallSubPostModel.setLike("24k");
        holder.add(commentUserWallSubPostModel);


        commentUserWallSubPostModel = new CommentUserWallSubPostModel();
        commentUserWallSubPostModel.setProfile(R.drawable.test_profile);
        commentUserWallSubPostModel.setAboutPage("What are this page about ?");
        commentUserWallSubPostModel.setTime("Aselylist");
        commentUserWallSubPostModel.setTimeAgo(":8 hours ago");
        commentUserWallSubPostModel.setComment("64k");
        commentUserWallSubPostModel.setLike("44k");
        holder.add(commentUserWallSubPostModel);

        commentUserWallSubPostModel = new CommentUserWallSubPostModel();
        commentUserWallSubPostModel.setProfile(R.drawable.test_profile);
        commentUserWallSubPostModel.setAboutPage("What are this page about ?");
        commentUserWallSubPostModel.setTime("Aselylist");
        commentUserWallSubPostModel.setTimeAgo(":8 hours ago");
        commentUserWallSubPostModel.setComment("64k");
        commentUserWallSubPostModel.setLike("24k");
        holder.add(commentUserWallSubPostModel);


        commentUserWallSubPostModel = new CommentUserWallSubPostModel();
        commentUserWallSubPostModel.setProfile(R.drawable.test_profile);
        commentUserWallSubPostModel.setAboutPage("What are this page about ?");
        commentUserWallSubPostModel.setTime("Aselylist");
        commentUserWallSubPostModel.setTimeAgo(":3 hours ago");
        commentUserWallSubPostModel.setComment("24k");
        commentUserWallSubPostModel.setLike("74k");
        holder.add(commentUserWallSubPostModel);


        commentUserWallSubPostModel = new CommentUserWallSubPostModel();
        commentUserWallSubPostModel.setProfile(R.drawable.test_profile);
        commentUserWallSubPostModel.setAboutPage("What are this page about ?");
        commentUserWallSubPostModel.setTime("Aselylist");
        commentUserWallSubPostModel.setTimeAgo(":3 hours ago");
        commentUserWallSubPostModel.setComment("84k");
        commentUserWallSubPostModel.setLike("24k");
        holder.add(commentUserWallSubPostModel);


        commentUserWallSubPostModel = new CommentUserWallSubPostModel();
        commentUserWallSubPostModel.setProfile(R.drawable.test_profile);
        commentUserWallSubPostModel.setAboutPage("What are this page about ?");
        commentUserWallSubPostModel.setTime("Aselylist");
        commentUserWallSubPostModel.setTimeAgo(":8 hours ago");
        commentUserWallSubPostModel.setComment("64k");
        commentUserWallSubPostModel.setLike("44k");
        holder.add(commentUserWallSubPostModel);

        commentUserWallSubPostModel = new CommentUserWallSubPostModel();
        commentUserWallSubPostModel.setProfile(R.drawable.test_profile);
        commentUserWallSubPostModel.setAboutPage("What are this page about ?");
        commentUserWallSubPostModel.setTime("Aselylist");
        commentUserWallSubPostModel.setTimeAgo(":8 hours ago");
        commentUserWallSubPostModel.setComment("64k");
        commentUserWallSubPostModel.setLike("24k");
        holder.add(commentUserWallSubPostModel);


        return holder;
    }

    private ArrayList<LiveProfileUserWall> dataqueue1112() {
        ArrayList<LiveProfileUserWall> holder = new ArrayList<LiveProfileUserWall>();

        LiveProfileUserWall liveProfileUserWall = new LiveProfileUserWall();
        liveProfileUserWall.setImage(R.drawable.demo);
        holder.add(liveProfileUserWall);

        liveProfileUserWall = new LiveProfileUserWall();
        liveProfileUserWall.setImage(R.drawable.demo);
        holder.add(liveProfileUserWall);

        liveProfileUserWall = new LiveProfileUserWall();
        liveProfileUserWall.setImage(R.drawable.demo);
        holder.add(liveProfileUserWall);

        liveProfileUserWall = new LiveProfileUserWall();
        liveProfileUserWall.setImage(R.drawable.demo);
        holder.add(liveProfileUserWall);

        liveProfileUserWall = new LiveProfileUserWall();
        liveProfileUserWall.setImage(R.drawable.demo);
        holder.add(liveProfileUserWall);

        liveProfileUserWall = new LiveProfileUserWall();
        liveProfileUserWall.setImage(R.drawable.demo);
        holder.add(liveProfileUserWall);

        liveProfileUserWall = new LiveProfileUserWall();
        liveProfileUserWall.setImage(R.drawable.demo);
        holder.add(liveProfileUserWall);

        liveProfileUserWall = new LiveProfileUserWall();
        liveProfileUserWall.setImage(R.drawable.demo);
        holder.add(liveProfileUserWall);

        liveProfileUserWall = new LiveProfileUserWall();
        liveProfileUserWall.setImage(R.drawable.demo);
        holder.add(liveProfileUserWall);

        liveProfileUserWall = new LiveProfileUserWall();
        liveProfileUserWall.setImage(R.drawable.demo);
        holder.add(liveProfileUserWall);

        liveProfileUserWall = new LiveProfileUserWall();
        liveProfileUserWall.setImage(R.drawable.demo);
        holder.add(liveProfileUserWall);

        liveProfileUserWall = new LiveProfileUserWall();
        liveProfileUserWall.setImage(R.drawable.demo);
        holder.add(liveProfileUserWall);

        liveProfileUserWall = new LiveProfileUserWall();
        liveProfileUserWall.setImage(R.drawable.demo);
        holder.add(liveProfileUserWall);

        liveProfileUserWall = new LiveProfileUserWall();
        liveProfileUserWall.setImage(R.drawable.demo);
        holder.add(liveProfileUserWall);

        liveProfileUserWall = new LiveProfileUserWall();
        liveProfileUserWall.setImage(R.drawable.demo);
        holder.add(liveProfileUserWall);

        liveProfileUserWall = new LiveProfileUserWall();
        liveProfileUserWall.setImage(R.drawable.demo);
        holder.add(liveProfileUserWall);

        liveProfileUserWall = new LiveProfileUserWall();
        liveProfileUserWall.setImage(R.drawable.demo);
        holder.add(liveProfileUserWall);

        liveProfileUserWall = new LiveProfileUserWall();
        liveProfileUserWall.setImage(R.drawable.demo);
        holder.add(liveProfileUserWall);

        liveProfileUserWall = new LiveProfileUserWall();
        liveProfileUserWall.setImage(R.drawable.demo);
        holder.add(liveProfileUserWall);


        return  holder;
    }

}