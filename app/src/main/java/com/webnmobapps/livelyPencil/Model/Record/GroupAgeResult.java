package com.webnmobapps.livelyPencil.Model.Record;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GroupAgeResult {

@SerializedName("id ")
@Expose
public Integer id;
@SerializedName("agediff")
@Expose
public String agediff;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAgediff() {
        return agediff;
    }

    public void setAgediff(String agediff) {
        this.agediff = agediff;
    }
}