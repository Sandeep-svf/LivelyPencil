package com.webnmobapps.livelyPencil.Model.Record;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PersonalInformationSettingWizardResult {

@SerializedName("firstname")
@Expose
public String firstname;
@SerializedName("lastname")
@Expose
public String lastname;
@SerializedName("ProfileImage")
@Expose
public String profileImage;
@SerializedName("birth_date")
@Expose
public String birthDate;
@SerializedName("Location")
@Expose
public String location;
@SerializedName("relationship")
@Expose
public String relationship;
@SerializedName("business")
@Expose
public Integer business;
@SerializedName("education")
@Expose
public Integer education;
@SerializedName("gender")
@Expose
public Integer gender;

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getLocation() {
        return location;
    }

    public String getRelationship() {
        return relationship;
    }

    public Integer getBusiness() {
        return business;
    }

    public Integer getEducation() {
        return education;
    }

    public Integer getGender() {
        return gender;
    }
}