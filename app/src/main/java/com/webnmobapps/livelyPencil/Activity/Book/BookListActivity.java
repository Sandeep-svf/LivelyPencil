package com.webnmobapps.livelyPencil.Activity.Book;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
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

import com.webnmobapps.livelyPencil.Activity.UserWall.HomeActivity;
import com.webnmobapps.livelyPencil.Adapter.BookListAdapter;
import com.webnmobapps.livelyPencil.ModelPython.BookListData;
import com.webnmobapps.livelyPencil.ModelPython.BookListModel;
import com.webnmobapps.livelyPencil.ModelPython.BookListModel;
import com.webnmobapps.livelyPencil.R;
import com.webnmobapps.livelyPencil.RetrofitApi.API_Client;
import com.webnmobapps.livelyPencil.newmodel.CustomBookListModel;
import com.webnmobapps.livelyPencil.newmodel.StreamModel;
import com.webnmobapps.livelyPencil.newmodel.StreamModelData;
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

public class BookListActivity extends AppCompatActivity {


    RecyclerView rcv_book_list;
    List<BookListData> bookListDataList = new ArrayList<>();
    List<CustomBookListModel> customBookListModelList = new ArrayList<>();

    AlertDialog dialogs;
    private String finalAccessToken,accessToken,user_id;
    ConstraintLayout create_book_icon;
    private CustomBookListModel customBookListModel;
    private AppCompatImageView back_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);
        inits();


        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookListActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        create_book_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookListActivity.this, CreateBookActivity.class);
                startActivity(intent);
            }
        });

        SharedPreferences sharedPreferences= BookListActivity.this.getSharedPreferences("AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
        user_id=sharedPreferences.getString("UserID","");
        accessToken=sharedPreferences.getString("accessToken","");
        finalAccessToken = StaticKey.prefixTokem+accessToken;


        stream_data_api();



    }

    private void stream_data_api() {

            final ProgressDialog pd = new ProgressDialog(BookListActivity.this);
            pd.setCancelable(false);
            pd.setMessage("loading...");
            pd.show();



            Call<StreamModel> call = API_Client.getClient().STREAM_MODEL_CALL(finalAccessToken);

            call.enqueue(new Callback<StreamModel>() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onResponse(Call<StreamModel> call, Response<StreamModel> response) {
                    pd.dismiss();


                    try {
                        if (response.isSuccessful()) {
                            String message = response.body().getMessage();
                            String success = response.body().getStatus();

                            if (success.equals("true") || success.equals("True")) {

                                StreamModel streamModel = response.body();
                                StreamModelData streamModelData = streamModel.getData();

                                customBookListModel = new CustomBookListModel();
                                customBookListModel.setBookName(streamModelData.getStreamTitle());
                                customBookListModel.setBookDescriptions("null");
                                customBookListModel.setBookImage( streamModelData.getStreamCoverImage());
                                customBookListModel.setId( "null");
                                customBookListModelList.add(customBookListModel);

                                Log.e("addbooklisttest","stream data is :");
                                Log.e("addbooklisttest","stream data is :"+streamModelData.getStreamTitle());
                                Log.e("addbooklisttest","stream data is :"+streamModelData.getStreamCoverImage());



                                book_list_api();
                            } else {
                                //  alert_dialog_message("7");
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
                public void onFailure(Call<StreamModel> call, Throwable t) {
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

    private void book_list_api() {
      

            final ProgressDialog pd = new ProgressDialog(BookListActivity.this);
            pd.setCancelable(false);
            pd.setMessage("loading...");
            pd.show();

         

            Call<BookListModel> call = API_Client.getClient().BOOK_LIST_MODEL_CALL(finalAccessToken);

            call.enqueue(new Callback<BookListModel>() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onResponse(Call<BookListModel> call, Response<BookListModel> response) {
                    pd.dismiss();


                    try {
                        if (response.isSuccessful()) {
                            String message = response.body().getMessage();
                            String success = response.body().getStatus();

                            if (success.equals("true") || success.equals("True")) {
                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                                bookListDataList = response.body().getData();

                                Log.e("addbooklisttest","bookListDataListsize Size is :"+bookListDataList.size());

                                for(int i=0; i<bookListDataList.size();i++){
                                    customBookListModel = new CustomBookListModel();
                                    customBookListModel.setBookName(bookListDataList.get(i).getBookName());
                                    customBookListModel.setBookDescriptions(bookListDataList.get(i).getBookDescriptions());
                                    customBookListModel.setBookImage( bookListDataList.get(i).getBookImage());
                                    customBookListModel.setId(String.valueOf(bookListDataList.get(i).getId()));
                                    customBookListModelList.add(customBookListModel);
                                }


                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(BookListActivity.this,RecyclerView.VERTICAL,false);
                                rcv_book_list.setLayoutManager(linearLayoutManager);
                                BookListAdapter bookListAdapter = new BookListAdapter(BookListActivity.this,customBookListModelList,finalAccessToken);
                                rcv_book_list.setAdapter(bookListAdapter);

                            } else {
                                //  alert_dialog_message("7");
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
                public void onFailure(Call<BookListModel> call, Throwable t) {
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

        final LayoutInflater inflater = BookListActivity.this.getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.test_dialog_xml_otp, null);

        final ImageView close_dialog = alertLayout.findViewById(R.id.close_dialog);
        final TextView error_message = alertLayout.findViewById(R.id.error_message);


        if(value.equals("1"))
        {
            error_message.setText(getResources().getString(R.string.please_enter_password));
        }else if(value.equals("2"))
        {
            error_message.setText(getResources().getString(R.string.minimum_password_length));
        }else if(value.equals("3"))
        {
            error_message.setText(getResources().getString(R.string.please_enter_confirm_password));
        }else if(value.equals("8"))
        {
            error_message.setText(getResources().getString(R.string.book_description));
        }else if(value.equals("5"))
        {
            error_message.setText(getResources().getString(R.string.enter_book_name));
        }else if(value.equals("6"))
        {
            error_message.setText(getResources().getString(R.string.select_book_image));
        }




        final AlertDialog.Builder alert = new AlertDialog.Builder(BookListActivity.this);

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
    private void inits() {
        rcv_book_list = findViewById(R.id.rcv_book_list);
        create_book_icon = findViewById(R.id.create_book_icon);
        back_button = findViewById(R.id.back_button);
    }



    @Override
    protected void onResume() {
        super.onResume();
       // book_list_api();
    }
}