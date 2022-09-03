package com.webnmobapps.livelyPencil.Activity.Tutorial;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.webnmobapps.livelyPencil.Activity.Login.LoginJoinusActivity;
import com.webnmobapps.livelyPencil.Activity.RunWizardActivity.RunWizardAdapter;
import com.webnmobapps.livelyPencil.R;

import me.relex.circleindicator.CircleIndicator;

public class TutorialActivity extends AppCompatActivity {

    private ViewPager viewpager;
    private ConstraintLayout liner;
    private SlideAdapter myadapter;

    private TextView[] mdots;
    private AppCompatButton next,back, finishBtn;
    CircleIndicator banner_indicator;
    private int mCureentPage;
    private int currentPage;




    // on page scroll in view pager
    int SCROLLING_RIGHT = 0;
    int SCROLLING_LEFT = 1;
    int SCROLLING_UNDETERMINED = 2;
    int currentScrollDirection = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);


        viewpager= findViewById(R.id.viewpager);
        liner=  findViewById(R.id.dots);
        banner_indicator = findViewById(R.id.banner_indicator);

        next= findViewById(R.id.nextBtn);
        back= findViewById(R.id.backBtn);
        finishBtn= findViewById(R.id.finishBtn);

        myadapter=new SlideAdapter(TutorialActivity.this);
        viewpager.setAdapter(myadapter);
        banner_indicator.setViewPager(viewpager);
        adddots(0);

        viewpager.addOnPageChangeListener(viewlistener);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewpager.setCurrentItem(mCureentPage+1);

             //   Log.e("value", "mCureentPage: ", String.valueOf(mdots.length));

                if(mCureentPage == mdots.length-1)
                {

                    finishBtn.setVisibility(View.VISIBLE);
                    next.setVisibility(View.GONE);
                  /*  Intent intent = new Intent(TutorialActivity.this, loginJoinusActivity.class);
                    startActivity(intent);*/


                }




            }
        });

        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TutorialActivity.this, LoginJoinusActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // To clean up all activities
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra("finish", true);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewpager.setCurrentItem(mCureentPage-1);
            }
        });


        banner_indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                currentPage = position;
            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

                if (isScrollDirectionUndetermined()){
                    setScrollingDirection(arg1);
                }

                if (isScrollingLeft()){
                    Log.e("TabLayout","Scrolling LEFT");
                }
                if (isScrollingRight()){
                    Log.e("TabLayout","Scrolling RIGHT");

                }


            }

            @Override
            public void onPageScrollStateChanged(int pos) {
            }
        });
        
        

    }

    public void adddots(int i){

        mdots=new TextView[6];
        liner.removeAllViews();

        for (int x=0;x<mdots.length;x++){

            mdots[x]=new TextView(TutorialActivity.this);
            mdots[x].setText(Html.fromHtml("&#8226;"));
            mdots[x].setTextSize(35);
            mdots[x].setTextColor(getResources().getColor(R.color.darkBlue));

            liner.addView(mdots[x]);
        }
        if (mdots.length>0){

            mdots[i].setTextColor(getResources().getColor(R.color.darkBlue));

        }

    }

    ViewPager.OnPageChangeListener viewlistener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            adddots(position);
            mCureentPage = position;

            if (position==0){
                next.setEnabled(true);
                back.setEnabled(false);
//                back.setVisibility(View.INVISIBLE);

                next.setText("NEXT");
//                back.setText("");
                next.setVisibility(View.VISIBLE);
                finishBtn.setVisibility(View.GONE);
            }
            else if(position==mdots.length-1){

                next.setEnabled(true);
                back.setEnabled(true);
//                back.setVisibility(View.VISIBLE);

                next.setText("FINISH");
//                back.setText("BACK");

            /*    next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        if(position==mdots.length-1)
                        {
                            Intent intent = new Intent(TutorialActivity.this, loginJoinusActivity.class);
                            startActivity(intent);
                        }


                    }
                });*/
                finishBtn.setVisibility(View.VISIBLE);
                next.setVisibility(View.GONE);

            }
            else {
                next.setEnabled(true);
                back.setEnabled(true );
//                back.setVisibility(View.VISIBLE);

                next.setText("NEXT");
//                back.setText("BACK");

                next.setVisibility(View.VISIBLE);
                finishBtn.setVisibility(View.GONE);
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };




    private void setScrollingDirection(float positionOffset){
        if ((1-positionOffset)>= 0.5){
            this.currentScrollDirection = SCROLLING_RIGHT;
        }
        else if ((1-positionOffset)<= 0.5){
            this.currentScrollDirection =  SCROLLING_LEFT;
        }
    }

    private boolean isScrollingRight(){
        return currentScrollDirection == SCROLLING_RIGHT;
    }

    private boolean isScrollingLeft(){
        return currentScrollDirection == SCROLLING_LEFT;
    }

    private boolean isScrollDirectionUndetermined(){
        return currentScrollDirection == SCROLLING_UNDETERMINED;
    }


}