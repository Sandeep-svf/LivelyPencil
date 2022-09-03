package com.webnmobapps.livelyPencil.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.webnmobapps.livelyPencil.Model.Record.UserWallResult;

import java.util.ArrayList;
import java.util.List;

public class UserWallModel {

@SerializedName("success")
@Expose
public String success;
@SerializedName("message")
@Expose
public String message;
@SerializedName("record")
@Expose
public List<UserWallResult> userWallResult = new ArrayList<UserWallResult>();


    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<UserWallResult> getRecord() {
        return userWallResult;
    }

    public void setRecord(List<UserWallResult> userWallResult) {
        this.userWallResult = userWallResult;
    }
}