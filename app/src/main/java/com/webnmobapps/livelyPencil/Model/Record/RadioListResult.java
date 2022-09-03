package com.webnmobapps.livelyPencil.Model.Record;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RadioListResult {

@SerializedName("radiologo")
@Expose
public String radiologo;
@SerializedName("audio")
@Expose
public String audio;
@SerializedName("comments")
@Expose
public Integer comments;
@SerializedName("listener")
@Expose
public Integer listener;
@SerializedName("Page")
@Expose
public Integer page;
@SerializedName("id")
@Expose
public Integer id;
    @SerializedName("Profilename")
    @Expose
    public String Profilename;
    @SerializedName("Streamname")
    @Expose
    public String Streamname;


    public String getProfilename() {
        return Profilename;
    }

    public void setProfilename(String profilename) {
        Profilename = profilename;
    }

    public String getStreamname() {
        return Streamname;
    }

    public void setStreamname(String streamname) {
        Streamname = streamname;
    }

    public String getRadiologo() {
        return radiologo;
    }

    public void setRadiologo(String radiologo) {
        this.radiologo = radiologo;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public Integer getComments() {
        return comments;
    }

    public void setComments(Integer comments) {
        this.comments = comments;
    }

    public Integer getListener() {
        return listener;
    }

    public void setListener(Integer listener) {
        this.listener = listener;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}