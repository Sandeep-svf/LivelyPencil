package com.webnmobapps.livelyPencil.ModelPython;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserProfileModel {

@SerializedName("status")
@Expose
public String status;
@SerializedName("message")
@Expose
public String message;
@SerializedName("pages")
@Expose
public UserProflePageData userProflePageData;
@SerializedName("data")
@Expose
public UserProfileData userProfileData;

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public UserProflePageData getUserProflePageData() {
        return userProflePageData;
    }

    public UserProfileData getUserProfileData() {
        return userProfileData;
    }
}