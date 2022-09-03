package com.webnmobapps.livelyPencil.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.webnmobapps.livelyPencil.Model.Record.EducationResult;

import java.util.ArrayList;
import java.util.List;

public class EducationModel {

@SerializedName("success")
@Expose
public String success;
@SerializedName("message")
@Expose
public String message;
@SerializedName("record")
@Expose
public List<EducationResult> record = new ArrayList<EducationResult>();

    public String getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public List<EducationResult> getRecord() {
        return record;
    }
}