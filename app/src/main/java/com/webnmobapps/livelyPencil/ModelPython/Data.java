package com.webnmobapps.livelyPencil.ModelPython;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

@SerializedName("notification")
@Expose
private String notification;

public String getNotification() {
return notification;
}

public void setNotification(String notification) {
this.notification = notification;
}

}