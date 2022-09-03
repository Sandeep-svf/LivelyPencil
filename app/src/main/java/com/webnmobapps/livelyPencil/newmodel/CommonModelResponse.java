package com.webnmobapps.livelyPencil.newmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CommonModelResponse {

@SerializedName("status")
@Expose
public String status;
@SerializedName("message")
@Expose
public String message;


    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}