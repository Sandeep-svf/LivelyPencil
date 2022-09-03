package com.webnmobapps.livelyPencil.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.webnmobapps.livelyPencil.Model.Record.RadioListResult;

import java.util.ArrayList;
import java.util.List;

public class RadioListModel {

@SerializedName("success")
@Expose
public String success;
@SerializedName("message")
@Expose
public String message;
@SerializedName("record")
@Expose
public List<RadioListResult> radioListResult = new ArrayList<RadioListResult>();


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

    public List<RadioListResult> getRecord() {
        return radioListResult;
    }

    public void setRecord(List<RadioListResult> radioListResult) {
        this.radioListResult = radioListResult;
    }
}