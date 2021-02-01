package com.mygdx.auber;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.google.gson.Gson;
import com.mygdx.auber.Screens.PlayScreen;
import com.mygdx.auber.entities.Player;

import java.util.Map;

public class LoadingGame {


    public void loadGame(Auber game){
        if (if_exists("Saved Game")){
            Gson gson = new Gson();
            String playerSave = Gdx.app.getPreferences("Saved Game").getString("playerInfo");
            PlayerInfo playerInfo = gson.fromJson(playerSave, PlayerInfo.class);
            //sets the starting game setting to be correct on creation
            game.setScreen(new PlayScreen(game, false,playerInfo.numberOfInfiltrators,playerInfo.numberOfCrew,playerInfo.maxIncorrectArrests, true));

        }

    }
    public boolean if_exists(String prefname)
    //code for checking if a preference exists
    //https://badlogicgames.com/forum/viewtopic.php?f=11&t=21008
    {
        Preferences tmprefs = Gdx.app.getPreferences ( prefname );
        Map tmpmap = tmprefs.get();
        if ( tmpmap.isEmpty() == true )
            return false;
        else
            return true;
    }

}
