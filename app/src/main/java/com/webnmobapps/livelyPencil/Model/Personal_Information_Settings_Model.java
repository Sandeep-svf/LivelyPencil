package com.webnmobapps.livelyPencil.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.webnmobapps.livelyPencil.Model.Record.Personal_Information_Settings_Result;

import java.util.ArrayList;
import java.util.List;

public class Personal_Information_Settings_Model {

@SerializedName("success")
@Expose
public String success;
@SerializedName("message")
@Expose
public String message;
@SerializedName("record")
@Expose
public List<Personal_Information_Settings_Result> personal_information_settings_results = new ArrayList<Personal_Information_Settings_Result>();


    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Personal_Information_Settings_Result> getPersonal_information_settings_results() {
        return personal_information_settings_results;
    }

    public void setPersonal_information_settings_results(List<Personal_Information_Settings_Result> personal_information_settings_results) {
        this.personal_information_settings_results = personal_information_settings_results;
    }
}