package com.webnmobapps.livelyPencil.ModelPython;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RoleSettingModel {

@SerializedName("status")
@Expose
public String status;
@SerializedName("message")
@Expose
public String message;
@SerializedName("data")
@Expose
public RoleSettingDataModel roleSettingDataModel;

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public RoleSettingDataModel getRoleSettingDataModel() {
        return roleSettingDataModel;
    }
}