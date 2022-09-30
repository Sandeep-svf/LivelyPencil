package com.webnmobapps.livelyPencil.ModelPython;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MyFollowersData {

@SerializedName("status")
@Expose
public String status;
@SerializedName("message")
@Expose
public String message;
@SerializedName("data")
@Expose
public List<MyFollowersModel> data = null;

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public List<MyFollowersModel> getData() {
        return data;
    }
}