package com.webnmobapps.livelyPencil.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.webnmobapps.livelyPencil.Model.Record.IntrestListModelRecord;

import java.util.ArrayList;
import java.util.List;

public class IntrestListModel {

@SerializedName("success")
@Expose
public String success;
@SerializedName("message")
@Expose
public String message;
@SerializedName("record")
@Expose
public List<IntrestListModelRecord> intrestListModelRecord = new ArrayList<IntrestListModelRecord>();


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

    public List<IntrestListModelRecord> getIntrestListModelRecord() {
        return intrestListModelRecord;
    }

    public void setIntrestListModelRecord(List<IntrestListModelRecord> intrestListModelRecord) {
        this.intrestListModelRecord = intrestListModelRecord;
    }
}