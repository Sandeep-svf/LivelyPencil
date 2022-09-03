package com.webnmobapps.livelyPencil.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.webnmobapps.livelyPencil.Model.Record.PPSettingsRessult;

import java.util.ArrayList;
import java.util.List;

public class PPSettingsModel {

@SerializedName("success")
@Expose
public String success;
@SerializedName("message")
@Expose
public String message;
@SerializedName("record")
@Expose
public List<PPSettingsRessult> PPSettingsRessult = new ArrayList<PPSettingsRessult>();

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

    public List<PPSettingsRessult> getRecord() {
        return PPSettingsRessult;
    }

    public void setRecord(List<PPSettingsRessult> PPSettingsRessult) {
        this.PPSettingsRessult = PPSettingsRessult;
    }
}