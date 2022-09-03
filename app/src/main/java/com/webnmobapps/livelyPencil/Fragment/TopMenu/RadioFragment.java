package com.webnmobapps.livelyPencil.Fragment.TopMenu;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.webnmobapps.livelyPencil.Activity.Utility.AudioStatus;
import com.webnmobapps.livelyPencil.Activity.Utility.MediaPlayerUtils;
import com.webnmobapps.livelyPencil.Adapter.PageAdapter;
import com.webnmobapps.livelyPencil.Adapter.RadioAdapter;
import com.webnmobapps.livelyPencil.Adapter.UserPostWallAdapter;
import com.webnmobapps.livelyPencil.Model.RadioListModel;
import com.webnmobapps.livelyPencil.Model.Record.RadioListResult;
import com.webnmobapps.livelyPencil.Model.Record.StreamPageResult;
import com.webnmobapps.livelyPencil.Model.StreamPageModel;
import com.webnmobapps.livelyPencil.R;
import com.webnmobapps.livelyPencil.RetrofitApi.API_Client;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RadioFragment extends Fragment implements MediaPlayerUtils.Listener{


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RecyclerView radio_rcv_page;
    RadioAdapter radioAdapter;
    private Context context;
    private String user_id;
    List<RadioListResult> radioListResultList = new ArrayList<>();
    public static List<AudioStatus> audioStatusList = new ArrayList<>();
    private List<String> contactList = new ArrayList<>();
    private Parcelable state;
    AppCompatEditText radio_search_text;

    public RadioFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_radio, container, false);
        inits(view);

        ButterKnife.bind(getActivity());
        context = getActivity();

        radio_search_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
               // radioAdapter.getFilter().filter(radio_search_text.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        SharedPreferences sharedPreferences= getActivity().getSharedPreferences("AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
        user_id=sharedPreferences.getString("UserID","");

        radio_list_api();


        return  view;
    }

    private void radio_list_api() {

        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();




        Call<RadioListModel> call = API_Client.getClient().popularRadioList(user_id);

        call.enqueue(new Callback<RadioListModel>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<RadioListModel> call, Response<RadioListModel> response) {
                pd.dismiss();


                try {
                    if (response.isSuccessful()) {
                        String message = response.body().getMessage();
                        String success = response.body().getSuccess();

                        if (success.equals("true") || success.equals("True")) {

                            radioListResultList = response.body().getRecord();
                            LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getActivity());
                            linearLayoutManager2.setOrientation(RecyclerView.VERTICAL);
                            radio_rcv_page.setLayoutManager(linearLayoutManager2);


                            for(int i = 0; i< radioListResultList.size() ; i++)
                            {
                                contactList.add(API_Client.BASE_VIDEO_URL+radioListResultList.get(i).getAudio());
                            }


                            for(int i = 0; i < radioListResultList.size(); i++) {
                              //  if(i==0) Toast.makeText(getActivity(), "Setting audio IDEL", Toast.LENGTH_SHORT).show();

                                audioStatusList.add(new AudioStatus(AudioStatus.AUDIO_STATE.IDLE.ordinal(), 0));
                                audioStatusList.get(i).setAudioState(0);

                                Log.e("check_state"," Set audio statuis as IDEL. ->"+audioStatusList.get(i).getAudioState());

                            }


                            setRecyclerViewAdapter(contactList);

                        } else {
                            Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                            pd.dismiss();
                        }


                    } else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(getActivity(), jObjError.getString("message"), Toast.LENGTH_LONG).show();
                            switch (response.code()) {
                                case 400:
                                    Toast.makeText(getActivity(), "The server did not understand the request.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 401:
                                    Toast.makeText(getActivity(), "Unauthorized The requested page needs a username and a password.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 404:
                                    Toast.makeText(getActivity(), "The server can not find the requested page.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 500:
                                    Toast.makeText(getActivity(), "Internal Server Error..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 503:
                                    Toast.makeText(getActivity(), "Service Unavailable..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 504:
                                    Toast.makeText(getActivity(), "Gateway Timeout..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 511:
                                    Toast.makeText(getActivity(), "Network Authentication Required ..", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(getActivity(), "unknown error", Toast.LENGTH_SHORT).show();
                                    break;
                            }

                        } catch (Exception e) {
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (
                        Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<RadioListModel> call, Throwable t) {
                Log.e("bhgyrrrthbh", String.valueOf(t));
                if (t instanceof IOException) {
                    Toast.makeText(getActivity(), "This is an actual network failure :( inform the user and possibly retry)" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                } else {
                    Log.e("conversion issue", t.getMessage());
                    Toast.makeText(getActivity(), "Please Check your Internet Connection...." + t.getMessage(), Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            }
        });

    }

    private void setRecyclerViewAdapter(List<String> contactList) {
        RadioAdapter radioAdapter = new RadioAdapter(getActivity(), contactList,radioListResultList,RadioFragment.this);
        radio_rcv_page.setAdapter(radioAdapter);
    }

    private void inits(View view) {
        radio_rcv_page = view.findViewById(R.id.radio_rcv_page);
        radio_search_text = view.findViewById(R.id.radio_search_text);
    }


    @Override
    public void onAudioUpdate(int currentPosition) {
        int playingAudioPosition = -1;
        for(int i = 0; i < audioStatusList.size(); i++) {
            AudioStatus audioStatus = audioStatusList.get(i);
            if(audioStatus.getAudioState() == AudioStatus.AUDIO_STATE.PLAYING.ordinal()) {
                playingAudioPosition = i;
                break;
            }
        }

        if(playingAudioPosition != -1) {
            RadioAdapter.RadioViewHolder holder
                    = (RadioAdapter.RadioViewHolder) radio_rcv_page.findViewHolderForAdapterPosition(playingAudioPosition);
            if (holder != null) {
                holder.seekBarAudio.setProgress(currentPosition);
            }
        }
    }

    @Override
    public void onAudioComplete() {
        // Store its state
        state = radio_rcv_page.getLayoutManager().onSaveInstanceState();

        audioStatusList.clear();
        for(int i = 0; i < contactList.size(); i++) {
            audioStatusList.add(new AudioStatus(AudioStatus.AUDIO_STATE.IDLE.ordinal(), 0));
        }
        setRecyclerViewAdapter(contactList);

        // Main position of RecyclerView when loaded again
        if (state != null) {
            radio_rcv_page.getLayoutManager().onRestoreInstanceState(state);
        }
    }



    @Override
    public void onStop() {
        super.onStop();
        MediaPlayerUtils.releaseMediaPlayer();
    }

    @Override
    public void onPause() {
        super.onPause();
       //  Store its state
        state = radio_rcv_page.getLayoutManager().onSaveInstanceState();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MediaPlayerUtils.releaseMediaPlayer();

        // reset current playing audio after change fragment


    }




    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onResume() {
        super.onResume();

      /*  for(int i= 0; i < radioListResultList.size(); i++)
        {
            audioStatusList.add(new AudioStatus(AudioStatus.AUDIO_STATE.IDLE.ordinal(), 0));
            audioStatusList.get(i).setAudioState(0);

            Log.e("check_state"," Set audio statuis as IDEL on resume. ->"+audioStatusList.get(i).getAudioState());
        }*/

        radio_list_api();

       /* if (state != null) {
            radio_rcv_page.getLayoutManager().onRestoreInstanceState(state);
        }*/
    }
}