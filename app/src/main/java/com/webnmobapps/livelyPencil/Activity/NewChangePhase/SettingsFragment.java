package com.webnmobapps.livelyPencil.Activity.NewChangePhase;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder;
import com.webnmobapps.livelyPencil.R;

import java.util.Calendar;

public class SettingsFragment extends Fragment implements  com.tsongkha.spinnerdatepicker.DatePickerDialog.OnDateSetListener {

    AppCompatImageView support_center_image;
    ConstraintLayout chang_user_profile_layout;
    AppCompatEditText name_editText, surname_editText,streamname_editText,country_name_editText;
    AppCompatTextView date_spin, month_spin, year_spin,change_username_text_layout;
    SwitchMaterial stream_privacy_setting_switchmaterial, notification_privacy_setting_switchmaterial;
    private String dayData, monthData, yearData;
    private Context  context;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_settings, container, false);
        intis(view);
        context = getActivity();

        date_spin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intisialization_method(v);

            }
        });

        month_spin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intisialization_method(v);
            }
        });

        year_spin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intisialization_method(v);
            }
        });



        return view;
    }

    private void intisialization_method(View v) {
        String temp = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));

        new SpinnerDatePickerDialogBuilder()
                .context(getActivity())
                .callback(this)
                .spinnerTheme(R.style.NumberPickerStyle)
                .showTitle(true)
                .showDaySpinner(true)
                .defaultDate(2000, 0, 1)
                .maxDate(Integer.parseInt(temp), 0, 1)
                .minDate(1950, 0, 1)
                .build()
                .show();
    }

    private void intis(View view) {
        change_username_text_layout = view.findViewById(R.id.change_username_text_layout);
        support_center_image = view.findViewById(R.id.support_center_image);
        chang_user_profile_layout = view.findViewById(R.id.chang_user_profile_layout);
        name_editText = view.findViewById(R.id.name_editText);
        surname_editText = view.findViewById(R.id.surname_editText);
        streamname_editText = view.findViewById(R.id.streamname_editText);
        country_name_editText = view.findViewById(R.id.country_name_editText);
        date_spin = view.findViewById(R.id.date_spin);
        month_spin = view.findViewById(R.id.month_spin);
        year_spin = view.findViewById(R.id.year_spin);
        stream_privacy_setting_switchmaterial = view.findViewById(R.id.stream_privacy_setting_switchmaterial);
        notification_privacy_setting_switchmaterial = view.findViewById(R.id.notification_privacy_setting_switchmaterial);
    }


    @Override
    public void onDateSet(com.tsongkha.spinnerdatepicker.DatePicker  view, int year, int month, int dayOfMonth) {

        Log.e("check","working.*&###########"+year+month+dayOfMonth);
    }


}