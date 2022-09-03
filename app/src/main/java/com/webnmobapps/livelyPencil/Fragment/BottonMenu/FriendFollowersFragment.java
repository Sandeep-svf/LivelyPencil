package com.webnmobapps.livelyPencil.Fragment.BottonMenu;

import android.content.res.Resources;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.webnmobapps.livelyPencil.Adapter.FriendFollowersAdapter;
import com.webnmobapps.livelyPencil.Model.friend_followers_model;
import com.webnmobapps.livelyPencil.R;
import com.webnmobapps.livelyPencil.utility.GridSpacingItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class FriendFollowersFragment extends Fragment {


    RecyclerView rcv_friend_followers;
    List<friend_followers_model>  friend_followers_modelList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_friend_followers, container, false);

        rcv_friend_followers = view.findViewById(R.id.rcv_friend_followers);

        RecyclerView.LayoutManager topLayoutManager = new GridLayoutManager(getActivity(), 2);
        rcv_friend_followers.setLayoutManager(topLayoutManager);
        rcv_friend_followers.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(8), true));
        rcv_friend_followers.setItemAnimator(new DefaultItemAnimator());

       FriendFollowersAdapter friendFollowersAdapter = new FriendFollowersAdapter(getActivity());
        rcv_friend_followers.setAdapter(friendFollowersAdapter);


        return view;
    }
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}