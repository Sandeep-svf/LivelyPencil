package com.webnmobapps.livelyPencil.Activity.Share;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.webnmobapps.livelyPencil.Adapter.ShareFriendAdapterThree;
import com.webnmobapps.livelyPencil.Model.ShareFriendThreeModel;
import com.webnmobapps.livelyPencil.R;

import java.util.ArrayList;

public class ToFriendThreeActivity extends AppCompatActivity {

    RecyclerView rcvShareFriendThree;
    RelativeLayout meessageLayout2, mailBoxLayout2;
    ShareFriendAdapterThree shareFriendAdapterThree;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_friend_three);


        rcvShareFriendThree = findViewById(R.id.rcvShareFriendThree);
        meessageLayout2 = findViewById(R.id.meessageLayout2);
        mailBoxLayout2 = findViewById(R.id.mailBoxLayout2);


        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager2.setOrientation(RecyclerView.VERTICAL);

        shareFriendAdapterThree = new ShareFriendAdapterThree(dataqueue6767(), ToFriendThreeActivity.this);
        rcvShareFriendThree.setAdapter(shareFriendAdapterThree);
        rcvShareFriendThree.setLayoutManager(linearLayoutManager2);


        meessageLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                meessageLayout2.setBackground(getResources().getDrawable(R.drawable.custom_upper_design));
                mailBoxLayout2.setBackground(getResources().getDrawable(R.drawable.custom_upper_design_two));

            }
        });

        mailBoxLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mailBoxLayout2.setBackground(getResources().getDrawable(R.drawable.custom_upper_design));
                meessageLayout2.setBackground(getResources().getDrawable(R.drawable.custom_upper_design_two));

            }
        });


    }

    private ArrayList<ShareFriendThreeModel> dataqueue6767() {

        ArrayList<ShareFriendThreeModel> holder = new ArrayList<ShareFriendThreeModel>();
        ShareFriendThreeModel shareFriendThreeModel = new ShareFriendThreeModel();

        shareFriendThreeModel = new ShareFriendThreeModel();
        shareFriendThreeModel.setShareFriendThreeViewHolder(R.drawable.demo);
        holder.add(shareFriendThreeModel);

        return  holder;
    }

}