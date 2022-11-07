package com.webnmobapps.livelyPencil.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.webnmobapps.livelyPencil.Model.Record.FollowersModelData;

import java.util.List;

public class FollowersModel {

@SerializedName("status")
@Expose
public String status;
@SerializedName("message")
@Expose
public String message;
@SerializedName("data")
@Expose
public List<FollowersModelData> data = null;


    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public List<FollowersModelData> getData() {
        return data;
    }
}