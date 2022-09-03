package com.webnmobapps.livelyPencil.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.webnmobapps.livelyPencil.Model.Record.PersonalInformationSettingWizardResult;

import java.util.ArrayList;
import java.util.List;

public class PersonalInformationSettingWizardModel {

@SerializedName("success")
@Expose
public String success;
@SerializedName("message")
@Expose
public String message;
@SerializedName("record")
@Expose
public List<PersonalInformationSettingWizardResult> personalInformationSettingWizardResult = new ArrayList<PersonalInformationSettingWizardResult>();


    public String getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public List<PersonalInformationSettingWizardResult> getRecord() {
        return personalInformationSettingWizardResult;
    }
}