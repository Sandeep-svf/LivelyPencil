package com.webnmobapps.livelyPencil.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.webnmobapps.livelyPencil.Model.Record.IntrestListModelRecord;
import com.webnmobapps.livelyPencil.ModelPython.InterestingListPython;
import com.webnmobapps.livelyPencil.R;


import java.util.List;

public class SelectIntrestAdapter extends RecyclerView.Adapter<SelectIntrestViewHolder>{

    List<InterestingListPython> intrestListModelRecords ;
    Context context;
    private String id;


    public SelectIntrestAdapter(List<InterestingListPython> intrestListModelRecords, Context context) {
        this.intrestListModelRecords = intrestListModelRecords;
        this.context = context;
    }





 /*   public interface  Get_Position_Intrest_Data
    {
        void page_details(int position);

    }
    private Get_Position_Intrest_Data get_position_intrest_data;

    public void setGet_position_itemDrawings(SelectIntrestAdapter.Get_Position_Intrest_Data get_position_intrest_data) {
        this.get_position_intrest_data = get_position_intrest_data;
    }*/

    public interface  Get_Position_Eye_Function
    {
        void page_details(int position, String id);
        void page_details2(int position, String id);

    }
    private  Get_Position_Eye_Function get_position_eye_function;

    public void setGet_position_itemDrawings(SelectIntrestAdapter.Get_Position_Eye_Function get_position_eye_function) {
        this.get_position_eye_function = get_position_eye_function;
    }

    @NonNull
    @Override
    public SelectIntrestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.select_intrest, parent, false);
        return  new SelectIntrestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectIntrestViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.select_intrest.setText(intrestListModelRecords.get(position).getCategory());





       /* holder.select_intrest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                get_position_intrest_data.page_details(position);
            }
        });*/

//        if(id == 1)
//        {
//            holder.select_intrest.setChecked(true);
//        }else
//        {
//            holder.select_intrest.setChecked(false);

        holder.select_intrest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                id = String.valueOf(intrestListModelRecords.get(position).getId());
                Boolean checkBoolan = intrestListModelRecords.get(position).isCheckBoolean();

                if(checkBoolan == false)
                {

                    intrestListModelRecords.get(position).setCheckBoolean(true);
                    get_position_eye_function.page_details(position,id);

                }else
                {
                    intrestListModelRecords.get(position).setCheckBoolean(false);
                    get_position_eye_function.page_details2(position,id);
                }


            }
        });
//        }


    }

    @Override
    public int getItemCount() {
        return intrestListModelRecords.size();
    }


}

class SelectIntrestViewHolder extends RecyclerView.ViewHolder {

    SwitchMaterial select_intrest;

    public SelectIntrestViewHolder(@NonNull View itemView) {
        super(itemView);
        select_intrest = itemView.findViewById(R.id.select_intrest);
    }
}


