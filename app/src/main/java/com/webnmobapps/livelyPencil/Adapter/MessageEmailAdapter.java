package com.webnmobapps.livelyPencil.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.webnmobapps.livelyPencil.R;

public class MessageEmailAdapter extends RecyclerView.Adapter<MEViewHolder> {

    private Context context;

    public MessageEmailAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MEViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.email_design, parent, false);
        return new MEViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MEViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 20;
    }
}
class MEViewHolder extends RecyclerView.ViewHolder {

    public MEViewHolder(@NonNull View itemView) {
        super(itemView);
    }
}
