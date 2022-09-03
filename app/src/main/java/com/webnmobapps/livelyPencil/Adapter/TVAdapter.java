package com.webnmobapps.livelyPencil.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.api.Api;
import com.webnmobapps.livelyPencil.Activity.Utility.VideoPlayerActivity;
import com.webnmobapps.livelyPencil.Model.Record.StreamPageResult;
import com.webnmobapps.livelyPencil.Model.Record.TvListResult;
import com.webnmobapps.livelyPencil.R;
import com.webnmobapps.livelyPencil.RetrofitApi.API_Client;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class TVAdapter extends RecyclerView.Adapter<TVViewHolder> implements Filterable {

    Context context;
    List<TvListResult> tvListResultList;
    List<TvListResult> backup;

    public TVAdapter(Context context, List<TvListResult> tvListResultList) {
        this.context = context;
        this.tvListResultList = tvListResultList;
        backup = new  ArrayList<>(tvListResultList);
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<TvListResult> getTvListResultList() {
        return tvListResultList;
    }

    public void setTvListResultList(List<TvListResult> tvListResultList) {
        this.tvListResultList = tvListResultList;
    }

    @NonNull
    @Override
    public TVViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.tv_layout,parent,false);
        return new TVViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TVViewHolder holder, @SuppressLint("RecyclerView") int position) {

        Glide.with(context).load(API_Client.BASE_LOGO_IMAGE+tvListResultList.get(position).getTvlogo()).placeholder(R.drawable.ic_launcher_background).into(holder.tv_profile_image);
        Glide.with(context).load(API_Client.BASE_VIDEO_URL+tvListResultList.get(position).getVideo()).placeholder(R.drawable.ic_launcher_background).into(holder.tv_background_image);


      /*  holder.playVidepIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, VideoPlayerActivity.class);
                intent.putExtra("videoUrl", API_Client.BASE_VIDEO_URL+tvListResultList.get(position).getVideo());
                Log.e("sjdkfklsdjfl",API_Client.BASE_VIDEO_URL+tvListResultList.get(position).getVideo());
                context.startActivity(intent);
            }
        });*/
        
        
       /* Uri uri = Uri.parse(API_Client.BASE_VIDEO_URL+tvListResultList.get(position).getVideo());
        holder.videoView.setVideoURI(uri);
        holder.videoView.start();*/

    }

    @Override
    public int getItemCount() {
        return tvListResultList.size();
    }

    @Override
    public Filter getFilter() {

        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence keyword) {
                ArrayList<TvListResult> filtereddata=new ArrayList<>();


                if(keyword.toString().isEmpty())
                    filtereddata.addAll(backup);
                else
                {
                    for(TvListResult obj : backup)
                    {
                        if(obj.getStreamName().toString().toLowerCase().contains(keyword.toString().toLowerCase()))
                            filtereddata.add(obj);
                    }
                }

                FilterResults results=new FilterResults();
                results.values=filtereddata;
                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults results) {

                tvListResultList.clear();
                tvListResultList.addAll((ArrayList<TvListResult>)results.values);
                notifyDataSetChanged();
            }
        };

        return filter;
    }
}

class TVViewHolder extends RecyclerView.ViewHolder {
    ImageView tv_background_image, playVidepIcon;
    CircleImageView tv_profile_image;

    public TVViewHolder(@NonNull View itemView) {
        super(itemView);
        tv_background_image = itemView.findViewById(R.id.tv_background_image);
        tv_profile_image = itemView.findViewById(R.id.tv_profile_image);
        playVidepIcon = itemView.findViewById(R.id.playVidepIcon);

    }
}
