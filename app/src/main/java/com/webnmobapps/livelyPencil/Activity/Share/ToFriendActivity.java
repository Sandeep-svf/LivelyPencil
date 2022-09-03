package com.webnmobapps.livelyPencil.Activity.Share;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.webnmobapps.livelyPencil.Adapter.ShareFriendAdapter;
import com.webnmobapps.livelyPencil.Model.SharefriendModel;
import com.webnmobapps.livelyPencil.R;

import java.util.ArrayList;

public class ToFriendActivity extends AppCompatActivity {

    RecyclerView rcvShareFriend;
    ShareFriendAdapter shareFriendAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_friend);

        rcvShareFriend = findViewById(R.id.rcvShareFriend);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

        shareFriendAdapter = new ShareFriendAdapter(dataqueue8989(),ToFriendActivity.this);
        rcvShareFriend.setAdapter(shareFriendAdapter);
        rcvShareFriend.setLayoutManager(linearLayoutManager);



    }

    private ArrayList<SharefriendModel> dataqueue8989() {
        ArrayList<SharefriendModel> holder = new ArrayList<SharefriendModel>();

        SharefriendModel sharefriendModel = new SharefriendModel();
        sharefriendModel.setUserProfileShareFriend(R.drawable.demo);
        sharefriendModel.setUserNameShareFriend("Wilson Merry");
        sharefriendModel.setUserAboutShareFriend("Writer of (Stream page name)");
        holder.add(sharefriendModel);

        sharefriendModel = new SharefriendModel();
        sharefriendModel.setUserProfileShareFriend(R.drawable.demo);
        sharefriendModel.setUserNameShareFriend("Wilson Merry");
        sharefriendModel.setUserAboutShareFriend("Writer of (Stream page name)");
        holder.add(sharefriendModel);


        sharefriendModel = new SharefriendModel();
        sharefriendModel.setUserProfileShareFriend(R.drawable.demo);
        sharefriendModel.setUserNameShareFriend("Wilson Merry");
        sharefriendModel.setUserAboutShareFriend("Writer of (Stream page name)");
        holder.add(sharefriendModel);


        sharefriendModel = new SharefriendModel();
        sharefriendModel.setUserProfileShareFriend(R.drawable.demo);
        sharefriendModel.setUserNameShareFriend("Wilson Merry");
        sharefriendModel.setUserAboutShareFriend("Writer of (Stream page name)");
        holder.add(sharefriendModel);


        sharefriendModel = new SharefriendModel();
        sharefriendModel.setUserProfileShareFriend(R.drawable.demo);
        sharefriendModel.setUserNameShareFriend("Wilson Merry");
        sharefriendModel.setUserAboutShareFriend("Writer of (Stream page name)");
        holder.add(sharefriendModel);


        sharefriendModel = new SharefriendModel();
        sharefriendModel.setUserProfileShareFriend(R.drawable.demo);
        sharefriendModel.setUserNameShareFriend("Wilson Merry");
        sharefriendModel.setUserAboutShareFriend("Writer of (Stream page name)");
        holder.add(sharefriendModel);


        sharefriendModel = new SharefriendModel();
        sharefriendModel.setUserProfileShareFriend(R.drawable.demo);
        sharefriendModel.setUserNameShareFriend("Wilson Merry");
        sharefriendModel.setUserAboutShareFriend("Writer of (Stream page name)");
        holder.add(sharefriendModel);


        sharefriendModel = new SharefriendModel();
        sharefriendModel.setUserProfileShareFriend(R.drawable.demo);
        sharefriendModel.setUserNameShareFriend("Wilson Merry");
        sharefriendModel.setUserAboutShareFriend("Writer of (Stream page name)");
        holder.add(sharefriendModel);

        sharefriendModel = new SharefriendModel();
        sharefriendModel.setUserProfileShareFriend(R.drawable.demo);
        sharefriendModel.setUserNameShareFriend("Wilson Merry");
        sharefriendModel.setUserAboutShareFriend("Writer of (Stream page name)");
        holder.add(sharefriendModel);

        sharefriendModel = new SharefriendModel();
        sharefriendModel.setUserProfileShareFriend(R.drawable.demo);
        sharefriendModel.setUserNameShareFriend("Wilson Merry");
        sharefriendModel.setUserAboutShareFriend("Writer of (Stream page name)");
        holder.add(sharefriendModel);

        sharefriendModel = new SharefriendModel();
        sharefriendModel.setUserProfileShareFriend(R.drawable.demo);
        sharefriendModel.setUserNameShareFriend("Wilson Merry");
        sharefriendModel.setUserAboutShareFriend("Writer of (Stream page name)");
        holder.add(sharefriendModel);


        sharefriendModel = new SharefriendModel();
        sharefriendModel.setUserProfileShareFriend(R.drawable.demo);
        sharefriendModel.setUserNameShareFriend("Wilson Merry");
        sharefriendModel.setUserAboutShareFriend("Writer of (Stream page name)");
        holder.add(sharefriendModel);


        sharefriendModel = new SharefriendModel();
        sharefriendModel.setUserProfileShareFriend(R.drawable.demo);
        sharefriendModel.setUserNameShareFriend("Wilson Merry");
        sharefriendModel.setUserAboutShareFriend("Writer of (Stream page name)");
        holder.add(sharefriendModel);


        sharefriendModel = new SharefriendModel();
        sharefriendModel.setUserProfileShareFriend(R.drawable.demo);
        sharefriendModel.setUserNameShareFriend("Wilson Merry");
        sharefriendModel.setUserAboutShareFriend("Writer of (Stream page name)");
        holder.add(sharefriendModel);


        sharefriendModel = new SharefriendModel();
        sharefriendModel.setUserProfileShareFriend(R.drawable.demo);
        sharefriendModel.setUserNameShareFriend("Wilson Merry");
        sharefriendModel.setUserAboutShareFriend("Writer of (Stream page name)");
        holder.add(sharefriendModel);


        sharefriendModel = new SharefriendModel();
        sharefriendModel.setUserProfileShareFriend(R.drawable.demo);
        sharefriendModel.setUserNameShareFriend("Wilson Merry");
        sharefriendModel.setUserAboutShareFriend("Writer of (Stream page name)");
        holder.add(sharefriendModel);


        sharefriendModel = new SharefriendModel();
        sharefriendModel.setUserProfileShareFriend(R.drawable.demo);
        sharefriendModel.setUserNameShareFriend("Wilson Merry");
        sharefriendModel.setUserAboutShareFriend("Writer of (Stream page name)");
        holder.add(sharefriendModel);


        sharefriendModel = new SharefriendModel();
        sharefriendModel.setUserProfileShareFriend(R.drawable.demo);
        sharefriendModel.setUserNameShareFriend("Wilson Merry");
        sharefriendModel.setUserAboutShareFriend("Writer of (Stream page name)");
        holder.add(sharefriendModel);

        sharefriendModel = new SharefriendModel();
        sharefriendModel.setUserProfileShareFriend(R.drawable.demo);
        sharefriendModel.setUserNameShareFriend("Wilson Merry");
        sharefriendModel.setUserAboutShareFriend("Writer of (Stream page name)");
        holder.add(sharefriendModel);

        sharefriendModel = new SharefriendModel();
        sharefriendModel.setUserProfileShareFriend(R.drawable.demo);
        sharefriendModel.setUserNameShareFriend("Wilson Merry");
        sharefriendModel.setUserAboutShareFriend("Writer of (Stream page name)");
        holder.add(sharefriendModel);

        sharefriendModel = new SharefriendModel();
        sharefriendModel.setUserProfileShareFriend(R.drawable.demo);
        sharefriendModel.setUserNameShareFriend("Wilson Merry");
        sharefriendModel.setUserAboutShareFriend("Writer of (Stream page name)");
        holder.add(sharefriendModel);


        return  holder;

    }

}