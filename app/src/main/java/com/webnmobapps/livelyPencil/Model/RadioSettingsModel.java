package com.webnmobapps.livelyPencil.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.webnmobapps.livelyPencil.Model.Record.RadioSettingsResult;

import java.util.ArrayList;
import java.util.List;

public class RadioSettingsModel {

@SerializedName("success")
@Expose
public String success;
@SerializedName("message")
@Expose
public String message;
@SerializedName("record")
@Expose
public List<RadioSettingsResult> radioSettingsResult = new ArrayList<RadioSettingsResult>();


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

    public List<RadioSettingsResult> getRecord() {
        return radioSettingsResult;
    }

    public void setRecord(List<RadioSettingsResult> radioSettingsResult) {
        this.radioSettingsResult = radioSettingsResult;
    }
}