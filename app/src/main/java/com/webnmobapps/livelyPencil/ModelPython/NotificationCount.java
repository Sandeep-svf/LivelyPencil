package com.webnmobapps.livelyPencil.ModelPython;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotificationCount {

@SerializedName("message__count")
@Expose
public Integer messageCount;


    public Integer getMessageCount() {
        return messageCount;
    }
}