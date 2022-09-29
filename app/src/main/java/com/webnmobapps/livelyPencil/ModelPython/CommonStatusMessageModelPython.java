package com.webnmobapps.livelyPencil.ModelPython;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CommonStatusMessageModelPython {

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