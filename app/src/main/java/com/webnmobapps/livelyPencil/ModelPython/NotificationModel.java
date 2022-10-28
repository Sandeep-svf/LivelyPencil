package com.webnmobapps.livelyPencil.ModelPython;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotificationModel {

@SerializedName("status")
@Expose
private String status;
@SerializedName("message")
@Expose
private String message;
@SerializedName("data")
@Expose
private NotificationModelSettingData notificationModelSettingData;

public String getStatus() {
return status;
}

public void setStatus(String status) {
this.status = status;
}

public String getMessage() {
return message;
}

public void setMessage(String message) {
this.message = message;
}

public NotificationModelSettingData getData() {
return notificationModelSettingData;
}

public void setData(NotificationModelSettingData notificationModelSettingData) {
this.notificationModelSettingData = notificationModelSettingData;
}

}