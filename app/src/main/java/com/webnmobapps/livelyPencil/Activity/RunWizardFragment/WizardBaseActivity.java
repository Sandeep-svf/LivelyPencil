package com.webnmobapps.livelyPencil.Activity.RunWizardFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

import com.webnmobapps.livelyPencil.Activity.RunWizardActivity.RunWizardSettingsActivity;
import com.webnmobapps.livelyPencil.R;

import me.relex.circleindicator.CircleIndicator;

public class WizardBaseActivity extends AppCompatActivity {


    CircleIndicator banner_indicator;
    private AppCompatButton next,back, finishBtn;
    private ConstraintLayout liner;

    private int mCureentPage;
    private int currentPage;
    private TextView[] mdots;

    // on page scroll in view pager
    int SCROLLING_RIGHT = 0;
    int SCROLLING_LEFT = 1;
    int SCROLLING_UNDETERMINED = 2;
    int currentScrollDirection = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wizard_base);

        intis();


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

        mdots=new TextView[7];
        liner.removeAllViews();

        for (int x=0;x<mdots.length;x++){

            mdots[x]=new TextView(WizardBaseActivity.this);
            mdots[x].setText(Html.fromHtml("&#8226;"));
            mdots[x].setTextSize(35);
            mdots[x].setTextColor(getResources().getColor(R.color.darkBlue));

            liner.addView(mdots[x]);
        }
        if (mdots.length>0){

            mdots[i].setTextColor(getResources().getColor(R.color.darkBlue));

        }

    }
    private void intis() {

        liner=  findViewById(R.id.dots);
        banner_indicator = findViewById(R.id.banner_indicator);
        next= findViewById(R.id.nextBtn);
        back= findViewById(R.id.backBtn);
        finishBtn= findViewById(R.id.finishBtn);
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
    private void setScrollingDirection(float positionOffset){
        if ((1-positionOffset)>= 0.5){
            this.currentScrollDirection = SCROLLING_RIGHT;
        }
        else if ((1-positionOffset)<= 0.5){
            this.currentScrollDirection =  SCROLLING_LEFT;
        }
    }
}