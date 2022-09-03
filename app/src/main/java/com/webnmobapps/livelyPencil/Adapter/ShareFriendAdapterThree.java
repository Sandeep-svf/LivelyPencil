package com.webnmobapps.livelyPencil.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.webnmobapps.livelyPencil.Model.ShareFriendThreeModel;
import com.webnmobapps.livelyPencil.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ShareFriendAdapterThree extends RecyclerView.Adapter<ShareFriendThreeViewHolder> {

    ArrayList<ShareFriendThreeModel> data ;
    Context context;

    public ShareFriendAdapterThree(ArrayList<ShareFriendThreeModel> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public ShareFriendThreeViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.profile_view,parent,false);
        return new ShareFriendThreeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ShareFriendThreeViewHolder holder, int position) {

     //   holder.userProfileShareFriendsthree.setImageResource(data.get(position).getShareFriendThreeViewHolder());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}

class  ShareFriendThreeViewHolder extends RecyclerView.ViewHolder {
  CircleImageView userProfileShareFriendsthree;
    public ShareFriendThreeViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);
        //userProfileShareFriendsthree = itemView.findViewById(R.id.userProfileShareFriendsthree);

    }
}
