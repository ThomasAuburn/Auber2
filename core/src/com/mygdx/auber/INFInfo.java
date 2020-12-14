package com.mygdx.auber;

import com.mygdx.auber.Screens.PlayScreen;
import com.mygdx.auber.entities.CrewMembers;
import com.mygdx.auber.entities.Infiltrator;
import com.mygdx.auber.entities.NPCCreator;

import java.util.ArrayList;
import java.util.List;

public class INFInfo {
    public List<CrewModel> data = new ArrayList<>();
    public int infNumber;
    public INFInfo() {

        for (Infiltrator inf: NPCCreator.infiltrators){
            if (! inf.isDestroying) {
                data.add(new CrewModel(inf.getX(), inf.getY(), inf.currentImage, inf.currentGoal.x, inf.currentGoal.y, inf.isDestroying));
            }
            else{
                data.add(new CrewModel(inf.getX(), inf.getY(), inf.currentImage, (float) 1700, (float) 3000, inf.isDestroying));
            }
            //,crew.currentGoal.x,crew.currentGoal.y
        }
        this.infNumber = PlayScreen.numberOfInfiltrators;
    }
}
