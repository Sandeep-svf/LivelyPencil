package com.webnmobapps.livelyPencil.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.webnmobapps.livelyPencil.Model.Record.TvSettingsResult;

import java.util.ArrayList;
import java.util.List;

public class TvSettingsModel {

@SerializedName("success")
@Expose
public String success;
@SerializedName("message")
@Expose
public String message;
@SerializedName("record")
@Expose
public List<TvSettingsResult> tvSettingsResult = new ArrayList<TvSettingsResult>();

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

    public List<TvSettingsResult> getRecord() {
        return tvSettingsResult;
    }

    public void setRecord(List<TvSettingsResult> tvSettingsResult) {
        this.tvSettingsResult = tvSettingsResult;
    }
}