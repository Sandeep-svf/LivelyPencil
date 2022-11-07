package com.webnmobapps.livelyPencil.Adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FollowersAdapter extends RecyclerView.Adapter<FollowersViewHolder> {
    @NonNull
    @Override
    public FollowersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull FollowersViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
class FollowersViewHolder extends RecyclerView.ViewHolder{

    public FollowersViewHolder(@NonNull View itemView) {
        super(itemView);
    }
}