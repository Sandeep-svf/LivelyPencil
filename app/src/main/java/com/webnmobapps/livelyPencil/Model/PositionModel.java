package com.webnmobapps.livelyPencil.Model;

public class PositionModel
{
    int pos;
    Boolean posValue=false;

    public PositionModel(Boolean posValue, int pos)
    {
        this.posValue = posValue;
        this.pos = pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public void setPosValue(Boolean posValue) {
        this.posValue = posValue;
    }

    public int getPos() {
        return pos;
    }

    public Boolean getPosValue() {
        return posValue;
    }
}
