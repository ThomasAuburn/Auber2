package com.mygdx.auber.entities;

import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Queue;
import com.mygdx.auber.Pathfinding.MapGraph;
import com.mygdx.auber.Pathfinding.Node;
import java.util.concurrent.ThreadLocalRandom;

public class NPC extends Sprite {
    private final TiledMapTileLayer collisionLayer;
    private boolean arrested;
    private boolean moving;
    private float elapsedTime = 0f;

    float deltaX,deltaY;
    float SPEED = 1f;
    MapGraph mapGraph;
    Node previousNode;
    Queue<Node> pathQueue = new Queue<>();

    public NPC(Sprite sprite, TiledMapTileLayer collisionLayer, Node start, MapGraph mapGraph){
        super(sprite);
        this.collisionLayer = collisionLayer;
        sprite.setPosition(start.x ,start.y);
        this.mapGraph = mapGraph;
        this.previousNode = start;
        this.setGoal(mapGraph.getRandomNode());
    }

//    public void moveToLocation(Node node)
//    {
//        setGoal(node);
//        step();
//    }
//
//    public void waitForMovement(float delta)
//    {
//        elapsedTime += delta;
//        if(elapsedTime > ThreadLocalRandom.current().nextInt(10, 60)) //Random number between 10 and 60
//        {
//            moveToLocation(mapGraph.getRandomNode());
//        }
//        return;
//    }

    public void step()
    {
        this.setX(this.getX() + deltaX);
        this.setY(this.getY() + deltaY);
        checkCollision();
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

    private void checkCollision()
    {
        if(pathQueue.size > 0){
            Node targetNode = pathQueue.first();
            if(Vector2.dst(this.getX(),this.getY(),targetNode.x,targetNode.y) < 5)
            {
                reachNextNode();
            }
        }
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
