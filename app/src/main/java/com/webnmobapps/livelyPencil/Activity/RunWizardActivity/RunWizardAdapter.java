package com.webnmobapps.livelyPencil.Activity.RunWizardActivity;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.viewpager.widget.PagerAdapter;

import com.webnmobapps.livelyPencil.Adapter.SelectIntrestAdapter;
import com.webnmobapps.livelyPencil.R;

public class RunWizardAdapter extends PagerAdapter {


    Context context;
    LayoutInflater inflater;

    public RunWizardAdapter(Context context) {
        this.context = context;
    }

    public interface  Get_Position_Wizard_Function
    {
        void page_details(int position, String key);

    }
    private RunWizardAdapter.Get_Position_Wizard_Function get_position_wizard_function;

    public void setGet_position_itemDrawings(RunWizardAdapter.Get_Position_Wizard_Function get_position_wizard_function) {
        this.get_position_wizard_function = get_position_wizard_function;
    }


    public String[] list_title={
            "Personal Information",
            "Contact Information",
            "About Yourself",
            "Streaming Area",
            "Tv Channel Settings",
            "Radio Channel Settings",
            "Privacy settings"

    };

    @Override
    public int getCount() {
        return 7;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==(ConstraintLayout)object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.run_wizard_view_holder,container,false);

        final int page = position;

        Log.e("page","page no is: "+page);


        ConstraintLayout linearLayout = (ConstraintLayout)view.findViewById(R.id.slider_constraint_layout2);
        ConstraintLayout RW1 = view.findViewById(R.id.RW1);
        ConstraintLayout RW2 = view.findViewById(R.id.RW2);
        ConstraintLayout RW3 = view.findViewById(R.id.RW3);
        ConstraintLayout RW4 = view.findViewById(R.id.RW4);
        ConstraintLayout RW5 = view.findViewById(R.id.RW5);
        ConstraintLayout RW6 = view.findViewById(R.id.RW6);
        ConstraintLayout RW7 = view.findViewById(R.id.RW7);
       // AppCompatEditText pw1_name = view.findViewById(R.id.pw1_name);


        if(position==0)
        {
            RW1.setVisibility(View.VISIBLE);
            RW2.setVisibility(View.GONE);
            RW3.setVisibility(View.GONE);
            RW4.setVisibility(View.GONE);
            RW5.setVisibility(View.GONE);
            RW6.setVisibility(View.GONE);
            RW7.setVisibility(View.GONE);
         //   String userName = pw1_name.getText().toString();
            get_position_wizard_function.page_details(position,"0");
        }else if(position==1)
        {
            RW2.setVisibility(View.VISIBLE);
            RW1.setVisibility(View.GONE);
            RW3.setVisibility(View.GONE);
            RW4.setVisibility(View.GONE);
            RW5.setVisibility(View.GONE);
            RW6.setVisibility(View.GONE);
            RW7.setVisibility(View.GONE);
            get_position_wizard_function.page_details(position,"1");
        }else if(position==2)
        {
            RW3.setVisibility(View.VISIBLE);
            RW1.setVisibility(View.GONE);
            RW2.setVisibility(View.GONE);
            RW4.setVisibility(View.GONE);
            RW5.setVisibility(View.GONE);
            RW6.setVisibility(View.GONE);
            RW7.setVisibility(View.GONE);
        }else if(position==3)
        {
            RW4.setVisibility(View.VISIBLE);
            RW1.setVisibility(View.GONE);
            RW2.setVisibility(View.GONE);
            RW3.setVisibility(View.GONE);
            RW5.setVisibility(View.GONE);
            RW6.setVisibility(View.GONE);
            RW7.setVisibility(View.GONE);
        }else if(position==4)
        {
            RW5.setVisibility(View.VISIBLE);
            RW1.setVisibility(View.GONE);
            RW2.setVisibility(View.GONE);
            RW4.setVisibility(View.GONE);
            RW3.setVisibility(View.GONE);
            RW6.setVisibility(View.GONE);
            RW7.setVisibility(View.GONE);
        }else if(position==5)
        {
            RW6.setVisibility(View.VISIBLE);
            RW1.setVisibility(View.GONE);
            RW2.setVisibility(View.GONE);
            RW4.setVisibility(View.GONE);
            RW5.setVisibility(View.GONE);
            RW3.setVisibility(View.GONE);
            RW7.setVisibility(View.GONE);
        }else if(position==6)
        {
            RW7.setVisibility(View.VISIBLE);
            RW1.setVisibility(View.GONE);
            RW2.setVisibility(View.GONE);
            RW4.setVisibility(View.GONE);
            RW5.setVisibility(View.GONE);
            RW6.setVisibility(View.GONE);
            RW3.setVisibility(View.GONE);
        }


        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ConstraintLayout)object);
    }



}


