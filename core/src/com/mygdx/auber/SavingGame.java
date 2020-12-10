package com.mygdx.auber;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.google.gson.Gson;
import com.mygdx.auber.entities.Player;

public class SavingGame {



    public void playerSave(){
        Preferences prefs = Gdx.app.getPreferences("Saved Game");
        Gson gson = new Gson();
        PlayerInfo playerInfo = new PlayerInfo();
        String player = gson.toJson(playerInfo);
        prefs.putString("playerInfo",player);
        prefs.flush();
        System.out.println(player);
    }

}
