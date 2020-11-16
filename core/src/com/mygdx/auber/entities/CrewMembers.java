package com.mygdx.auber.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.auber.Pathfinding.MapGraph;
import com.mygdx.auber.Pathfinding.Node;

public class CrewMembers extends NPC{

    public int index;
    private final float SPEED = 1;

    public CrewMembers(Sprite sprite, TiledMapTileLayer collisionLayer, Node node, MapGraph mapGraph)
    {
        super(sprite, collisionLayer, node, mapGraph);
        this.setPosition(node.x, node.y);
    }

    public void setIndex(int index)
    {
        this.index = index;
    }

    public static void dispose()
    {
        return;
    }

    /**
     * Step needs to be called in the update method, makes the NPC move and check if it has reached its next node
     */
    public void step(float delta) {
        this.setX(this.getX() + this.velocity.x);
        this.setY(this.getY() + this.velocity.y);
        this.elapsedTime += delta;
        this.checkCollision();

        if(!(this.elapsedTime < 20) && !move)
        {
            this.elapsedTime = 0;
            Node newGoal;
            newGoal = MapGraph.nodes.random();
            this.setGoal(newGoal);
        }
    }

    /**
     * Called when the path queue is empty
     */
    public void reachDestination()
    {
        this.velocity.x = 0;
        this.velocity.y = 0;

        move = false;

//        Node newGoal;
//        do {
//            newGoal = MapGraph.nodes.random();
//        }while(newGoal == previousNode);
//        {
//            setGoal(newGoal);
//        }
    }
}
