package com.webnmobapps.livelyPencil.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.webnmobapps.livelyPencil.Model.Record.PopularListResult;

import java.util.ArrayList;
import java.util.List;

public class PopularListModel {

@SerializedName("success")
@Expose
public String success;
@SerializedName("message")
@Expose
public String message;
@SerializedName("record")
@Expose
public List<PopularListResult> popularListResult = new ArrayList<PopularListResult>();


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

    public List<PopularListResult> getPopularListResult() {
        return popularListResult;
    }

    public void setPopularListResult(List<PopularListResult> popularListResult) {
        this.popularListResult = popularListResult;
    }
}