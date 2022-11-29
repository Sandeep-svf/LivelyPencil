package com.webnmobapps.livelyPencil.ModelPython;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginModelPython {

    @SerializedName("image")
    @Expose
    public String image;
@SerializedName("status")
@Expose
public String status;
@SerializedName("message")
@Expose
public String message;
@SerializedName("token")
@Expose
public TokenPython tokenPython;

    public String getImage() {
        return image;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public TokenPython getToken() {
        return tokenPython;
    }
}