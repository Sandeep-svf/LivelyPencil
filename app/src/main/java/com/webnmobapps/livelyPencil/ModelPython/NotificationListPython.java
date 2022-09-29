package com.webnmobapps.livelyPencil.ModelPython;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NotificationListPython {

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
@SerializedName("message")
@Expose
public String message;
@SerializedName("recieved_date")
@Expose
public String recievedDate;
@SerializedName("sender")
@Expose
public Integer sender;
@SerializedName("recipient")
@Expose
public List<Integer> recipient = null;
@SerializedName("is_seen")
@Expose
public List<Integer> isSeen = null;


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

    public String getMessage() {
        return message;
    }

    public String getRecievedDate() {
        return recievedDate;
    }

    public Integer getSender() {
        return sender;
    }

    public List<Integer> getRecipient() {
        return recipient;
    }

    public List<Integer> getIsSeen() {
        return isSeen;
    }
}