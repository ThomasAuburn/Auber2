package com.mygdx.auber.entities;

import com.badlogic.gdx.ai.pfa.GraphPath;
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
    private final Collision collision;

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
        this.collision = new Collision();
    }

    /**
     * Step needs to be called in the update method, makes the NPC move and check if it has reached its next node
     */
    public void step()
    {
        this.setX(this.getX() + velocity.x);
        this.setY(this.getY() + velocity.y);
        checkCollision();
    }

    /**
     * Sets the goal node and calculates the path to take there
     * @param goal Node to move NPC to
     */
    public void setGoal(Node goal)
    {
        GraphPath<Node> graphPath = mapGraph.findPath(previousNode, goal);

        for(int i = 1; i < graphPath.getCount(); i++)
        {
            pathQueue.addLast(graphPath.get(i));
        }
        setSpeedToNextNode();
    }

    /**
     * Checks whether the NPC has made it to the next node
     */
    private void checkCollision()
    {
        if(pathQueue.size > 0){
            Node targetNode = pathQueue.first();
            if(Vector2.dst(this.getX(),this.getY(),targetNode.x,targetNode.y) < 1)
            {
                reachNextNode();
            }
            else
            {
                setSpeedToNextNode();
            }
        }
    }

    /**
     * Called when NPC has reached a node, sets the next node to be moved to, or if the path queue is empty, destination is reached
     */
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

    /**
     * Sets the velocity towards the next node
     */
    private void setSpeedToNextNode()
    {
        velocity.x = 0;
        velocity.y = 0;
        Node nextNode = pathQueue.first();
        float angle = MathUtils.atan2(nextNode.y - previousNode.y, nextNode.x - previousNode.x);
        velocity.x += MathUtils.cos(angle) * SPEED;
        velocity.y += MathUtils.sin(angle) * SPEED;
    }

    /**
     * Called when the path queue is empty
     */
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
