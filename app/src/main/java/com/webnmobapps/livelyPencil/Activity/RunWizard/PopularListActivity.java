package com.webnmobapps.livelyPencil.Activity.RunWizard;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.webnmobapps.livelyPencil.Activity.Book.BookListActivity;
import com.webnmobapps.livelyPencil.Activity.Interface.RefreshInterface;
import com.webnmobapps.livelyPencil.Activity.JoinUs.SelectIntrestActivity;
import com.webnmobapps.livelyPencil.Activity.StaticList.FollowersModel;
import com.webnmobapps.livelyPencil.Activity.UserWall.HomeActivity;
import com.webnmobapps.livelyPencil.Adapter.FollowersModel2;
import com.webnmobapps.livelyPencil.Adapter.LiveUserAdapter;
import com.webnmobapps.livelyPencil.Adapter.PopularListRunWizardAdapter;
import com.webnmobapps.livelyPencil.Model.PopularListModel;
import com.webnmobapps.livelyPencil.Model.Record.PopularListResult;
import com.webnmobapps.livelyPencil.Model.RegisterModel;
import com.webnmobapps.livelyPencil.ModelPython.CommonStatusMessageModelPython;
import com.webnmobapps.livelyPencil.ModelPython.PopularListModelDataNew;
import com.webnmobapps.livelyPencil.ModelPython.PopularListModelNew;
import com.webnmobapps.livelyPencil.R;
import com.webnmobapps.livelyPencil.RetrofitApi.API_Client;
import com.webnmobapps.livelyPencil.StaticModel.LiveUserModel;
import com.webnmobapps.livelyPencil.utility.StaticKey;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PopularListActivity extends AppCompatActivity implements PopularListRunWizardAdapter.Get_Position_Eye_Function {

    List<FollowersModel> followersModelList2 = new ArrayList<>();

    AppCompatButton popular_list_start_button;
    RecyclerView rcv_popular_list;
    PopularListRunWizardAdapter popularListRunWizardAdapter;
    List<PopularListRunWizardModel> liveUserModelList = new ArrayList<>();
    ConstraintLayout cp;
    List<PopularListModelDataNew> popularListModelDataNewList = new ArrayList<>();
    private String user_id,accessToken,finalAccessToken;
    RefreshInterface refreshInterface;
    List<Integer> userIdList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular_list);

        //refreshInterface = this;
        inits();

        followersModelList2.clear();


        try {
            SharedPreferences sharedPreferences= getSharedPreferences("AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
            user_id=sharedPreferences.getString("UserID","");
        } catch (Exception e) {
            e.printStackTrace();
        }

        SharedPreferences sharedPreferences= PopularListActivity.this.getSharedPreferences("AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
        accessToken=sharedPreferences.getString("accessToken","");
        finalAccessToken = StaticKey.prefixTokem+accessToken;

        Log.e("dsafsad","user_id: "+user_id);
       // add_data_in_model();

        popular_list_api();

        popular_list_start_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               // Log.e("test2","list size is: "+followersModelList2.size());

                for(int i=0; i < followersModelList2.size() ; i++ ){
                    String status = String.valueOf(followersModelList2.get(i).getFollowersStatus());

                   // Log.e("test2","status is : "+status);
                    if(status.equals("0")){
                        userIdList.add(followersModelList2.get(i).getUserId());
                      //  Log.e("test2","user id id: "+followersModelList2.get(i).getUserId());
                    }
                }


                Log.e("test2","userIdList size is: "+userIdList.size());
                for(int i=0; i< userIdList.size();i++){
                    Log.e("test2","userIdList item is: "+userIdList.get(i).toString());
                }

                follow_unfollow_api();


            }
        });


        cp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PopularListActivity.this, ChooseLanguageActivity.class);
                startActivity(intent);
            }
        });

    }

    public void follow_unfollow_api(){

            Log.e("dsafsad","API Calling ...........");
            final ProgressDialog pd = new ProgressDialog(PopularListActivity.this);
            pd.setCancelable(false);
            pd.setMessage("loading...");
            pd.show();

            Call<CommonStatusMessageModelPython> call = API_Client.getClient().ADD_FOLLOWERS_COMMON_STATUS_MESSAGE_MODEL_PYTHON_CALL(finalAccessToken,userIdList);

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

                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                                pd.dismiss();
                                Intent intent = new Intent(PopularListActivity.this, HomeActivity.class);
                                startActivity(intent);
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
                public void onFailure(Call<CommonStatusMessageModelPython> call, Throwable t) {
                    Log.e("bhgyrrrthbh", String.valueOf(t));
                    if (t instanceof IOException) {
                        Toast.makeText(getApplicationContext(), "This is an actual network failure :( inform the user and possibly retry)" + t.getMessage(), Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    } else {
                        Log.e("conversion issue", t.getMessage());
                        Toast.makeText(getApplicationContext(), "Please Check your Internet Connection...." + t.getMessage(), Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }
                }
            });
        }

    public void popular_list_api() {
        Log.e("dsafsad","API Calling ...........");
            final ProgressDialog pd = new ProgressDialog(PopularListActivity.this);
            pd.setCancelable(false);
            pd.setMessage("loading...");
            pd.show();

            Call<PopularListModelNew> call = API_Client.getClient().POPULAR_LIST_MODEL_NEW_CALL(finalAccessToken);

            call.enqueue(new Callback<PopularListModelNew>() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onResponse(Call<PopularListModelNew> call, Response<PopularListModelNew> response) {
                    pd.dismiss();


                    try {
                        if (response.isSuccessful()) {
                            String message = response.body().getMessage();
                            String success = response.body().getStatus();

                            if (success.equals("true") || success.equals("True")) {

                                popularListModelDataNewList = response.body().getData();
                                Log.e("dsafsad", String.valueOf(popularListModelDataNewList.size()+"ok"));

                                for(int i=0; i<popularListModelDataNewList.size();i++){
                                    followersModelList2.add(new FollowersModel(1,i,0));
                                }


                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(PopularListActivity.this);
                                linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
                                rcv_popular_list.setLayoutManager(linearLayoutManager);


                                popularListRunWizardAdapter = new PopularListRunWizardAdapter(popularListModelDataNewList,PopularListActivity.this);
                                rcv_popular_list.setAdapter(popularListRunWizardAdapter);
                                popularListRunWizardAdapter.setGet_position_itemDrawings(PopularListActivity.this);



                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

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
                public void onFailure(Call<PopularListModelNew> call, Throwable t) {
                    Log.e("bhgyrrrthbh", String.valueOf(t));
                    if (t instanceof IOException) {
                        Toast.makeText(getApplicationContext(), "This is an actual network failure :( inform the user and possibly retry)" + t.getMessage(), Toast.LENGTH_SHORT).show();
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

        final LayoutInflater inflater = PopularListActivity.this.getLayoutInflater();
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
        }else if(value.equals("400"))
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


        final AlertDialog.Builder alert = new AlertDialog.Builder(PopularListActivity.this);

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

   /* private void add_data_in_model() {
        PopularListRunWizardModel popularListRunWizardModel = new PopularListRunWizardModel();
        popularListRunWizardModel.setUserName("Maria");
        popularListRunWizardModel.setUserSurname("Yerasimos");
        popularListRunWizardModel.setUserImage(R.drawable.demo);
        popularListRunWizardModel.setBackgroundImage(R.drawable.stream_cover_image);
        popularListRunWizardModel.setTitle("Breaking News");
        liveUserModelList.add(popularListRunWizardModel);

        popularListRunWizardModel = new PopularListRunWizardModel();
        popularListRunWizardModel.setUserName("Maria");
        popularListRunWizardModel.setUserSurname("Yerasimos");
        popularListRunWizardModel.setUserImage(R.drawable.test_profile);
        popularListRunWizardModel.setBackgroundImage(R.drawable.breaking_news_bg);
        popularListRunWizardModel.setTitle("Drawing Course");
        liveUserModelList.add(popularListRunWizardModel);


        popularListRunWizardModel = new PopularListRunWizardModel();
        popularListRunWizardModel.setUserName("Maria");
        popularListRunWizardModel.setUserSurname("Yerasimos");
        popularListRunWizardModel.setUserImage(R.drawable.breaking_news_bg);
        popularListRunWizardModel.setBackgroundImage(R.drawable.demo);
        popularListRunWizardModel.setTitle("Breaking News");
        liveUserModelList.add(popularListRunWizardModel);

        popularListRunWizardModel = new PopularListRunWizardModel();
        popularListRunWizardModel.setUserName("Maria");
        popularListRunWizardModel.setUserSurname("Yerasimos");
        popularListRunWizardModel.setUserImage(R.drawable.demo);
        popularListRunWizardModel.setBackgroundImage(R.drawable.stream_cover_image);
        popularListRunWizardModel.setTitle("Breaking News");
        liveUserModelList.add(popularListRunWizardModel);

        popularListRunWizardModel = new PopularListRunWizardModel();
        popularListRunWizardModel.setUserName("Maria");
        popularListRunWizardModel.setUserSurname("Yerasimos");
        popularListRunWizardModel.setUserImage(R.drawable.test_profile);
        popularListRunWizardModel.setBackgroundImage(R.drawable.breaking_news_bg);
        popularListRunWizardModel.setTitle("Drawing Course");
        liveUserModelList.add(popularListRunWizardModel);


        popularListRunWizardModel = new PopularListRunWizardModel();
        popularListRunWizardModel.setUserName("Maria");
        popularListRunWizardModel.setUserSurname("Yerasimos");
        popularListRunWizardModel.setUserImage(R.drawable.breaking_news_bg);
        popularListRunWizardModel.setBackgroundImage(R.drawable.demo);
        popularListRunWizardModel.setTitle("Breaking News");
        liveUserModelList.add(popularListRunWizardModel);

    }*/


    private void inits() {
        rcv_popular_list = findViewById(R.id.rcv_popular_list);
        cp = findViewById(R.id.cp);
        popular_list_start_button = findViewById(R.id.popular_list_start_button);
    }


    @Override
    public void page_details(String id, Integer userId, String status) {
        Integer position = Integer.valueOf(id);



        Log.e("test","CASE 1 : position"+ (position));
        Log.e("test","CASE 2 : userId"+userId);
        Log.e("test","CASE 3 : status"+status);

        //followersModelList2.add(new FollowersModel(0,i,0));

        followersModelList2.get(position).setFollowersStatus(Integer.parseInt(status));
        followersModelList2.get(position).setUserId(userId);


       // followersModelList2.add(new FollowersModel(Integer.parseInt(status),position,userId));


    }
}