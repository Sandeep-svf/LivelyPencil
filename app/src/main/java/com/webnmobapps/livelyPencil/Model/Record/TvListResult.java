package com.webnmobapps.livelyPencil.Model.Record;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TvListResult {

    @SerializedName("tvlogo")
    @Expose
    public String tvlogo;
    @SerializedName("video")
    @Expose
    public String video;
    @SerializedName("comments")
    @Expose
    public Integer comments;
    @SerializedName("view")
    @Expose
    public Integer view;
    @SerializedName("Page")
    @Expose
    public Integer page;
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("stream_name")
    @Expose
    public String streamName;


    public String getStreamName() {
        return streamName;
    }

    public void setStreamName(String streamName) {
        this.streamName = streamName;
    }

    public String getTvlogo() {
        return tvlogo;
    }

    public void setTvlogo(String tvlogo) {
        this.tvlogo = tvlogo;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public Integer getComments() {
        return comments;
    }

    public void setComments(Integer comments) {
        this.comments = comments;
    }

    public Integer getView() {
        return view;
    }

    public void setView(Integer view) {
        this.view = view;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}