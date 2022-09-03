package com.webnmobapps.livelyPencil.newmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginToken {

@SerializedName("refresh")
@Expose
public String refresh;
@SerializedName("access")
@Expose
public String access;


    public String getRefresh() {
        return refresh;
    }

    public String getAccess() {
        return access;
    }
}