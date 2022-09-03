package com.webnmobapps.livelyPencil.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegisterModel {

    @SerializedName("success")
    @Expose
    public String success;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("record")
    @Expose
    public Integer record;


    public String getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public Integer getRecord() {
        return record;
    }
}