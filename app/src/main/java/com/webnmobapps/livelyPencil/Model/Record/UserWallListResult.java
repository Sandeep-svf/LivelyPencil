package com.webnmobapps.livelyPencil.Model.Record;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class    UserWallListResult {

@SerializedName("id")
@Expose
public Integer id;
@SerializedName("user_id")
@Expose
public Integer userId;
@SerializedName("title")
@Expose
public String title;
@SerializedName("file")
@Expose
public String file;
@SerializedName("description")
@Expose
public String description;
@SerializedName("type")
@Expose
public Integer type;
@SerializedName("posttime")
@Expose
public String posttime;
@SerializedName("total_like")
@Expose
public Integer totalLike;
@SerializedName("total_Comment")
@Expose
public Integer totalComment;
@SerializedName("total_friend")
@Expose
public Integer totalFriend;
@SerializedName("firstname")
@Expose
public String firstname;
@SerializedName("lastname")
@Expose
public String lastname;
@SerializedName("image")
@Expose
public String image;
@SerializedName("total_post")
@Expose
public Integer totalPost;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getPosttime() {
        return posttime;
    }

    public void setPosttime(String posttime) {
        this.posttime = posttime;
    }

    public Integer getTotalLike() {
        return totalLike;
    }

    public void setTotalLike(Integer totalLike) {
        this.totalLike = totalLike;
    }

    public Integer getTotalComment() {
        return totalComment;
    }

    public void setTotalComment(Integer totalComment) {
        this.totalComment = totalComment;
    }

    public Integer getTotalFriend() {
        return totalFriend;
    }

    public void setTotalFriend(Integer totalFriend) {
        this.totalFriend = totalFriend;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getTotalPost() {
        return totalPost;
    }

    public void setTotalPost(Integer totalPost) {
        this.totalPost = totalPost;
    }
}