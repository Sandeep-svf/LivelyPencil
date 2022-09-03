package com.webnmobapps.livelyPencil.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.webnmobapps.livelyPencil.Activity.Utility.AudioStatus;
import com.webnmobapps.livelyPencil.Fragment.TopMenu.RadioFragment;
import com.webnmobapps.livelyPencil.Model.PositionModel;
import com.webnmobapps.livelyPencil.Model.Record.RadioListResult;
import com.webnmobapps.livelyPencil.Model.SecondPositionModel;
import com.webnmobapps.livelyPencil.R;
import com.webnmobapps.livelyPencil.RetrofitApi.API_Client;

import java.util.ArrayList;
import java.util.List;

public class AboutPostAdapter extends RecyclerView.Adapter<AboutPostViewHolder> {


    Context context;
    List<RadioListResult> radioListResultList;
    List<RadioListResult> backup;
    List<PositionModel> positionModelList = new ArrayList<>();
    List<SecondPositionModel> secondPositionModelList = new ArrayList<>();
    /*RadioFragment radioFragment;*/
    private List<String> contactList = new ArrayList<>();


    @NonNull
    @Override
    public AboutPostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.about_channel_view_holder, parent, false);
        return new AboutPostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AboutPostViewHolder holder, int position) {


        final int pos = position;
        positionModelList.add(new PositionModel(false, pos));
        secondPositionModelList.add(new SecondPositionModel(false, pos));

     //  Glide.with(context).load(API_Client.BASE_LOGO_IMAGE + radioListResultList.get(position).getRadiologo()).placeholder(R.drawable.ic_launcher_background).into(holder.radio_profile);

//        holder.radio_listen_num.setText(radioListResultList.get(position).getListener() + " Listened");
//        holder.radio_comment_number.setText(radioListResultList.get(position).getComments() + "");
//        holder.radio_page_number.setText(radioListResultList.get(position).getPage() + "");
//        holder.radio_profile_name.setText(radioListResultList.get(position).getProfilename());
//        holder.radio_stream_name.setText(radioListResultList.get(position).getStreamname());


        Log.e("audio_path", API_Client.BASE_VIDEO_URL + radioListResultList.get(position).getAudio());


    /*    if (radioFragment.audioStatusList.get(position).getAudioState() != AudioStatus.AUDIO_STATE.IDLE.ordinal()) {
            holder.seekBarAudio.setMax(radioFragment.audioStatusList.get(position).getTotalDuration());
            holder.seekBarAudio.setProgress(radioFragment.audioStatusList.get(position).getCurrentValue());
            holder.seekBarAudio.setEnabled(true);

            holder.currentTxt.setText(calculateDuration(radioFragment.audioStatusList.get(position).getTotalDuration()));
            //Log.e("dsfsdfs", String.valueOf(radioFragment.audioStatusList.get(position).getTotalDuration()));
        } else {
            holder.seekBarAudio.setProgress(0);
            holder.seekBarAudio.setEnabled(false);
        }

        if (radioFragment.audioStatusList.get(position).getAudioState() == AudioStatus.AUDIO_STATE.IDLE.ordinal()
                || radioFragment.audioStatusList.get(position).getAudioState() == AudioStatus.AUDIO_STATE.PAUSED.ordinal()) {
            holder.btnPlay.setImageResource(R.drawable.ic_play_circle_filled_black_24dp);
        } else {
            holder.btnPlay.setImageResource(R.drawable.ic_pause_circle_filled_black_24dp);
        }*/


    }

    @Override
    public int getItemCount() {
        return 20;
    }
}
class AboutPostViewHolder extends RecyclerView.ViewHolder {

    public AboutPostViewHolder(@NonNull View itemView) {
        super(itemView);
    }
}