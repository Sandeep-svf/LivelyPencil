package com.webnmobapps.livelyPencil.Fragment.BottonMenu;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.webnmobapps.livelyPencil.Activity.Book.BookListActivity;
import com.webnmobapps.livelyPencil.Adapter.BookListAdapter;
import com.webnmobapps.livelyPencil.Adapter.FollowersAdapter;
import com.webnmobapps.livelyPencil.Adapter.FriendFollowersAdapter;
import com.webnmobapps.livelyPencil.Fragment.TopMenu.PageFragment;
import com.webnmobapps.livelyPencil.Model.FollowersModel;
import com.webnmobapps.livelyPencil.Model.Record.FollowersModelData;
import com.webnmobapps.livelyPencil.ModelPython.LiveUserListModelPython;
import com.webnmobapps.livelyPencil.R;
import com.webnmobapps.livelyPencil.RetrofitApi.API_Client;
import com.webnmobapps.livelyPencil.utility.GridSpacingItemDecoration;
import com.webnmobapps.livelyPencil.utility.StaticKey;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FollowersFragment extends Fragment {

    RecyclerView rcv_followers;
    List<FollowersModelData> followersModelDataList = new ArrayList<>();
    private String finalAccessToken,accessToken;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_followers, container, false);

        rcv_followers = view.findViewById(R.id.rcv_followers);



        SharedPreferences sharedPreferences= getActivity().getSharedPreferences("AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
        // user_id=sharedPreferences.getString("UserID","");
        accessToken=sharedPreferences.getString("accessToken","");
        finalAccessToken = StaticKey.prefixTokem+accessToken;




        followers_api();



        return view;
    }
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
    private void followers_api() {



            final ProgressDialog pd = new ProgressDialog(getActivity());
            pd.setCancelable(false);
            pd.setMessage("loading...");
            pd.show();


            Call<FollowersModel> call = API_Client.getClient().FOLLOWERS_MODEL_CALL(finalAccessToken);

            call.enqueue(new Callback<FollowersModel>() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onResponse(Call<FollowersModel> call, Response<FollowersModel> response) {
                    pd.dismiss();


                    try {
                        if (response.isSuccessful()) {
                            String message = response.body().getMessage();
                            String success = response.body().getStatus();

                            if (success.equals("true") || success.equals("True")) {

                                followersModelDataList = response.body().getData();

                                Log.e("list_size",followersModelDataList.size()+"ok");


                                RecyclerView.LayoutManager topLayoutManager = new GridLayoutManager(getActivity(), 2);
                                rcv_followers.setLayoutManager(topLayoutManager);
                                rcv_followers.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(8), true));
                                rcv_followers.setItemAnimator(new DefaultItemAnimator());

                                /*FriendFollowersAdapter friendFollowersAdapter = new FriendFollowersAdapter(getActivity(),followersModelDataList);
                                rcv_followers.setAdapter(friendFollowersAdapter);*/

                                /*LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false);
                                rcv_followers.setLayoutManager(linearLayoutManager);*/
                                FollowersAdapter bookListAdapter = new FollowersAdapter(getActivity(),followersModelDataList);
                                rcv_followers.setAdapter(bookListAdapter);


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
                public void onFailure(Call<FollowersModel> call, Throwable t) {
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

}