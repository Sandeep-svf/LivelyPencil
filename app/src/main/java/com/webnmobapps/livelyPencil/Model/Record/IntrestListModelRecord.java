package com.webnmobapps.livelyPencil.Model.Record;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IntrestListModelRecord {

@SerializedName("id")
@Expose
public Integer id;
@SerializedName("status")
@Expose
public String status;
        @SerializedName("checkBoolean")
        @Expose
    public boolean checkBoolean = false;

    public boolean isCheckBoolean() {
        return checkBoolean;
    }

    public void setCheckBoolean(boolean checkBoolean) {
        this.checkBoolean = checkBoolean;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}