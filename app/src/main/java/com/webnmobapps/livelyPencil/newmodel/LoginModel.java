package com.webnmobapps.livelyPencil.newmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginModel {

@SerializedName("status")
@Expose
public String status;
@SerializedName("message")
@Expose
public String message;
@SerializedName("token")
@Expose
public LoginToken loginToken;

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public LoginToken getToken() {
        return loginToken;
    }
}