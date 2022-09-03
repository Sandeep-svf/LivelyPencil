package com.webnmobapps.livelyPencil.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.webnmobapps.livelyPencil.Model.friend_followers_model;
import com.webnmobapps.livelyPencil.R;

import java.util.ArrayList;
import java.util.List;

public class FriendFollowersAdapter extends RecyclerView.Adapter<FriendFollowersViewHolder> {

    private Context context;
     List<friend_followers_model>  friend_followers_modelList = new ArrayList<>();

    public FriendFollowersAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public FriendFollowersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.friend_follwers_view_holder2, parent, false);
        return new FriendFollowersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendFollowersViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 20;
    }
}
class FriendFollowersViewHolder extends RecyclerView.ViewHolder {

    public FriendFollowersViewHolder(@NonNull View itemView) {
        super(itemView);
    }
}