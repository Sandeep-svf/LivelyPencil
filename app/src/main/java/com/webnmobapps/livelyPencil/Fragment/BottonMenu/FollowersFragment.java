package com.webnmobapps.livelyPencil.Fragment.BottonMenu;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.webnmobapps.livelyPencil.R;


public class FollowersFragment extends Fragment {

    RecyclerView rcv_followers;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_followers, container, false);

        rcv_followers = view.findViewById(R.id.rcv_followers);



        return view;
    }
}