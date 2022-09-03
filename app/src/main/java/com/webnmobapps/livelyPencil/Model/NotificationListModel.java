package com.webnmobapps.livelyPencil.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.webnmobapps.livelyPencil.Model.Record.NotificationListResult;

import java.util.ArrayList;
import java.util.List;

public class NotificationListModel {

@SerializedName("success")
@Expose
public String success;
@SerializedName("message")
@Expose
public String message;
@SerializedName("record")
@Expose
public List<NotificationListResult> notificationListResult = new ArrayList<NotificationListResult>();


    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<NotificationListResult> getRecord() {
        return notificationListResult;
    }

    public void setRecord(List<NotificationListResult> notificationListResult) {
        this.notificationListResult = notificationListResult;
    }
}