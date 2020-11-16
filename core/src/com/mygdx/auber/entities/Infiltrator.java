package com.mygdx.auber.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.auber.Pathfinding.MapGraph;
import com.mygdx.auber.Pathfinding.Node;

public class Infiltrator extends NPC{
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

    /**
     * Step needs to be called in the update method, makes the NPC move and check if it has reached its next node
     */
    public void step(float delta)
    {
        this.setX(this.getX() + this.velocity.x);
        this.setY(this.getY() + this.velocity.y);
        if(this.velocity.x < 0)
        {
            this.setScale(-1,1);
        }
        else if(this.velocity.x > 0)
        {
            this.setScale(1,1);
        }
        this.elapsedTime += delta;
        this.checkCollision();
    }

    /**
     * Called when the path queue is empty
     */
    public void reachDestination()
    {
        this.velocity.x = 0;
        this.velocity.y = 0;

        boolean move = false;

        Node newGoal;
        do {
            newGoal = MapGraph.nodes.random();
        }while(newGoal == previousNode);
        {
            setGoal(newGoal);
        }
    }

}

