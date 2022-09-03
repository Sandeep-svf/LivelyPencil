package com.webnmobapps.livelyPencil.Model.Record;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StreamPageResult {
    @SerializedName("streamtitle")
    @Expose
    public String streamtitle;
    @SerializedName("streamcoverimage")
    @Expose
    public String streamcoverimage;
    @SerializedName("image")
    @Expose
    public String image;
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("totalfollower")
    @Expose
    public Integer totalfollower;
    @SerializedName("totallike")
    @Expose
    public Integer totallike;
    @SerializedName("totalimage")
    @Expose
    public Integer totalimage;
    @SerializedName("totalvideo")
    @Expose
    public Integer totalvideo;
    @SerializedName("audio")
    @Expose
    public Integer audio;
    @SerializedName("TotalComment")
    @Expose
    public Integer totalComment;


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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTotalfollower() {
        return totalfollower;
    }

    public void setTotalfollower(Integer totalfollower) {
        this.totalfollower = totalfollower;
    }

    public Integer getTotallike() {
        return totallike;
    }

    public void setTotallike(Integer totallike) {
        this.totallike = totallike;
    }

    public Integer getTotalimage() {
        return totalimage;
    }

    public void setTotalimage(Integer totalimage) {
        this.totalimage = totalimage;
    }

    public Integer getTotalvideo() {
        return totalvideo;
    }

    public void setTotalvideo(Integer totalvideo) {
        this.totalvideo = totalvideo;
    }

    public Integer getAudio() {
        return audio;
    }

    public void setAudio(Integer audio) {
        this.audio = audio;
    }

    public Integer getTotalComment() {
        return totalComment;
    }

    public void setTotalComment(Integer totalComment) {
        this.totalComment = totalComment;
    }
}