package com.webnmobapps.livelyPencil.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.webnmobapps.livelyPencil.Model.Record.CityListResult;

import java.util.ArrayList;
import java.util.List;

public class CityListModel {

@SerializedName("success")
@Expose
public String success;
@SerializedName("message")
@Expose
public String message;
@SerializedName("record")
@Expose
public List<CityListResult> cityListResult = new ArrayList<CityListResult>();

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

    public List<CityListResult> getRecord() {
        return cityListResult;
    }

    public void setRecord(List<CityListResult> cityListResult) {
        this.cityListResult = cityListResult;
    }
}