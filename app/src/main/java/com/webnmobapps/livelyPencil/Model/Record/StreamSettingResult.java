package com.webnmobapps.livelyPencil.Model.Record;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StreamSettingResult {

@SerializedName("id")
@Expose
public Integer id;
@SerializedName("streamtitle")
@Expose
public String streamtitle;
@SerializedName("streamcoverimage")
@Expose
public String streamcoverimage;
@SerializedName("username")
@Expose
public String username;
    @SerializedName("streamaboutme")
    @Expose
    public Object streamaboutme;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStreamtitle() {
        return streamtitle;
    }

    public void setStreamtitle(String streamtitle) {
        this.streamtitle = streamtitle;
    }

    public String getStreamcoverimage() {
        return streamcoverimage;
    }

    public void setStreamcoverimage(String streamcoverimage) {
        this.streamcoverimage = streamcoverimage;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Object getStreamaboutme() {
        return streamaboutme;
    }

    public void setStreamaboutme(Object streamaboutme) {
        this.streamaboutme = streamaboutme;
    }
}