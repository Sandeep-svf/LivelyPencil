package com.webnmobapps.livelyPencil.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.webnmobapps.livelyPencil.Model.Record.StreamPageResult;

import java.util.ArrayList;
import java.util.List;

public class StreamPageModel {

@SerializedName("success")
@Expose
public String success;
@SerializedName("message")
@Expose
public String message;
@SerializedName("record")
@Expose
public List<StreamPageResult> streamPageResult = new ArrayList<StreamPageResult>();


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

    public List<StreamPageResult> getRecord() {
        return streamPageResult;
    }

    public void setRecord(List<StreamPageResult> streamPageResult) {
        this.streamPageResult = streamPageResult;
    }
}