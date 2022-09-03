package com.webnmobapps.livelyPencil.Activity.Tutorial;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.viewpager.widget.PagerAdapter;

import com.webnmobapps.livelyPencil.R;



public class SlideAdapter extends PagerAdapter {

    Context context;
    LayoutInflater inflater;

    public SlideAdapter(Context context){
        this.context=context;
    }

    //Array
    public int[] list_images={

            R.drawable.logo_svg,
            R.drawable.logo_svg,
            R.drawable.logo_svg,
            R.drawable.logo_svg,
            R.drawable.logo_svg,
            R.drawable.logo_svg
    };

    public String[] list_title={

            "How is it working?",
            "Content Creation",
            "Profile Page",
            "Friends, Faimly",
            "Global Area",
            "Live Market"

    };

    public String[] list_description={

            "Lively Pencil defines a stream area of 365 pages to each user. Allows you to create a newstream area when the page limits reached. Completed Flow fields accumulate in your profile.\n" +
                    "\n Users create content through the pages they create. When the page content limits reached, it automatcally moves to the next page.\n" +
                    "\nThe content you add to the pages creates a source for the sections in your profile.",


            "Our platform is equipped with rich content tools to meet wide usage purposes.\n" +
                    "\nEasily publish all your content, from personal use to publishing professional content, from education to the activities of commercial enterprises.\n" +
                    "\nYou can write content, share photos, videos, audios and express yourself by drawing. We enable you to do all of this with live broadcast.",


            "Every content added to the pages creates a source for the fields on your profile page.\n" +
                    "\nLkes, shares and announcements of your content are added to the activities page, your video files to your TV channel, audio files to the Radio channel, and your photos to the gallery tab.\n" +
                    "\nIn order for your content to reach wider audiences, it wll be open to the whole world in the global area accordng to the statistics of views.\n",


            "Lively Pencil cares about friends and faimly relationships. You need to know the phone or email address of the people you want to add.\n" +
                    "\nWe prefer the verificaton method to securely connect you online with faimly members, relatives and friends.\n" +
                    "\nWe have put a message system at your service where you can instant message with your friends list and a general message box for people you don't know to contact you.",

            "In order for your content to reach large audiences, we take advantage of your statistical informaton and move it to the global arena.\n" +
                    "\nRegional level or sections that publish popular content around the world also allow you to access quality content.\n" +

                    "\nYou can become popular by enriching your content to carry your TV-Radio channel to the global arena. Our platform offers equal opportunities to all its users.",

            "We have created a market area for the advertisements you want to place for your products and services. We provide free service for all your advertisements if they comply with the laws of the countries you are in. We would like to inform you that it is our goal to develop the tools for our users to benefit from the platform.\n" +

                    "\nYou can reach your goal faster by using the live market secton for a service you offer in your streaming area or for your purchase, rental or event announcements."
    };
    public int[] list_color={

            Color.rgb(255,255,255),
            Color.rgb(255,255,255),
            Color.rgb(255,255,255),
            Color.rgb(255,255,255),
            Color.rgb(255,255,255),
            Color.rgb(255,255,255)

    };

    @Override
    public int getCount() {
        return list_title.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object obj) {
        return view==(NestedScrollView)obj;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.slide,container,false);



        Log.e("jfklsjdlf", String.valueOf(position)+"fffffffffffff");


        ConstraintLayout linearLayout = (ConstraintLayout)view.findViewById(R.id.slidelinearlayout);
        AppCompatImageView img = view.findViewById(R.id.slideimg);
        AppCompatTextView txt1 =  view.findViewById(R.id.slidetitle);
        AppCompatTextView txt2 = view.findViewById(R.id.slidedescription);


        img.setImageResource(list_images[position]);
        txt1.setText(list_title[position]);
        txt2.setText(list_description[position]);
        linearLayout.setBackgroundColor(list_color[position]);


        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((NestedScrollView)object);
    }
}
