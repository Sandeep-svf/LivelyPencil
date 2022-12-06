package com.webnmobapps.livelyPencil.Activity.StaticList;

public class FollowersModel {
    int followersStatus ;
    int position ;
    int userId;

    public FollowersModel(int followersStatus, int position, int userId) {
        this.followersStatus = followersStatus;
        this.position = position;
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getFollowersStatus() {
        return followersStatus;
    }

    public void setFollowersStatus(int followersStatus) {
        this.followersStatus = followersStatus;
    }
}
