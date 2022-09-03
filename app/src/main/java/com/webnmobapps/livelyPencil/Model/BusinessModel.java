package com.webnmobapps.livelyPencil.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.webnmobapps.livelyPencil.Model.Record.BusinessResult;

import java.util.ArrayList;
import java.util.List;

public class BusinessModel {

@SerializedName("success")
@Expose
public String success;
@SerializedName("message")
@Expose
public String message;
@SerializedName("record")
@Expose
public List<BusinessResult> businessResult = new ArrayList<BusinessResult>();

    public String getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public List<BusinessResult> getRecord() {
        return businessResult;
    }
}