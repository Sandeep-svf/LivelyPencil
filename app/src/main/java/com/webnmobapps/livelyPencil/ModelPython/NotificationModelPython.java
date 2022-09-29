package com.webnmobapps.livelyPencil.ModelPython;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NotificationModelPython {

@SerializedName("status")
@Expose
public String status;
@SerializedName("count")
@Expose
public NotificationCount notificationCount;
@SerializedName("message")
@Expose
public String message;
@SerializedName("data")
@Expose
public List<NotificationListPython> data = null;

    public String getStatus() {
        return status;
    }

    public NotificationCount getCount() {
        return notificationCount;
    }

    public String getMessage() {
        return message;
    }

    public List<NotificationListPython> getData() {
        return data;
    }
}