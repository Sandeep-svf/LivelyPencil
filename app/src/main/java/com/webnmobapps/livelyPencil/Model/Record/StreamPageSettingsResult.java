package com.webnmobapps.livelyPencil.Model.Record;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StreamPageSettingsResult {

@SerializedName("fornttype")
@Expose
public String fornttype;
@SerializedName("forntcolor")
@Expose
public String forntcolor;
@SerializedName("textcolor")
@Expose
public String textcolor;
@SerializedName("background")
@Expose
public String background;
@SerializedName("followrequest")
@Expose
public String followrequest;
@SerializedName("comments")
@Expose
public Integer comments;
@SerializedName("mailbox")
@Expose
public Integer mailbox;

    public String getFornttype() {
        return fornttype;
    }

    public void setFornttype(String fornttype) {
        this.fornttype = fornttype;
    }

    public String getForntcolor() {
        return forntcolor;
    }

    public void setForntcolor(String forntcolor) {
        this.forntcolor = forntcolor;
    }

    public String getTextcolor() {
        return textcolor;
    }

    public void setTextcolor(String textcolor) {
        this.textcolor = textcolor;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getFollowrequest() {
        return followrequest;
    }

    public void setFollowrequest(String followrequest) {
        this.followrequest = followrequest;
    }

    public Integer getComments() {
        return comments;
    }

    public void setComments(Integer comments) {
        this.comments = comments;
    }

    public Integer getMailbox() {
        return mailbox;
    }

    public void setMailbox(Integer mailbox) {
        this.mailbox = mailbox;
    }
}