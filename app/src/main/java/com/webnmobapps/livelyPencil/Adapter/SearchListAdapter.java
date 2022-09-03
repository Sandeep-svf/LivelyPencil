package com.webnmobapps.livelyPencil.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.webnmobapps.livelyPencil.Model.Record.StreamPageResult;
import com.webnmobapps.livelyPencil.Model.SearchListModel;
import com.webnmobapps.livelyPencil.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SearchListAdapter extends RecyclerView.Adapter<SearchListViewHolder> implements Filterable {

    List<SearchListModel> backup;
    private Context context;
    List<SearchListModel> searchListModelList = new ArrayList<>();

    public SearchListAdapter(Context context, List<SearchListModel> searchListModelList) {
        this.context = context;
        this.searchListModelList = searchListModelList;
        backup = new ArrayList<>((searchListModelList));
    }

    @NonNull
    @Override
    public SearchListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.seatch_list_design, parent, false);
        return new SearchListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchListViewHolder holder, int position) {

        holder.userSearchName.setText(searchListModelList.get(position).getUserName());
        holder.userSearchStream.setText(searchListModelList.get(position).getStreamName());
        Glide.with(context).load(searchListModelList.get(position).getUserProfile()).
                placeholder(R.drawable.ic_launcher_background).into(holder.userSearchProfile);

    }

    @Override
    public int getItemCount() {
        return searchListModelList.size();
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence keyword) {

                List<SearchListModel> filtereddata=new ArrayList<>();


                if(keyword.toString().isEmpty())
                 filtereddata.addAll(backup);
                else
                {
                    for(SearchListModel obj : backup)
                    {
                        if(obj.getUserName().toString().toLowerCase().contains(keyword.toString().toLowerCase()))
                            filtereddata.add(obj);
                    }
                }

                FilterResults results=new FilterResults();
                results.values=filtereddata;
                return results;

            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults results) {

                searchListModelList.clear();
                searchListModelList.addAll((ArrayList<SearchListModel>)results.values);
                notifyDataSetChanged();
            }

        };

        return filter;
    }
}
class SearchListViewHolder extends RecyclerView.ViewHolder {


    CircleImageView userSearchProfile;
    AppCompatTextView userSearchName,userSearchStream;


    public SearchListViewHolder(@NonNull View itemView) {
        super(itemView);

        userSearchProfile=itemView.findViewById(R.id.userSearchProfile);
        userSearchName=itemView.findViewById(R.id.userSearchName);
        userSearchStream=itemView.findViewById(R.id.userSearchStream);
    }
}