package com.webnmobapps.livelyPencil.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.webnmobapps.livelyPencil.Model.Record.StreamSettingResult;

import java.util.ArrayList;
import java.util.List;

public class StreamSettingModel {

@SerializedName("success")
@Expose
public String success;
@SerializedName("message")
@Expose
public String message;
@SerializedName("record")
@Expose
public List<StreamSettingResult> streamSettingResult = new ArrayList<StreamSettingResult>();

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

    public List<StreamSettingResult> getRecord() {
        return streamSettingResult;
    }

    public void setRecord(List<StreamSettingResult> streamSettingResult) {
        this.streamSettingResult = streamSettingResult;
    }
}