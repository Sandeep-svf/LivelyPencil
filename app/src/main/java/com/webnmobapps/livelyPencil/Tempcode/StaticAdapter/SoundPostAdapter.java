package com.webnmobapps.livelyPencil.Tempcode.StaticAdapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.webnmobapps.livelyPencil.Activity.Utility.AudioStatus;
import com.webnmobapps.livelyPencil.Activity.Utility.MediaPlayerUtils;

import com.webnmobapps.livelyPencil.Model.PositionModel;

import com.webnmobapps.livelyPencil.Model.Record.SoundPostResult;
import com.webnmobapps.livelyPencil.Model.SecondPositionModel;
import com.webnmobapps.livelyPencil.R;
import com.webnmobapps.livelyPencil.Tempcode.TempActivity.TempActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SoundPostAdapter extends RecyclerView.Adapter<SoundPostViewHolder> {

    Context context;
    TempActivity radioFragment;
    private List<String> contactList = new ArrayList<>();
    List<PositionModel> positionModelList = new ArrayList<>();
    List<SecondPositionModel> secondPositionModelList = new ArrayList<>();
    List<SoundPostResult> soundPostResults;
    List<SoundPostResult> backup;

    // Radio adapter reference

    /*List<RadioListResult> radioListResultList;
    List<RadioListResult> backup;*/

    public SoundPostAdapter(Context context, TempActivity radioFragment, List<String> contactList, List<SoundPostResult> soundPostResults) {
        this.context = context;
        this.radioFragment = radioFragment;
        this.contactList = contactList;
        this.soundPostResults = soundPostResults;
    }

    public SoundPostAdapter() {
    }

    @NonNull
    @Override
    public SoundPostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.sound_post_view_holder_new, parent, false);
        return new SoundPostViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull SoundPostViewHolder holder, int position) {



    }

    @Override
    public int getItemCount() {
        return 20;
    }
}
class SoundPostViewHolder extends RecyclerView.ViewHolder {

    public SeekBar seekBarAudio;
    ImageView btnPlay;

    public SoundPostViewHolder(@NonNull View itemView) {
        super(itemView);


        seekBarAudio = itemView.findViewById(R.id.seekBarAudio_SPT);
        btnPlay = itemView.findViewById(R.id.btnPlay_SPT);


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


      /*  btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 *//*   boolean ifRequest = audioFragment.requestPermissionIfNeeded();
                    if(ifRequest) return;*//*

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
                           *//* int temp = MediaPlayerUtils.getTotalDuration();
                            Log.e("temp", String.valueOf(calculateDuration(temp)));

                            String temp2 = calculateDuration(temp);

                            Log.e("temp", temp2);
                            current.setText(temp2);*//*
                        radioFragment.audioStatusList.set(position, audioStatus);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
*/

    }
}