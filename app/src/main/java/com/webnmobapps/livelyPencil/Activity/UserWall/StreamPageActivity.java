package com.webnmobapps.livelyPencil.Activity.UserWall;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.tabs.TabLayout;
import com.webnmobapps.livelyPencil.Activity.Share.CommentActivity;
import com.webnmobapps.livelyPencil.Activity.Share.FollowUnfollowActivity;
import com.webnmobapps.livelyPencil.Fragment.temp.Birth_Control_Fragment;
import com.webnmobapps.livelyPencil.Fragment.temp.Fertility_Fragment;
import com.webnmobapps.livelyPencil.Fragment.temp.News_Fragment;
import com.webnmobapps.livelyPencil.Fragment.temp.Pregenancy_Fragment;
import com.webnmobapps.livelyPencil.Fragment.temp.Pregenancy_Fragment2;
import com.webnmobapps.livelyPencil.Fragment.temp.Pregenancy_Fragment3;
import com.webnmobapps.livelyPencil.Fragment.temp.Pregenancy_Fragment4;
import com.webnmobapps.livelyPencil.Fragment.temp.Pregenancy_Fragment5;
import com.webnmobapps.livelyPencil.R;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import de.hdodenhof.circleimageview.CircleImageView;


public class StreamPageActivity extends AppCompatActivity {

    CircleImageView post_icon;
    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    private int[] tabIcons = {
            R.drawable.edit,
            R.drawable.html_edit,
            R.drawable.insert_image,
            R.drawable.music,
            R.drawable.youtube,
            R.drawable.ic_format_quote,
            R.drawable.code,
            R.drawable.read,
    };
    ConstraintLayout follow_unfollow, comment_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stream_page2);

        intis();

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        setupViewPager(viewPager,adapter);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
       // setupTabtitle();
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                String anme= String.valueOf(tabLayout.getTabAt(position).getText());
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        follow_unfollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StreamPageActivity.this, FollowUnfollowActivity.class);
                startActivity(intent);
            }
        });

        comment_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StreamPageActivity.this, CommentActivity.class);
                startActivity(intent);
            }
        });

        post_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StreamPageActivity.this, ShareStreamPageByReadresActivity.class);
                startActivity(intent);
            }
        });

    }

    private void intis() {
        post_icon = findViewById(R.id.post_icon);
        viewPager = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.tabs);
        follow_unfollow = findViewById(R.id.follow_unfollow);
        comment_layout = findViewById(R.id.comment_layout);
    }


    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
        tabLayout.getTabAt(3).setIcon(tabIcons[3]);
        tabLayout.getTabAt(4).setIcon(tabIcons[4]);
        tabLayout.getTabAt(5).setIcon(tabIcons[5]);
        tabLayout.getTabAt(6).setIcon(tabIcons[6]);
        tabLayout.getTabAt(7).setIcon(tabIcons[7]);

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static void setupViewPager(ViewPager viewPager, ViewPagerAdapter adapter)
    {
        {
            adapter.addFragment(new News_Fragment());
            adapter.addFragment(new Birth_Control_Fragment());
            adapter.addFragment(new Fertility_Fragment());
            adapter.addFragment(new Pregenancy_Fragment());
            adapter.addFragment(new Pregenancy_Fragment2());
            adapter.addFragment(new Pregenancy_Fragment3());
            adapter.addFragment(new Pregenancy_Fragment4());
            adapter.addFragment(new Pregenancy_Fragment5());

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

