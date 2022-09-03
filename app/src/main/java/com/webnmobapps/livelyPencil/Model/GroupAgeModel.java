package com.webnmobapps.livelyPencil.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.webnmobapps.livelyPencil.Model.Record.GroupAgeResult;

import java.util.ArrayList;
import java.util.List;

public class GroupAgeModel {

@SerializedName("success")
@Expose
public String success;
@SerializedName("message")
@Expose
public String message;
@SerializedName("record")
@Expose
public List<GroupAgeResult> groupAgeResult = new ArrayList<GroupAgeResult>();

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

    public List<GroupAgeResult> getRecord() {
        return groupAgeResult;
    }

    public void setRecord(List<GroupAgeResult> groupAgeResult) {
        this.groupAgeResult = groupAgeResult;
    }
}