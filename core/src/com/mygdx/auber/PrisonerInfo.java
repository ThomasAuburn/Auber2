package com.mygdx.auber;

import com.badlogic.gdx.utils.Array;
import com.mygdx.auber.entities.Prisoners;

import java.util.ArrayList;
import java.util.List;

public class PrisonerInfo {
    public List<PrisonerModel> data = new ArrayList<>();
    public int prisNumber;

    public PrisonerInfo() {
        for (int i = 0; i < Prisoners.prisonerSide.size(); i++) {
            data.add(new PrisonerModel(Prisoners.prisonerSide.get(i),Prisoners.prisonerChance.get(i)));
        }
        this.prisNumber = Prisoners.prisonerSide.size();
    }
}
