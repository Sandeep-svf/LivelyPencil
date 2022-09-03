package com.webnmobapps.livelyPencil.Model.Record;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ShareSettingDetailsResult {

@SerializedName("pagesharing")
@Expose
public Integer pagesharing;
@SerializedName("allowcitation")
@Expose
public Integer allowcitation;


    public Integer getPagesharing() {
        return pagesharing;
    }

    public void setPagesharing(Integer pagesharing) {
        this.pagesharing = pagesharing;
    }

    public Integer getAllowcitation() {
        return allowcitation;
    }

    public void setAllowcitation(Integer allowcitation) {
        this.allowcitation = allowcitation;
    }
}