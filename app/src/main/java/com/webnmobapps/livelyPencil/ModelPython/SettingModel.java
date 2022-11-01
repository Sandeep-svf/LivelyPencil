package com.webnmobapps.livelyPencil.ModelPython;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SettingModel {

@SerializedName("status")
@Expose
public String status;
@SerializedName("message")
@Expose
public String message;
@SerializedName("Data")
@Expose
public SettingModelData settingModelData;

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public SettingModelData getData() {
        return settingModelData;
    }
}