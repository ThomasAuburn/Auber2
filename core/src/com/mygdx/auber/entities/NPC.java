package com.mygdx.auber.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class NPC extends Sprite {
    private final TiledMapTileLayer collisionLayer;

    public NPC(Sprite sprite, TiledMapTileLayer collisionLayer, float x, float y){
        super(sprite);
        this.collisionLayer = collisionLayer;
        setPosition(x, y);

    }
}
