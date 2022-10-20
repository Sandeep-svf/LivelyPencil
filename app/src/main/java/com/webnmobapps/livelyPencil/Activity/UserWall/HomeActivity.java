package com.webnmobapps.livelyPencil.Activity.UserWall;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.PopupMenu;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.webnmobapps.livelyPencil.Activity.Book.CreateBookActivity;
import com.webnmobapps.livelyPencil.Activity.JoinUs.SelectIntrestActivity;
import com.webnmobapps.livelyPencil.Activity.Login.LoginActivity;
import com.webnmobapps.livelyPencil.Activity.Login.LoginJoinusActivity;
import com.webnmobapps.livelyPencil.Activity.NewChangePhase.SettingsFragment;
import com.webnmobapps.livelyPencil.Activity.PostType.CreateOfflinePostActivity;
import com.webnmobapps.livelyPencil.Activity.PostType.Fragment.HomePostFragment;
import com.webnmobapps.livelyPencil.Activity.Setting.PersonalInformationActivity;
import com.webnmobapps.livelyPencil.Activity.Setting.SettingsActivity;
import com.webnmobapps.livelyPencil.Activity.Utility.AudioStatus;
import com.webnmobapps.livelyPencil.Activity.Utility.MediaPlayerUtils;
import com.webnmobapps.livelyPencil.Activity.Utility.PrivacyPolicyActivity;
import com.webnmobapps.livelyPencil.Activity.Utility.TermsConditionsActivity;
import com.webnmobapps.livelyPencil.Adapter.SelectIntrestAdapter;
import com.webnmobapps.livelyPencil.Fragment.BottonMenu.FriendFollowersFragment;
import com.webnmobapps.livelyPencil.Fragment.BottonMenu.MessageEmailFragment;
import com.webnmobapps.livelyPencil.Fragment.BottonMenu.NotificationFragment;
import com.webnmobapps.livelyPencil.Fragment.BottonMenu.ProfileFragment;
import com.webnmobapps.livelyPencil.Fragment.BottonMenu.SearchFragment;
import com.webnmobapps.livelyPencil.Fragment.BottonMenu.UserWallFragment;
import com.webnmobapps.livelyPencil.Fragment.TopMenu.GameFragment;
import com.webnmobapps.livelyPencil.Fragment.TopMenu.MarketFragment;
import com.webnmobapps.livelyPencil.Fragment.TopMenu.PageFragment;
import com.webnmobapps.livelyPencil.Fragment.TopMenu.RadioFragment;
import com.webnmobapps.livelyPencil.Fragment.TopMenu.TvFragment;
import com.webnmobapps.livelyPencil.Model.IntrestListModel;
import com.webnmobapps.livelyPencil.Model.NotificationCountModel;
import com.webnmobapps.livelyPencil.Model.Record.IntrestListModelRecord;
import com.webnmobapps.livelyPencil.Model.SmFlaxibleModel;
import com.webnmobapps.livelyPencil.R;
import com.webnmobapps.livelyPencil.RetrofitApi.API_Client;

import org.json.JSONObject;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    AppCompatImageView page_top_icon, tv_top_icon, radio_top_icon, market_top_icon, game_top_icon, three_dot_top_icon;
    AppCompatImageView home_icon, search_icon, friends_followers_icon, messages_mailbox_icon, notification_icon;
    ConstraintLayout fragment_contaner, n_count_layout;
    CircleImageView profile_icon;
    private String user_id,accessToken;
    private int notificationCount;
    AppCompatTextView notification_count;
    AppCompatImageView contant_icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_wall);

        inits();

        SharedPreferences sharedPreferences= getSharedPreferences("AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
        user_id=sharedPreferences.getString("UserID","");
        accessToken=sharedPreferences.getString("accessToken","");

        // API
        notificaiton_count_api();
        message_count_api();
        followers_count_api();

        // Default fragment
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contaner, new PageFragment()).commit();
        home_icon.setImageResource(R.drawable.home_color);


        contant_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent intent = new Intent(HomeActivity.this, CreateOfflinePostActivity.class);
                startActivity(intent);*/

                HomePostFragment homePostFragment = new HomePostFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                //  ((ConstraintLayout)findViewById(R.id.fragment_contaner)).removeAllViews();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
                fragmentTransaction.replace(R.id.fragment_contaner, homePostFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                relaseMediaPlayer();

                page_top_icon.setImageResource(R.drawable.n_shelf);
                tv_top_icon.setImageResource(R.drawable.n_tv);
                radio_top_icon.setImageResource(R.drawable.n_radio);
                Glide.with(HomeActivity.this).load(R.drawable.menu_button).into(three_dot_top_icon);

            }
        });


        // Top Layout Custom Menu

        page_top_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                PageFragment pageFragment = new PageFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
             //   ((ConstraintLayout)findViewById(R.id.fragment_contaner)).removeAllViews();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
                fragmentTransaction.replace(R.id.fragment_contaner, pageFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                relaseMediaPlayer();


                page_top_icon.setImageResource(R.drawable.n_shelf_color);
                tv_top_icon.setImageResource(R.drawable.n_tv);
                radio_top_icon.setImageResource(R.drawable.n_radio);
                Glide.with(HomeActivity.this).load(R.drawable.menu_button).into(three_dot_top_icon);
            }
        });


       /* tv_top_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TvFragment tvFragment = new TvFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
             //   ((ConstraintLayout)findViewById(R.id.fragment_contaner)).removeAllViews();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
                fragmentTransaction.replace(R.id.fragment_contaner, tvFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                relaseMediaPlayer();
                page_top_icon.setImageResource(R.drawable.n_shelf);
                tv_top_icon.setImageResource(R.drawable.n_tv_color);
                radio_top_icon.setImageResource(R.drawable.n_radio);
                Glide.with(HomeActivity.this).load(R.drawable.menu_button).into(three_dot_top_icon);

            }
        });

        radio_top_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RadioFragment radioFragment = new RadioFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
              //  ((ConstraintLayout)findViewById(R.id.fragment_contaner)).removeAllViews();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
                fragmentTransaction.replace(R.id.fragment_contaner, radioFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                relaseMediaPlayer();

                page_top_icon.setImageResource(R.drawable.n_shelf);
                tv_top_icon.setImageResource(R.drawable.n_tv);
                radio_top_icon.setImageResource(R.drawable.n_radio_color);
                Glide.with(HomeActivity.this).load(R.drawable.menu_button).into(three_dot_top_icon);

            }
        });*/


        three_dot_top_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Glide.with(HomeActivity.this).load(R.drawable.menu_button_color).into(three_dot_top_icon);
                Context wrapper=new ContextThemeWrapper(HomeActivity.this,R.style.PopupMenu_style);
                PopupMenu popupMenu = new PopupMenu(wrapper,three_dot_top_icon);
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch(item.getItemId()) {
                            // Handle the non group menu items here
                            case R.id.create_story:
                                // Set the text color to red
                              //  relaseMediaPlayer();

                                // create book....

                              //  alert_dialog_create_book();
                                Intent intent = new Intent(HomeActivity.this, CreateBookActivity.class);
                                startActivity(intent);



                                return true;
                            case R.id.terms_conditions:
                                // Set the text color to red
                                relaseMediaPlayer();
                                Intent intent3 = new Intent(HomeActivity.this, TermsConditionsActivity.class);
                                startActivity(intent3);
                                return true;

                            case R.id.logout:
                                // Set the text color to red

                                final Dialog dialog = new Dialog(HomeActivity.this);
                                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                dialog.setContentView(R.layout.logout_dialog);
                                LinearLayout noDialogLogout = dialog.findViewById(R.id.noDialogLogout);
                                LinearLayout yesDialogLogout = dialog.findViewById(R.id.yesDialogLogout);


                                dialog.show();
                                Window window = dialog.getWindow();
                                window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                                yesDialogLogout.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        relaseMediaPlayer();
                                        //geting userID data


                                        SharedPreferences getUserIdData = getSharedPreferences("AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = getUserIdData.edit();
                                        editor.putString("UserID", "");


                                        editor.apply();
                                        Intent intent = new Intent(HomeActivity.this, LoginJoinusActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // To clean up all activities
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        intent.putExtra("finish", true);
                                        startActivity(intent);

//                        logout_api();
                                    }

                                });

                                noDialogLogout.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        RadioFragment radioFragment = new RadioFragment();
                                        FragmentManager fragmentManager = getSupportFragmentManager();
                                        //  ((ConstraintLayout)findViewById(R.id.fragment_contaner)).removeAllViews();
                                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                        fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
                                        fragmentTransaction.replace(R.id.fragment_contaner, radioFragment);
                                        fragmentTransaction.addToBackStack(null);
                                        fragmentTransaction.commit();

                                        relaseMediaPlayer();

                                        page_top_icon.setImageResource(R.drawable.n_shelf);
                                        tv_top_icon.setImageResource(R.drawable.n_tv);
                                        radio_top_icon.setImageResource(R.drawable.n_radio_color);
                                        market_top_icon.setImageResource(R.drawable.n_ads);
                                        game_top_icon.setImageResource(R.drawable.n_game);

                                        dialog.dismiss();
                                    }
                                });

                                return true;

                            case R.id.privacy_policies:
                                // Set the text color to red
                                relaseMediaPlayer();
                                Intent intent2 = new Intent(HomeActivity.this, PrivacyPolicyActivity.class);
                                startActivity(intent2);
                                return true;
                            case R.id.Settings:
                                // Set the text color to red
                                relaseMediaPlayer();
                               /* Intent intent = new Intent(HomeActivity.this, SettingsActivity.class);
                                startActivity(intent);*/

                                SettingsFragment settingsFragment = new SettingsFragment();
                                FragmentManager fragmentManager = getSupportFragmentManager();
                                //  ((ConstraintLayout)findViewById(R.id.fragment_contaner)).removeAllViews();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
                                fragmentTransaction.replace(R.id.fragment_contaner, settingsFragment);
                                fragmentTransaction.addToBackStack(null);
                                fragmentTransaction.commit();

                                return true;

                            default:
                                return false;
                        }
                    }
                });
                popupMenu.show();


            }
        });


        // Bottom Layout Custom Menu

        home_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                UserWallFragment userWallFragment = new UserWallFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
              //  ((ConstraintLayout)findViewById(R.id.fragment_contaner)).removeAllViews();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
                fragmentTransaction.replace(R.id.fragment_contaner, userWallFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                /*FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
                transaction.replace(R.id.fragment_contaner, userWallFragment);
                transaction.addToBackStack(null);
                transaction.commit();
*/
                relaseMediaPlayer();

                page_top_icon.setImageResource(R.drawable.n_shelf);
                tv_top_icon.setImageResource(R.drawable.n_tv);
                radio_top_icon.setImageResource(R.drawable.n_radio);
                Glide.with(HomeActivity.this).load(R.drawable.menu_button).into(three_dot_top_icon);

                home_icon.setImageResource(R.drawable.home_color);
                search_icon.setImageResource(R.drawable.search);
                friends_followers_icon.setImageResource(R.drawable.friends_followers);
                messages_mailbox_icon.setImageResource(R.drawable.messages_mailbox);
                notification_icon.setImageResource(R.drawable.notification);

            }
        });

        profile_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ProfileFragment profileFragment = new ProfileFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
              //  ((ConstraintLayout)findViewById(R.id.fragment_contaner)).removeAllViews();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
                fragmentTransaction.replace(R.id.fragment_contaner, profileFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                relaseMediaPlayer();

                page_top_icon.setImageResource(R.drawable.n_shelf);
                tv_top_icon.setImageResource(R.drawable.n_tv);
                radio_top_icon.setImageResource(R.drawable.n_radio);
                Glide.with(HomeActivity.this).load(R.drawable.menu_button).into(three_dot_top_icon);


                home_icon.setImageResource(R.drawable.home);
                search_icon.setImageResource(R.drawable.search);
                friends_followers_icon.setImageResource(R.drawable.friends_followers);
                messages_mailbox_icon.setImageResource(R.drawable.messages_mailbox);
                notification_icon.setImageResource(R.drawable.notification);
            }
        });


        search_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                SearchFragment searchFragment = new SearchFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
               // ((ConstraintLayout)findViewById(R.id.fragment_contaner)).removeAllViews();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
                fragmentTransaction.replace(R.id.fragment_contaner, searchFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                relaseMediaPlayer();

                page_top_icon.setImageResource(R.drawable.n_shelf);
                tv_top_icon.setImageResource(R.drawable.n_tv);
                radio_top_icon.setImageResource(R.drawable.n_radio);
                Glide.with(HomeActivity.this).load(R.drawable.menu_button).into(three_dot_top_icon);


                home_icon.setImageResource(R.drawable.home);
                search_icon.setImageResource(R.drawable.search_color);
                friends_followers_icon.setImageResource(R.drawable.friends_followers);
                messages_mailbox_icon.setImageResource(R.drawable.messages_mailbox);
                notification_icon.setImageResource(R.drawable.notification);
            }
        });


        friends_followers_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FriendFollowersFragment friendFollowersFragment = new FriendFollowersFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
               // ((ConstraintLayout)findViewById(R.id.fragment_contaner)).removeAllViews();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
                fragmentTransaction.replace(R.id.fragment_contaner, friendFollowersFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                relaseMediaPlayer();

                page_top_icon.setImageResource(R.drawable.n_shelf);
                tv_top_icon.setImageResource(R.drawable.n_tv);
                radio_top_icon.setImageResource(R.drawable.n_radio);
                Glide.with(HomeActivity.this).load(R.drawable.menu_button).into(three_dot_top_icon);
                home_icon.setImageResource(R.drawable.home);
                search_icon.setImageResource(R.drawable.search);
                friends_followers_icon.setImageResource(R.drawable.user_color);
                messages_mailbox_icon.setImageResource(R.drawable.messages_mailbox);
                notification_icon.setImageResource(R.drawable.notification);

            }
        });


        messages_mailbox_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MessageEmailFragment messageEmailFragment = new MessageEmailFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
              //  ((ConstraintLayout)findViewById(R.id.fragment_contaner)).removeAllViews();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
                fragmentTransaction.replace(R.id.fragment_contaner, messageEmailFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                relaseMediaPlayer();

                page_top_icon.setImageResource(R.drawable.n_shelf);
                tv_top_icon.setImageResource(R.drawable.n_tv);
                radio_top_icon.setImageResource(R.drawable.n_radio);
                Glide.with(HomeActivity.this).load(R.drawable.menu_button).into(three_dot_top_icon);
                home_icon.setImageResource(R.drawable.home);
                search_icon.setImageResource(R.drawable.search);
                friends_followers_icon.setImageResource(R.drawable.friends_followers);
                messages_mailbox_icon.setImageResource(R.drawable.msg_color);
                notification_icon.setImageResource(R.drawable.notification);

            }
        });

        notification_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                NotificationFragment notificationFragment = new NotificationFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
               // ((ConstraintLayout)findViewById(R.id.fragment_contaner)).removeAllViews();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
                fragmentTransaction.replace(R.id.fragment_contaner, notificationFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                relaseMediaPlayer();
                notification_read_api();

                page_top_icon.setImageResource(R.drawable.n_shelf);
 //               tv_top_icon.setImageResource(R.drawable.n_tv);
   //             radio_top_icon.setImageResource(R.drawable.n_radio);
                Glide.with(HomeActivity.this).load(R.drawable.menu_button).into(three_dot_top_icon);
   //             home_icon.setImageResource(R.drawable.home);
   //             search_icon.setImageResource(R.drawable.search);
                friends_followers_icon.setImageResource(R.drawable.friends_followers);
                messages_mailbox_icon.setImageResource(R.drawable.messages_mailbox);
                notification_icon.setImageResource(R.drawable.noti_color);
            }
        });

    }


    private void alert_dialog_create_book(String value) {

        Dialog dialogs;
        final LayoutInflater inflater = HomeActivity.this.getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.test_dialog_xml_otp, null);

        final ImageView close_dialog = alertLayout.findViewById(R.id.close_dialog);




        final AlertDialog.Builder alert = new AlertDialog.Builder(HomeActivity.this);

        alert.setView(alertLayout);
        alert.setCancelable(false);

        dialogs = alert.create();
        dialogs.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogs.show();
        dialogs.setCanceledOnTouchOutside(true);
        // dialogs.getWindow().setBackgroundDrawableResource(R.color.black_transparent_50);
        // dialogs.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));



        close_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialogs.dismiss();
            }
        });
    }
    private void relaseMediaPlayer() {
        MediaPlayerUtils.releaseMediaPlayer();
    }

    private void notification_read_api() {

        // show till load api data

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();

        Call<SmFlaxibleModel> call = API_Client.getClient().notificationRead(user_id);

        call.enqueue(new Callback<SmFlaxibleModel>() {
            @Override
            public void onResponse(Call<SmFlaxibleModel> call, Response<SmFlaxibleModel> response) {
                pd.dismiss();
                try {
                    //if api response is successful ,taking message and success
                    if (response.isSuccessful()) {
                        String message = response.body().getMessage();
                        String success = String.valueOf(response.body().getSuccess());

                        if (success.equals("true") || success.equals("True")) {
                            n_count_layout.setVisibility(View.GONE);

                        } else {
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                            pd.dismiss();
                        }

                    } else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(getApplicationContext(), jObjError.getString("message"), Toast.LENGTH_LONG).show();
                            switch (response.code()) {
                                case 400:
                                    alert_dialog_message("400");
                                    //  Toast.makeText(getApplicationContext(), "The server did not understand the request.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 401:
                                    alert_dialog_message("401");
                                    // Toast.makeText(getApplicationContext(), "Unauthorized The requested page needs a username and a password.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 404:
                                    alert_dialog_message("404");
                                    //Toast.makeText(getApplicationContext(), "The server can not find the requested page.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 500:
                                    alert_dialog_message("500");
                                    //Toast.makeText(getApplicationContext(), "Internal Server Error..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 503:
                                    alert_dialog_message("503");
                                    // Toast.makeText(getApplicationContext(), "Service Unavailable..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 504:
                                    alert_dialog_message("504");
                                    //  Toast.makeText(getApplicationContext(), "Gateway Timeout..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 511:
                                    alert_dialog_message("511");
                                    // Toast.makeText(getApplicationContext(), "Network Authentication Required ..", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    alert_dialog_message("default");
                                    //Toast.makeText(getApplicationContext(), "unknown error", Toast.LENGTH_SHORT).show();
                                    break;
                            }

                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (
                        Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<SmFlaxibleModel> call, Throwable t) {
                Log.e("conversion issue", t.getMessage());

                if (t instanceof IOException) {
                    Toast.makeText(getApplicationContext(), "This is an actual network failure :( inform the user and possibly retry)", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                } else {
                    Log.e("conversion issue", t.getMessage());
                    Toast.makeText(getApplicationContext(), "Please Check your Internet Connection...." + t.getMessage(), Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            }
        });
    }

    private void alert_dialog_message(String value) {

        AlertDialog dialogs;

        final LayoutInflater inflater = HomeActivity.this.getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.test_dialog_xml_otp, null);

        final ImageView close_dialog = alertLayout.findViewById(R.id.close_dialog);
        final TextView error_message = alertLayout.findViewById(R.id.error_message);

        if(value.equals("400"))
        {
            error_message.setText(getResources().getString(R.string.case_4_0_0));
        }else if(value.equals("401"))
        {
            error_message.setText(getResources().getString(R.string.case_4_0_1));
        }else if(value.equals("404"))
        {
            error_message.setText(getResources().getString(R.string.case_4_0_4));
        }else if(value.equals("500"))
        {
            error_message.setText(getResources().getString(R.string.case_5_0_0));
        }else if(value.equals("503"))
        {
            error_message.setText(getResources().getString(R.string.case_5_0_3));
        }else if(value.equals("504"))
        {
            error_message.setText(getResources().getString(R.string.case_5_0_4));
        }else if(value.equals("511"))
        {
            error_message.setText(getResources().getString(R.string.case_5_1_1));
        }else if(value.equals("504"))
        {
            error_message.setText(getResources().getString(R.string.case_5_0_4));
        }else if(value.equals("default"))
        {
            error_message.setText(getResources().getString(R.string.default_api_error));
        }

        final AlertDialog.Builder alert = new AlertDialog.Builder(HomeActivity.this);

        alert.setView(alertLayout);
        alert.setCancelable(false);

        dialogs = alert.create();
        dialogs.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogs.show();
        dialogs.setCanceledOnTouchOutside(true);


        close_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialogs.dismiss();
            }
        });
    }

    private void message_count_api() {
    }

    private void followers_count_api() {


    }

    private void notificaiton_count_api(){

        // show till load api data

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();

        Call<NotificationCountModel> call = API_Client.getClient().notificationCount(user_id);

        call.enqueue(new Callback<NotificationCountModel>() {
            @Override
            public void onResponse(Call<NotificationCountModel> call, Response<NotificationCountModel> response) {
                pd.dismiss();
                try {
                    //if api response is successful ,taking message and success
                    if (response.isSuccessful()) {
                        String message = response.body().getMessage();
                        String success = String.valueOf(response.body().getSuccess());

                        if (success.equals("true") || success.equals("True")) {

                        NotificationCountModel notificationCountModel = response.body();

                            notificationCount = notificationCountModel.getCount();
                            Log.e("notificationCount", String.valueOf(notificationCount));
                            String temp = String.valueOf(notificationCount);
                           if(notificationCount == 0)
                           {
                               n_count_layout.setVisibility(View.GONE);
                           }else
                           {
                               n_count_layout.setVisibility(View.VISIBLE);
                               notification_count.setText(notificationCount);
                           }


                        } else {
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                            pd.dismiss();
                        }

                    } else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(getApplicationContext(), jObjError.getString("message"), Toast.LENGTH_LONG).show();
                            switch (response.code()) {
                                case 400:
                                    Toast.makeText(getApplicationContext(), "The server did not understand the request.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 401:
                                    Toast.makeText(getApplicationContext(), "Unauthorized The requested page needs a username and a password.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 404:
                                    Toast.makeText(getApplicationContext(), "The server can not find the requested page.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 500:
                                    Toast.makeText(getApplicationContext(), "Internal Server Error..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 503:
                                    Toast.makeText(getApplicationContext(), "Service Unavailable..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 504:
                                    Toast.makeText(getApplicationContext(), "Gateway Timeout..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 511:
                                    Toast.makeText(getApplicationContext(), "Network Authentication Required ..", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(getApplicationContext(), "unknown error", Toast.LENGTH_SHORT).show();
                                    break;
                            }

                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (
                        Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<NotificationCountModel> call, Throwable t) {
                Log.e("conversion issue", t.getMessage());

                if (t instanceof IOException) {
                    Toast.makeText(getApplicationContext(), "This is an actual network failure :( inform the user and possibly retry)", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                } else {
                    Log.e("conversion issue", t.getMessage());
                    Toast.makeText(getApplicationContext(), "Please Check your Internet Connection...." + t.getMessage(), Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            }
        });

    }

    private void inits() {
        contant_icon = findViewById(R.id.contant_icon);
        n_count_layout = findViewById(R.id.n_count_layout);
        notification_count = findViewById(R.id.notification_count);
        page_top_icon = findViewById(R.id.page_top_icon);
        //tv_top_icon = findViewById(R.id.tv_top_icon);
       // radio_top_icon = findViewById(R.id.radio_top_icon);
        three_dot_top_icon = findViewById(R.id.three_dot_top_icon);
        home_icon = findViewById(R.id.home_icon);
        fragment_contaner = findViewById(R.id.fragment_contaner);
        profile_icon = findViewById(R.id.profile_icon);
        search_icon = findViewById(R.id.search_icon);
        friends_followers_icon = findViewById(R.id.friends_followers_icon);
        messages_mailbox_icon = findViewById(R.id.messages_mailbox_icon);
        notification_icon = findViewById(R.id.notification_icon);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // Default fragment
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contaner, new UserWallFragment()).commit();

    }
}