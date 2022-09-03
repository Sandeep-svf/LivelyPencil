package com.webnmobapps.livelyPencil.Model;

public class SecondPositionModel {
    int pos;
    Boolean posValue=false;

    public SecondPositionModel(Boolean posValue, int pos)
    {
        this.posValue = posValue;
        this.pos = pos;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public Boolean getPosValue() {
        return posValue;
    }

    public void setPosValue(Boolean posValue) {
        this.posValue = posValue;
    }
}
