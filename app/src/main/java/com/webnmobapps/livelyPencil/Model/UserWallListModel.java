package com.webnmobapps.livelyPencil.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.webnmobapps.livelyPencil.Model.Record.UserWallListResult;

import java.util.ArrayList;
import java.util.List;

public class UserWallListModel {

@SerializedName("success")
@Expose
public String success;
@SerializedName("message")
@Expose
public String message;
@SerializedName("record")
@Expose
public List<UserWallListResult> userWallListResult = new ArrayList<UserWallListResult>();


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

    public List<UserWallListResult> getRecord() {
        return userWallListResult;
    }

    public void setRecord(List<UserWallListResult> userWallListResult) {
        this.userWallListResult = userWallListResult;
    }
}