package com.webnmobapps.livelyPencil.Fragment.BottonMenu;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.webnmobapps.livelyPencil.Fragment.TopMenu.PageFragment;
import com.webnmobapps.livelyPencil.R;

import java.util.ArrayList;
import java.util.List;


public class ProfileFragment extends Fragment {

    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    public static TextView title;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);

        viewPager = (ViewPager)view.findViewById(R.id.view_pager);
        tabLayout = (TabLayout)view.findViewById(R.id.tab_layout);
        title= view.findViewById(R.id.title);

        setupTabtitle();

        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        setupViewPager(viewPager,adapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                String anme= String.valueOf(tabLayout.getTabAt(position).getText());
                //getActivity().title.setText(anme);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        return  view;
    }
    private void setupTabtitle() {
        tabLayout.getTabAt(0).setText(getResources().getString(R.string.pages));
        tabLayout.getTabAt(1).setText(getResources().getString(R.string.retell));
        tabLayout.getTabAt(2).setText(getResources().getString(R.string.followers));



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
            adapter.addFragment(new RetellFragment());
            adapter.addFragment(new FriendFollowersFragment());

        }

        viewPager.setAdapter(adapter);
    }
}