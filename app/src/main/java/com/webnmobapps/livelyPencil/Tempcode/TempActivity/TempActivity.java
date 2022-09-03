package com.webnmobapps.livelyPencil.Tempcode.TempActivity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.webnmobapps.livelyPencil.Adapter.StramChannelAdapter;
import com.webnmobapps.livelyPencil.Adapter.TVAdapter;
import com.webnmobapps.livelyPencil.Adapter.TvChannelAdapter;
import com.webnmobapps.livelyPencil.R;
import com.webnmobapps.livelyPencil.Tempcode.StaticAdapter.SoundPostAdapter;

import de.hdodenhof.circleimageview.CircleImageView;

public class TempActivity extends AppCompatActivity {

    RecyclerView sound_type_rcv;
    ConstraintLayout stream_tab_layout, tv_tab_layout, radio_tab_layout, photos_tab_layout, about_tab_layout;

    // SPT
    AppCompatImageView radio_image_view_SPT, mail_SPT;
    AppCompatTextView username_textview_SPT, stream_name_textview_SPT, headline_SPT;
    CircleImageView logo_SPT, userprofile_SPT;
    AppCompatButton tv_save_change_button_SPT;
    ConstraintLayout headline2_SPT,white_spread_SPT,black_spread_SPT;

    // TVPT
    ConstraintLayout TVPT_layout;

    //StreamPT
    ConstraintLayout StreamPT;

    //AboutPT
    ConstraintLayout AboutPT;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_design_updated);

        intis();


        //DEFAULT SHOWN LAYOUT....
        //SPT
        radio_image_view_SPT.setVisibility(View.VISIBLE);
        black_spread_SPT.setVisibility(View.VISIBLE);
        white_spread_SPT.setVisibility(View.VISIBLE);
        headline2_SPT.setVisibility(View.VISIBLE);
        tv_save_change_button_SPT.setVisibility(View.VISIBLE);
        userprofile_SPT.setVisibility(View.VISIBLE);
        logo_SPT.setVisibility(View.VISIBLE);
        username_textview_SPT.setVisibility(View.VISIBLE);
        stream_name_textview_SPT.setVisibility(View.VISIBLE);
        headline_SPT.setVisibility(View.VISIBLE);
        mail_SPT.setVisibility(View.VISIBLE);
        // TVPT
        TVPT_layout.setVisibility(View.VISIBLE);
        //ADAPTER
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(TempActivity.this, LinearLayoutManager.VERTICAL,false);
        sound_type_rcv.setLayoutManager(linearLayoutManager);
        SoundPostAdapter soundPostAdapter = new SoundPostAdapter();
        sound_type_rcv.setAdapter(soundPostAdapter);

        stream_tab_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stream_tab_layout.setBackgroundResource(R.drawable.tab_layout_background2);

                tv_tab_layout.setBackgroundResource(R.drawable.tab_layout_background);
                radio_tab_layout.setBackgroundResource(R.drawable.tab_layout_background);
                photos_tab_layout.setBackgroundResource(R.drawable.tab_layout_background);
                about_tab_layout.setBackgroundResource(R.drawable.tab_layout_background);




                //StramPT
                StreamPT.setVisibility(View.VISIBLE);

                // TVPT
                TVPT_layout.setVisibility(View.GONE);

                //AboutPT
                AboutPT.setVisibility(View.GONE);

                //SPT
                radio_image_view_SPT.setVisibility(View.GONE);
                black_spread_SPT.setVisibility(View.GONE);
                white_spread_SPT.setVisibility(View.GONE);
                headline2_SPT.setVisibility(View.GONE);
                tv_save_change_button_SPT.setVisibility(View.GONE);
                userprofile_SPT.setVisibility(View.GONE);
                logo_SPT.setVisibility(View.GONE);
                username_textview_SPT.setVisibility(View.GONE);
                stream_name_textview_SPT.setVisibility(View.GONE);
                headline_SPT.setVisibility(View.GONE);
                mail_SPT.setVisibility(View.GONE);

                // Adapter
               // LinearLayoutManager linearLayoutManager = new LinearLayoutManager(TempActivity.this, LinearLayoutManager.VERTICAL,false);
                sound_type_rcv.setLayoutManager(new GridLayoutManager(TempActivity.this, 3));
                //sound_type_rcv.setLayoutManager(linearLayoutManager);
                StramChannelAdapter  stramChannelAdapter = new StramChannelAdapter();
                sound_type_rcv.setAdapter(stramChannelAdapter);

            }
        });

        tv_tab_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_tab_layout.setBackgroundResource(R.drawable.tab_layout_background2);

                stream_tab_layout.setBackgroundResource(R.drawable.tab_layout_background);
                radio_tab_layout.setBackgroundResource(R.drawable.tab_layout_background);
                photos_tab_layout.setBackgroundResource(R.drawable.tab_layout_background);
                about_tab_layout.setBackgroundResource(R.drawable.tab_layout_background);

                //StramPT
                StreamPT.setVisibility(View.GONE);

                // TVPT
                TVPT_layout.setVisibility(View.VISIBLE);
                //AboutPT
                AboutPT.setVisibility(View.GONE);

                //SPT
                radio_image_view_SPT.setVisibility(View.GONE);
                black_spread_SPT.setVisibility(View.GONE);
                white_spread_SPT.setVisibility(View.GONE);
                headline2_SPT.setVisibility(View.GONE);
                tv_save_change_button_SPT.setVisibility(View.GONE);
                userprofile_SPT.setVisibility(View.GONE);
                logo_SPT.setVisibility(View.GONE);
                username_textview_SPT.setVisibility(View.GONE);
                stream_name_textview_SPT.setVisibility(View.GONE);
                headline_SPT.setVisibility(View.GONE);
                mail_SPT.setVisibility(View.GONE);



                // Adapter
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(TempActivity.this, LinearLayoutManager.VERTICAL,false);
                sound_type_rcv.setLayoutManager(linearLayoutManager);
                TvChannelAdapter tvChannelAdapter = new TvChannelAdapter();
                sound_type_rcv.setAdapter(tvChannelAdapter);

            }
        });

        radio_tab_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radio_tab_layout.setBackgroundResource(R.drawable.tab_layout_background2);

                stream_tab_layout.setBackgroundResource(R.drawable.tab_layout_background);
                tv_tab_layout.setBackgroundResource(R.drawable.tab_layout_background);
                photos_tab_layout.setBackgroundResource(R.drawable.tab_layout_background);
                about_tab_layout.setBackgroundResource(R.drawable.tab_layout_background);

                //StramPT
                StreamPT.setVisibility(View.GONE);

                // TVPT
                TVPT_layout.setVisibility(View.GONE);

                //AboutPT
                AboutPT.setVisibility(View.GONE);

                //SPT
                radio_image_view_SPT.setVisibility(View.VISIBLE);
                black_spread_SPT.setVisibility(View.VISIBLE);
                white_spread_SPT.setVisibility(View.VISIBLE);
                headline2_SPT.setVisibility(View.VISIBLE);
                tv_save_change_button_SPT.setVisibility(View.VISIBLE);
                userprofile_SPT.setVisibility(View.VISIBLE);
                logo_SPT.setVisibility(View.VISIBLE);
                username_textview_SPT.setVisibility(View.VISIBLE);
                stream_name_textview_SPT.setVisibility(View.VISIBLE);
                headline_SPT.setVisibility(View.VISIBLE);
                mail_SPT.setVisibility(View.VISIBLE);

                //Adapter
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(TempActivity.this, LinearLayoutManager.VERTICAL,false);
                sound_type_rcv.setLayoutManager(linearLayoutManager);
                SoundPostAdapter soundPostAdapter = new SoundPostAdapter();
                sound_type_rcv.setAdapter(soundPostAdapter);

            }
        });

        photos_tab_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photos_tab_layout.setBackgroundResource(R.drawable.tab_layout_background2);

                stream_tab_layout.setBackgroundResource(R.drawable.tab_layout_background);
                radio_tab_layout.setBackgroundResource(R.drawable.tab_layout_background);
                tv_tab_layout.setBackgroundResource(R.drawable.tab_layout_background);
                about_tab_layout.setBackgroundResource(R.drawable.tab_layout_background);
            }
        });

        about_tab_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                about_tab_layout.setBackgroundResource(R.drawable.tab_layout_background2);

                stream_tab_layout.setBackgroundResource(R.drawable.tab_layout_background);
                radio_tab_layout.setBackgroundResource(R.drawable.tab_layout_background);
                photos_tab_layout.setBackgroundResource(R.drawable.tab_layout_background);
                tv_tab_layout.setBackgroundResource(R.drawable.tab_layout_background);

                //StramPT
                StreamPT.setVisibility(View.GONE);

                // TVPT
                TVPT_layout.setVisibility(View.GONE);

                //AboutPT
                AboutPT.setVisibility(View.VISIBLE);

                //SPT
                radio_image_view_SPT.setVisibility(View.GONE);
                black_spread_SPT.setVisibility(View.GONE);
                white_spread_SPT.setVisibility(View.GONE);
                headline2_SPT.setVisibility(View.GONE);
                tv_save_change_button_SPT.setVisibility(View.GONE);
                userprofile_SPT.setVisibility(View.GONE);
                logo_SPT.setVisibility(View.GONE);
                username_textview_SPT.setVisibility(View.GONE);
                stream_name_textview_SPT.setVisibility(View.GONE);
                headline_SPT.setVisibility(View.GONE);
                mail_SPT.setVisibility(View.GONE);

                //Adapter
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(TempActivity.this, LinearLayoutManager.VERTICAL,false);
                sound_type_rcv.setLayoutManager(linearLayoutManager);
                SoundPostAdapter soundPostAdapter = new SoundPostAdapter();
                sound_type_rcv.setAdapter(soundPostAdapter);


            }
        });

    }

    private void intis() {
        // SPT
        radio_image_view_SPT = findViewById(R.id.radio_image_view_SPT);
        black_spread_SPT = findViewById(R.id.black_spread_SPT);
        white_spread_SPT = findViewById(R.id.white_spread_SPT);
        headline2_SPT = findViewById(R.id.headline2_SPT);
        tv_save_change_button_SPT = findViewById(R.id.tv_save_change_button_SPT);
        userprofile_SPT = findViewById(R.id.userprofile_SPT);
        logo_SPT = findViewById(R.id.logo_SPT);
        username_textview_SPT = findViewById(R.id.username_textview_SPT);
        stream_name_textview_SPT = findViewById(R.id.stream_name_textview_SPT);
        headline_SPT = findViewById(R.id.headline_SPT);
        mail_SPT = findViewById(R.id.mail_SPT);

        // TVPT
        TVPT_layout = findViewById(R.id.TVPT_layout);

        //StreamPT
        StreamPT = findViewById(R.id.StreamPT);

        //AboutPT
        AboutPT = findViewById(R.id.AboutPT);



        // General
        sound_type_rcv = findViewById(R.id.sound_type_rcv);
        about_tab_layout = findViewById(R.id.about_tab_layout);
        photos_tab_layout = findViewById(R.id.photos_tab_layout);
        radio_tab_layout = findViewById(R.id.radio_tab_layout);
        tv_tab_layout = findViewById(R.id.tv_tab_layout);
        stream_tab_layout = findViewById(R.id.stream_tab_layout);
    }
}