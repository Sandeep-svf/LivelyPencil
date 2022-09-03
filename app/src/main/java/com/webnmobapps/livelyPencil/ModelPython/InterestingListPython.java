package com.webnmobapps.livelyPencil.ModelPython;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InterestingListPython {

@SerializedName("id")
@Expose
public Integer id;
@SerializedName("created_at")
@Expose
public String createdAt;
@SerializedName("updated_at")
@Expose
public String updatedAt;
@SerializedName("category")
@Expose
public String category;
    @SerializedName("checkBoolean")
    @Expose
    public boolean checkBoolean = false;

    public Integer getId() {
        return id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public String getCategory() {
        return category;
    }

    public boolean isCheckBoolean() {
        return checkBoolean;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setCheckBoolean(boolean checkBoolean) {
        this.checkBoolean = checkBoolean;
    }
}