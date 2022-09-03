package com.webnmobapps.livelyPencil.Fragment.BottonMenu;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.webnmobapps.livelyPencil.Activity.Setting.PersonalInformationActivity;
import com.webnmobapps.livelyPencil.Activity.Setting.ShareSettingsActivity;
import com.webnmobapps.livelyPencil.Adapter.NotificationListAdapter;
import com.webnmobapps.livelyPencil.Adapter.RadioAdapter;
import com.webnmobapps.livelyPencil.Model.EditShareSettingsModel;
import com.webnmobapps.livelyPencil.Model.NotificationListModel;
import com.webnmobapps.livelyPencil.Model.Personal_Information_Settings_Model;
import com.webnmobapps.livelyPencil.Model.Record.NotificationListResult;
import com.webnmobapps.livelyPencil.Model.SmFlaxibleModel;
import com.webnmobapps.livelyPencil.R;
import com.webnmobapps.livelyPencil.RetrofitApi.API_Client;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private String user_id;
    List<NotificationListResult> notificationListResultList = new ArrayList<>();
    RecyclerView rcv_notification_list;
    AppCompatButton n_clear_all_button;


    public NotificationFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static NotificationFragment newInstance(String param1, String param2) {
        NotificationFragment fragment = new NotificationFragment();
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

        View view =  inflater.inflate(R.layout.fragment_notification, container, false);

        inits(view);

        SharedPreferences sharedPreferences= getActivity().getSharedPreferences("AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
        user_id=sharedPreferences.getString("UserID","");

        notification_list_api();


        n_clear_all_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final ProgressDialog pd = new ProgressDialog(getActivity());
                pd.setCancelable(false);
                pd.setMessage("loading...");
                pd.show();


                Call<SmFlaxibleModel> call = API_Client.getClient().notificationClearAll(user_id);

                call.enqueue(new Callback<SmFlaxibleModel>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onResponse(Call<SmFlaxibleModel> call, Response<SmFlaxibleModel> response) {
                        pd.dismiss();


                        try {
                            if (response.isSuccessful()) {
                                String message = response.body().getMessage();
                                String success = response.body().getSuccess();

                                if (success.equals("true") || success.equals("True")) {

                                    notification_list_api();
                                    Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();

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
                    public void onFailure(Call<SmFlaxibleModel> call, Throwable t) {
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
        });



        return view;
    }

    private void inits(View view) {
        rcv_notification_list = view.findViewById(R.id.rcv_notification_list);
        n_clear_all_button = view.findViewById(R.id.n_clear_all_button);
    }

    private void notification_list_api() {
        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();




        Call<NotificationListModel> call = API_Client.getClient().notificationList(user_id);

        call.enqueue(new Callback<NotificationListModel>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<NotificationListModel> call, Response<NotificationListModel> response) {
                pd.dismiss();


                try {
                    if (response.isSuccessful()) {
                        String message = response.body().getMessage();
                        String success = response.body().getSuccess();

                        if (success.equals("true") || success.equals("True")) {

                            notificationListResultList = response.body().getRecord();

                            if(notificationListResultList.size() == 0)
                            {
                                n_clear_all_button.setVisibility(View.GONE);
                            }else
                            {
                                n_clear_all_button.setVisibility(View.VISIBLE);
                            }

                            LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getActivity());
                            linearLayoutManager2.setOrientation(RecyclerView.VERTICAL);
                            rcv_notification_list.setLayoutManager(linearLayoutManager2);
                            NotificationListAdapter notificationListAdapter = new NotificationListAdapter(notificationListResultList, getActivity(),user_id);
                            rcv_notification_list.setAdapter(notificationListAdapter);
                            //Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();

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
                                    alert_dialog_message("400");
                                    //  Toast.makeText(getApplicationContext(), "The server did not understand the request.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 401:
                                    alert_dialog_message("401");
                                    // Toast.makeText(getApplicationContext(), "Unauthorized The requested page needs a username and a password.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 404:
                                    alert_dialog_message("404");
                                    //Toast.makeText(getApplicationContext(), "The server can not find the requested page.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 500:
                                    alert_dialog_message("500");
                                    //Toast.makeText(getApplicationContext(), "Internal Server Error..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 503:
                                    alert_dialog_message("503");
                                    // Toast.makeText(getApplicationContext(), "Service Unavailable..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 504:
                                    alert_dialog_message("504");
                                    //  Toast.makeText(getApplicationContext(), "Gateway Timeout..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 511:
                                    alert_dialog_message("511");
                                    // Toast.makeText(getApplicationContext(), "Network Authentication Required ..", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    alert_dialog_message("default");
                                    //Toast.makeText(getApplicationContext(), "unknown error", Toast.LENGTH_SHORT).show();
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
            public void onFailure(Call<NotificationListModel> call, Throwable t) {
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

    private void alert_dialog_message(String value) {

        AlertDialog dialogs;

        final LayoutInflater inflater = NotificationFragment.this.getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.test_dialog_xml_otp, null);

        final ImageView close_dialog = alertLayout.findViewById(R.id.close_dialog);
        final TextView error_message = alertLayout.findViewById(R.id.error_message);

        if(value.equals("400"))
        {
            error_message.setText(getResources().getString(R.string.case_4_0_0));
        }else if(value.equals("401"))
        {
            error_message.setText(getResources().getString(R.string.case_4_0_1));
        }else if(value.equals("404"))
        {
            error_message.setText(getResources().getString(R.string.case_4_0_4));
        }else if(value.equals("500"))
        {
            error_message.setText(getResources().getString(R.string.case_5_0_0));
        }else if(value.equals("503"))
        {
            error_message.setText(getResources().getString(R.string.case_5_0_3));
        }else if(value.equals("504"))
        {
            error_message.setText(getResources().getString(R.string.case_5_0_4));
        }else if(value.equals("511"))
        {
            error_message.setText(getResources().getString(R.string.case_5_1_1));
        }else if(value.equals("504"))
        {
            error_message.setText(getResources().getString(R.string.case_5_0_4));
        }else if(value.equals("default"))
        {
            error_message.setText(getResources().getString(R.string.default_api_error));
        }

        final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

        alert.setView(alertLayout);
        alert.setCancelable(false);

        dialogs = alert.create();
        dialogs.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogs.show();
        dialogs.setCanceledOnTouchOutside(true);


        close_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialogs.dismiss();
            }
        });
    }
}