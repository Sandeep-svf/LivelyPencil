package com.webnmobapps.livelyPencil.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.webnmobapps.livelyPencil.Model.Record.StreamPageResult;
import com.webnmobapps.livelyPencil.ModelPython.PostListDataPython;
import com.webnmobapps.livelyPencil.R;
import com.webnmobapps.livelyPencil.RetrofitApi.API_Client;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PageAdapter extends RecyclerView.Adapter<PageViewHolder>  {

    Context context;
    List<PostListDataPython> postListDataPythonList;
    //List<PostListDataPython> backup;

    public PageAdapter(Context context, List<PostListDataPython> postListDataPythonList) {
        this.context = context;
        this.postListDataPythonList = postListDataPythonList;
//        backup = new ArrayList<>((streamPageResultList));
    }

    @NonNull
    @Override
    public PageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.page_layout2,parent,false);
        return new PageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PageViewHolder holder, int position) {



      /*  Log.e("image_Link",API_Client.BASE_IMAGE+postListDataPythonList.get(position).getFile());*/
    /*    holder.stream_title4.setText(streamPageResultList.get(position).getStreamtitle());
        holder.page.setText(String.valueOf(streamPageResultList.get(position).getTotalimage()));
        holder.video.setText(String.valueOf(streamPageResultList.get(position).getTotalvideo()));
        holder.sound.setText(String.valueOf(streamPageResultList.get(position).getAudio()));
        holder.like.setText(String.valueOf(streamPageResultList.get(position).getTotallike()));
        holder.followers.setText(String.valueOf(streamPageResultList.get(position).getTotalfollower()));
        holder.comment.setText(String.valueOf(streamPageResultList.get(position).getTotalComment()));
*/
      //  Glide.with(context).load(API_Client.BASE_IMAGE+streamPageResultList.get(position).getImage()).placeholder(R.drawable.ic_launcher_background).into(holder.profile_image);
        Glide.with(context).load(API_Client.BASE_IMAGE+postListDataPythonList.get(position).getFile()).placeholder(R.drawable.ic_launcher_background).into(holder.background_image);
    }

    @Override
    public int getItemCount() {
        return postListDataPythonList.size();
    }

   /* @Override
    public Filter getFilter() {

        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence keyword) {

                ArrayList<StreamPageResult> filtereddata=new ArrayList<>();


                if(keyword.toString().isEmpty())
                    filtereddata.addAll(backup);
                else
                {
                    for(StreamPageResult obj : backup)
                    {
                        if(obj.getStreamtitle().toString().toLowerCase().contains(keyword.toString().toLowerCase()))
                            filtereddata.add(obj);
                    }
                }

                FilterResults results=new FilterResults();
                results.values=filtereddata;
                return results;

            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults results) {

                postListDataPythonList.clear();
                postListDataPythonList.addAll((ArrayList<StreamPageResult>)results.values);
                notifyDataSetChanged();
            }

        };

        return filter;
    }*/
}


class PageViewHolder extends RecyclerView.ViewHolder {
    AppCompatTextView stream_title4;
    CircleImageView profile_image;
    ImageView background_image;
    TextView followers, like, comment, sound, video, page;

    public PageViewHolder(@NonNull View itemView) {
        super(itemView);
  /*      page = itemView.findViewById(R.id.page);
        video = itemView.findViewById(R.id.video);
        sound = itemView.findViewById(R.id.sound);
        comment = itemView.findViewById(R.id.comment);
        like = itemView.findViewById(R.id.like);
        followers = itemView.findViewById(R.id.followers);
        stream_title4 = itemView.findViewById(R.id.stream_title4);
        profile_image = itemView.findViewById(R.id.profile_image);*/
        background_image = itemView.findViewById(R.id.background_image);
    }
}
