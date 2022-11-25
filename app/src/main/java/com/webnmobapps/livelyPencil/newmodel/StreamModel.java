package com.webnmobapps.livelyPencil.newmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StreamModel {

@SerializedName("status")
@Expose
public String status;
@SerializedName("message")
@Expose
public String message;
@SerializedName("Data")
@Expose
public StreamModelData streamModelData;

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public StreamModelData getData() {
        return streamModelData;
    }
}