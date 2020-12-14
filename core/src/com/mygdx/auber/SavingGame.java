package com.mygdx.auber;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.google.gson.Gson;
import com.mygdx.auber.entities.Player;

public class SavingGame {



    public void playerSave(){
        Preferences prefs = Gdx.app.getPreferences("Saved Game");
        Gson gson = new Gson();

        //saves player info
        PlayerInfo playerInfo = new PlayerInfo();
        String player = gson.toJson(playerInfo);
        prefs.putString("playerInfo",player);

        //saves NPC info
        NPCInfo npcInfo = new NPCInfo();
        String npc = gson.toJson(npcInfo);
        prefs.putString("npcInfo",npc);

        //saves infiltrator info
        INFInfo infInfo = new INFInfo();
        String inf = gson.toJson(infInfo);
        prefs.putString("infInfo",inf);
        prefs.flush();



        System.out.println(inf);
    }

}
