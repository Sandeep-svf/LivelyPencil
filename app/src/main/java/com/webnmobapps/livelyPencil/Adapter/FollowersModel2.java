package com.webnmobapps.livelyPencil.Adapter;

public class FollowersModel2 {
    int followersStatus ;
    int position ;


    public FollowersModel2(int followersStatus, int position) {
        this.followersStatus = followersStatus;
        this.position = position;

    }

    public int getFollowersStatus() {
        return followersStatus;
    }

    public void setFollowersStatus(int followersStatus) {
        this.followersStatus = followersStatus;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
