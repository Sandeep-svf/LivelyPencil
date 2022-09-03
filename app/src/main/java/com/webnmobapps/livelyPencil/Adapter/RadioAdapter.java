package com.webnmobapps.livelyPencil.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.webnmobapps.livelyPencil.Activity.Utility.AudioStatus;
import com.webnmobapps.livelyPencil.Activity.Utility.MediaPlayerUtils;
import com.webnmobapps.livelyPencil.Fragment.TopMenu.RadioFragment;
import com.webnmobapps.livelyPencil.Model.PositionModel;
import com.webnmobapps.livelyPencil.Model.Record.RadioListResult;
import com.webnmobapps.livelyPencil.Model.SecondPositionModel;
import com.webnmobapps.livelyPencil.R;
import com.webnmobapps.livelyPencil.RetrofitApi.API_Client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class RadioAdapter extends RecyclerView.Adapter<RadioAdapter.RadioViewHolder> implements Filterable {


    Context context;
    List<RadioListResult> radioListResultList;
    List<RadioListResult> backup;
    List<PositionModel> positionModelList = new ArrayList<>();
    List<SecondPositionModel> secondPositionModelList = new ArrayList<>();
    RadioFragment radioFragment;
    private List<String> contactList = new ArrayList<>();


    public RadioAdapter(Context context, List<String> contactList, List<RadioListResult> radioListResultList, RadioFragment radioFragment) {
        this.context = context;
        this.contactList = contactList;
        this.radioListResultList = radioListResultList;
        backup = new ArrayList<>(radioListResultList);
        this.radioFragment = radioFragment;
    }

    @NonNull
    @Override
    public RadioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.radio_layout, parent, false);
        return new RadioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RadioViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        final int pos = position;
        positionModelList.add(new PositionModel(false, pos));
        secondPositionModelList.add(new SecondPositionModel(false, pos));

        Glide.with(context).load(API_Client.BASE_LOGO_IMAGE + radioListResultList.get(position).getRadiologo()).placeholder(R.drawable.ic_launcher_background).into(holder.radio_profile);

        holder.radio_listen_num.setText(radioListResultList.get(position).getListener() + " Listened");
        holder.radio_comment_number.setText(radioListResultList.get(position).getComments() + "");
        holder.radio_page_number.setText(radioListResultList.get(position).getPage() + "");
        holder.radio_profile_name.setText(radioListResultList.get(position).getProfilename());
        holder.radio_stream_name.setText(radioListResultList.get(position).getStreamname());


        Log.e("audio_path", API_Client.BASE_VIDEO_URL + radioListResultList.get(position).getAudio());


        if (radioFragment.audioStatusList.get(position).getAudioState() != AudioStatus.AUDIO_STATE.IDLE.ordinal()) {
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
        }

    }


    @Override
    public int getItemCount() {
        return radioListResultList.size();
    }

    @Override
    public Filter getFilter() {

        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence keyword) {
                ArrayList<RadioListResult> filtereddata = new ArrayList<>();


                if (keyword.toString().isEmpty())
                    filtereddata.addAll(backup);
                else {
                    for (RadioListResult obj : backup) {
                        if (obj.getProfilename().toString().toLowerCase().contains(keyword.toString().toLowerCase()))
                            filtereddata.add(obj);
                    }
                }

                FilterResults results = new FilterResults();
                results.values = filtereddata;
                return results;

            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults results) {
                radioListResultList.clear();
                radioListResultList.addAll((ArrayList<RadioListResult>) results.values);
                notifyDataSetChanged();
            }

        };

        return filter;
    }

    public class RadioViewHolder extends RecyclerView.ViewHolder {
        CircleImageView radio_profile;
        AppCompatTextView radio_profile_name;
        AppCompatTextView radio_comment_number;
        AppCompatTextView radio_page_number;
        AppCompatTextView radio_stream_name;
        AppCompatTextView radio_listen_num;
        LinearLayout showProgress;
        ImageView btnPlay;
        public SeekBar seekBarAudio;
        TextView currentTxt;

        public RadioViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            radio_stream_name = itemView.findViewById(R.id.radio_stream_name);
            radio_page_number = itemView.findViewById(R.id.radio_page_number);
            radio_listen_num = itemView.findViewById(R.id.radio_listen_num);
            radio_profile = itemView.findViewById(R.id.radio_profile);
            radio_profile_name = itemView.findViewById(R.id.radio_profile_name);
            showProgress = itemView.findViewById(R.id.showProgress);
            btnPlay = itemView.findViewById(R.id.btnPlay);
            seekBarAudio = itemView.findViewById(R.id.seekBarAudio);
            currentTxt = itemView.findViewById(R.id.current);
            radio_comment_number = itemView.findViewById(R.id.radio_comment_number);


            seekBarAudio.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (fromUser) MediaPlayerUtils.applySeekBarValue(progress);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });

            btnPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                 /*   boolean ifRequest = audioFragment.requestPermissionIfNeeded();
                    if(ifRequest) return;*/

                    int position = getAdapterPosition();

                    // Check if any other audio is playing
                    if (radioFragment.audioStatusList.get(position).getAudioState()
                            == AudioStatus.AUDIO_STATE.IDLE.ordinal()) {

                        // Reset media player
                        MediaPlayerUtils.Listener listener = (MediaPlayerUtils.Listener) radioFragment;
                        listener.onAudioComplete();
                    }

                    String audioPath = contactList.get(position);
                    AudioStatus audioStatus = radioFragment.audioStatusList.get(position);
                    int currentAudioState = audioStatus.getAudioState();

                    if (currentAudioState == AudioStatus.AUDIO_STATE.PLAYING.ordinal()) {
                        // If mediaPlayer is playing, pause mediaPlayer
                        Log.e("check_state"," If mediaPlayer is playing, pause mediaPlayer: "+audioStatus.getAudioState());

                        btnPlay.setImageResource(R.drawable.ic_play_circle_filled_black_24dp);
                        MediaPlayerUtils.pauseMediaPlayer();
                        audioStatus.setAudioState(AudioStatus.AUDIO_STATE.PAUSED.ordinal());
                        radioFragment.audioStatusList.set(position, audioStatus);
                    } else if (currentAudioState == AudioStatus.AUDIO_STATE.PAUSED.ordinal()) {
                        // If mediaPlayer is paused, play mediaPlayer
                        Log.e("check_state"," If mediaPlayer is paused, play mediaPlayer: "+audioStatus.getAudioState());
                        btnPlay.setImageResource(R.drawable.ic_pause_circle_filled_black_24dp);
                        MediaPlayerUtils.playMediaPlayer();
                        audioStatus.setAudioState(AudioStatus.AUDIO_STATE.PLAYING.ordinal());
                        radioFragment.audioStatusList.set(position, audioStatus);
                    } else {
                        // If mediaPlayer is in idle state, start and play mediaPlayer

                        Log.e("check_state"," If mediaPlayer is in idle state, start and play mediaPlayer: "+audioStatus.getAudioState());
                        btnPlay.setImageResource(R.drawable.ic_pause_circle_filled_black_24dp);

                        audioStatus.setAudioState(AudioStatus.AUDIO_STATE.PLAYING.ordinal());
                        radioFragment.audioStatusList.set(position, audioStatus);

                        try {
                            MediaPlayerUtils.startAndPlayMediaPlayer(audioPath, (MediaPlayerUtils.Listener) radioFragment);
                            audioStatus.setTotalDuration(MediaPlayerUtils.getTotalDuration());
                           /* int temp = MediaPlayerUtils.getTotalDuration();
                            Log.e("temp", String.valueOf(calculateDuration(temp)));

                            String temp2 = calculateDuration(temp);

                            Log.e("temp", temp2);
                            current.setText(temp2);*/
                            radioFragment.audioStatusList.set(position, audioStatus);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

        }
    }

    private String calculateDuration(int duration) {
        String finalDuration = "";
        long minutes = TimeUnit.MILLISECONDS.toMinutes(duration);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(duration);
        if (minutes == 0) {
            finalDuration = "0:" + seconds;
        } else {
            if (seconds >= 60) {
                long sec = seconds - (minutes * 60);
                finalDuration = minutes + ":" + sec;

            }
        }
        return finalDuration;
    }

}


