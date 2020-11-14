package com.mygdx.auber.entities;

import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.ai.pfa.PathSmoother;
import com.badlogic.gdx.ai.utils.Collision;
import com.badlogic.gdx.ai.utils.Ray;
import com.badlogic.gdx.ai.utils.RaycastCollisionDetector;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Queue;
import com.mygdx.auber.Pathfinding.MapGraph;
import com.mygdx.auber.Pathfinding.Node;

public class NPC extends Sprite {
    private TiledMapTileLayer collisionLayer;
    private boolean arrested;
    private final Vector2 velocity = new Vector2(0,0);

    float SPEED = .5f;
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

    public void step()
    {
        System.out.println(velocity);
        this.setX(this.getX() + velocity.x);
        this.setY(this.getY() + velocity.y);
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
        velocity.x = 0;
        velocity.y = 0;
        Node nextNode = pathQueue.first();
        float angle = MathUtils.atan2(nextNode.y - previousNode.y, nextNode.x - previousNode.x);
        velocity.x += MathUtils.cos(angle) * SPEED;
        velocity.y += MathUtils.sin(angle) * SPEED;
    }

    private void reachDestination()
    {
        velocity.x = 0;
        velocity.y = 0;

        Node newGoal;
        do {
            newGoal = mapGraph.nodes.random();
        }while(newGoal == previousNode);
        {
            setGoal(newGoal);
        }
    }

}
