package com.mygdx.auber;

import com.mygdx.auber.Screens.PlayScreen;
import com.mygdx.auber.entities.Player;

public class PlayerInfo {
    public float x;
    public float y;
    public float health;
    public int numberOfInfiltrators;
    public int numberOfCrew;
    public int maxIncorrectArrests;

    public PlayerInfo() {
        this.x = Player.x;
        this.y = Player.y;
        this.health= Player.health;
        this.numberOfInfiltrators = PlayScreen.numberOfInfiltrators;
        this.numberOfCrew = PlayScreen.numberOfCrew;
        this.maxIncorrectArrests = PlayScreen.maxIncorrectArrests;
    }
}
