package com.webnmobapps.livelyPencil.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.webnmobapps.livelyPencil.Model.AddItemModel;
import com.webnmobapps.livelyPencil.R;

import java.util.ArrayList;


public class AddEmailLayoutAdapter extends RecyclerView.Adapter<MyViewHolder> {

    Context context;
    ArrayList<AddItemModel> addItemModelArrayList;


    public AddEmailLayoutAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.hihihi, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return addItemModelArrayList.size();
    }

    public void add(ArrayList<AddItemModel> addItemModelArrayList) {
        this.addItemModelArrayList = addItemModelArrayList;
        notifyDataSetChanged();
    }
}

class MyViewHolder extends RecyclerView.ViewHolder {

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
    }
}