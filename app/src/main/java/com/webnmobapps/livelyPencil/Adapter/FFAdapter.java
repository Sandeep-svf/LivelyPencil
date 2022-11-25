package com.webnmobapps.livelyPencil.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.webnmobapps.livelyPencil.Model.Record.FollowersModelData;
import com.webnmobapps.livelyPencil.ModelPython.FFModelData;
import com.webnmobapps.livelyPencil.R;
import com.webnmobapps.livelyPencil.RetrofitApi.API_Client;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class FFAdapter extends RecyclerView.Adapter<FFViewHolder> {

    private Context context;
    private List<FFModelData> followersModelDataList = new ArrayList<>();

    public FFAdapter(Context context, List<FFModelData> followersModelDataList) {
        this.context = context;
        this.followersModelDataList = followersModelDataList;
    }

    @NonNull
    @Override
    public FFViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.followers_list_view_holder2, parent, false);
        return  new FFViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FFViewHolder holder, int position) {
        try {
            Glide.with(context).load(API_Client.BASE_IMAGE+followersModelDataList.get(position).getImage())
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(holder.following_user_profile);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        holder.following_user_name.setText(followersModelDataList.get(position).getFirstName()+followersModelDataList.get(position).getLastName());
        holder.followers_stream_name.setText(followersModelDataList.get(position).getStreamTitle());
    }

    @Override
    public int getItemCount() {
        return followersModelDataList.size();
    }
}
class FFViewHolder extends RecyclerView.ViewHolder {
    CircleImageView following_user_profile;
    AppCompatTextView following_user_name,followers_stream_name;
    CardView followers_user_details_layout;
    public FFViewHolder(@NonNull View itemView) {
        super(itemView);
        following_user_profile = itemView.findViewById(R.id.following_user_profile);
        following_user_name = itemView.findViewById(R.id.following_user_name);
        followers_stream_name = itemView.findViewById(R.id.followers_stream_name);
        followers_user_details_layout = itemView.findViewById(R.id.followers_user_details_layout);
    }
}