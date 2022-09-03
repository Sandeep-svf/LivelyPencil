package com.webnmobapps.livelyPencil.Activity.RunWizard;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tsongkha.spinnerdatepicker.DatePicker;
import com.tsongkha.spinnerdatepicker.DatePickerDialog;
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder;
import com.webnmobapps.livelyPencil.Activity.JoinUs.NameEmailActivity;
import com.webnmobapps.livelyPencil.Activity.JoinUs.SetPasswordActivity;
import com.webnmobapps.livelyPencil.Activity.Setting.PersonalInformationActivity;
import com.webnmobapps.livelyPencil.Activity.Setting.TVChannelSettingsActivity;
import com.webnmobapps.livelyPencil.Activity.Utility.ImagePickerActivity;
import com.webnmobapps.livelyPencil.Activity.Utility.Permission;
import com.webnmobapps.livelyPencil.Model.BusinessModel;
import com.webnmobapps.livelyPencil.Model.CheckUserModel;
import com.webnmobapps.livelyPencil.Model.CityListModel;
import com.webnmobapps.livelyPencil.Model.CountryListModel;
import com.webnmobapps.livelyPencil.Model.Edit_Personal_Information_Model;
import com.webnmobapps.livelyPencil.Model.EducationModel;
import com.webnmobapps.livelyPencil.Model.GenderModel;
import com.webnmobapps.livelyPencil.Model.PersonalInformationSettingWizardModel;
import com.webnmobapps.livelyPencil.Model.Personal_Information_Settings_Model;
import com.webnmobapps.livelyPencil.Model.Record.BusinessResult;
import com.webnmobapps.livelyPencil.Model.Record.CityListResult;
import com.webnmobapps.livelyPencil.Model.Record.CountryListResult;
import com.webnmobapps.livelyPencil.Model.Record.EducationResult;
import com.webnmobapps.livelyPencil.Model.Record.PersonalInformationSettingWizardResult;
import com.webnmobapps.livelyPencil.Model.Record.Personal_Information_Settings_Result;
import com.webnmobapps.livelyPencil.R;
import com.webnmobapps.livelyPencil.RetrofitApi.API_Client;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PersonalInformationWizardActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    CircleImageView male_circle,female_circle,custom_circle, married_circle,single_circle ;
    ConstraintLayout male_layout, female_layout, custom_layout, married_layout, single_layout;
    AppCompatButton personal_info_save_change_button;
    AppCompatEditText name_pi_editText, surname_pi_editText, about_me_pi_editText;
    AppCompatSpinner country_spin, state_spin;
    AppCompatTextView date_spin,month_spin,year_spin ;
    CircleImageView userprofile;

    private String user_id, dayData="", monthData="", yearData="" , firstNameData, surNameData, aboutMeData, birthdayData="", genderData ="", relationshipData="", locationData="";
    List<Personal_Information_Settings_Result> recordList = new ArrayList<>();
    List<CountryListResult> countryList = new ArrayList<>();
    List<String> country_name_list = new ArrayList<>();

    List<CityListResult> cityList = new ArrayList<>();
    List<String> city_name_list = new ArrayList<>();

    final List<String> groupstatus2 = new ArrayList<>();
    private String country_sp_id, city_sp_id, Country="", state="", country_name="", city_name="";

    SimpleDateFormat simpleDateFormat;
    AlertDialog dialogs;
    CircleIndicator Indicator;
    AppCompatTextView edit_profilelayout;
    private static final int REQUEST_IMAGE = 100;
    private static final int MY_CAMERA_REQUEST_CODE = 100;
    private File userProfileImage;
    private String cameraGalleryimageURI;
    private File usrProfieImageFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_information_wizard);


        SharedPreferences sharedPreferences= getSharedPreferences("AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
        user_id=sharedPreferences.getString("UserID","");

        SharedPreferences getUserIdData = getSharedPreferences("AUTHENTICATION_FILE_NAME", MODE_PRIVATE);
        cameraGalleryimageURI = getUserIdData.getString("cameraGalleryimageURI", "");

        intis();
        getProfileImage();

       // personal_information_api();
        country_list_api();

        // Manual Circle Indicatior........
        Indicator = findViewById(R.id.pi_indicator_PW);
        Indicator.createIndicators(3,0);
        Indicator.animatePageSelected(0);

        edit_profilelayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // crop image upload functionally code .............
                Log.e("check","working.......");
                final LayoutInflater inflater = PersonalInformationWizardActivity.this.getLayoutInflater();
                View alertLayout = inflater.inflate(R.layout.test_dialog_xml, null);

                final ImageView close_dialog = alertLayout.findViewById(R.id.close_dialog);
                final ImageView camera_icon = alertLayout.findViewById(R.id.camera_icon);
                final ImageView browse_icon = alertLayout.findViewById(R.id.browse_icon);

                final AlertDialog.Builder alert = new AlertDialog.Builder(PersonalInformationWizardActivity.this);

                alert.setView(alertLayout);
                alert.setCancelable(false);

                dialogs = alert.create();
                dialogs.show();
                dialogs.setCanceledOnTouchOutside(true);



                close_dialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogs.dismiss();
                    }
                });

                camera_icon.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onClick(View view) {
                        profile_camera_open();
                        dialogs.dismiss();
                    }
                });


                browse_icon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        gallery();
                        dialogs.dismiss();
                    }
                });



            }
        });


        String temp = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        date_spin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SpinnerDatePickerDialogBuilder()
                        .context(PersonalInformationWizardActivity.this)
                        .callback(PersonalInformationWizardActivity.this)
                        .spinnerTheme(R.style.NumberPickerStyle)
                        .showTitle(true)
                        .showDaySpinner(true)
                        .defaultDate(2000, 0, 1)
                        .maxDate(Integer.parseInt(temp), 0, 1)
                        .minDate(1950, 0, 1)
                        .build()
                        .show();
            }
        });


        month_spin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SpinnerDatePickerDialogBuilder()
                        .context(PersonalInformationWizardActivity.this)
                        .callback(PersonalInformationWizardActivity.this)
                        .spinnerTheme(R.style.NumberPickerStyle)
                        .showTitle(true)
                        .showDaySpinner(true)
                        .defaultDate(2000, 0, 1)
                        .maxDate(Integer.parseInt(temp), 0, 1)
                        .minDate(1950, 0, 1)
                        .build()
                        .show();
            }
        });

        year_spin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SpinnerDatePickerDialogBuilder()
                        .context(PersonalInformationWizardActivity.this)
                        .callback(PersonalInformationWizardActivity.this)
                        .spinnerTheme(R.style.NumberPickerStyle)
                        .showTitle(true)
                        .showDaySpinner(true)
                        .defaultDate(2000, 0, 1)
                        .maxDate(Integer.parseInt(temp), 0, 1)
                        .minDate(1950, 0, 1)
                        .build()
                        .show();
            }
        });


        country_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //  Toast.makeText(getActivity(), "Country Spinner Working **********", Toast.LENGTH_SHORT).show();

                String item = country_spin.getSelectedItem().toString();
                if (item.equals(getResources().getString(R.string.select_country)))
                {

                    // int spinnerPosition = dAdapter.getPosition(compareValue);
                    // spinner_category.setSelection(Integer.parseInt("Select Category"));
                }   else
                {

                    country_sp_id = String.valueOf(countryList.get(i).getId());
                    country_name = countryList.get(i).getCountryName();

                    city_list_api();
                    Log.e("LIST_ID_COUNTRY",country_sp_id);
                    Log.e("LIST_ID_COUNTRY",country_name);


                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {


            }
        });

        state_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //  Toast.makeText(getActivity(), "Country Spinner Working **********", Toast.LENGTH_SHORT).show();

                String item = state_spin.getSelectedItem().toString();
                if (item.equals(getResources().getString(R.string.select_city)))
                {

                    // int spinnerPosition = dAdapter.getPosition(compareValue);
                    // spinner_category.setSelection(Integer.parseInt("Select Category"));
                }   else
                {

                    city_sp_id = String.valueOf(cityList.get(i).getId());
                    city_name = String.valueOf(cityList.get(i).getCityName());
                    Log.e("LIST_ID_City",city_sp_id+"ID");
                    Log.e("LIST_ID_City",city_name);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {


            }
        });

        // Default click on male
        male_circle.setImageResource(R.color.white);
        female_circle.setImageResource(R.color.white);
        custom_circle.setImageResource(R.color.white);


        // Default click married
        married_circle.setImageResource(R.color.white);
        single_circle.setImageResource(R.color.white);




        personal_info_save_change_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                get_form_data();
                if(validaton())
                {
                    edit_personal_information_api();
                }
            }
        });

        male_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                genderData = "0";
                male_circle.setImageResource(R.color.settingEditBackgroundColor);
                female_circle.setImageResource(R.color.white);
                custom_circle.setImageResource(R.color.white);
            }
        });

        female_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                genderData = "1";
                female_circle.setImageResource(R.color.settingEditBackgroundColor);
                male_circle.setImageResource(R.color.white);
                custom_circle.setImageResource(R.color.white);

            }
        });

        custom_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                genderData = "2";
                custom_circle.setImageResource(R.color.settingEditBackgroundColor);
                male_circle.setImageResource(R.color.white);
                female_circle.setImageResource(R.color.white);

            }
        });

        single_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                relationshipData = "Single";
                married_circle.setImageResource(R.color.white);
                single_circle.setImageResource(R.color.settingEditBackgroundColor);
            }
        });

        married_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                relationshipData = "Married";

                married_circle.setImageResource(R.color.settingEditBackgroundColor);
                single_circle.setImageResource(R.color.white);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void profile_camera_open() {

        PackageManager packageManager = getPackageManager();

        boolean readExternal = Permission.checkPermissionReadExternal(PersonalInformationWizardActivity.this);
        boolean writeExternal = Permission.checkPermissionReadExternal2(PersonalInformationWizardActivity.this);
        boolean camera = Permission.checkPermissionCamera(PersonalInformationWizardActivity.this);

        if (camera && writeExternal && readExternal ) {
            if (packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
              /*  values7 = new ContentValues();
                values7.put(MediaStore.Images.Media.TITLE, "New Picture");
                values7.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
                imageUri7 = getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values7);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri7);
                startActivityForResult(intent, CAMERA_PIC_REQUEST_R);*/


                Intent intent = new Intent(PersonalInformationWizardActivity.this, ImagePickerActivity.class);
                intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_IMAGE_CAPTURE);

                // setting aspect ratio
                intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
                intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
                intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);

                // setting maximum bitmap width and height
                intent.putExtra(ImagePickerActivity.INTENT_SET_BITMAP_MAX_WIDTH_HEIGHT, true);
                intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_WIDTH, 1000);
                intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_HEIGHT, 1000);

                startActivityForResult(intent, REQUEST_IMAGE);


            }
        } else {
            Toast.makeText(PersonalInformationWizardActivity.this, "camera permission required", Toast.LENGTH_LONG).show();
            requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
        }

    }

    private void gallery() {

        boolean readExternal = Permission.checkPermissionReadExternal(PersonalInformationWizardActivity.this);
        boolean writeExternal = Permission.checkPermissionReadExternal2(PersonalInformationWizardActivity.this);
        boolean camera = Permission.checkPermissionCamera(PersonalInformationWizardActivity.this);
        if (readExternal && camera ) {
      /*      Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, PICK_IMAGE_G);*/


            Intent intent = new Intent(PersonalInformationWizardActivity.this, ImagePickerActivity.class);
            intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_GALLERY_IMAGE);

            // setting aspect ratio
            intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
            intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
            intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);
            startActivityForResult(intent, REQUEST_IMAGE);

        }else
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
            }
        }
    }

    private boolean validaton() {

        if(name_pi_editText.getText().toString().equals(""))
        {
            alert_dialog_message("1");
            return false;
        }else  if(surname_pi_editText.getText().toString().equals(""))
        {
            alert_dialog_message("2");
            return false;
        }else if(city_name.equals(""))
        {
            alert_dialog_message("3");
            return false;
        }else if(genderData.equals(""))
        {
            alert_dialog_message("4");
            return false;
        }else if(relationshipData.equals(""))
        {
            alert_dialog_message("5");
            return false;
        }else if(dayData.equals(""))
        {
            alert_dialog_message("6");
            return false;
        }


        return true;
    }


    private void get_form_data() {
        birthdayData = dayData+","+monthData+","+yearData;
        locationData = country_name+","+city_name;
        try {
            firstNameData = name_pi_editText.getText().toString();
            surNameData = surname_pi_editText.getText().toString();
            aboutMeData = about_me_pi_editText.getText().toString();
        } catch (NullPointerException e)
        {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void city_list_api() {

        final ProgressDialog pd = new ProgressDialog(PersonalInformationWizardActivity.this);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();


        Call<CityListModel> call = API_Client.getClient().cityList(country_sp_id);

        call.enqueue(new Callback<CityListModel>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<CityListModel> call, Response<CityListModel> response) {
                pd.dismiss();


                try {
                    if (response.isSuccessful()) {
                        String message = response.body().getMessage();
                        String success = response.body().getSuccess();


                        if (success.equals("true") || success.equals("True")) {

                    /*        for(int i = 0; i < countryList.size() ; i++)
                            {
                                final List<String> groupstatus2 = new ArrayList<>();
                                groupstatus2.add(countryList.get(i).getCountryName());

                            }

                            for(int i = 0; i < groupstatus2.size() ; i++)
                            {
                                Toast.makeText(PersonalInformationActivity.this, groupstatus2.get(i).toString(), Toast.LENGTH_SHORT).show();

                            }*/

                            // Set data on spinner

                            // Set data on spinner


                            city_name_list.clear();
                            cityList = response.body().getRecord();

                            CityListResult cityListResult =new CityListResult();
                            cityListResult.setCityName(getResources().getString(R.string.select_city));
                            cityListResult.setId(0);
                            cityList.add(0,cityListResult);
                            for (int j=0;j<cityList.size();j++)
                            {
                                String category_name = cityList.get(j).getCityName();
                                //    Toast.makeText(getActivity(), (CharSequence) countryList.get(j), Toast.LENGTH_SHORT).show();
                                if(category_name!=null)
                                {
                                    city_name_list.add(category_name);
                                }
                                Log.e("city_name_list",city_name_list.size()+"S");
                            }

                            spinnerAdapter dAdapter = new spinnerAdapter(getApplicationContext(), R.layout.custom_spinner_two, city_name_list);
                            dAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            dAdapter.addAll(city_name_list);
                            dAdapter.add(getResources().getString(R.string.select_city));
                            state_spin.setAdapter(dAdapter);
                            state_spin.setSelection(dAdapter.getCount());

                            if (state.equals("")) {
                            } else {
                                int spinnerPosition = dAdapter.getPosition(state);
                                state_spin.setSelection(spinnerPosition);
                                // city_list_api(str_country);
                            }

                            //Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

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
            public void onFailure(Call<CityListModel> call, Throwable t) {
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

    private void edit_personal_information_api() {

        final ProgressDialog pd = new ProgressDialog(PersonalInformationWizardActivity.this);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();

        Log.e("editPersonalInfo","user_id: "+user_id );
        Log.e("editPersonalInfo","birthdayData: "+birthdayData );
        Log.e("editPersonalInfo","genderData: "+genderData );
        Log.e("editPersonalInfo","locationData: "+locationData );
        Log.e("editPersonalInfo","relationshipData: "+relationshipData );
        Log.e("editPersonalInfo","aboutMeData: "+aboutMeData );
        Log.e("editPersonalInfo","firstNameData: "+firstNameData );
        Log.e("editPersonalInfo","surNameData: "+surNameData );

        Call<Edit_Personal_Information_Model> call = API_Client.getClient().EditPersonalInformation(user_id,
                birthdayData,
                genderData,
                locationData,
                relationshipData,
                "null",
                firstNameData,
                surNameData);

        call.enqueue(new Callback<Edit_Personal_Information_Model>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<Edit_Personal_Information_Model> call, Response<Edit_Personal_Information_Model> response) {
                pd.dismiss();


                try {
                    if (response.isSuccessful()) {
                        String message = response.body().getMessage();
                        String success = response.body().getSuccess();

                        if (success.equals("true") || success.equals("True")) {

                            personal_information_api();
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

                            // Calling TV setting ......
                            Intent intent = new Intent(PersonalInformationWizardActivity.this, TvChannelSettingsWizardActivity.class);
                            startActivity(intent);
                            Log.e("check","Running..........");

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
            public void onFailure(Call<Edit_Personal_Information_Model> call, Throwable t) {
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

    private void country_list_api() {

        final ProgressDialog pd = new ProgressDialog(PersonalInformationWizardActivity.this);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();


        Call<CountryListModel> call = API_Client.getClient().countryList();

        call.enqueue(new Callback<CountryListModel>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<CountryListModel> call, Response<CountryListModel> response) {
                pd.dismiss();


                try {
                    if (response.isSuccessful()) {
                        String message = response.body().getMessage();
                        String success = response.body().getSuccess();



                        if (success.equals("true") || success.equals("True")) {



                    /*        for(int i = 0; i < countryList.size() ; i++)
                            {
                                final List<String> groupstatus2 = new ArrayList<>();
                                groupstatus2.add(countryList.get(i).getCountryName());

                            }

                            for(int i = 0; i < groupstatus2.size() ; i++)
                            {
                                Toast.makeText(PersonalInformationActivity.this, groupstatus2.get(i).toString(), Toast.LENGTH_SHORT).show();

                            }*/

                            // Set data on spinner

                            // Set data on spinner


                            country_name_list.clear();
                            countryList = response.body().getRecord();

                            CountryListResult country_result =new CountryListResult();
                            country_result.setCountryName(getResources().getString(R.string.select_country));
                            country_result.setId(0);
                            countryList.add(0,country_result);
                            for (int j=0;j<countryList.size();j++)
                            {
                                String category_name = countryList.get(j).getCountryName();
                                //    Toast.makeText(getActivity(), (CharSequence) countryList.get(j), Toast.LENGTH_SHORT).show();
                                if(category_name!=null)
                                {
                                    country_name_list.add(category_name);
                                }
                                Log.e("country_name_list",country_name_list.size()+"S");
                            }

                            spinnerAdapter dAdapter = new spinnerAdapter(getApplicationContext(), R.layout.custom_spinner_two, country_name_list);
                            dAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            dAdapter.addAll(country_name_list);
                            dAdapter.add(getResources().getString(R.string.select_country));
                            country_spin.setAdapter(dAdapter);
                            country_spin.setSelection(dAdapter.getCount());

                            if (Country.equals("")) {
                            } else {
                                int spinnerPosition = dAdapter.getPosition(Country);
                                country_spin.setSelection(spinnerPosition);
                                // city_list_api(str_country);
                            }


                            //Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

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
            public void onFailure(Call<CountryListModel> call, Throwable t) {
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

    private void personal_information_api() {


        final ProgressDialog pd = new ProgressDialog(PersonalInformationWizardActivity.this);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();


        Call<Personal_Information_Settings_Model> call = API_Client.getClient().PersonalInformationDetails(user_id);

        call.enqueue(new Callback<Personal_Information_Settings_Model>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<Personal_Information_Settings_Model> call, Response<Personal_Information_Settings_Model> response) {
                pd.dismiss();


                try {
                    if (response.isSuccessful()) {
                        String message = response.body().getMessage();
                        String success = response.body().getSuccess();



                        if (success.equals("true") || success.equals("True")) {

                            recordList = response.body().getPersonal_information_settings_results();




                            name_pi_editText.setText(recordList.get(0).getFirstname());
                            surname_pi_editText.setText(recordList.get(0).getLastname());
                            String location = recordList.get(0).getLocation();

                            String[] parts = location.split(",");
                            Country = parts[0];
                            state = parts[1];

                            Log.e("dfssfsd",Country);
                            Log.e("dfssfsd",state);


                            String gender = String.valueOf(recordList.get(0).gender);

                            if(gender.equals("0"))  // Male
                            {
                                genderData = "0";
                                male_circle.setImageResource(R.color.settingEditBackgroundColor);
                                female_circle.setImageResource(R.color.white);
                                custom_circle.setImageResource(R.color.white);

                            }else if (gender.equals("1"))  // Female
                            {
                                genderData = "1";
                                female_circle.setImageResource(R.color.settingEditBackgroundColor);
                                male_circle.setImageResource(R.color.white);
                                custom_circle.setImageResource(R.color.white);
                            }else if(gender.equals("2"))  // Custom
                            {
                                genderData = "2";
                                custom_circle.setImageResource(R.color.settingEditBackgroundColor);
                                male_circle.setImageResource(R.color.white);
                                female_circle.setImageResource(R.color.white);
                            }else
                            {

                            }

                            String relationship = recordList.get(0).getRelationship();

                            if(relationship.equals("Single"))   //Single
                            {
                                relationshipData = "Single";
                                married_circle.setImageResource(R.color.white);
                                single_circle.setImageResource(R.color.settingEditBackgroundColor);
                            }else if(relationship.equals("Married"))  // Married
                            {
                                relationshipData = "Married";
                                married_circle.setImageResource(R.color.settingEditBackgroundColor);
                                single_circle.setImageResource(R.color.white);
                            }

                            String aboutMe = recordList.get(0).getAboutme();
                            about_me_pi_editText.setText(aboutMe);


                            String DOB = recordList.get(0).getBirthDate();
                            Log.e("fddsdfs",DOB);
                            String[] parts2 = DOB.split(",");
                            String day2 = parts2[0];
                            String month2 = parts2[1];
                            String year2 = parts2[2];
                            Log.e("fddsdfs",day2);
                            Log.e("fddsdfs",month2);
                            Log.e("fddsdfs",year2);

                            birthdayData = day2+","+month2+","+year2;
                            Toast.makeText(PersonalInformationWizardActivity.this, birthdayData, Toast.LENGTH_SHORT).show();

                            Log.e("fddsdfs",birthdayData+"OK");

                            // Set value to DOB
                            date_spin.setText(day2);
                            month_spin.setText(month2);
                            year_spin.setText(year2);

                            // Set default value for gender and relationship

                            //Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

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
            public void onFailure(Call<Personal_Information_Settings_Model> call, Throwable t) {
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

        final LayoutInflater inflater = PersonalInformationWizardActivity.this.getLayoutInflater();
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
        }else if(value.equals("1"))
       {
           error_message.setText(R.string.please_enter_name);
       }else if(value.equals("2"))
       {
           error_message.setText(R.string.please_enter_surname);
       }else if(value.equals("3"))
       {
           error_message.setText(R.string.please_choose_location);
       }else if(value.equals("4"))
       {
           error_message.setText(R.string.please_choose_gender);
       }else if(value.equals("5"))
       {
           error_message.setText(R.string.please_choose_relationship);
       }else if(value.equals("6"))
       {
           error_message.setText(R.string.please_choose_birthdate);
       } else{
           error_message.setText(value);
        }

        final AlertDialog.Builder alert = new AlertDialog.Builder(PersonalInformationWizardActivity.this);

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

            Glide.with(PersonalInformationWizardActivity.this).load(usrProfieImageFile).into(userprofile);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    private void intis() {
        userprofile = findViewById(R.id.userprofile);
        edit_profilelayout = findViewById(R.id.edit_profilelayout);
        male_circle = findViewById(R.id.male_circle_PW);
        female_circle = findViewById(R.id.female_circle_PW);
        custom_circle = findViewById(R.id.custom_circle_PW);
        male_layout = findViewById(R.id.male_layout_PW);
        female_layout = findViewById(R.id.female_layout_PW);
        custom_layout = findViewById(R.id.custom_layout_PW);
        married_circle = findViewById(R.id.married_circle_PW);
        single_circle = findViewById(R.id.single_circle_PW);
        single_layout = findViewById(R.id.married_layout_PW);
        married_layout = findViewById(R.id.single_layout_PW);
        personal_info_save_change_button = findViewById(R.id.personal_info_save_change_button_PW);
        name_pi_editText = findViewById(R.id.name_pi_editText_PW);
        surname_pi_editText = findViewById(R.id.surname_pi_editText_PW);
        date_spin = findViewById(R.id.date_spin_PW);
        month_spin = findViewById(R.id.month_spin_PW);
        year_spin = findViewById(R.id.year_spin_PW);
        state_spin = findViewById(R.id.state_spin_PW);
        country_spin = findViewById(R.id.country_spin_PW);

    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

        date_spin.setText(String.valueOf(dayOfMonth));
        dayData = String.valueOf(dayOfMonth);
        if(monthOfYear == 0)
        {
            month_spin.setText("Jan");
            monthData = "Jan";
        }

        if(monthOfYear == 1)
        {
            month_spin.setText("Feb");
            monthData = "Feb";
        }

        if(monthOfYear == 2)
        {
            month_spin.setText("Mar");
            monthData = "Mar";
        }

        if(monthOfYear == 3)
        {
            month_spin.setText("Apr");
            monthData = "Apr";
        }

        if(monthOfYear == 4)
        {
            month_spin.setText("May");
            monthData = "May";
        }

        if(monthOfYear == 5)
        {
            month_spin.setText("Jun");
            monthData = "Jun";
        }

        if(monthOfYear == 6)
        {
            month_spin.setText("Jul");
            monthData = "Jul";
        }

        if(monthOfYear == 7)
        {
            month_spin.setText("Aug");
            monthData = "Aug";
        }

        if(monthOfYear == 8)
        {
            month_spin.setText("Sep");
            monthData = "Sep";
        }

        if(monthOfYear == 9)
        {
            month_spin.setText("Oct");
            monthData = "Oct";
        }

        if(monthOfYear == 10)
        {
            month_spin.setText("Nov");
            monthData = "Nov";
        }

        if(monthOfYear == 11)
        {
            month_spin.setText("Dec");
            monthData = "Dec";
        }


        year_spin.setText(String.valueOf(year));
        yearData = String.valueOf(year);


        Log.e("dfssdfsdfsd", String.valueOf(dayOfMonth));
        Log.e("dfssdfsdfsd", String.valueOf(monthOfYear));
        Log.e("dfssdfsdfsd", String.valueOf(year));



    }


    public class spinnerAdapter extends ArrayAdapter<String>
    {
        private spinnerAdapter(Context context, int textViewResourceId, List<String> smonking)
        {
            super(context, textViewResourceId);
        }

        @Override
        public int getCount()
        {
            int count = super.getCount();
            return count > 0 ? count - 1 : count;
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_IMAGE) {

            //  Toast.makeText(TVChannelSettingsActivity.this, "imageKey: "+imageKey, Toast.LENGTH_SHORT).show();

            if (resultCode == Activity.RESULT_OK) {
               Uri uri = data.getParcelableExtra("path");
                // String sel_path = getpath(uri);
                try {
                    // You can update this bitmap to your server
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);

                    // loading profile image from local cache
                    //loadProfile(uri.toString());


                    userProfileImage = new File(uri.getPath());
                    Log.e("file ", "path " + userProfileImage.getAbsolutePath());
                    Uri uu = Uri.fromFile(userProfileImage);

                    Glide.with(PersonalInformationWizardActivity.this).
                            load(userProfileImage).
                            placeholder(R.drawable.ic_launcher_background).
                            into(userprofile);


                   /* if(imageKey.equals("1"))
                    {
                        tvLogoSettingsImage = new File(uri.getPath());
                        Log.e("file ", "path " + tvLogoSettingsImage.getAbsolutePath());

                        Uri uu = Uri.fromFile(tvLogoSettingsImage);
                        Glide.with(TVChannelSettingsActivity.this).
                                load(tvLogoSettingsImage).
                                placeholder(R.drawable.ic_launcher_background).
                                into(tv_logo_image);
                    }else if(imageKey.equals("2"))
                    {
                        tvCoverSettingsImage = new File(uri.getPath());
                        Uri uu = Uri.fromFile(tvCoverSettingsImage);
                        Glide.with(TVChannelSettingsActivity.this).
                                load(tvCoverSettingsImage).
                                placeholder(R.drawable.ic_launcher_background).
                                into(tv_cover_image);
                    }
*/

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }



}