package com.webnmobapps.livelyPencil.Activity.Share;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.webnmobapps.livelyPencil.Adapter.ShareFriendAdapterTwo;
import com.webnmobapps.livelyPencil.Model.ShareFriendTwoModel;
import com.webnmobapps.livelyPencil.R;

import java.util.ArrayList;

public class ToFriendTwoActivity extends AppCompatActivity {


    RecyclerView rcvShareFriendTwo;
    ShareFriendAdapterTwo shareFriendAdapterTwo;
    RelativeLayout mailBoxLayout, meessageLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_friend_two);


        rcvShareFriendTwo = findViewById(R.id.rcvShareFriendTwo);
        mailBoxLayout = findViewById(R.id.mailBoxLayout);
        meessageLayout = findViewById(R.id.meessageLayout);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

        shareFriendAdapterTwo = new ShareFriendAdapterTwo(dataqueue898989(),ToFriendTwoActivity.this);
        rcvShareFriendTwo.setAdapter(shareFriendAdapterTwo);
        rcvShareFriendTwo.setLayoutManager(linearLayoutManager);



        mailBoxLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                mailBoxLayout.setBackgroundColor(getResources().getColor(R.color.white));
//                meessageLayout.setBackgroundColor(getResources().getColor(R.color.darkwhite));

                mailBoxLayout.setBackground(getResources().getDrawable(R.drawable.custom_upper_design));
                meessageLayout.setBackground(getResources().getDrawable(R.drawable.custom_upper_design_two));
            }
        });


        meessageLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                mailBoxLayout.setBackgroundColor(getResources().getColor(R.color.darkwhite));
//                meessageLayout.setBackgroundColor(getResources().getColor(R.color.white));

                meessageLayout.setBackground(getResources().getDrawable(R.drawable.custom_upper_design));
                mailBoxLayout.setBackground(getResources().getDrawable(R.drawable.custom_upper_design_two));

            }
        });


    }

    private ArrayList<ShareFriendTwoModel> dataqueue898989() {
        ArrayList<ShareFriendTwoModel> holder = new ArrayList<ShareFriendTwoModel>();

        ShareFriendTwoModel shareFriendTwoModel = new ShareFriendTwoModel();
        shareFriendTwoModel.setUserProfileShareFriendTwo(R.drawable.demo);
        shareFriendTwoModel.setUserNameShareFriendTwo("Wilson Merry");
        shareFriendTwoModel.setUserAboutShareFriendTwo("Writer of (Stream page name)");
        shareFriendTwoModel.setTimeShareFriendTwo("190:00 pm");
        shareFriendTwoModel.setDateShareFriendTwo("08/07/2021");
        holder.add(shareFriendTwoModel);

        shareFriendTwoModel  = new ShareFriendTwoModel();
        shareFriendTwoModel.setUserProfileShareFriendTwo(R.drawable.demo);
        shareFriendTwoModel.setUserNameShareFriendTwo("Wilson Merry");
        shareFriendTwoModel.setUserAboutShareFriendTwo("Writer of (Stream page name)");
        shareFriendTwoModel.setTimeShareFriendTwo("190:00 pm");
        shareFriendTwoModel.setDateShareFriendTwo("08/07/2021");
        holder.add(shareFriendTwoModel);

        shareFriendTwoModel  = new ShareFriendTwoModel();
        shareFriendTwoModel.setUserProfileShareFriendTwo(R.drawable.demo);
        shareFriendTwoModel.setUserNameShareFriendTwo("Wilson Merry");
        shareFriendTwoModel.setUserAboutShareFriendTwo("Writer of (Stream page name)");
        shareFriendTwoModel.setTimeShareFriendTwo("190:00 pm");
        shareFriendTwoModel.setDateShareFriendTwo("08/07/2021");
        holder.add(shareFriendTwoModel);

        shareFriendTwoModel  = new ShareFriendTwoModel();
        shareFriendTwoModel.setUserProfileShareFriendTwo(R.drawable.demo);
        shareFriendTwoModel.setUserNameShareFriendTwo("Wilson Merry");
        shareFriendTwoModel.setUserAboutShareFriendTwo("Writer of (Stream page name)");
        shareFriendTwoModel.setTimeShareFriendTwo("190:00 pm");
        shareFriendTwoModel.setDateShareFriendTwo("08/07/2021");
        holder.add(shareFriendTwoModel);

        shareFriendTwoModel  = new ShareFriendTwoModel();
        shareFriendTwoModel.setUserProfileShareFriendTwo(R.drawable.demo);
        shareFriendTwoModel.setUserNameShareFriendTwo("Wilson Merry");
        shareFriendTwoModel.setUserAboutShareFriendTwo("Writer of (Stream page name)");
        shareFriendTwoModel.setTimeShareFriendTwo("190:00 pm");
        shareFriendTwoModel.setDateShareFriendTwo("08/07/2021");
        holder.add(shareFriendTwoModel);

        shareFriendTwoModel  = new ShareFriendTwoModel();
        shareFriendTwoModel.setUserProfileShareFriendTwo(R.drawable.demo);
        shareFriendTwoModel.setUserNameShareFriendTwo("Wilson Merry");
        shareFriendTwoModel.setUserAboutShareFriendTwo("Writer of (Stream page name)");
        shareFriendTwoModel.setTimeShareFriendTwo("190:00 pm");
        shareFriendTwoModel.setDateShareFriendTwo("08/07/2021");
        holder.add(shareFriendTwoModel);

        shareFriendTwoModel  = new ShareFriendTwoModel();
        shareFriendTwoModel.setUserProfileShareFriendTwo(R.drawable.demo);
        shareFriendTwoModel.setUserNameShareFriendTwo("Wilson Merry");
        shareFriendTwoModel.setUserAboutShareFriendTwo("Writer of (Stream page name)");
        shareFriendTwoModel.setTimeShareFriendTwo("190:00 pm");
        shareFriendTwoModel.setDateShareFriendTwo("08/07/2021");
        holder.add(shareFriendTwoModel);

        shareFriendTwoModel  = new ShareFriendTwoModel();
        shareFriendTwoModel.setUserProfileShareFriendTwo(R.drawable.demo);
        shareFriendTwoModel.setUserNameShareFriendTwo("Wilson Merry");
        shareFriendTwoModel.setUserAboutShareFriendTwo("Writer of (Stream page name)");
        shareFriendTwoModel.setTimeShareFriendTwo("190:00 pm");
        shareFriendTwoModel.setDateShareFriendTwo("08/07/2021");
        holder.add(shareFriendTwoModel);

        shareFriendTwoModel  = new ShareFriendTwoModel();
        shareFriendTwoModel.setUserProfileShareFriendTwo(R.drawable.demo);
        shareFriendTwoModel.setUserNameShareFriendTwo("Wilson Merry");
        shareFriendTwoModel.setUserAboutShareFriendTwo("Writer of (Stream page name)");
        shareFriendTwoModel.setTimeShareFriendTwo("190:00 pm");
        shareFriendTwoModel.setDateShareFriendTwo("08/07/2021");
        holder.add(shareFriendTwoModel);

        shareFriendTwoModel  = new ShareFriendTwoModel();
        shareFriendTwoModel.setUserProfileShareFriendTwo(R.drawable.demo);
        shareFriendTwoModel.setUserNameShareFriendTwo("Wilson Merry");
        shareFriendTwoModel.setUserAboutShareFriendTwo("Writer of (Stream page name)");
        shareFriendTwoModel.setTimeShareFriendTwo("190:00 pm");
        shareFriendTwoModel.setDateShareFriendTwo("08/07/2021");
        holder.add(shareFriendTwoModel);

        shareFriendTwoModel  = new ShareFriendTwoModel();
        shareFriendTwoModel.setUserProfileShareFriendTwo(R.drawable.demo);
        shareFriendTwoModel.setUserNameShareFriendTwo("Wilson Merry");
        shareFriendTwoModel.setUserAboutShareFriendTwo("Writer of (Stream page name)");
        shareFriendTwoModel.setTimeShareFriendTwo("190:00 pm");
        shareFriendTwoModel.setDateShareFriendTwo("08/07/2021");
        holder.add(shareFriendTwoModel);


        shareFriendTwoModel  = new ShareFriendTwoModel();
        shareFriendTwoModel.setUserProfileShareFriendTwo(R.drawable.demo);
        shareFriendTwoModel.setUserNameShareFriendTwo("Wilson Merry");
        shareFriendTwoModel.setUserAboutShareFriendTwo("Writer of (Stream page name)");
        shareFriendTwoModel.setTimeShareFriendTwo("190:00 pm");
        shareFriendTwoModel.setDateShareFriendTwo("08/07/2021");
        holder.add(shareFriendTwoModel);

        shareFriendTwoModel  = new ShareFriendTwoModel();
        shareFriendTwoModel.setUserProfileShareFriendTwo(R.drawable.demo);
        shareFriendTwoModel.setUserNameShareFriendTwo("Wilson Merry");
        shareFriendTwoModel.setUserAboutShareFriendTwo("Writer of (Stream page name)");
        shareFriendTwoModel.setTimeShareFriendTwo("190:00 pm");
        shareFriendTwoModel.setDateShareFriendTwo("08/07/2021");
        holder.add(shareFriendTwoModel);

        shareFriendTwoModel  = new ShareFriendTwoModel();
        shareFriendTwoModel.setUserProfileShareFriendTwo(R.drawable.demo);
        shareFriendTwoModel.setUserNameShareFriendTwo("Wilson Merry");
        shareFriendTwoModel.setUserAboutShareFriendTwo("Writer of (Stream page name)");
        shareFriendTwoModel.setTimeShareFriendTwo("190:00 pm");
        shareFriendTwoModel.setDateShareFriendTwo("08/07/2021");
        holder.add(shareFriendTwoModel);

        shareFriendTwoModel  = new ShareFriendTwoModel();
        shareFriendTwoModel.setUserProfileShareFriendTwo(R.drawable.demo);
        shareFriendTwoModel.setUserNameShareFriendTwo("Wilson Merry");
        shareFriendTwoModel.setUserAboutShareFriendTwo("Writer of (Stream page name)");
        shareFriendTwoModel.setTimeShareFriendTwo("190:00 pm");
        shareFriendTwoModel.setDateShareFriendTwo("08/07/2021");
        holder.add(shareFriendTwoModel);

        shareFriendTwoModel  = new ShareFriendTwoModel();
        shareFriendTwoModel.setUserProfileShareFriendTwo(R.drawable.demo);
        shareFriendTwoModel.setUserNameShareFriendTwo("Wilson Merry");
        shareFriendTwoModel.setUserAboutShareFriendTwo("Writer of (Stream page name)");
        shareFriendTwoModel.setTimeShareFriendTwo("190:00 pm");
        shareFriendTwoModel.setDateShareFriendTwo("08/07/2021");
        holder.add(shareFriendTwoModel);

        shareFriendTwoModel  = new ShareFriendTwoModel();
        shareFriendTwoModel.setUserProfileShareFriendTwo(R.drawable.demo);
        shareFriendTwoModel.setUserNameShareFriendTwo("Wilson Merry");
        shareFriendTwoModel.setUserAboutShareFriendTwo("Writer of (Stream page name)");
        shareFriendTwoModel.setTimeShareFriendTwo("190:00 pm");
        shareFriendTwoModel.setDateShareFriendTwo("08/07/2021");
        holder.add(shareFriendTwoModel);

        shareFriendTwoModel  = new ShareFriendTwoModel();
        shareFriendTwoModel.setUserProfileShareFriendTwo(R.drawable.demo);
        shareFriendTwoModel.setUserNameShareFriendTwo("Wilson Merry");
        shareFriendTwoModel.setUserAboutShareFriendTwo("Writer of (Stream page name)");
        shareFriendTwoModel.setTimeShareFriendTwo("190:00 pm");
        shareFriendTwoModel.setDateShareFriendTwo("08/07/2021");
        holder.add(shareFriendTwoModel);

        shareFriendTwoModel  = new ShareFriendTwoModel();
        shareFriendTwoModel.setUserProfileShareFriendTwo(R.drawable.demo);
        shareFriendTwoModel.setUserNameShareFriendTwo("Wilson Merry");
        shareFriendTwoModel.setUserAboutShareFriendTwo("Writer of (Stream page name)");
        shareFriendTwoModel.setTimeShareFriendTwo("190:00 pm");
        shareFriendTwoModel.setDateShareFriendTwo("08/07/2021");
        holder.add(shareFriendTwoModel);

        shareFriendTwoModel  = new ShareFriendTwoModel();
        shareFriendTwoModel.setUserProfileShareFriendTwo(R.drawable.demo);
        shareFriendTwoModel.setUserNameShareFriendTwo("Wilson Merry");
        shareFriendTwoModel.setUserAboutShareFriendTwo("Writer of (Stream page name)");
        shareFriendTwoModel.setTimeShareFriendTwo("190:00 pm");
        shareFriendTwoModel.setDateShareFriendTwo("08/07/2021");
        holder.add(shareFriendTwoModel);

        shareFriendTwoModel  = new ShareFriendTwoModel();
        shareFriendTwoModel.setUserProfileShareFriendTwo(R.drawable.demo);
        shareFriendTwoModel.setUserNameShareFriendTwo("Wilson Merry");
        shareFriendTwoModel.setUserAboutShareFriendTwo("Writer of (Stream page name)");
        shareFriendTwoModel.setTimeShareFriendTwo("190:00 pm");
        shareFriendTwoModel.setDateShareFriendTwo("08/07/2021");
        holder.add(shareFriendTwoModel);


        return  holder;
    }

}