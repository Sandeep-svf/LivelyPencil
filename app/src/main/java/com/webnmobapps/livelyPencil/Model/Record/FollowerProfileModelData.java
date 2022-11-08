package com.webnmobapps.livelyPencil.Model.Record;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FollowerProfileModelData {

@SerializedName("id")
@Expose
public Integer id;
@SerializedName("first_name")
@Expose
public String firstName;
@SerializedName("last_name")
@Expose
public String lastName;
@SerializedName("stream_title")
@Expose
public String streamTitle;
@SerializedName("stream_cover_image")
@Expose
public String streamCoverImage;
@SerializedName("image")
@Expose
public String image;
@SerializedName("created_at")
@Expose
public String createdAt;
@SerializedName("updated_at")
@Expose
public String updatedAt;
@SerializedName("followers_count")
@Expose
public Integer followersCount;
@SerializedName("likes_count")
@Expose
public Integer likesCount;
@SerializedName("followed")
@Expose
public Boolean followed;

    public Integer getId() {
        return id;
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

    public String getStreamCoverImage() {
        return streamCoverImage;
    }

    public String getImage() {
        return image;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public Integer getFollowersCount() {
        return followersCount;
    }

    public Integer getLikesCount() {
        return likesCount;
    }

    public Boolean getFollowed() {
        return followed;
    }
}