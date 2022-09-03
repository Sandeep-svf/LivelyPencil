package com.webnmobapps.livelyPencil.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.webnmobapps.livelyPencil.Model.Record.CountryListResult;

import java.util.ArrayList;
import java.util.List;

public class CountryListModel {

    @SerializedName("success")
    @Expose
    public String success;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("record")
    @Expose
    public List<CountryListResult> countryListResult = new ArrayList<CountryListResult>();

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

    public List<CountryListResult> getRecord() {
        return countryListResult;
    }

    public void setRecord(List<CountryListResult> countryListResult) {
        this.countryListResult = countryListResult;
    }
}