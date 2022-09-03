package com.webnmobapps.livelyPencil.Fragment.BottonMenu;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.webnmobapps.livelyPencil.Adapter.AddEmailLayoutAdapter;
import com.webnmobapps.livelyPencil.Adapter.MessageEmailAdapter;
import com.webnmobapps.livelyPencil.R;

public class MessageEmailFragment extends Fragment {

    RecyclerView rcv_email;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_message_email, container, false);


        inits(view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false);
        rcv_email.setLayoutManager(linearLayoutManager);

        MessageEmailAdapter messageEmailFragment = new MessageEmailAdapter(getActivity());
        rcv_email.setAdapter(messageEmailFragment);


        return  view;
    }

    private void inits(View view) {
        rcv_email = view.findViewById(R.id.rcv_email);
    }
}