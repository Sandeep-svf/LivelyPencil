package com.webnmobapps.livelyPencil.Adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FollowerListAdapter extends RecyclerView.Adapter<FollowerListViewHolder> {
    @NonNull
    @Override
    public FollowerListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull FollowerListViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
class FollowerListViewHolder extends RecyclerView.ViewHolder{

    public FollowerListViewHolder(@NonNull View itemView) {
        super(itemView);
    }
}