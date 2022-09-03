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

import com.webnmobapps.livelyPencil.Activity.Interface.RefreshInterface;
import com.webnmobapps.livelyPencil.Activity.JoinUs.SelectIntrestActivity;
import com.webnmobapps.livelyPencil.Activity.UserWall.HomeActivity;
import com.webnmobapps.livelyPencil.Adapter.LiveUserAdapter;
import com.webnmobapps.livelyPencil.Adapter.PopularListRunWizardAdapter;
import com.webnmobapps.livelyPencil.Model.PopularListModel;
import com.webnmobapps.livelyPencil.Model.Record.PopularListResult;
import com.webnmobapps.livelyPencil.Model.RegisterModel;
import com.webnmobapps.livelyPencil.R;
import com.webnmobapps.livelyPencil.RetrofitApi.API_Client;
import com.webnmobapps.livelyPencil.StaticModel.LiveUserModel;

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

public class PopularListActivity extends AppCompatActivity {


    AppCompatButton popular_list_start_button;
    RecyclerView rcv_popular_list;
    PopularListRunWizardAdapter popularListRunWizardAdapter;
    List<PopularListRunWizardModel> liveUserModelList = new ArrayList<>();
    ConstraintLayout cp;
    List<PopularListResult> popularListResultList = new ArrayList<>();
    private String user_id;
    RefreshInterface refreshInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular_list);

        //refreshInterface = this;
        inits();


        SharedPreferences sharedPreferences= getSharedPreferences("AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
        user_id=sharedPreferences.getString("UserID","");

        Log.e("dsafsad","user_id: "+user_id);
       // add_data_in_model();

        popular_list_api();

        popular_list_start_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PopularListActivity.this, HomeActivity.class);
                startActivity(intent);
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

    private void popular_list_api() {
        Log.e("dsafsad","API Calling ...........");
            final ProgressDialog pd = new ProgressDialog(PopularListActivity.this);
            pd.setCancelable(false);
            pd.setMessage("loading...");
            pd.show();

            Call<PopularListModel> call = API_Client.getClient2().POPULAR_LIST_MODEL_CALL("47");

            call.enqueue(new Callback<PopularListModel>() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onResponse(Call<PopularListModel> call, Response<PopularListModel> response) {
                    pd.dismiss();


                    try {
                        if (response.isSuccessful()) {
                            String message = response.body().getMessage();
                            String success = response.body().getSuccess();

                            if (success.equals("true") || success.equals("True")) {

                                popularListResultList = response.body().getPopularListResult();
                                Log.e("dsafsad", String.valueOf(popularListResultList.size()+"ok"));

                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(PopularListActivity.this);
                                linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
                                rcv_popular_list.setLayoutManager(linearLayoutManager);
                                popularListRunWizardAdapter = new PopularListRunWizardAdapter(popularListResultList,PopularListActivity.this, user_id, refreshInterface);
                                rcv_popular_list.setAdapter(popularListRunWizardAdapter);



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
                public void onFailure(Call<PopularListModel> call, Throwable t) {
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

    private void add_data_in_model() {
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

    }


    private void inits() {
        rcv_popular_list = findViewById(R.id.rcv_popular_list);
        cp = findViewById(R.id.cp);
        popular_list_start_button = findViewById(R.id.popular_list_start_button);
    }



}