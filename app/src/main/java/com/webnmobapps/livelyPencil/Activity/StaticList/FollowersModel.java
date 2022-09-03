package com.webnmobapps.livelyPencil.Activity.StaticList;

public class FollowersModel {
    int followersStatus ;
    int position ;

    public FollowersModel(int followersStatus, int position) {
        this.followersStatus = followersStatus;
        this.position = position;
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
