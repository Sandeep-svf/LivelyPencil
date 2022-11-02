package com.webnmobapps.livelyPencil.ModelPython;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LiveUserPythonModel {

@SerializedName("status")
@Expose
public String status;
@SerializedName("message")
@Expose
public String message;
@SerializedName("data")
@Expose
public List<LiveUserPythonModelData> data = null;


    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public List<LiveUserPythonModelData> getData() {
        return data;
    }
}