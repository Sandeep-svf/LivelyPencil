package com.webnmobapps.livelyPencil.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.webnmobapps.livelyPencil.Model.Record.ContactInformationResult;

import java.util.ArrayList;
import java.util.List;

public class ContactInformationModel {

@SerializedName("success")
@Expose
public String success;
@SerializedName("message")
@Expose
public String message;
@SerializedName("record")
@Expose
public List<ContactInformationResult> contactInformationResult = new ArrayList<ContactInformationResult>();

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

    public List<ContactInformationResult> getRecord() {
        return contactInformationResult;
    }

    public void setRecord(List<ContactInformationResult> contactInformationResult) {
        this.contactInformationResult = contactInformationResult;
    }
}