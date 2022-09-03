package com.webnmobapps.livelyPencil.Model.Record;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PPSettingsRessult {

@SerializedName("profile")
@Expose
public Integer profile;
@SerializedName("Friend")
@Expose
public Integer friend;
@SerializedName("Message")
@Expose
public Integer message;
@SerializedName("Mailbox")
@Expose
public Integer mailbox;
@SerializedName("Relationships")
@Expose
public Integer relationships;
@SerializedName("TVChannel")
@Expose
public Integer tVChannel;
@SerializedName("RadioChannel")
@Expose
public Integer radioChannel;
@SerializedName("Photos")
@Expose
public Integer photos;
@SerializedName("Activities")
@Expose
public Integer activities;
@SerializedName("Email")
@Expose
public Integer email;
@SerializedName("Phone")
@Expose
public Integer phone;
@SerializedName("WebUrl")
@Expose
public Integer webUrl;
@SerializedName("Aboutme")
@Expose
public Integer aboutme;
@SerializedName("MyStatics")
@Expose
public Integer myStatics;
@SerializedName("id")
@Expose
public Integer id;

    public Integer getProfile() {
        return profile;
    }

    public void setProfile(Integer profile) {
        this.profile = profile;
    }

    public Integer getFriend() {
        return friend;
    }

    public void setFriend(Integer friend) {
        this.friend = friend;
    }

    public Integer getMessage() {
        return message;
    }

    public void setMessage(Integer message) {
        this.message = message;
    }

    public Integer getMailbox() {
        return mailbox;
    }

    public void setMailbox(Integer mailbox) {
        this.mailbox = mailbox;
    }

    public Integer getRelationships() {
        return relationships;
    }

    public void setRelationships(Integer relationships) {
        this.relationships = relationships;
    }

    public Integer gettVChannel() {
        return tVChannel;
    }

    public void settVChannel(Integer tVChannel) {
        this.tVChannel = tVChannel;
    }

    public Integer getRadioChannel() {
        return radioChannel;
    }

    public void setRadioChannel(Integer radioChannel) {
        this.radioChannel = radioChannel;
    }

    public Integer getPhotos() {
        return photos;
    }

    public void setPhotos(Integer photos) {
        this.photos = photos;
    }

    public Integer getActivities() {
        return activities;
    }

    public void setActivities(Integer activities) {
        this.activities = activities;
    }

    public Integer getEmail() {
        return email;
    }

    public void setEmail(Integer email) {
        this.email = email;
    }

    public Integer getPhone() {
        return phone;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    public Integer getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(Integer webUrl) {
        this.webUrl = webUrl;
    }

    public Integer getAboutme() {
        return aboutme;
    }

    public void setAboutme(Integer aboutme) {
        this.aboutme = aboutme;
    }

    public Integer getMyStatics() {
        return myStatics;
    }

    public void setMyStatics(Integer myStatics) {
        this.myStatics = myStatics;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}