package com.webnmobapps.livelyPencil.Model.Record;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ContactInformationResult {

@SerializedName("id")
@Expose
public Integer id;
@SerializedName("email")
@Expose
public String email;
@SerializedName("phone")
@Expose
public String phone;
@SerializedName("url")
@Expose
public String url;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}