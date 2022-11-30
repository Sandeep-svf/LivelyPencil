package com.webnmobapps.livelyPencil.ModelPython;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotificationCountPythonModel {

@SerializedName("status")
@Expose
public String status;
@SerializedName("count")
@Expose
public Count count;
@SerializedName("message")
@Expose
public String message;

    public String getStatus() {
        return status;
    }

    public Count getCount() {
        return count;
    }

    public String getMessage() {
        return message;
    }
}