package com.webnmobapps.livelyPencil.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.webnmobapps.livelyPencil.Activity.UserWall.StreamPageActivity;
import com.webnmobapps.livelyPencil.Model.PositionModel;
import com.webnmobapps.livelyPencil.Model.Record.UserWallListResult;
import com.webnmobapps.livelyPencil.R;
import com.webnmobapps.livelyPencil.Tempcode.PostwallTypeModel;

import java.util.List;

public class UserPostWallAdapter extends RecyclerView.Adapter<UserPostWallViewHolder> {


    Context context;
    List<UserWallListResult> userWallListResultList;
    private int postType;
    List<PostwallTypeModel> postwallTypeModelList;

    public UserPostWallAdapter(Context context, List<PostwallTypeModel> postwallTypeModelList) {
        this.context = context;
        this.postwallTypeModelList = postwallTypeModelList;
    }

    @NonNull
    @Override
    public UserPostWallViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.user_post_wall_layout2,parent,false);
        return new UserPostWallViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserPostWallViewHolder holder, int position) {

     /*   holder.viewHomeProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, StreamPageActivity.class);
                context.startActivity(intent);

            }
        });*/

        String postType = postwallTypeModelList.get(position).getPostType();
        switch (postType)
        {
            case  "1":
                holder.sound_post_type.setVisibility(View.GONE);
                holder.image_post_type.setVisibility(View.VISIBLE);
                break;
            case "0":
                holder.sound_post_type.setVisibility(View.VISIBLE);
                holder.image_post_type.setVisibility(View.GONE);
                break;
            default:
                Toast.makeText(context, "Something went wrong...", Toast.LENGTH_SHORT).show();
                break;
            }
        }

     /*   postType = userWallListResultList.get(position).getType();

        if(postType == 0)
        {
            holder.userName.setText(userWallListResultList.get(position).getFirstname()+userWallListResultList.get(position).getLastname());
            String postTime = userWallListResultList.get(position).getPosttime();
            // split time and data
            String[] parts = postTime.split(".");
            String raw1 = parts[0]; // 004
            String raw2 = parts[1]; // 034556

            String[] part2 = raw1.split("T");
            String date = part2[0];
            String time = part2[1];

            holder.updateTime.setText(date+" "+time+" updated");

        //    holder.updateDetails.setText(userWallListResultList.get(position).);

        }else if(postType == 1)
        {

        }else if(postType == 2)
        {

        }*/
    @Override
    public int getItemCount() {
        return postwallTypeModelList.size();
    }
}
class UserPostWallViewHolder extends RecyclerView.ViewHolder {

    TextView updateTime, userName, updateDetails, userText1, userText2, userText3,
            totalPage, totalVideo, totalComment, totalLike, totalFollowers;
    ImageView postImage, viewHomeProfile;
    ConstraintLayout sound_post_type, image_post_type;

    public UserPostWallViewHolder(@NonNull View itemView) {
        super(itemView);

        image_post_type = itemView.findViewById(R.id.image_post_type);
        sound_post_type = itemView.findViewById(R.id.sound_post_type);
       /* updateTime = itemView.findViewById(R.id.updateTime);
        userName = itemView.findViewById(R.id.userName);
        updateDetails = itemView.findViewById(R.id.updateDetails);
        postImage = itemView.findViewById(R.id.postImage);
        userText1 = itemView.findViewById(R.id.userText1);
        userText2 = itemView.findViewById(R.id.userText2);
        userText3 = itemView.findViewById(R.id.userText3);
        totalPage = itemView.findViewById(R.id.totalPage);
        totalVideo = itemView.findViewById(R.id.totalVideo);
        totalComment = itemView.findViewById(R.id.totalComment);
        totalLike = itemView.findViewById(R.id.totalLike);
        totalFollowers = itemView.findViewById(R.id.totalFollowers);
        viewHomeProfile = itemView.findViewById(R.id.viewHomeProfile);*/
    }
}