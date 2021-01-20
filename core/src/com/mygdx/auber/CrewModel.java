package com.mygdx.auber;

public class CrewModel {
    public Float x, y,goalX,goalY;
    public double chance;
    public boolean destoying;

    public CrewModel(Float x, Float y,double chance,Float goalX, Float goalY,Boolean destoying) {
        this.x = x;
        this.y = y;
        this.chance = chance;
        this.goalX = goalX;
        this.goalY = goalY;
        this.destoying = destoying;

    }
}