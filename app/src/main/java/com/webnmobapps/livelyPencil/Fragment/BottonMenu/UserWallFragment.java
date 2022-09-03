package com.webnmobapps.livelyPencil.Fragment.BottonMenu;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.webnmobapps.livelyPencil.Activity.Setting.PersonalInformationActivity;
import com.webnmobapps.livelyPencil.Adapter.LiveUserAdapter;
import com.webnmobapps.livelyPencil.Adapter.PopularListAdapter;
import com.webnmobapps.livelyPencil.Adapter.TVAdapter;
import com.webnmobapps.livelyPencil.Adapter.UserPostWallAdapter;
import com.webnmobapps.livelyPencil.Model.Record.UserWallListResult;
import com.webnmobapps.livelyPencil.Model.TvListModel;
import com.webnmobapps.livelyPencil.Model.UserWallListModel;
import com.webnmobapps.livelyPencil.R;
import com.webnmobapps.livelyPencil.RetrofitApi.API_Client;
import com.webnmobapps.livelyPencil.StaticModel.LiveUserModel;
import com.webnmobapps.livelyPencil.Tempcode.PostwallTypeModel;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UserWallFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // dev.sam
    RecyclerView rcv_user_post_wall, rcv_live_user, popular_list;
    LiveUserAdapter liveUserAdapter;
    UserPostWallAdapter userPostWallAdapter;
    PopularListAdapter popularListAdapter ;

    List<LiveUserModel> liveUserModelList = new ArrayList<>();
    List<UserWallListResult> userWallListResultList = new ArrayList<>();
    List<PostwallTypeModel> postwallTypeModelList = new ArrayList<>();


    public UserWallFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static UserWallFragment newInstance(String param1, String param2) {
        UserWallFragment fragment = new UserWallFragment();
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
        View v= inflater.inflate(R.layout.fragment_user_wall, container, false);

        inits(v);

        addDataInLiveUserModel();
        add_data_in_static_model();

        //user_wall_list_api();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        liveUserAdapter = new LiveUserAdapter(liveUserModelList, getActivity());
        rcv_live_user.setLayoutManager(linearLayoutManager);
        rcv_live_user.setAdapter(liveUserAdapter);


        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(getActivity());
        linearLayoutManager3.setOrientation(RecyclerView.HORIZONTAL);
        popularListAdapter = new PopularListAdapter(getActivity());
        popular_list.setLayoutManager(linearLayoutManager3);
        //popular_list.setAdapter(popularListAdapter);


        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getActivity());
        linearLayoutManager2.setOrientation(RecyclerView.VERTICAL);
        userPostWallAdapter = new UserPostWallAdapter(getActivity() ,postwallTypeModelList);
        rcv_user_post_wall.setLayoutManager(linearLayoutManager2);
        rcv_user_post_wall.setAdapter(userPostWallAdapter);


        return v;
    }

    private void add_data_in_static_model() {
        PostwallTypeModel postwallTypeModel = new PostwallTypeModel();
        postwallTypeModel.setPostType("1");
        postwallTypeModelList.add(postwallTypeModel);

        postwallTypeModel = new PostwallTypeModel();
        postwallTypeModel.setPostType("0");
        postwallTypeModelList.add(postwallTypeModel);

        postwallTypeModel = new PostwallTypeModel();
        postwallTypeModel.setPostType("0");
        postwallTypeModelList.add(postwallTypeModel);

        postwallTypeModel = new PostwallTypeModel();
        postwallTypeModel.setPostType("1");
        postwallTypeModelList.add(postwallTypeModel);

        postwallTypeModel = new PostwallTypeModel();
        postwallTypeModel.setPostType("0");
        postwallTypeModelList.add(postwallTypeModel);

        postwallTypeModel = new PostwallTypeModel();
        postwallTypeModel.setPostType("1");
        postwallTypeModelList.add(postwallTypeModel);

        postwallTypeModel = new PostwallTypeModel();
        postwallTypeModel.setPostType("0");
        postwallTypeModelList.add(postwallTypeModel);

        postwallTypeModel = new PostwallTypeModel();
        postwallTypeModel.setPostType("1");
        postwallTypeModelList.add(postwallTypeModel);

    }

    private void user_wall_list_api() {

            final ProgressDialog pd = new ProgressDialog(getActivity());
            pd.setCancelable(false);
            pd.setMessage("loading...");
            pd.show();


            Call<UserWallListModel> call = API_Client.getClient().userWallLlist();

            call.enqueue(new Callback<UserWallListModel>() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onResponse(Call<UserWallListModel> call, Response<UserWallListModel> response) {
                    pd.dismiss();


                    try {
                        if (response.isSuccessful()) {
                            String message = response.body().getMessage();
                            String success = response.body().getSuccess();

                            if (success.equals("true") || success.equals("True")) {

                                userWallListResultList = response.body().getRecord();

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
                public void onFailure(Call<UserWallListModel> call, Throwable t) {
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

        final LayoutInflater inflater = UserWallFragment.this.getLayoutInflater();
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

    private void addDataInLiveUserModel() {

        LiveUserModel liveUserModel = new LiveUserModel();
        liveUserModel.setImage(R.drawable.test_profile);
        liveUserModelList.add(liveUserModel);

        liveUserModel = new LiveUserModel();
        liveUserModel.setImage(R.drawable.test_profile);
        liveUserModelList.add(liveUserModel);

        liveUserModel = new LiveUserModel();
        liveUserModel.setImage(R.drawable.test_profile);
        liveUserModelList.add(liveUserModel);

        liveUserModel = new LiveUserModel();
        liveUserModel.setImage(R.drawable.test_profile);
        liveUserModelList.add(liveUserModel);

        liveUserModel = new LiveUserModel();
        liveUserModel.setImage(R.drawable.test_profile);
        liveUserModelList.add(liveUserModel);

        liveUserModel = new LiveUserModel();
        liveUserModel.setImage(R.drawable.test_profile);
        liveUserModelList.add(liveUserModel);

        liveUserModel = new LiveUserModel();
        liveUserModel.setImage(R.drawable.test_profile);
        liveUserModelList.add(liveUserModel);

    }

    private void inits(View v) {
        rcv_user_post_wall = v.findViewById(R.id.rcv_user_post_wall);
        rcv_live_user = v.findViewById(R.id.rcv_live_user);
        popular_list = v.findViewById(R.id.popular_list);
    }


}