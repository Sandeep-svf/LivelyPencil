package com.webnmobapps.livelyPencil.Model.Record;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BusinessResult {

@SerializedName("id")
@Expose
public Integer id;
@SerializedName("LabelValue")
@Expose
public String labelValue;
@SerializedName("created_at")
@Expose
public Object createdAt;
@SerializedName("updated_at")
@Expose
public Object updatedAt;

    public Integer getId() {
        return id;
    }

    public String getLabelValue() {
        return labelValue;
    }

    public Object getCreatedAt() {
        return createdAt;
    }

    public Object getUpdatedAt() {
        return updatedAt;
    }
}