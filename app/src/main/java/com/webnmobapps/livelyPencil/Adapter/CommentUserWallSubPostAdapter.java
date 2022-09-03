package com.webnmobapps.livelyPencil.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.webnmobapps.livelyPencil.Model.CommentUserWallSubPostModel;
import com.webnmobapps.livelyPencil.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentUserWallSubPostAdapter  extends  RecyclerView.Adapter<commentViewHolderUserWallSubPost>   {

    ArrayList<CommentUserWallSubPostModel> data;
    Context context;

    public CommentUserWallSubPostAdapter(ArrayList<CommentUserWallSubPostModel> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public commentViewHolderUserWallSubPost onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.comment_user_post_wall_holder, parent, false);
        return new commentViewHolderUserWallSubPost(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull commentViewHolderUserWallSubPost holder, int position) {
        holder.userProfile.setImageResource(data.get(position).getProfile());
        holder.userAbout.setText(data.get(position).getAboutPage());
        holder.asleyList.setText(data.get(position).getTime());
        holder.timeAgo.setText(data.get(position).getTimeAgo());
        holder.commentNumber.setText(data.get(position).getComment());
        holder.likeNumber.setText(data.get(position).getLike());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}

class commentViewHolderUserWallSubPost extends RecyclerView.ViewHolder {
CircleImageView userProfile;
TextView userAbout, asleyList, timeAgo , likeNumber , commentNumber;

    public commentViewHolderUserWallSubPost(@NonNull @NotNull View itemView) {
        super(itemView);
        userProfile = itemView.findViewById(R.id.userProfile);
        userAbout = itemView.findViewById(R.id.userAbout);
        asleyList = itemView.findViewById(R.id.asleyList);
        timeAgo = itemView.findViewById(R.id.timeAgo);
        likeNumber = itemView.findViewById(R.id.likeNumber);
        commentNumber = itemView.findViewById(R.id.commentNumber);
    }
}
