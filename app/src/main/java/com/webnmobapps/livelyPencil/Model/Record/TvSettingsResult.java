package com.webnmobapps.livelyPencil.Model.Record;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TvSettingsResult {

@SerializedName("tvlogo")
@Expose
public String tvlogo;
@SerializedName("topic")
@Expose
public String topic;
@SerializedName("agegroup")
@Expose
public String agegroup;
@SerializedName("otherTags")
@Expose
public String otherTags;
@SerializedName("tvcover")
@Expose
public String tvcover;
@SerializedName("id")
@Expose
public Integer id;

    public String getTvlogo() {
        return tvlogo;
    }

    public void setTvlogo(String tvlogo) {
        this.tvlogo = tvlogo;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getAgegroup() {
        return agegroup;
    }

    public void setAgegroup(String agegroup) {
        this.agegroup = agegroup;
    }

    public String getOtherTags() {
        return otherTags;
    }

    public void setOtherTags(String otherTags) {
        this.otherTags = otherTags;
    }

    public String getTvcover() {
        return tvcover;
    }

    public void setTvcover(String tvcover) {
        this.tvcover = tvcover;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}