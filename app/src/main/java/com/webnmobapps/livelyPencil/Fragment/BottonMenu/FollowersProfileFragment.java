package com.webnmobapps.livelyPencil.Fragment.BottonMenu;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.webnmobapps.livelyPencil.Fragment.TopMenu.PageFragment;
import com.webnmobapps.livelyPencil.Model.FollowerProfileModel;
import com.webnmobapps.livelyPencil.Model.Record.FollowerProfileModelData;
import com.webnmobapps.livelyPencil.ModelPython.CommonStatusMessageModelPython;
import com.webnmobapps.livelyPencil.ModelPython.UserProfileData;
import com.webnmobapps.livelyPencil.ModelPython.UserProfileModel;
import com.webnmobapps.livelyPencil.R;
import com.webnmobapps.livelyPencil.RetrofitApi.API_Client;
import com.webnmobapps.livelyPencil.utility.StaticKey;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FollowersProfileFragment extends Fragment {

    private String followerUserId;
    private String accessToken,finalAccessToken;
    private AppCompatImageView streamCoverImage;
    private CircleImageView userProfile;
    private AppCompatTextView userName,streamName,createdOnDate;
    private ConstraintLayout back_layout,report_profile_layout;


    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    private AppCompatImageView back_arrow;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.followers_profile, container, false);

        intis(view);

        SharedPreferences sharedPreferences= getActivity().getSharedPreferences("AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
        // user_id=sharedPreferences.getString("UserID","");
        accessToken=sharedPreferences.getString("accessToken","");
        finalAccessToken = StaticKey.prefixTokem+accessToken;

        Bundle bundle = this.getArguments();
        if(bundle != null){
            followerUserId = getArguments().getString("followerUserId");
        }

        Log.e("followerUserId",followerUserId+"ok");

        SharedPreferences fsdfsd = getActivity().getSharedPreferences("followerUserId", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = fsdfsd.edit();
        editor.putString("followerUserId", String.valueOf(followerUserId));
        editor.apply();


        ProfileFragment.ViewPagerAdapter adapter = new ProfileFragment.ViewPagerAdapter(getChildFragmentManager());
        setupViewPager(viewPager,adapter);
        tabLayout.setupWithViewPager(viewPager);
        setupTabtitle();


        follower_profile_api();



        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ProfileFragment fragment2 = new ProfileFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_contaner, fragment2);
                fragmentTransaction.commit();
            }
        });

        report_profile_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                report_profile_api();
            }
        });

        back_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



        return view;
    }

    private void report_profile_api() {


            final ProgressDialog pd = new ProgressDialog(getActivity());
            pd.setCancelable(false);
            pd.setMessage("loading...");
            pd.show();


            Call<CommonStatusMessageModelPython> call = API_Client.getClient().REPORT_PROFILE_COMMON_STATUS_MESSAGE_MODEL_PYTHON_CALL(finalAccessToken,followerUserId);

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

    private void intis(View view) {
        back_arrow = view.findViewById(R.id.back_arrow);
        back_layout = view.findViewById(R.id.back_layout);
        streamCoverImage = view.findViewById(R.id.streamCoverImage);
        userProfile = view.findViewById(R.id.userProfile);
        userName = view.findViewById(R.id.userName);
        streamName = view.findViewById(R.id.appCompatTextView5);
        createdOnDate = view.findViewById(R.id.appCompatTextView6);
        report_profile_layout = view.findViewById(R.id.report_profile_layout);
        viewPager = (ViewPager)view.findViewById(R.id.view_pager);
        tabLayout = (TabLayout)view.findViewById(R.id.tab_layout);
    }

    private void follower_profile_api() {

            final ProgressDialog pd = new ProgressDialog(getActivity());
            pd.setCancelable(false);
            pd.setMessage("loading...");
            pd.show();


            Call<FollowerProfileModel> call = API_Client.getClient().FOLLOWER_PROFILE_MODEL_CALL("user/"+followerUserId+"/",finalAccessToken);

            call.enqueue(new Callback<FollowerProfileModel>() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onResponse(Call<FollowerProfileModel> call, Response<FollowerProfileModel> response) {
                    pd.dismiss();


                    try {
                        if (response.isSuccessful()) {
                            String message = response.body().getMessage();
                            String success = response.body().getStatus();

                            if (success.equals("true") || success.equals("True")) {
                                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();



                                FollowerProfileModel followerProfileModel = response.body();
                                FollowerProfileModelData followerProfileModelData = followerProfileModel.getData();


                                try {

                                    Glide.with(getActivity()).load(API_Client.BASE_IMAGE+followerProfileModelData.getImage())
                                            .placeholder(R.drawable.ic_launcher_background)
                                            .into(userProfile);

                                    Glide.with(getActivity()).load(API_Client.BASE_IMAGE+followerProfileModelData.getStreamCoverImage())
                                            .placeholder(R.drawable.ic_launcher_background)
                                            .into(streamCoverImage);

                                    userName.setText(followerProfileModelData.getFirstName()+" "+followerProfileModelData.getLastName());
                                    streamName.setText(followerProfileModelData.getStreamTitle());
                                    createdOnDate.setText(followerProfileModelData.getCreatedAt());





                                } catch (Exception exception) {
                                    exception.printStackTrace();
                                    Toast.makeText(getActivity(), "Something went wrong, while loading user profile data form APIs.", Toast.LENGTH_SHORT).show();
                                }

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
                public void onFailure(Call<FollowerProfileModel> call, Throwable t) {
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


    private void setupTabtitle() {
        tabLayout.getTabAt(0).setText(getResources().getString(R.string.pages));
        tabLayout.getTabAt(1).setText(getResources().getString(R.string.followers));
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static void setupViewPager(ViewPager viewPager, ProfileFragment.ViewPagerAdapter adapter)
    {
        {
            adapter.addFragment(new PageFragment());
            adapter.addFragment(new FollowerFollowersFragment());

        }

        viewPager.setAdapter(adapter);
    }

    static class ViewPagerAdapter extends FragmentPagerAdapter
    {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment) {
            mFragmentList.add(fragment);


        }


    }

}