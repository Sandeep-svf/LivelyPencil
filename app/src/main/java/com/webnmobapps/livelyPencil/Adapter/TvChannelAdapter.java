package com.webnmobapps.livelyPencil.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.webnmobapps.livelyPencil.R;


public class TvChannelAdapter extends RecyclerView.Adapter<TvchannelListViewHolder> {
    @NonNull
    @Override
    public TvchannelListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.tv_channel_view_holder, parent, false);
        return new TvchannelListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TvchannelListViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 20;
    }
}
class TvchannelListViewHolder extends RecyclerView.ViewHolder {

    public TvchannelListViewHolder(@NonNull View itemView) {
        super(itemView);
    }
}