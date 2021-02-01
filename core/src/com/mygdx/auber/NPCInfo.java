package com.mygdx.auber;

import com.badlogic.gdx.utils.Array;
import com.mygdx.auber.Screens.PlayScreen;
import com.mygdx.auber.entities.CrewMembers;
import com.mygdx.auber.entities.NPCCreator;

import java.util.ArrayList;
import java.util.List;


public class NPCInfo {
    public List<CrewModel> data = new ArrayList<>();
    public int npcNumber;
    public NPCInfo() {

        for (CrewMembers crew: NPCCreator.crew){
            data.add(new CrewModel(crew.getX(),crew.getY(),crew.currentImage,crew.currentGoal.x,crew.currentGoal.y,false));
            //,crew.currentGoal.x,crew.currentGoal.y
        }
        this.npcNumber = PlayScreen.numberOfCrew;
    }
}


