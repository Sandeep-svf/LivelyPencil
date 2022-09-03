package com.webnmobapps.livelyPencil.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.webnmobapps.livelyPencil.Activity.RunWizard.PopularListRunWizardModel;
import com.webnmobapps.livelyPencil.R;

import java.util.List;

public class PopularListAdapter extends RecyclerView.Adapter<PLviewHolder> {


    Context context;



    public PopularListAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public PLviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.tv_layout,parent,false);
        return new PLviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PLviewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 15;
    }
}

class PLviewHolder extends RecyclerView.ViewHolder {

    public PLviewHolder(@NonNull View itemView) {
        super(itemView);

    }
}
