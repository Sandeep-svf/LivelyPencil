package com.webnmobapps.livelyPencil.Model.Record;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RadioSettingsResult {

@SerializedName("radiologo")
@Expose
public String radiologo;
@SerializedName("topic")
@Expose
public String topic;
@SerializedName("agegroup")
@Expose
public String agegroup;
@SerializedName("otherTags")
@Expose
public String otherTags;
@SerializedName("radiocover")
@Expose
public String radiocover;
@SerializedName("id")
@Expose
public Integer id;

    public String getRadiologo() {
        return radiologo;
    }

    public void setRadiologo(String radiologo) {
        this.radiologo = radiologo;
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

    public String getRadiocover() {
        return radiocover;
    }

    public void setRadiocover(String radiocover) {
        this.radiocover = radiocover;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}