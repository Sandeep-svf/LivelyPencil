package com.webnmobapps.livelyPencil.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.webnmobapps.livelyPencil.Model.Record.TvListResult;

import java.util.ArrayList;
import java.util.List;

public class TvListModel {

@SerializedName("success")
@Expose
public String success;
@SerializedName("message")
@Expose
public String message;
@SerializedName("record")
@Expose
public List<TvListResult> tvListResult = new ArrayList<TvListResult>();


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

    public List<TvListResult> getRecord() {
        return tvListResult;
    }

    public void setRecord(List<TvListResult> tvListResult) {
        this.tvListResult = tvListResult;
    }
}