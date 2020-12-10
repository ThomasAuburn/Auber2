package com.mygdx.auber;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.google.gson.Gson;
import com.mygdx.auber.Screens.PlayScreen;
import com.mygdx.auber.entities.Player;

public class LoadingGame {


    public void loadGame(Auber game){
//        Gson gson = new Gson();
//        PlayerInfo playerInfo = new PlayerInfo();
//        String player = gson.toJson(playerInfo);
//        System.out.println(player);
//        Preferences prefs = Gdx.app.getPreferences("Saved Game");
//        String player = Gdx.app.getPreferences("Saved Game").getString("playerInfo");
//        System.out.println(player);
        game.setScreen(new PlayScreen(game, false,6,15,5,true));

    }

}
