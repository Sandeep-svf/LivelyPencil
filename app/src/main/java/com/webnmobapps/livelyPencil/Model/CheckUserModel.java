package com.webnmobapps.livelyPencil.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.webnmobapps.livelyPencil.Model.Record.CheckUserRecord;

import java.util.ArrayList;
import java.util.List;

public class CheckUserModel {

@SerializedName("success")
@Expose
public String success;
@SerializedName("message")
@Expose
public String message;
    @SerializedName("record")
    @Expose
    public List<CheckUserRecord> checkUserRecord = new ArrayList<CheckUserRecord>();

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

    public List<CheckUserRecord> getRecord() {
        return checkUserRecord;
    }

    public void setRecord(List<CheckUserRecord> checkUserRecord) {
        this.checkUserRecord = checkUserRecord;
    }
}