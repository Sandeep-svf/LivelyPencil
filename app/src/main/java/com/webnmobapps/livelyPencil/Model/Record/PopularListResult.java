package com.webnmobapps.livelyPencil.Model.Record;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PopularListResult {

    @SerializedName("followerStatus")
    @Expose
    public Integer followerStatus;
@SerializedName("id")
@Expose
public Integer id;
@SerializedName("firstname")
@Expose
public String firstname;
@SerializedName("lastname")
@Expose
public String lastname;
@SerializedName("image")
@Expose
public String image;
@SerializedName("streamtitle")
@Expose
public String streamtitle;
@SerializedName("streamcoverimage")
@Expose
public String streamcoverimage;


    @SerializedName("total-page")
    @Expose
    public Integer totalPpage;

@SerializedName("total-like")
@Expose
public Integer totalLike;
@SerializedName("last_stream")
@Expose
public String lastStream;
@SerializedName("total-followers")
@Expose
public Integer totalFollowers;

    public Integer getFollowerStatus() {
        return followerStatus;
    }

    public Integer getTotalPpage() {
        return totalPpage;
    }

    public Integer getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getImage() {
        return image;
    }

    public String getStreamtitle() {
        return streamtitle;
    }

    public String getStreamcoverimage() {
        return streamcoverimage;
    }

    public Integer getTotalLike() {
        return totalLike;
    }

    public String getLastStream() {
        return lastStream;
    }

    public Integer getTotalFollowers() {
        return totalFollowers;
    }

    public void setFollowerStatus(Integer followerStatus) {
        this.followerStatus = followerStatus;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setStreamtitle(String streamtitle) {
        this.streamtitle = streamtitle;
    }

    public void setStreamcoverimage(String streamcoverimage) {
        this.streamcoverimage = streamcoverimage;
    }

    public void setTotalPpage(Integer totalPpage) {
        this.totalPpage = totalPpage;
    }

    public void setTotalLike(Integer totalLike) {
        this.totalLike = totalLike;
    }

    public void setLastStream(String lastStream) {
        this.lastStream = lastStream;
    }

    public void setTotalFollowers(Integer totalFollowers) {
        this.totalFollowers = totalFollowers;
    }
}