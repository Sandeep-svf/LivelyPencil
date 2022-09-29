package com.webnmobapps.livelyPencil.ModelPython;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserProfileModelPython {

@SerializedName("status")
@Expose
public String status;
@SerializedName("message")
@Expose
public String message;
@SerializedName("data")
@Expose
public UserProfileDataPython userProfileDataPython;


    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public UserProfileDataPython getData() {
        return userProfileDataPython;
    }
}