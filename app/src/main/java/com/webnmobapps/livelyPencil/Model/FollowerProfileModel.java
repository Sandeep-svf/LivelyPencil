package com.webnmobapps.livelyPencil.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.webnmobapps.livelyPencil.Model.Record.FollowerProfileModelData;

public class FollowerProfileModel {

@SerializedName("status")
@Expose
public String status;
@SerializedName("message")
@Expose
public String message;
@SerializedName("data")
@Expose
public FollowerProfileModelData followerProfileModelData;

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public FollowerProfileModelData getData() {
        return followerProfileModelData;
    }
}