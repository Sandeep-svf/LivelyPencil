package com.webnmobapps.livelyPencil.Activity.JoinUs;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.webnmobapps.livelyPencil.Activity.Login.LoginActivity;
import com.webnmobapps.livelyPencil.Activity.Login.LoginJoinusActivity;
import com.webnmobapps.livelyPencil.Activity.RunWizard.PopularListActivity;
import com.webnmobapps.livelyPencil.Adapter.SelectIntrestAdapter;
import com.webnmobapps.livelyPencil.Model.IntrestListModel;
import com.webnmobapps.livelyPencil.Model.Record.IntrestListModelRecord;
import com.webnmobapps.livelyPencil.Model.RegisterModel;
import com.webnmobapps.livelyPencil.ModelPython.InterestingListPython;
import com.webnmobapps.livelyPencil.ModelPython.InterestingModelPython;
import com.webnmobapps.livelyPencil.ModelPython.LoginModelPython;
import com.webnmobapps.livelyPencil.ModelPython.TokenPython;
import com.webnmobapps.livelyPencil.R;
import com.webnmobapps.livelyPencil.RetrofitApi.API_Client;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectIntrestActivity extends AppCompatActivity implements  SelectIntrestAdapter.Get_Position_Eye_Function {

    SwitchMaterial Peoples, Family, Friends, Talk, Food, Science, Political, Travel, Music, Movie, News, Womens, Children, Local, Male, Health, Business, Economy, Education;
    AppCompatButton confirm_button;
    private String intrestUserData = "";
    SelectIntrestAdapter selectIntrestAdapter;
    List<IntrestListModelRecord> IntrtestListData = new ArrayList<>();
    List<InterestingListPython> interestingListPythonArrayList = new ArrayList<>();
    RecyclerView rcv_intrest;
    private String device_token;
    AlertDialog dialogs;

    List<Integer> list = new ArrayList<Integer>();
    ArrayList<String> addIntrestIdArrayList = new ArrayList();
    List<Integer> list12345 = new ArrayList<>();
    private String selectedIntrestId = "";
    private String finalSelectedInrestedId = "";
    private String selectedIntrestId2 = "";
    private String finalSelectedIntrestId = "";
    private String temp_s = "";
    private int count;
    private String userName, usersurName, userEmail,userPhone,key,
            userPasswordData, cameraGalleryimageURI,
            usreStreamPageCoverImage, streamPagePrivacy, userStreamNameData,countryCode;
    private File usrProfieImageFile, userStreamCoverImageFile ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_intrest);
        inits();


        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("ritik", "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        device_token = task.getResult().getToken();
                        Log.e("tokenn",""+device_token);
                    }
                });


        GridLayoutManager linearLayoutManager2 = new GridLayoutManager(SelectIntrestActivity.this, 2);
        linearLayoutManager2.setOrientation(RecyclerView.VERTICAL);
        rcv_intrest.setLayoutManager(linearLayoutManager2);

        selectIntrestListApi();


        //geting userID data
        SharedPreferences getUserIdData = null;
        try {
            getUserIdData = getSharedPreferences("AUTHENTICATION_FILE_NAME", MODE_PRIVATE);
            userName = getUserIdData.getString("userName", "");
            usersurName = getUserIdData.getString("usersurName", "");
            userEmail = getUserIdData.getString("userEmail", "");
            //  userPhone = getUserIdData.getString("userPhone", "");
            userPasswordData = getUserIdData.getString("userPasswordData", "");
            cameraGalleryimageURI = getUserIdData.getString("cameraGalleryimageURI", "");
            usreStreamPageCoverImage = getUserIdData.getString("usreStreamPageCoverImage", "");
            streamPagePrivacy = getUserIdData.getString("streamPagePrivacy", "");
            userStreamNameData = getUserIdData.getString("userStreamNameData", "");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        //   countryCode=getUserIdData.getString("countryCode","");
     //   key=getUserIdData.getString("key","");

     //   Log.e("DATA",""+countryCode);
        Log.e("DATA","userName: "+userName);
        Log.e("DATA","usersurName: "+usersurName);
        Log.e("DATA","userEmail: "+userEmail);
       // Log.e("DATA","userPhone: "+userPhone);
        Log.e("DATA","userPasswordData: "+userPasswordData);
        Log.e("DATA","cameraGalleryimageURI: "+cameraGalleryimageURI);
        Log.e("DATA","usreStreamPageCoverImage: "+usreStreamPageCoverImage);
        Log.e("DATA","streamPagePrivacy: "+streamPagePrivacy);
        Log.e("DATA","userStreamNameData: "+userStreamNameData);


        confirm_button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
              //  userEmail = getUserIdData.getString("userEmail", "");

                Log.e("sdfsdf","button clicked");
              /*  String comma = selectedIntrestId.substring(0,1);
                if(comma.equals(","))
                {
                    selectedIntrestId = selectedIntrestId.substring(1, selectedIntrestId.length());
                    Log.e("fdsfdsfsdfsdf",  selectedIntrestId);
                }else
                {

                    Log.e("fdsfdsfsdfsdf",  selectedIntrestId);
                }*/
                getProfileImage();
                getStreamCoverImage();




              /*  Intent intent = new Intent(SelectIntrestActivity.this, PopularListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // To clean up all activities
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra("finish", true);
                startActivity(intent);*/

                // register();
                // converting Stirng in to List <Integer>


                try {

                    Log.e("sdfsdf","String length is: "+selectedIntrestId.length());
                    Log.e("sdfsdf","String is: "+selectedIntrestId);

                     list12345 = Arrays.stream(selectedIntrestId.split("\\s"))
                            .map(Integer::parseInt)
                            .collect(Collectors.toList());

                    Scanner scanner = new Scanner(selectedIntrestId);
                    while (scanner.hasNextInt()) {
                        list.add(scanner.nextInt());
                    }
                    Log.e("ScannerTest","list size is: "+String.valueOf(list.size()));


                    for(int i=0; i < list.size();i++){
                        Log.e("ScannerTest","list is: "+String.valueOf(list.get(i)));
                    }


           /*         Log.e("fdsjlkfjdslk","Inside try block");


                    List<RequestBody> list = null;
                    
                    String[] s = selectedIntrestId.split("\\s+");
                    List<Integer> selectedIntrestIdList = new ArrayList<>();
                    // List<Integer> selectedIntrestIdList = new List<Integer>();
                    for(int index = 0 ; index < s.length ; index++) {
                        selectedIntrestIdList.add(Integer.parseInt(s[index]));
                    }
                    *//*Log.e("printFinalId", String.valueOf(s.length));
                    Log.e("printFinalId", String.valueOf(selectedIntrestIdList.size()));
                    for(int i=0; i<selectedIntrestIdList.size() ;i++){
                        Log.e("printFinalId", String.valueOf(selectedIntrestIdList));
                    }*//*

                    Log.e("fdsjlkfjdslk","string value is: "+selectedIntrestId);
                    Log.e("fdsjlkfjdslk","string length is: "+selectedIntrestId.length());
                    Log.e("fdsjlkfjdslk","String List size is: "+s.length);
                    Log.e("fdsjlkfjdslk","Int List size is: "+selectedIntrestIdList.size());

                    Log.e("fdsjlkfjdslk","pos:  is :"+String.valueOf(s.toString()));

                    for(int i =0; i<selectedIntrestIdList.size();i++){
                        Log.e("fdsjlkfjdslk","pos: "+i+" is :"+String.valueOf(selectedIntrestIdList.get(i)));
                    }

                    finalSelectedInrestedId = "["+selectedIntrestId+"]";*/
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    Log.e("sdfsdf","Inside catch block");
                    //Toast.makeText(SelectIntrestActivity.this, e, Toast.LENGTH_SHORT).show();
                }finally {
                    //Toast.makeText(SelectIntrestActivity.this, "Something went wrong while converting Stirng in to List<Integer>.", Toast.LENGTH_SHORT).show();
                }


                if(validation()){
                   new_registration_api();
                }

                Log.e("finalSelectedInrestedId",finalSelectedInrestedId);
            }
        });

    }

    private boolean validation() {
        return true;
    }


    private void new_registration_api() {


            final ProgressDialog pd = new ProgressDialog(SelectIntrestActivity.this);
            pd.setCancelable(false);
            pd.setMessage("loading...");
            pd.show();


            String userEmailPhone = "";
            MultipartBody.Part usrProfieImageFileBody , userStreamCoverImageFileBody;

    //   RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), usrProfieImageFile);
       // usrProfieImageFileBody = MultipartBody.Part.createFormData("streamcoverimage", usrProfieImageFile.getName(), requestFile);
        Log.e("Photo", usrProfieImageFile.getName());


            userStreamCoverImageFileBody = MultipartBody.Part.createFormData("stream_cover_image", userStreamCoverImageFile.getName(), RequestBody.create(MediaType.parse("image/*"), userStreamCoverImageFile));

            usrProfieImageFileBody = MultipartBody.Part.createFormData("image", usrProfieImageFile.getName(), RequestBody.create(MediaType.parse("image/*"), usrProfieImageFile));

      //RequestBody requestFile2 = RequestBody.create(MediaType.parse("image/*"), userStreamCoverImageFile);
       // userStreamCoverImageFileBody = MultipartBody.Part.createFormData("image", userStreamCoverImageFile.getName(), requestFile2);
        Log.e("Photo", userStreamCoverImageFile.getName());

          /*  if(key.equals("1"))
            {
                userEmailPhone = userEmail;
            }else if(key.equals("2"))
            {
                userEmailPhone = userPhone;
            }*/


        try {
            Log.e("DATA","userName: "+userName);
            Log.e("DATA","usersurName: "+usersurName);
            Log.e("DATA","userEmail: "+userEmail);
            Log.e("DATA","userPasswordData: "+userPasswordData);
            Log.e("DATA","cameraGalleryimageURI: "+cameraGalleryimageURI);
            Log.e("DATA","usreStreamPageCoverImage: "+usreStreamPageCoverImage);
            Log.e("DATA","streamPagePrivacy: "+streamPagePrivacy);
            Log.e("DATA","userStreamNameData: "+userStreamNameData);
            Log.e("DATA","selectedIntrestId: "+finalSelectedInrestedId);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        List<Integer> intrestingList = new ArrayList<>();
        intrestingList.add(1);
        intrestingList.add(3);
        intrestingList.add(2);

    /*    for(int i=0; i<list12345.size();i++){
            intrestingList.add(list12345.get(i));
            Log.e("sdfsdf", "Final is is: "+String.valueOf(intrestingList.get(i)));
            Log.e("sdfsdf", "Final is is: "+String.valueOf(intrestingList.size()));
        }*/

        //   RequestBody tokenRB = RequestBody.create(MediaType.parse("text/plain"), device_token);
            RequestBody userNameRB = RequestBody.create(MediaType.parse("text/plain"), userName);
            RequestBody usersurNameRB = RequestBody.create(MediaType.parse("text/plain"), usersurName);
            RequestBody userEmailRB = RequestBody.create(MediaType.parse("text/plain"), userEmail);
           // RequestBody intrestingListRB = RequestBody.create(MediaType.parse("text/plain"), intrestingList);
            RequestBody userPasswordDataRB = RequestBody.create(MediaType.parse("text/plain"), userPasswordData);
            RequestBody streamPagePrivacyRB = RequestBody.create(MediaType.parse("text/plain"), streamPagePrivacy);
            RequestBody userStreamNameDataRB = RequestBody.create(MediaType.parse("text/plain"), userStreamNameData);
            RequestBody selectedIntrestIdRB = RequestBody.create(MediaType.parse("text/plain"), finalSelectedInrestedId);
          //  RequestBody userMobileNumberRB = RequestBody.create(MediaType.parse("text/plain"), userEmailPhone);
           // RequestBody countryCodeRB = RequestBody.create(MediaType.parse("text/plain"), countryCode);


            Call<LoginModelPython> call = API_Client.getClient().register2(intrestingList,
                    userNameRB,
                    usersurNameRB,
                    userEmailRB,
                    userStreamNameDataRB,
                    streamPagePrivacyRB,
                    userPasswordDataRB,
                    userStreamCoverImageFileBody,
                    usrProfieImageFileBody);

            call.enqueue(new Callback<LoginModelPython>() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onResponse(Call<LoginModelPython> call, Response<LoginModelPython> response) {
                    pd.dismiss();


                    try {
                        if (response.isSuccessful()) {
                            String message = response.body().getMessage();
                            String success = response.body().getStatus();


                            if (success.equals("true") || success.equals("True")) {

                                LoginModelPython registerModel = response.body();
                                TokenPython tokenPython = registerModel.getToken();
                                String accessToken = String.valueOf(tokenPython.getAccess());

                                SharedPreferences getUserIdData = getSharedPreferences("AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = getUserIdData.edit();
                                editor.putString("accessToken", String.valueOf(accessToken));
                                editor.apply();

                                Intent intent = new Intent(SelectIntrestActivity.this, PopularListActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // To clean up all activities
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.putExtra("finish", true);
                                startActivity(intent);
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
                public void onFailure(Call<LoginModelPython> call, Throwable t) {
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

    private void getProfileImage() {

        try {
            // You can update this bitmap to your server
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(cameraGalleryimageURI));

            // loading profile image from local cache
            //loadProfile(uri.toString());
            usrProfieImageFile = new File( Uri.parse(cameraGalleryimageURI).getPath());
            Log.e("file ", "path " + usrProfieImageFile.getAbsolutePath());

            Uri uu = Uri.fromFile(usrProfieImageFile);
            Log.e("image_poath","uu: "+ String.valueOf(uu));
           /* Glide.with(this).load(uu)
                    .into(uesrProfileImage);*/

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void getStreamCoverImage ()
    {
        try {
            // You can update this bitmap to your server
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(usreStreamPageCoverImage));

            // loading profile image from local cache
            //loadProfile(uri.toString());
            userStreamCoverImageFile = new File(Uri.parse(usreStreamPageCoverImage).getPath());
            Log.e("file ", "path " + userStreamCoverImageFile.getAbsolutePath());

            Uri uu = Uri.fromFile(userStreamCoverImageFile);
         /*   Glide.with(this).load(uu)
                    .into(stream_user_image);*/


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void inits() {

        confirm_button = findViewById(R.id.confirm_button);
        rcv_intrest = findViewById(R.id.rcv_intrest);
    }

    private void selectIntrestListApi() {

        // show till load api data

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();

        Call<InterestingModelPython> call = API_Client.getClient().IntrestList2();

        call.enqueue(new Callback<InterestingModelPython>() {
            @Override
            public void onResponse(Call<InterestingModelPython> call, Response<InterestingModelPython> response) {
                pd.dismiss();
                try {
                    //if api response is successful ,taking message and success
                    if (response.isSuccessful()) {
                        String message = response.body().getMessage();
                        String success = String.valueOf(response.body().getStatus());

                        if (success.equals("true") || success.equals("True")) {

                            interestingListPythonArrayList = response.body().getData();

                            Log.e("soze","size: "+interestingListPythonArrayList.size());

                            for(int i = 0; i> interestingListPythonArrayList.size(); i++)
                            {
                                InterestingListPython intrestListModelRecord = new InterestingListPython();
                                intrestListModelRecord.setCheckBoolean(false);
                                interestingListPythonArrayList.add(intrestListModelRecord);

                            }

                            selectIntrestAdapter = new SelectIntrestAdapter(interestingListPythonArrayList, SelectIntrestActivity.this);
                            rcv_intrest.setAdapter(selectIntrestAdapter);
                            selectIntrestAdapter.setGet_position_itemDrawings(SelectIntrestActivity.this);



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
            public void onFailure(Call<InterestingModelPython> call, Throwable t) {
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

        final LayoutInflater inflater = SelectIntrestActivity.this.getLayoutInflater();
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



        final AlertDialog.Builder alert = new AlertDialog.Builder(SelectIntrestActivity.this);

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


    @Override
    public void page_details(int position, String id) {

        /*Toast.makeText(getApplicationContext(), String.valueOf(position), Toast.LENGTH_SHORT).show();
        Toast.makeText(getApplicationContext(), id, Toast.LENGTH_SHORT).show();*/

        /*AddIntrestId addIntrestId = new AddIntrestId();
        addIntrestId.setId(Integer.valueOf(id));
        addIntrestId.setPosition(position);*/



      /*  if(position == addIntrestIdArrayList.get(position).getPosition())
        {
            Log.e("dkfjkdf","Dont't add");
        }else
        {
            Log.e("dkfjkdf","Add");
            addIntrestIdArrayList.add(addIntrestId);
        }*/
        addIntrestIdArrayList.add(id);
        // selectedIntrestId = selectedIntrestId +","+id;
        selectedIntrestId = selectedIntrestId +id;
        Log.e("fdfdfde"," Add : " + selectedIntrestId);



    }

    @Override
    public void page_details2(int position, String id) {
       // Log.e("fdfdfde"," Removed"+ addIntrestIdArrayList.size());
        selectedIntrestId = "";
       // Log.e("erwerwer"," Before########: " + addIntrestIdArrayList.size());

        addIntrestIdArrayList.remove(id);

        for(int i = 0; i < addIntrestIdArrayList.size(); i++)
        {

            selectedIntrestId = selectedIntrestId + addIntrestIdArrayList.get(i);
        }



       /* for(int i=0; i < addIntrestIdArrayList.size(); i++)
        {
            if(id == addIntrestIdArrayList.get(i))
            {
                Log.e("fdfdfde"," Removed****** :     "+selectedIntrestId);
            }else
            {


                selectedIntrestId = selectedIntrestId + "," + addIntrestIdArrayList.get(i);
                addIntrestIdArrayList.clear();
                addIntrestIdArrayList.add(selectedIntrestId);
                Log.e("fdfdfde"," Removed########: " + selectedIntrestId);

            }
        }*/

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


    private void register() {

        final ProgressDialog pd = new ProgressDialog(SelectIntrestActivity.this);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();


        String userEmailPhone = "";
        MultipartBody.Part usrProfieImageFileBody , userStreamCoverImageFileBody;

     /*   RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), usrProfieImageFile);
        usrProfieImageFileBody = MultipartBody.Part.createFormData("streamcoverimage", usrProfieImageFile.getName(), requestFile);
        Log.e("Photo", usrProfieImageFile.getName());
*/

        userStreamCoverImageFileBody = MultipartBody.Part.createFormData("streamcoverimage", userStreamCoverImageFile.getName(), RequestBody.create(MediaType.parse("image/*"), userStreamCoverImageFile));

        usrProfieImageFileBody = MultipartBody.Part.createFormData("image", usrProfieImageFile.getName(), RequestBody.create(MediaType.parse("image/*"), usrProfieImageFile));

        /*RequestBody requestFile2 = RequestBody.create(MediaType.parse("image/*"), userStreamCoverImageFile);
        userStreamCoverImageFileBody = MultipartBody.Part.createFormData("image", userStreamCoverImageFile.getName(), requestFile2);
        Log.e("Photo", userStreamCoverImageFile.getName());*/

        if(key.equals("1"))
        {
            userEmailPhone = userEmail;
        }else if(key.equals("2"))
        {
            userEmailPhone = userPhone;
        }


        Log.e("DATA","userName: "+userName);
        Log.e("DATA","usersurName: "+usersurName);
        Log.e("DATA","userEmail: "+userEmail);
        Log.e("DATA","userPasswordData: "+userPasswordData);
        Log.e("DATA","cameraGalleryimageURI: "+cameraGalleryimageURI);
        Log.e("DATA","usreStreamPageCoverImage: "+usreStreamPageCoverImage);
        Log.e("DATA","streamPagePrivacy: "+streamPagePrivacy);
        Log.e("DATA","userStreamNameData: "+userStreamNameData);
        Log.e("DATA","selectedIntrestId: "+selectedIntrestId);

        RequestBody tokenRB = RequestBody.create(MediaType.parse("text/plain"), device_token);
        RequestBody userNameRB = RequestBody.create(MediaType.parse("text/plain"), userName);
        RequestBody usersurNameRB = RequestBody.create(MediaType.parse("text/plain"), usersurName);
        RequestBody userPasswordDataRB = RequestBody.create(MediaType.parse("text/plain"), userPasswordData);
        RequestBody streamPagePrivacyRB = RequestBody.create(MediaType.parse("text/plain"), streamPagePrivacy);
        RequestBody userStreamNameDataRB = RequestBody.create(MediaType.parse("text/plain"), userStreamNameData);
        RequestBody selectedIntrestIdRB = RequestBody.create(MediaType.parse("text/plain"), selectedIntrestId);
        RequestBody userMobileNumberRB = RequestBody.create(MediaType.parse("text/plain"), userEmailPhone);
        RequestBody countryCodeRB = RequestBody.create(MediaType.parse("text/plain"), countryCode);


        Call<RegisterModel> call = API_Client.getClient().register(tokenRB,
                userNameRB,
                usersurNameRB,
                userPasswordDataRB,
                userMobileNumberRB,
                userStreamNameDataRB,
                streamPagePrivacyRB,
                selectedIntrestIdRB,
                countryCodeRB,
                userStreamCoverImageFileBody,
                usrProfieImageFileBody);

        call.enqueue(new Callback<RegisterModel>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<RegisterModel> call, Response<RegisterModel> response) {
                pd.dismiss();


                try {
                    if (response.isSuccessful()) {
                        String message = response.body().getMessage();
                        String success = response.body().getSuccess();


                        if (success.equals("true") || success.equals("True")) {

                            RegisterModel registerModel = response.body();
                            String UserID = String.valueOf(registerModel.getRecord());

                            SharedPreferences getUserIdData = getSharedPreferences("AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = getUserIdData.edit();
                            editor.putString("UserID", String.valueOf(UserID));
                            editor.apply();

                            Intent intent = new Intent(SelectIntrestActivity.this, PopularListActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // To clean up all activities
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.putExtra("finish", true);
                            startActivity(intent);
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
            public void onFailure(Call<RegisterModel> call, Throwable t) {
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

}