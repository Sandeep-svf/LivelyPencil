package com.webnmobapps.livelyPencil.ModelPython;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RoleSettingDataModel {

@SerializedName("role")
@Expose
public String role;

    public String getRole() {
        return role;
    }
}