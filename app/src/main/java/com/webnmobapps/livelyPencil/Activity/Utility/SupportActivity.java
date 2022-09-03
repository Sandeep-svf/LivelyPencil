package com.webnmobapps.livelyPencil.Activity.Utility;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.webnmobapps.livelyPencil.Activity.ImportDocHelperClass.ImagesActivity;
import com.webnmobapps.livelyPencil.Activity.JoinUs.NameEmailActivity;
import com.webnmobapps.livelyPencil.Model.SmFlaxibleModel;
import com.webnmobapps.livelyPencil.R;
import com.webnmobapps.livelyPencil.RetrofitApi.API_Client;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SupportActivity extends AppCompatActivity {

    private LinearLayout lnrImages;
    private ArrayList<String> imagesPathList;
    private ArrayList<File> Temp_test = new ArrayList<>();
    private ArrayList<File> Temp_test2 = new ArrayList<>();
    private Bitmap yourbitmap;
    LinearLayout attach_file;
    private int attach_file_code = 2014;
    AppCompatButton send_supprt_button;
    AppCompatEditText name_surname_edittext, user_phone_email_edit_text3,user_phone_email_edit_text2 ;
    private final int PICK_IMAGE_MULTIPLE =1;
    private String nameSurnameData, emailData , aboutData;
    AlertDialog dialogs;
    private String regexEmail = "(?:[A-Za-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[A-Za-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[A-Za-z0-9](?:[A-Za-z0-9-]*[A-Za-z0-9])?\\.)+[A-Za-z0-9](?:[A-Za-z0-9-]*[A-Za-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[A-Za-z0-9]:(?:|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suppport3);

        inits();

        send_supprt_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                get_form_data();
                if(validation())
                {
                    support_form_api();
                }

            }
        });

        attach_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // open file manager for select pictures
               /* Intent intent = new Intent(SupportActivity.this, ImagesActivity.class);
                startActivity(intent);*/
                Intent intent = new Intent(SupportActivity.this, ImagesActivity.class);
                intent.putExtra("image_activity", true);
                startActivityForResult(intent,1);

//                String[] mimeTypes = {"image/*"};
//
//                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT).setType("*/*")
//                        .putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
//                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
//                startActivityForResult(intent,PICK_IMAGE_MULTIPLE);
               }
        });

    }

    private void get_form_data() {
        try {
            nameSurnameData = name_surname_edittext.getText().toString();
            String mysz2 = user_phone_email_edit_text2.getText().toString();
            emailData = mysz2.replaceAll("\\s","");
            aboutData = user_phone_email_edit_text3.getText().toString();
        } catch ( NullPointerException e)
        {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean validation() {

        if(nameSurnameData.equals(""))
        {
            alert_dialog_message("1");
            return  false;
        }else if(emailData.equals(""))
        {
            alert_dialog_message("2");
            return  false;
        }else if(!emailData.matches(regexEmail))
        {
            alert_dialog_message("5");
            return  false;
        }else if(aboutData.equals(""))
        {
            alert_dialog_message("3");
            return  false;
        }else if(Temp_test2.size() == 0)
        {
            alert_dialog_message("4");
            return  false;
        }else if(Temp_test2.size() > 5)
        {
            alert_dialog_message("6");
            return  false;
        }

        return true;
    }


    private void support_form_api() {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());


        RequestBody nameSurnameDataRB = RequestBody.create(MediaType.parse("text/plain"), nameSurnameData);
        RequestBody emailDataRB = RequestBody.create(MediaType.parse("text/plain"), emailData);
        RequestBody aboutDataRB = RequestBody.create(MediaType.parse("text/plain"), aboutData);


        MultipartBody.Part[] helpreportImagesParts = new MultipartBody.Part[Temp_test2.size()];
        for (int index = 0; index < Temp_test2.size(); index++) {
//            Log.d("chathelpscreenshots", "requestUploadSurvey:selectedImageList2 help report image " + index + "  " + Image_list.get(index).getPath());

            try {

//                String filename=Temp_test.get(index).getName();

                RequestBody reportBody = RequestBody.create(MediaType.parse("*/*"), Temp_test2.get(index));
                helpreportImagesParts[index] = MultipartBody.Part.createFormData("image[]", Temp_test2.get(index).getName(), reportBody);
                Log.e("image_path_screening","Temp_test2.get(index)***"+Temp_test2.get(index));
            } catch (Exception e) {
                Log.e("image_path_screening","e3***"+e);
//                Toast.makeText(this, "" + e, Toast.LENGTH_SHORT).show();
            }
        }

        // show till load api data
        final ProgressDialog pd = new ProgressDialog(SupportActivity.this);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();

        // calling API
        Call<SmFlaxibleModel> call = API_Client.getClient().support_form(nameSurnameDataRB,
                emailDataRB,
                aboutDataRB,
                helpreportImagesParts);
        call.enqueue(new Callback<SmFlaxibleModel>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<SmFlaxibleModel> call, Response<SmFlaxibleModel> response) {
                pd.dismiss();


                try {
                    //if api response is successful ,taking message and success
                    if (response.isSuccessful()) {
                        String success = String.valueOf(response.body().getSuccess());
                        String   message = response.body().getMessage();

                        // if sucess is true , take all data in to list and set adapter
                        if (success.equals("true") || success.equals("True"))

                        {
                            Temp_test.clear();
                            Temp_test2.clear();

                            Toast.makeText(getApplicationContext(),"Form Submitted Successfully",Toast.LENGTH_SHORT).show();
                            // onResume();
                            Intent intent = new Intent(SupportActivity.this, SupportSubmitFormActivity.class);
                            startActivity(intent);
                        }else {
                            Toast.makeText(getApplicationContext(), message+" "+success, Toast.LENGTH_LONG).show();
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
                            Log.e("image_path_screening","e2***"+e);
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (
                        Exception e) {
                    Log.e("image_path_screening","e***"+e);
                }
            }

            @Override
            public void onFailure(Call<SmFlaxibleModel> call, Throwable t) {
                Log.e("image_path_screening","Throwable***"+t);
                if (t instanceof IOException) {
                    Toast.makeText(getApplicationContext(), "This is an actual network failure :( inform the user and possibly retry)"+t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("dhdj",t.getMessage());
                    pd.dismiss();
                } else {
                    Log.e("conversion issue", t.getMessage());
                    Toast.makeText(getApplicationContext(), "Please Check your Internet Connection...."+t.getMessage(), Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK || resultCode == 0) {
            if(requestCode == 1){

                Temp_test2.clear();

                Temp_test =ImagesActivity.Temp_file_list;
                Log.e("image_path_screening2",""+Temp_test.size());

                for(int i=0; i<Temp_test.size();i++)
                {

                    Temp_test2.add(Temp_test.get(i));
                    //Sending_list.add(getImagePath(Uri.fromFile(Temp_test.get(i))));
                    Log.e("image_path_screening","******"+Temp_test2.get(i));
                }
            }
        }

    }

    private void inits() {
        name_surname_edittext = findViewById(R.id.name_surname_edittext);
        attach_file = findViewById(R.id.attach_file);
        user_phone_email_edit_text2 = findViewById(R.id.user_phone_email_edit_text2);
        user_phone_email_edit_text3 = findViewById(R.id.user_phone_email_edit_text3);
        send_supprt_button = findViewById(R.id.send_supprt_button);
    }

    private void alert_dialog_message(String value) {

        final LayoutInflater inflater = SupportActivity.this.getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.test_dialog_xml_otp, null);

        final ImageView close_dialog = alertLayout.findViewById(R.id.close_dialog);
        final TextView error_message = alertLayout.findViewById(R.id.error_message);


        if(value.equals("1"))
        {
            error_message.setText(getResources().getString(R.string.please_enter_name));
        }else if(value.equals("2"))
        {
            error_message.setText(getResources().getString(R.string.please_enter_email));
        }else if(value.equals("3"))
        {
            error_message.setText(getResources().getString(R.string.please_enter_about));
        }else if(value.equals("4"))
        {
            error_message.setText(getResources().getString(R.string.please_attach_file));
        }else if(value.equals("5"))
        {
            error_message.setText(getResources().getString(R.string.please_enter_valid_email));
        }else if(value.equals("6"))
        {
            error_message.setText(getResources().getString(R.string.please_select_max_five_image_only));
        }



        final AlertDialog.Builder alert = new AlertDialog.Builder(SupportActivity.this);

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
}