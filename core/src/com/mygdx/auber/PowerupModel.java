package com.mygdx.auber;

import com.mygdx.auber.entities.Powerup;

public class PowerupModel {
    public Float x, y,goalX,goalY;
    public String ability;
    public double chance;

    public PowerupModel(Float x, Float y, double chance) {
        this.x = x;
        this.y = y;
        this.chance = chance;

    }
}