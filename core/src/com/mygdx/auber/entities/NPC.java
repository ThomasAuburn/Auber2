package com.mygdx.auber.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.ai.GdxAI;

public class NPC extends Sprite{
    private final TiledMapTileLayer collisionLayer;
    private boolean arrested;
    private int[][] roomLocations;
    private boolean moving;

    public NPC(Sprite sprite, TiledMapTileLayer collisionLayer, float x, float y){
        super(sprite);
        this.collisionLayer = collisionLayer;
        setPosition(x, y);
    }

    public void moveToLocation(int x, int y)
    {
        return;
    }

    public void waitForMovement()
    {
        return;
    }
}
