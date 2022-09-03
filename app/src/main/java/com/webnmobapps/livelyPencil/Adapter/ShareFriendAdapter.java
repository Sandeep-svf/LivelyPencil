package com.webnmobapps.livelyPencil.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.webnmobapps.livelyPencil.Activity.Share.ToFriendTwoActivity;
import com.webnmobapps.livelyPencil.Model.SharefriendModel;
import com.webnmobapps.livelyPencil.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class ShareFriendAdapter extends RecyclerView.Adapter<ShareFriendViewHolder> {

    private static Context context;
    ArrayList<SharefriendModel> data;

    public ShareFriendAdapter(ArrayList<SharefriendModel> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public ShareFriendViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.share_friend_view_holder,parent,false);
        return new ShareFriendViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ShareFriendViewHolder holder, int position) {
        holder.userNameShareFriend.setText(data.get(position).getUserNameShareFriend());
        holder.userAboutShareFriend.setText(data.get(position).getUserAboutShareFriend());
        holder.userProfileShareFriend.setImageResource(data.get(position).getUserProfileShareFriend());

        holder.sendShareFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ToFriendTwoActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}

class ShareFriendViewHolder extends RecyclerView.ViewHolder {
    CircleImageView userProfileShareFriend;
    TextView userNameShareFriend, userAboutShareFriend;
    RelativeLayout sendShareFriend;

    public ShareFriendViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);
        userProfileShareFriend = itemView.findViewById(R.id.userProfileShareFriend);
        userNameShareFriend = itemView.findViewById(R.id.userNameShareFriend);
        userAboutShareFriend = itemView.findViewById(R.id.userAboutShareFriend);
        sendShareFriend = itemView.findViewById(R.id.sendShareFriend);

    }
}
