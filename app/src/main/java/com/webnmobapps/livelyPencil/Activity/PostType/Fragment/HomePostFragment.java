package com.webnmobapps.livelyPencil.Activity.PostType.Fragment;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.webnmobapps.livelyPencil.Fragment.TopMenu.RadioFragment;
import com.webnmobapps.livelyPencil.R;


public class HomePostFragment extends Fragment {

    ConstraintLayout sound;


    public HomePostFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home_post, container, false);

        inits(view);

        sound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Replace fragment from fragment......
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
                ft.replace(R.id.fragment_contaner, new SoundFragment(), "NewFragmentTag");
                ft.commit();
            }
        });

        return view;
    }

    private void inits(View view) {
        sound = view.findViewById(R.id.sound);
    }
}