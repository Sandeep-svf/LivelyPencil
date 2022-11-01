package com.webnmobapps.livelyPencil.Activity.NewChangePhase;

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
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder;
import com.webnmobapps.livelyPencil.ModelPython.CommonStatusMessageModelPython;
import com.webnmobapps.livelyPencil.ModelPython.NotificationSettingModel;
import com.webnmobapps.livelyPencil.ModelPython.NotificationModelSettingData;
import com.webnmobapps.livelyPencil.ModelPython.RoleSettingDataModel;
import com.webnmobapps.livelyPencil.ModelPython.RoleSettingModel;
import com.webnmobapps.livelyPencil.ModelPython.SettingModel;
import com.webnmobapps.livelyPencil.ModelPython.SettingModelData;
import com.webnmobapps.livelyPencil.ModelPython.UserProfileData;
import com.webnmobapps.livelyPencil.ModelPython.UserProfileModel;
import com.webnmobapps.livelyPencil.R;
import com.webnmobapps.livelyPencil.RetrofitApi.API_Client;
import com.webnmobapps.livelyPencil.utility.StaticKey;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;
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
    private String notificatinStatus, roleStatus;
    CircleImageView usere_profile_circle_image_view;
    AppCompatTextView user_name_data_text;


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
        role_setting_api();
        user_profle_api();


        stream_privacy_setting_switchmaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                role_change_settings_api();
            }
        });

        notification_privacy_setting_switchmaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notification_change_settings_api();
            }
        });

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

    private void user_profle_api() {




            final ProgressDialog pd = new ProgressDialog(getActivity());
            pd.setCancelable(false);
            pd.setMessage("loading...");
            pd.show();





            Call<SettingModel> call = API_Client.getClient().SETTING_MODEL_CALL(finalAccessToken);

            call.enqueue(new Callback<SettingModel>() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onResponse(Call<SettingModel> call, Response<SettingModel> response) {
                    pd.dismiss();


                    try {
                        if (response.isSuccessful()) {
                            String message = response.body().getMessage();
                            String success = response.body().getStatus();

                            if (success.equals("true") || success.equals("True")) {
                                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();


                                SettingModel settingModel = response.body();
                                SettingModelData settingModelData = settingModel.getData();

                                name_editText.setText(settingModelData.getFirstName());
                                surname_editText.setText(settingModelData.getLastName());
                                streamname_editText.setText(settingModelData.getStreamTitle());
                                user_name_data_text.setText(settingModelData.getUsername());
                                date_spin.setText("null");
                                month_spin.setText("null");
                                year_spin.setText("null");
                                Glide.with(getActivity()).load(API_Client.BASE_IMAGE+settingModelData.getImage())
                                        .placeholder(R.drawable.ic_launcher_background)
                                        .into(usere_profile_circle_image_view);
                                try {
                                    country_name_editText.setText(settingModelData.getCountry());
                                } catch (Exception exception) {
                                    exception.printStackTrace();
                                }finally {
                                    country_name_editText.setText("null");
                                }
                               /* UserProfileModel userProfileModel = response.body();
                                UserProfileData userProfileData = userProfileModel.getUserProfileData();


                                try {
                                    name_editText.setText(userProfileData.getFirstName());
                                    surname_editText.setText(userProfileData.getLastName());
                                    streamname_editText.setText(userProfileData.getStreamTitle());
                                    Glide.with(getActivity()).load(API_Client.BASE_IMAGE+userProfileData.getImage())
                                            .placeholder(R.drawable.ic_launcher_background)
                                            .into(usere_profile_circle_image_view);
                                    country_name_editText.setText("India");
                                } catch (Exception exception) {
                                    exception.printStackTrace();
                                    Toast.makeText(getActivity(), "Something went wrong, while loading user profile data form APIs.", Toast.LENGTH_SHORT).show();
                                }*/

                                pd.dismiss();
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
                public void onFailure(Call<SettingModel> call, Throwable t) {
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

    private void role_change_settings_api() {



            final ProgressDialog pd = new ProgressDialog(getActivity());
            pd.setCancelable(false);
            pd.setMessage("loading...");
            pd.show();

            String roleStatus="";

            if(stream_privacy_setting_switchmaterial.isChecked()){
                roleStatus = "1";
            }else{
                roleStatus = "0";
            }



            Call<CommonStatusMessageModelPython> call = API_Client.getClient().ROLE_CHANGE_SETTINGS_CALL(finalAccessToken,roleStatus);

            call.enqueue(new Callback<CommonStatusMessageModelPython>() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onResponse(Call<CommonStatusMessageModelPython> call, Response<CommonStatusMessageModelPython> response) {
                    pd.dismiss();


                    try {
                        if (response.isSuccessful()) {
                            String message = response.body().getMessage();
                            String success = response.body().getStatus();

                            if (success.equals("true") || success.equals("True")) {
                                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                                role_setting_api();
                                pd.dismiss();
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
                public void onFailure(Call<CommonStatusMessageModelPython> call, Throwable t) {
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

    private void notification_change_settings_api() {


            final ProgressDialog pd = new ProgressDialog(getActivity());
            pd.setCancelable(false);
            pd.setMessage("loading...");
            pd.show();

            String notificationStatus="";

        if(notification_privacy_setting_switchmaterial.isChecked()){
            notificationStatus = "1";
        }else{
            notificationStatus = "0";
        }



            Call<CommonStatusMessageModelPython> call = API_Client.getClient().NOTIFICATION_MODEL_CHANGE_SETTINGS_CALL(finalAccessToken,notificationStatus);

            call.enqueue(new Callback<CommonStatusMessageModelPython>() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onResponse(Call<CommonStatusMessageModelPython> call, Response<CommonStatusMessageModelPython> response) {
                    pd.dismiss();


                    try {
                        if (response.isSuccessful()) {
                            String message = response.body().getMessage();
                            String success = response.body().getStatus();

                            if (success.equals("true") || success.equals("True")) {
                                    Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                                    notification_settring_api();
                                    pd.dismiss();
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
                public void onFailure(Call<CommonStatusMessageModelPython> call, Throwable t) {
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

        private void role_setting_api(){


                final ProgressDialog pd = new ProgressDialog(getActivity());
                pd.setCancelable(false);
                pd.setMessage("loading...");
                pd.show();

                Call<RoleSettingModel> call = API_Client.getClient().ROLE_SETTING_MODEL_CALL(finalAccessToken);

                call.enqueue(new Callback<RoleSettingModel>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onResponse(Call<RoleSettingModel> call, Response<RoleSettingModel> response) {
                        pd.dismiss();


                        try {
                            if (response.isSuccessful()) {
                                String message = response.body().getMessage();
                                String success = response.body().getStatus();

                                if (success.equals("true") || success.equals("True")) {
                                    RoleSettingModel roleSettingModel = response.body();
                                    RoleSettingDataModel roleSettingDataModel = roleSettingModel.getRoleSettingDataModel();

                                    roleStatus = roleSettingDataModel.getRole();

                                    Log.e("notificatinStatus",notificatinStatus+"ok");

                                    // 0-> public
                                    // 1-> private

                                    if(roleStatus.equals("0")){
                                        stream_privacy_setting_switchmaterial.setChecked(false);
                                        stream_privacy_setting_switchmaterial.setText("Private");
                                    }else if(roleStatus.equals("1")){
                                        stream_privacy_setting_switchmaterial.setChecked(true);
                                        stream_privacy_setting_switchmaterial.setText("Public");

                                    }else{
                                        Toast.makeText(getActivity(), "Something went wrong while loading notification settings data", Toast.LENGTH_SHORT).show();
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
                    public void onFailure(Call<RoleSettingModel> call, Throwable t) {
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

    private void notification_settring_api() {
  
            final ProgressDialog pd = new ProgressDialog(getActivity());
            pd.setCancelable(false);
            pd.setMessage("loading...");
            pd.show();




            Call<NotificationSettingModel> call = API_Client.getClient().NOTIFICATION_MODEL_SETTINGS_CALL(finalAccessToken);

            call.enqueue(new Callback<NotificationSettingModel>() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onResponse(Call<NotificationSettingModel> call, Response<NotificationSettingModel> response) {
                    pd.dismiss();


                    try {
                        if (response.isSuccessful()) {
                            String message = response.body().getMessage();
                            String success = response.body().getStatus();

                            if (success.equals("true") || success.equals("True")) {
                                NotificationSettingModel notificationSettingModel = response.body();
                                NotificationModelSettingData notificationModelSettingData = notificationSettingModel.getData();

                                notificatinStatus = notificationModelSettingData.getNotification();

                                Log.e("notificatinStatus",notificatinStatus+"ok");

                                // 0-> open
                                // 1-> close

                                if(notificatinStatus.equals("0")){
                                    notification_privacy_setting_switchmaterial.setChecked(false);
                                    notification_privacy_setting_switchmaterial.setText("Close");
                                }else if(notificatinStatus.equals("1")){
                                    notification_privacy_setting_switchmaterial.setChecked(true);
                                    notification_privacy_setting_switchmaterial.setText("Open");

                                }else{
                                    Toast.makeText(getActivity(), "Something went wrong while loading notification settings data", Toast.LENGTH_SHORT).show();
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
                public void onFailure(Call<NotificationSettingModel> call, Throwable t) {
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
        user_name_data_text = view.findViewById(R.id.user_name_data_text);
        change_username_text_layout = view.findViewById(R.id.change_username_text_layout);
        support_center_image = view.findViewById(R.id.support_center_image);
        chang_user_profile_layout = view.findViewById(R.id.chang_user_profile_layout);
        name_editText = view.findViewById(R.id.name_editText);
        surname_editText = view.findViewById(R.id.surname_editText);
        streamname_editText = view.findViewById(R.id.streamname_editText);
        country_name_editText = view.findViewById(R.id.country_name_editText);
        date_spin = view.findViewById(R.id.date_spin);
        month_spin = view.findViewById(R.id.month_spin);
        usere_profile_circle_image_view = view.findViewById(R.id.usere_profile_circle_image_view);
        year_spin = view.findViewById(R.id.year_spin);
        stream_privacy_setting_switchmaterial = view.findViewById(R.id.stream_privacy_setting_switchmaterial);
        notification_privacy_setting_switchmaterial = view.findViewById(R.id.notification_privacy_setting_switchmaterial);
    }


    @Override
    public void onDateSet(com.tsongkha.spinnerdatepicker.DatePicker  view, int year, int month, int dayOfMonth) {

        Log.e("check","working.*&###########"+year+month+dayOfMonth);
    }


}