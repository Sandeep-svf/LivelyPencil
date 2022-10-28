package com.webnmobapps.livelyPencil.Activity.NewChangePhase;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder;
import com.webnmobapps.livelyPencil.Adapter.PageAdapter;
import com.webnmobapps.livelyPencil.ModelPython.NotificationModel;
import com.webnmobapps.livelyPencil.ModelPython.NotificationModelSettingData;
import com.webnmobapps.livelyPencil.R;
import com.webnmobapps.livelyPencil.RetrofitApi.API_Client;
import com.webnmobapps.livelyPencil.utility.StaticKey;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsFragment extends Fragment implements  com.tsongkha.spinnerdatepicker.DatePickerDialog.OnDateSetListener {

    AppCompatImageView support_center_image;
    ConstraintLayout chang_user_profile_layout;
    AppCompatEditText name_editText, surname_editText,streamname_editText,country_name_editText;
    AppCompatTextView date_spin, month_spin, year_spin,change_username_text_layout;
    SwitchMaterial stream_privacy_setting_switchmaterial, notification_privacy_setting_switchmaterial;
    private String dayData, monthData, yearData;
    private Context  context;
    private String finalAccessToken,accessToken;
    private String notificatinStatus;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_settings, container, false);
        intis(view);
        context = getActivity();


        SharedPreferences sharedPreferences= getActivity().getSharedPreferences("AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
        // user_id=sharedPreferences.getString("UserID","");
        accessToken=sharedPreferences.getString("accessToken","");
        finalAccessToken = StaticKey.prefixTokem+accessToken;
        
        // notification settings get API
        notification_settring_api();


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

    private void notification_settring_api() {
  
            final ProgressDialog pd = new ProgressDialog(getActivity());
            pd.setCancelable(false);
            pd.setMessage("loading...");
            pd.show();




            Call<NotificationModel> call = API_Client.getClient().NOTIFICATION_MODEL_SETTINGS_CALL(finalAccessToken);

            call.enqueue(new Callback<NotificationModel>() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onResponse(Call<NotificationModel> call, Response<NotificationModel> response) {
                    pd.dismiss();


                    try {
                        if (response.isSuccessful()) {
                            String message = response.body().getMessage();
                            String success = response.body().getStatus();

                            if (success.equals("true") || success.equals("True")) {
                                NotificationModel notificationModel = response.body();
                                NotificationModelSettingData notificationModelSettingData = notificationModel.getData();

                                notificatinStatus = notificationModelSettingData.getNotification();

                                // 0-> open
                                // 1-> close

                                if(notificatinStatus.equals("0")){

                                }else if(notificatinStatus.equals("1")){

                                }else{
                                    Toast.makeText(getActivity(), "Something weent wrong while loasing notification settings data", Toast.LENGTH_SHORT).show();
                                }

                               


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
                public void onFailure(Call<NotificationModel> call, Throwable t) {
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