package com.mygdx.auber.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.auber.Pathfinding.MapGraph;
import com.mygdx.auber.Pathfinding.Node;

public class Infiltrator extends NPC{

    private Vector2 velocity = new Vector2(0,0);
    public int index;
    private final float SPEED = 1;

    public Infiltrator(Sprite sprite, TiledMapTileLayer collisionLayer, Node node, MapGraph mapGraph) {
        super(sprite, collisionLayer, node, mapGraph);
        this.setPosition(node.x, node.y);
    }

    public void setIndex(int index)
    {
        this.index = index;
        System.out.println(index);
    }

    public static void dispose()
    {
        return;
    }

}

