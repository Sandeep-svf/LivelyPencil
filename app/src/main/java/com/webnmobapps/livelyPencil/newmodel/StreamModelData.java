package com.webnmobapps.livelyPencil.newmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StreamModelData {



    @SerializedName("created_at")
    @Expose
    public String created_at;


@SerializedName("id")
@Expose
public Integer id;
@SerializedName("stream_title")
@Expose
public String streamTitle;
@SerializedName("stream_cover_image")
@Expose
public String streamCoverImage;

    public String getCreated_at() {
        return created_at;
    }

    public Integer getId() {
        return id;
    }

    public String getStreamTitle() {
        return streamTitle;
    }

    public String getStreamCoverImage() {
        return streamCoverImage;
    }
}