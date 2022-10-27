package com.webnmobapps.livelyPencil.Fragment.BottonMenu;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.webnmobapps.livelyPencil.Adapter.PageAdapter;
import com.webnmobapps.livelyPencil.Fragment.TopMenu.PageFragment;
import com.webnmobapps.livelyPencil.ModelPython.PostListModelPython;
import com.webnmobapps.livelyPencil.ModelPython.UserProfileDataPython;
import com.webnmobapps.livelyPencil.ModelPython.UserProfileModelPython;
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


public class ProfileFragment extends Fragment {

    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    public static TextView title;
    private  String accessToken,finalAccessToken;
    AppCompatTextView userName,streamName,createdOnTime;
    CircleImageView userProfile;
    AppCompatImageView streamCoverImage;
    CircleImageView add_stream_image;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);

        viewPager = (ViewPager)view.findViewById(R.id.view_pager);
        tabLayout = (TabLayout)view.findViewById(R.id.tab_layout);
        title= view.findViewById(R.id.title);
        streamName= view.findViewById(R.id.appCompatTextView5);
        userName= view.findViewById(R.id.userName);
        createdOnTime= view.findViewById(R.id.appCompatTextView6);
        userProfile= view.findViewById(R.id.userProfile);
        streamCoverImage= view.findViewById(R.id.streamCoverImage);
        add_stream_image= view.findViewById(R.id.add_stream_image);


        SharedPreferences sharedPreferences= getActivity().getSharedPreferences("AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
        // user_id=sharedPreferences.getString("UserID","");
        accessToken=sharedPreferences.getString("accessToken","");
        finalAccessToken = StaticKey.prefixTokem+accessToken;

        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        setupViewPager(viewPager,adapter);
        tabLayout.setupWithViewPager(viewPager);
        setupTabtitle();


        user_profile_details_api();


        return  view;
    }

    private void user_profile_details_api() {

            final ProgressDialog pd = new ProgressDialog(getActivity());
            pd.setCancelable(false);
            pd.setMessage("loading...");
            pd.show();

            Call<UserProfileModelPython> call = API_Client.getClient().USER_PROFILE_MODEL_PYTHON_CALL("user/18/",finalAccessToken);

            call.enqueue(new Callback<UserProfileModelPython>() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onResponse(Call<UserProfileModelPython> call, Response<UserProfileModelPython> response) {
                    pd.dismiss();


                    try {
                        if (response.isSuccessful()) {
                            String message = response.body().getMessage();
                            String success = response.body().getStatus();

                            if (success.equals("true") || success.equals("True")) {

                                UserProfileModelPython userProfileModelPython = response.body();
                                UserProfileDataPython userProfileDataPython = userProfileModelPython.getData();

                                try {
                                    userName.setText(userProfileDataPython.getFirstName()+" "+userProfileDataPython.getFirstName());
                                    streamName.setText(userProfileDataPython.getStreamTitle());
                                    createdOnTime.setText("Joined on "+userProfileDataPython.getCreatedAt());

                                    Glide.with(getActivity()).load(API_Client.BASE_IMAGE+userProfileDataPython.getImage()).
                                            placeholder(R.drawable.ic_launcher_background).
                                            into(userProfile);

                                    Glide.with(getActivity()).load(API_Client.BASE_IMAGE+userProfileDataPython.getStreamCoverImage()).
                                            placeholder(R.drawable.ic_launcher_background).
                                            into(streamCoverImage);
                                } catch (Exception exception) {
                                    exception.printStackTrace();
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
                public void onFailure(Call<UserProfileModelPython> call, Throwable t) {
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


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static void setupViewPager(ViewPager viewPager, ViewPagerAdapter adapter)
    {
        {
            adapter.addFragment(new PageFragment());
            adapter.addFragment(new FriendFollowersFragment());

        }

        viewPager.setAdapter(adapter);
    }
}