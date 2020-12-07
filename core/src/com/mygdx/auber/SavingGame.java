package com.mygdx.auber;

import com.google.gson.Gson;
import com.mygdx.auber.entities.Player;

public class SavingGame {



    public void testing(){
        Gson gson = new Gson();
        PlayerInfo playerInfo = new PlayerInfo();
        String player = gson.toJson(playerInfo);
        System.out.println(player);
    }

}
