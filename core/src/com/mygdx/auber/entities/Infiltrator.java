package com.mygdx.auber.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.auber.Screens.PlayScreen;

public class Infiltrator extends NPC{

    //private final NPC infiltrator;
    private Vector2 velocity = new Vector2(0,0);

    private final float SPEED = 1;

    public Infiltrator(Sprite sprite, TiledMapTileLayer collisionLayer, float x, float y) {
        super(sprite, collisionLayer, x, y);

        Map map;
        //infiltrator = new NPC(new Sprite(new Texture("SpriteTest.png")),(TiledMapTileLayer) PlayScreen.map.getLayers().get(0), 20, 20);
        //infiltrator.setPosition(infiltrator.getX(), infiltrator.getY());
    }

    public void update(float dt){
    }
}

