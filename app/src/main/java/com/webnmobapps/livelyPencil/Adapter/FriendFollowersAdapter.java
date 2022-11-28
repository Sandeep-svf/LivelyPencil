package com.webnmobapps.livelyPencil.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.webnmobapps.livelyPencil.Model.friend_followers_model;
import com.webnmobapps.livelyPencil.ModelPython.LiveUserListDataPython;
import com.webnmobapps.livelyPencil.R;
import com.webnmobapps.livelyPencil.RetrofitApi.API_Client;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class FriendFollowersAdapter extends RecyclerView.Adapter<FriendFollowersViewHolder> implements Filterable {

    private Context context;
     private static List<LiveUserListDataPython>  liveUserListDataPythonArrayList = new ArrayList<>();
    private static  List<LiveUserListDataPython>  backup;

    public FriendFollowersAdapter(Context context, List<LiveUserListDataPython> liveUserListDataPythonArrayList) {
        this.context = context;
        this.liveUserListDataPythonArrayList = liveUserListDataPythonArrayList;
        backup = new ArrayList<>((liveUserListDataPythonArrayList));
    }

    @NonNull
    @Override
    public FriendFollowersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.friend_follwers_view_holder2, parent, false);
        return new FriendFollowersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendFollowersViewHolder holder, int position) {

        try {
            if(liveUserListDataPythonArrayList.get(position).getFirstName() != null){
                holder.liveUserName.setText(liveUserListDataPythonArrayList.get(position).getFirstName()+" "+liveUserListDataPythonArrayList.get(position).getLastName());
            }else{
               // Toast.makeText(context, "API response null value at position: "+position, Toast.LENGTH_SHORT).show();
            }

            if(liveUserListDataPythonArrayList.get(position).getStreamTitle() != null){
                holder.liveUserStreamName.setText(String.valueOf(liveUserListDataPythonArrayList.get(position).getStreamTitle()));
            }else{
               // Toast.makeText(context, "API response null value at position: "+position, Toast.LENGTH_SHORT).show();
            }
            Glide.with(context).load(API_Client.BASE_IMAGE+liveUserListDataPythonArrayList.get(position).getImage()).
                    placeholder(R.drawable.ic_launcher_background).
                    into(holder.live_user_profile);

            if(liveUserListDataPythonArrayList.get(position).getFollowed()){
                holder.live_add_follow_button.setText("Follow");
            }else{
                holder.live_add_follow_button.setText("Followed");
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return liveUserListDataPythonArrayList.size();
    }

    @Override
    public Filter getFilter() {

        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence keyword) {
                ArrayList<LiveUserListDataPython> filtereddata=new ArrayList<>();


                if(keyword.toString().isEmpty())
                    filtereddata.addAll(backup);
                else
                {
                    for(LiveUserListDataPython obj : backup)
                    {
                        if(obj.getStreamTitle().toString().toLowerCase().contains(keyword.toString().toLowerCase()) || obj.getFirstName().toString().toLowerCase().contains(keyword.toString().toLowerCase()))
                            filtereddata.add(obj);
                    }
                }

                FilterResults results=new FilterResults();
                results.values=filtereddata;
                return results;
            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                liveUserListDataPythonArrayList.clear();
                liveUserListDataPythonArrayList.addAll((ArrayList<LiveUserListDataPython>)results.values);
                notifyDataSetChanged();
            }
        };

        return filter;
    }
}
class FriendFollowersViewHolder extends RecyclerView.ViewHolder {

    CircleImageView live_user_profile;
    AppCompatTextView liveUserName, liveUserStreamName;
    AppCompatButton live_add_follow_button;

    public FriendFollowersViewHolder(@NonNull View itemView) {
        super(itemView);
        live_user_profile = itemView.findViewById(R.id.live_user_profile);
        liveUserName = itemView.findViewById(R.id.liveUserName);
        liveUserStreamName = itemView.findViewById(R.id.liveUserStreamName);
        live_add_follow_button = itemView.findViewById(R.id.live_add_follow_button);
    }
}