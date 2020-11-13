package com.mygdx.auber.entities;

import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.ai.GdxAI;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Queue;
import com.mygdx.auber.Pathfinding.MapGraph;
import com.mygdx.auber.Pathfinding.Node;

public class NPC extends Sprite {
    private final TiledMapTileLayer collisionLayer;
    private boolean arrested;
    private boolean moving;

    float deltaX,deltaY;
    float SPEED = 1f;
    MapGraph mapGraph;
    Node previousNode;
    Queue<Node> pathQueue = new Queue<>();

    public NPC(Sprite sprite, TiledMapTileLayer collisionLayer, float x, float y){
        super(sprite);
        this.collisionLayer = collisionLayer;
        sprite.setPosition(x,y);
    }

    public void moveToLocation(int x, int y)
    {
        return;
    }

    public void waitForMovement()
    {
        return;
    }

    public void setGoal(Node goal)
    {
        GraphPath<Node> graphPath = mapGraph.findPath(previousNode, goal);
        for(int i = 1; i < graphPath.getCount(); i++)
        {
            pathQueue.addLast(graphPath.get(i));
        }
        setSpeedToNextNode();
    }

    private void reachNextNode()
    {
        Node nextNode = pathQueue.first();

        this.previousNode = nextNode;
        pathQueue.removeFirst();

        if(pathQueue.size == 0)
        {
            reachDestination();
        }else{
            setSpeedToNextNode();
        }
    }

    private void setSpeedToNextNode()
    {
        Node nextNode = pathQueue.first();
        float angle = MathUtils.atan2(nextNode.y - previousNode.y, nextNode.x - previousNode.x);
        deltaX = MathUtils.cos(angle) * SPEED;
        deltaY = MathUtils.sin(angle) * SPEED;
    }

    private void reachDestination()
    {
        deltaX = 0;
        deltaY = 0;

        Node newGoal;
        do {
            newGoal = mapGraph.nodes.random();
        }while(newGoal == previousNode);
        {
            setGoal(newGoal);
        }
    }

}
