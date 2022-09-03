package com.webnmobapps.livelyPencil.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.webnmobapps.livelyPencil.Model.LiveProfileUserWall;
import com.webnmobapps.livelyPencil.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class LiveProfileUserWallAdapter extends RecyclerView.Adapter<LiveProfileViewHolderUserWall> {
    ArrayList<LiveProfileUserWall> data;
    Context context;

    public LiveProfileUserWallAdapter(ArrayList<LiveProfileUserWall> data, Context context) {
        this.data = data;
        this.context = context;
    }



    @NonNull
    @Override
    public LiveProfileViewHolderUserWall onCreateViewHolder( @NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.live_proifle_user_wall_view_holder, parent, false);
        return new LiveProfileViewHolderUserWall(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull LiveProfileViewHolderUserWall holder, int position) {
        holder.liveProfileUserWall.setImageResource(data.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}

class LiveProfileViewHolderUserWall extends RecyclerView.ViewHolder{
    CircleImageView liveProfileUserWall;


    public LiveProfileViewHolderUserWall(@NonNull @NotNull View itemView) {
        super(itemView);

        liveProfileUserWall = itemView.findViewById(R.id.liveProfileUserWall);
    }
}