package com.webnmobapps.livelyPencil.ModelPython;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TokenPython {

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