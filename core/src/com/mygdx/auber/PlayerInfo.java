package com.mygdx.auber;

import com.mygdx.auber.entities.Player;

public class PlayerInfo {
    public float x;
    public float y;
    public float health;
    public PlayerInfo() {
        this.x = Player.x;
        this.y = Player.y;
        this.health= Player.health;
    }
}
