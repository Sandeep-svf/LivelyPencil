package com.webnmobapps.livelyPencil.Activity.PostType.Fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.webnmobapps.livelyPencil.Activity.JoinUs.NameEmailActivity;
import com.webnmobapps.livelyPencil.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SoundFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SoundFragment extends Fragment {


    private enum SoundPostType{
        BROWSE, RECORD, ONAIR;
    }

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ConstraintLayout browse,record,onair;
    private String temp;

    public SoundFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static SoundFragment newInstance(String param1, String param2) {
        SoundFragment fragment = new SoundFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_sound, container, false);

        inits(view);



        browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 temp = String.valueOf(SoundPostType.BROWSE);
                warningpopup(view, temp);
            }
        });

        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 temp = String.valueOf(SoundPostType.RECORD);
                warningpopup(view, temp);
            }
        });

        onair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 temp = String.valueOf(SoundPostType.ONAIR);
                warningpopup(view, temp);
            }
        });

        return view;
    }

    private void warningpopup(View view, String type) {
        AlertDialog dialogs;

        final LayoutInflater inflater = getActivity().getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.offline_sound_post_warning_popup, null);

        final AppCompatEditText title_edit_text = alertLayout.findViewById(R.id.title_edit_text);
        final AppCompatTextView continue_layout = alertLayout.findViewById(R.id.continue_layout);
        final AppCompatTextView text_title1 = alertLayout.findViewById(R.id.text_title1);
        final ImageView close_dialog12345 = alertLayout.findViewById(R.id.close_dialog12345);
        final TextView on_air_text = alertLayout.findViewById(R.id.on_air_text);
        final View vcvc = alertLayout.findViewById(R.id.vcvc);

        if(type.equals(String.valueOf(SoundPostType.ONAIR)))
        {
            close_dialog12345.setVisibility(View.GONE);
            continue_layout.setVisibility(View.GONE);
            title_edit_text.setVisibility(View.GONE);
            text_title1.setVisibility(View.GONE);
            vcvc.setVisibility(View.GONE);
            on_air_text.setVisibility(View.VISIBLE);

        }else
        {
            close_dialog12345.setVisibility(View.VISIBLE);
            continue_layout.setVisibility(View.VISIBLE);
            title_edit_text.setVisibility(View.VISIBLE);
            vcvc.setVisibility(View.VISIBLE);
            text_title1.setVisibility(View.VISIBLE);
            on_air_text.setVisibility(View.GONE);
        }


        final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

        alert.setView(alertLayout);
        alert.setCancelable(false);

        dialogs = alert.create();
        dialogs.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogs.show();
        dialogs.setCanceledOnTouchOutside(true);


        close_dialog12345.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialogs.dismiss();
            }
        });
    }


    private void inits(View view) {

        browse = view.findViewById(R.id.browse);
        record = view.findViewById(R.id.record);
        onair = view.findViewById(R.id.onair);
    }
}