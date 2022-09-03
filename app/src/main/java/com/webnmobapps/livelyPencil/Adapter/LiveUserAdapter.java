package com.webnmobapps.livelyPencil.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.webnmobapps.livelyPencil.R;
import com.webnmobapps.livelyPencil.StaticModel.LiveUserModel;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class LiveUserAdapter extends RecyclerView.Adapter<LiveUserViewHolder> {

    List<LiveUserModel> liveUserModelList;
    Context context;

    public LiveUserAdapter(List<LiveUserModel> liveUserModelList, Context context) {
        this.liveUserModelList = liveUserModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public LiveUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.live_user_layout,parent,false);
        return new LiveUserViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull LiveUserViewHolder holder, int position) {

        holder.live_user_prifile.setImageResource(liveUserModelList.get(position).getImage());
    }

    @Override
    public int getItemCount() {

        return liveUserModelList.size();
    }
}
class LiveUserViewHolder extends RecyclerView.ViewHolder {

    CircleImageView live_user_prifile, live_user_color;

    public LiveUserViewHolder(@NonNull View itemView) {
        super(itemView);

        live_user_prifile = itemView.findViewById(R.id.live_user_prifile);
        live_user_color = itemView.findViewById(R.id.live_user_color);
    }
}