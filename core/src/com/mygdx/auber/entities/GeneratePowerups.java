package com.mygdx.auber.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;
import com.mygdx.auber.Pathfinding.Node;


public class GeneratePowerups {
    public static Array<Powerup> Powerups= new Array<>();

    private static int lastPowerupIndex = 0;


    /**
     * Creates powerups, adds them to the array, sets its index and increments the index counter
     * @param sprite Sprite to give infiltrator
     * @param position Start node for infiltrator
     */
    public static void createPowerups(Sprite sprite, Node position, double chance)
    {
        Powerup powerup = new Powerup(sprite, position, chance);
        Powerups.add(powerup);
        powerup.setIndex(lastPowerupIndex);
        lastPowerupIndex++;
    }


    /**
     * Removes power-up for given id
     * @param id id to remove
     */

    public static void removePowerup(int id)
    {
        Powerups.removeIndex(id);
        if(Powerups.isEmpty())
        {
            return;
        }
        for(int i = id; i < Powerups.size; i++)
        {
            Powerup powerup = Powerups.get(i);
            powerup.index -= 1;
        }
    }

    public static void dispose()
    {
        lastPowerupIndex = 0;
        Powerups.clear();
    }
}
