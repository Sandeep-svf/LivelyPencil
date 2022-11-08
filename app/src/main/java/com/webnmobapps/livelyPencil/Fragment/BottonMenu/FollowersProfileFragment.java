package com.webnmobapps.livelyPencil.Fragment.BottonMenu;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.webnmobapps.livelyPencil.R;


public class FollowersProfileFragment extends Fragment {

    private String followerUserId;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_followers_profile, container, false);

        Bundle bundle = this.getArguments();
        if(bundle != null){
            followerUserId = getArguments().getString("followerUserId");
        }

        Log.e("followerUserId",followerUserId+"ok");



        return view;
    }
}