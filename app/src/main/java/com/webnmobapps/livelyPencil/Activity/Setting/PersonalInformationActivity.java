package com.webnmobapps.livelyPencil.Activity.Setting;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.tsongkha.spinnerdatepicker.DatePicker;
import com.tsongkha.spinnerdatepicker.DatePickerDialog;
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder;
import com.webnmobapps.livelyPencil.Activity.Login.LoginActivity;
import com.webnmobapps.livelyPencil.Activity.UserWall.HomeActivity;
import com.webnmobapps.livelyPencil.MainActivity;
import com.webnmobapps.livelyPencil.Model.CityListModel;
import com.webnmobapps.livelyPencil.Model.CountryListModel;
import com.webnmobapps.livelyPencil.Model.Edit_Personal_Information_Model;
import com.webnmobapps.livelyPencil.Model.LoginModel;
import com.webnmobapps.livelyPencil.Model.Personal_Information_Settings_Model;
import com.webnmobapps.livelyPencil.Model.Record.CityListResult;
import com.webnmobapps.livelyPencil.Model.Record.CountryListResult;
import com.webnmobapps.livelyPencil.Model.Record.Personal_Information_Settings_Result;
import com.webnmobapps.livelyPencil.R;
import com.webnmobapps.livelyPencil.RetrofitApi.API_Client;

import org.json.JSONObject;

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

public class PersonalInformationActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    CircleImageView male_circle,female_circle,custom_circle, married_circle,single_circle ;
    ConstraintLayout male_layout, female_layout, custom_layout, married_layout, single_layout;
    AppCompatButton personal_info_save_change_button;
    AppCompatEditText name_pi_editText, surname_pi_editText, about_me_pi_editText;
    AppCompatSpinner  country_spin, state_spin;
    AppCompatTextView date_spin,month_spin,year_spin ;
    AppCompatImageView back_arrow;
    private String user_id , dayData, monthData, yearData , firstNameData, surNameData, aboutMeData, birthdayData, genderData, relationshipData, locationData;
    List<Personal_Information_Settings_Result> recordList = new ArrayList<>();
    List<CountryListResult> countryList = new ArrayList<>();
    List<String> country_name_list = new ArrayList<>();

    List<CityListResult> cityList = new ArrayList<>();
    List<String> city_name_list = new ArrayList<>();

    final List<String> groupstatus2 = new ArrayList<>();
    private String country_sp_id, city_sp_id, Country, state, country_name, city_name;

    SimpleDateFormat simpleDateFormat;
    AlertDialog dialogs;
    CircleIndicator Indicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_information);

        inits();

        SharedPreferences sharedPreferences= getSharedPreferences("AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
        user_id=sharedPreferences.getString("UserID","");

        Log.e("user_id",": "+user_id);
        // API

        personal_information_api();
        country_list_api();



        // Manual Circle Indicatior........
        Indicator = findViewById(R.id.pi_indicator);
        Indicator.createIndicators(3,0);
        Indicator.animatePageSelected(0);

        String temp = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        date_spin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SpinnerDatePickerDialogBuilder()
                        .context(PersonalInformationActivity.this)
                        .callback(PersonalInformationActivity.this)
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
                        .context(PersonalInformationActivity.this)
                        .callback(PersonalInformationActivity.this)
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
                        .context(PersonalInformationActivity.this)
                        .callback(PersonalInformationActivity.this)
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

        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        personal_info_save_change_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                get_form_data();
                edit_personal_information_api();
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

    private void city_list_api() {

        final ProgressDialog pd = new ProgressDialog(PersonalInformationActivity.this);
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

    private void country_list_api() {

        final ProgressDialog pd = new ProgressDialog(PersonalInformationActivity.this);
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

    private void edit_personal_information_api() {

        final ProgressDialog pd = new ProgressDialog(PersonalInformationActivity.this);
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
                aboutMeData,
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
                           /* Intent intent = new Intent(PersonalInformationActivity.this, TVChannelSettingsActivity.class);
                            startActivity(intent);
                            Log.e("check","Running..........");*/

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

    private void get_form_data() {

        firstNameData = name_pi_editText.getText().toString();
        surNameData = surname_pi_editText.getText().toString();
        aboutMeData = about_me_pi_editText.getText().toString();
        birthdayData = dayData+","+monthData+","+yearData;
        locationData = country_name+","+city_name;
    }

    private void personal_information_api() {


        final ProgressDialog pd = new ProgressDialog(PersonalInformationActivity.this);
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

                            Log.e("fddsdfs",birthdayData);

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

        final LayoutInflater inflater = PersonalInformationActivity.this.getLayoutInflater();
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

        final AlertDialog.Builder alert = new AlertDialog.Builder(PersonalInformationActivity.this);

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
        back_arrow = findViewById(R.id.back_arrow);
        male_circle = findViewById(R.id.male_circle);
        female_circle = findViewById(R.id.female_circle);
        custom_circle = findViewById(R.id.custom_circle);
        male_layout = findViewById(R.id.male_layout);
        female_layout = findViewById(R.id.female_layout);
        custom_layout = findViewById(R.id.custom_layout);
        married_circle = findViewById(R.id.married_circle);
        single_circle = findViewById(R.id.single_circle);
        single_layout = findViewById(R.id.single_layout);
        married_layout = findViewById(R.id.married_layout);
        personal_info_save_change_button = findViewById(R.id.personal_info_save_change_button);
        name_pi_editText = findViewById(R.id.name_pi_editText);
        surname_pi_editText = findViewById(R.id.surname_pi_editText);
        date_spin = findViewById(R.id.date_spin);
        month_spin = findViewById(R.id.month_spin);
        year_spin = findViewById(R.id.year_spin);
        state_spin = findViewById(R.id.state_spin);
        country_spin = findViewById(R.id.country_spin);
        about_me_pi_editText = findViewById(R.id.about_me_pi_editText);


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


}

