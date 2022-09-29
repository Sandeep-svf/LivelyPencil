package com.webnmobapps.livelyPencil.ModelPython;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PostListDataPython {

@SerializedName("id")
@Expose
public Integer id;
@SerializedName("created_at")
@Expose
public String createdAt;
@SerializedName("updated_at")
@Expose
public String updatedAt;
@SerializedName("title")
@Expose
public String title;
@SerializedName("description")
@Expose
public String description;
@SerializedName("file")
@Expose
public String file;
@SerializedName("role")
@Expose
public String role;
@SerializedName("type")
@Expose
public String type;
@SerializedName("user")
@Expose
public Integer user;
@SerializedName("interersting")
@Expose
public Integer interersting;
@SerializedName("likes")
@Expose
public List<Object> likes = null;
@SerializedName("dislikes")
@Expose
public List<Object> dislikes = null;

    public Integer getId() {
        return id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getFile() {
        return file;
    }

    public String getRole() {
        return role;
    }

    public String getType() {
        return type;
    }

    public Integer getUser() {
        return user;
    }

    public Integer getInterersting() {
        return interersting;
    }

    public List<Object> getLikes() {
        return likes;
    }

    public List<Object> getDislikes() {
        return dislikes;
    }
}