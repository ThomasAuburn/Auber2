package com.mygdx.auber.entities;

import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Queue;
import com.mygdx.auber.Pathfinding.GraphCreator;
import com.mygdx.auber.Pathfinding.Node;
import com.mygdx.auber.Screens.PlayScreen;


public class Powerup extends Sprite {

    public int index;

    String ability;
    double chance;

    Node position;

    public static Array<Sprite> sprites = new Array<>();

    /**
     * Constructor for Powerup
     * @param sprite Sprite to be used for the powerup
     * @param position Node for the powerup's position
     */
    public Powerup(Sprite sprite, Node position, double chance){
        super(sprite);
        this.chance = chance;
        this.position = position;
        this.setPosition(position.x ,position.y);


        if (chance < 1) {
            this.ability = "invisibility";
            //infiltrators will not react to player's presence, they will continue to damage systems and won't activate abilities
        }
        else if (chance < 2) {
            this.ability = "invulnerable";
            //player will not take damage
        }
        else if (chance < 3) {
            this.ability = "speed boost";
            //player moves faster
        }
        else if (chance < 4) {
            this.ability = "increased range";
            //increases the arrest range
        }
        else{
            this.ability = "freeze infiltrators";
            //infiltrators can't move
        }
    }

    public void setIndex(int index){
        this.index = index;
    }


    public static void render(Batch batch)
    {
        for (Powerup powerup: GeneratePowerups.Powerups)
        {
            powerup.draw(batch);
        }
    }

    /**
     * Updates every powerup, checking if in position to be picked up by player. To be called in a screens update method
     */
    public static void updatePowerup()
    {
        if(!GeneratePowerups.Powerups.isEmpty())
        {
            for (Powerup powerup:
                    GeneratePowerups.Powerups) {
                if(Vector2.dst(Player.x, Player.y, powerup.getX(), powerup.getY()) < 20)
                {
                    if (PlayScreen.player.activeAbility == "None"){
                        System.out.println(GeneratePowerups.Powerups.size);
                        PlayScreen.player.SetActiveAbility(powerup.ability,10);
                        GeneratePowerups.removePowerup(powerup.index);
                    }

                }
            }
        }

    }

    /**
     * Dispose method to be called in dispose method of screen
     */
    public static void disposePowerup()
    {
        for (Powerup powerup: GeneratePowerups.Powerups)
        {
            powerup.dispose();

        }
        GeneratePowerups.dispose();
    }


    public static void createPowerupSprites()
    {
        Powerup.sprites.add(new Sprite(new Texture("invisibility.png")));
        Powerup.sprites.add(new Sprite(new Texture("invulnerability.png")));
        Powerup.sprites.add(new Sprite(new Texture("speed.png")));
        Powerup.sprites.add(new Sprite(new Texture("range.png")));
        Powerup.sprites.add(new Sprite(new Texture("freeze.png")));
    }

    public static Sprite selectSprite(double chance) {


        if (chance < 1) {
            return sprites.get(0);
        }
        else if (chance < 2) {
            return sprites.get(1);
        }
        else if (chance < 3) {
            return sprites.get(2);
        }
        else if (chance < 4) {
            return sprites.get(3);
        }
        else{
            return sprites.get(4);
        }

    }

    public double getChance(){
        return this.chance;
    }

    public void dispose(){
        sprites.clear();
    }
}
