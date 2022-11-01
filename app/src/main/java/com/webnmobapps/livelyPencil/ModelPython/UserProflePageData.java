package com.webnmobapps.livelyPencil.ModelPython;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserProflePageData {

@SerializedName("text__count")
@Expose
public Integer textCount;

    public Integer getTextCount() {
        return textCount;
    }
}