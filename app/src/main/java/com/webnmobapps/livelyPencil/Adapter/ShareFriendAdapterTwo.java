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

import com.webnmobapps.livelyPencil.Activity.Share.ToFriendThreeActivity;
import com.webnmobapps.livelyPencil.Model.ShareFriendTwoModel;
import com.webnmobapps.livelyPencil.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ShareFriendAdapterTwo extends RecyclerView.Adapter<ShareFriendTwoViewHolder> {
    ArrayList<ShareFriendTwoModel> data;
     Context context;

    public ShareFriendAdapterTwo(ArrayList<ShareFriendTwoModel> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public ShareFriendTwoViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.share_friend_view_holder_two,parent,false);
        return new ShareFriendTwoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ShareFriendTwoViewHolder holder, int position) {
        holder.userNameShareFriendTwo.setText(data.get(position).getUserNameShareFriendTwo());
        holder.userAboutShareFriendTwo.setText(data.get(position).getUserAboutShareFriendTwo());
        holder.userProfileShareFriendTwo.setImageResource(data.get(position).getUserProfileShareFriendTwo());
        holder.dateShareFriendTwo.setText(data.get(position).getDateShareFriendTwo());
        holder.timeShareFriendTwo.setText(data.get(position).getTimeShareFriendTwo());

        holder.dateTimeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ToFriendThreeActivity.class);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}

class ShareFriendTwoViewHolder extends RecyclerView.ViewHolder {
CircleImageView userProfileShareFriendTwo;
TextView userNameShareFriendTwo, userAboutShareFriendTwo, timeShareFriendTwo, dateShareFriendTwo;
RelativeLayout dateTimeLayout;
    public ShareFriendTwoViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);
        userProfileShareFriendTwo = itemView.findViewById(R.id.userProfileShareFriendTwo);
        userNameShareFriendTwo = itemView.findViewById(R.id.userNameShareFriendTwo);
        userAboutShareFriendTwo = itemView.findViewById(R.id.userAboutShareFriendTwo);
        timeShareFriendTwo = itemView.findViewById(R.id.timeShareFriendTwo);
        dateShareFriendTwo = itemView.findViewById(R.id.dateShareFriendTwo);
        dateTimeLayout = itemView.findViewById(R.id.dateTimeLayout);

    }
}
