package com.webnmobapps.livelyPencil.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.webnmobapps.livelyPencil.R;

public class StramChannelAdapter extends RecyclerView.Adapter<StreamViewHolderPostType> {
    @NonNull
    @Override
    public StreamViewHolderPostType onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.stream_channel_view_holder, parent, false);
        return new StreamViewHolderPostType(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StreamViewHolderPostType holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
class StreamViewHolderPostType extends RecyclerView.ViewHolder {

    public StreamViewHolderPostType(@NonNull View itemView) {
        super(itemView);
    }
}