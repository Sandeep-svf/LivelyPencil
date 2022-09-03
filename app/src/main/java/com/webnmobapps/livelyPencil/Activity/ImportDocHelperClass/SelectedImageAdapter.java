package com.webnmobapps.livelyPencil.Activity.ImportDocHelperClass;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.webnmobapps.livelyPencil.R;


import java.io.File;
import java.util.ArrayList;

public class SelectedImageAdapter extends RecyclerView.Adapter<SelectedImageAdapter.ViewHolder>{

    Context context;
    ArrayList<String> stringArrayList;

    public SelectedImageAdapter(Context context, ArrayList<String> stringArrayList) {
        this.context = context;
        this.stringArrayList = stringArrayList;
    }

    @Override
    public  ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.selected_image_list, viewGroup, false);
        return new ViewHolder(view);
    }
    @Override
    public  void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        File my_file= new File(stringArrayList.get(position));
        if (stringArrayList.get(position).contains(".jpg") || stringArrayList.get(position).contains(".jpeg") || stringArrayList.get(position).contains(".png")) {
            Glide.with(context)
                    .load(stringArrayList.get(position))
                    .placeholder(R.color.codeGray)
                    .centerCrop()
                    .into(holder.image);

           /* holder.image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(context, FullImageActivity.class).putExtra("image", stringArrayList.get(position)));
                }
            });*/
        }

    }

    @Override
    public int getItemCount() {
        return stringArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        public ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
        }
    }
}