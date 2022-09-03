package com.webnmobapps.livelyPencil.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.webnmobapps.livelyPencil.Model.Record.ShareSettingDetailsResult;

import java.util.ArrayList;
import java.util.List;

public class ShareSettingDetailsModel {

@SerializedName("success")
@Expose
public String success;
@SerializedName("message")
@Expose
public String message;
@SerializedName("record")
@Expose
public List<ShareSettingDetailsResult> shareSettingDetailsResult = new ArrayList<ShareSettingDetailsResult>();


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

    public List<ShareSettingDetailsResult> getRecord() {
        return shareSettingDetailsResult;
    }

    public void setRecord(List<ShareSettingDetailsResult> shareSettingDetailsResult) {
        this.shareSettingDetailsResult = shareSettingDetailsResult;
    }
}