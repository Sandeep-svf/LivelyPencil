package com.webnmobapps.livelyPencil.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.webnmobapps.livelyPencil.Activity.NewChangePhase.SettingsFragment;
import com.webnmobapps.livelyPencil.Fragment.BottonMenu.FollowersProfileFragment;
import com.webnmobapps.livelyPencil.Model.FollowersModel;
import com.webnmobapps.livelyPencil.Model.Record.FollowersModelData;
import com.webnmobapps.livelyPencil.R;
import com.webnmobapps.livelyPencil.RetrofitApi.API_Client;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class FollowersAdapter extends RecyclerView.Adapter<FollowersViewHolder> {

    private Context context;
    private List<FollowersModelData> followersModelDataList = new ArrayList<>();

    public FollowersAdapter(Context context, List<FollowersModelData> followersModelDataList) {
        this.context = context;
        this.followersModelDataList = followersModelDataList;
    }

    @NonNull
    @Override
    public FollowersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.followers_list_view_holder2, parent, false);
        return  new FollowersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FollowersViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.followers_user_details_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String followerUserId = String.valueOf(followersModelDataList.get(position).getId());

                Bundle bundle = new Bundle();
                bundle.putString("followerUserId",followerUserId);
                FollowersProfileFragment nextFrag= new FollowersProfileFragment();
                nextFrag.setArguments(bundle);
                ((FragmentActivity)context).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_contaner, nextFrag, "findThisFragment")
                        .addToBackStack(null)
                        .commit();


            }
        });

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
class FollowersViewHolder extends RecyclerView.ViewHolder{
    CircleImageView following_user_profile;
    AppCompatTextView following_user_name,followers_stream_name;
    CardView followers_user_details_layout;

    public FollowersViewHolder(@NonNull View itemView) {
        super(itemView);

        following_user_profile = itemView.findViewById(R.id.following_user_profile);
        following_user_name = itemView.findViewById(R.id.following_user_name);
        followers_stream_name = itemView.findViewById(R.id.followers_stream_name);
        followers_user_details_layout = itemView.findViewById(R.id.followers_user_details_layout);


    }
}