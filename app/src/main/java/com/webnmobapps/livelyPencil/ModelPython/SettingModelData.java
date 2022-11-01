package com.webnmobapps.livelyPencil.ModelPython;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SettingModelData {

@SerializedName("id")
@Expose
public Integer id;
@SerializedName("username")
@Expose
public String username;
@SerializedName("first_name")
@Expose
public String firstName;
@SerializedName("last_name")
@Expose
public String lastName;
@SerializedName("stream_title")
@Expose
public String streamTitle;
@SerializedName("birth_date")
@Expose
public String birthDate;
@SerializedName("country")
@Expose
public String country;

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getStreamTitle() {
        return streamTitle;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getCountry() {
        return country;
    }
}