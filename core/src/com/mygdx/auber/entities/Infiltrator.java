package com.mygdx.auber.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.auber.Pathfinding.GraphCreator;
import com.mygdx.auber.Pathfinding.MapGraph;
import com.mygdx.auber.Pathfinding.Node;
import com.mygdx.auber.Screens.PlayScreen;

public class Infiltrator extends NPC{
    public boolean isDestroying = false;
    public double timeToWait = Math.random() * 15;

    private float timeInvisibleStart;
    private boolean isInvisible = false;

    public Infiltrator(Sprite sprite, Node node, MapGraph mapGraph) {
        super(sprite, node, mapGraph);
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
        moveNPC();

        if(isDestroying)
        {
            if(Vector2.dst(Player.x, Player.y, this.getX(), this.getY()) < 50)
            {
                useAbility();
            }
            else
            {
                return;
            }
        }

        if(isInvisible)
        {
            if(timeInvisibleStart - System.currentTimeMillis() < 10 * 1000)
            {
                this.setAlpha(1);
            }
        }

        this.elapsedTime += delta;
        this.checkCollision();

        if(!(this.elapsedTime < timeToWait)) {
            this.elapsedTime = 0;
            reachDestination();
        }
    }

    /**
     * Called when the path queue is empty
     */
    @Override
    public void reachDestination()
    {
        this.velocity.x = 0;
        this.velocity.y = 0;
        timeToWait = Math.random() * 15;


//        if(Math.random() > .9f) // 1/10 chance of infiltrator deciding to destroy a keysystem
//        {
//            destroyKeySystem();
//            return;
//        }
//        else
//        {
            Node newGoal;
            do {
                newGoal = MapGraph.nodes.random();
            }while(newGoal == previousNode);
            {
                setGoal(newGoal);
            }
        //}
    }

    /**
     * Checks whether the NPC has made it to the next node
     */
    public void checkCollision()
    {
        if(this.pathQueue.size > 0){
            Node targetNode = this.pathQueue.first();
            if(Vector2.dst(this.getX(),this.getY(),targetNode.x,targetNode.y) <= 10)
            {
                if(GraphCreator.keySystemsNodes.contains(targetNode, false) && GraphCreator.keySystemsNodes.contains(this.pathQueue.last(), false)) //If the node the NPC is on is a keysystem node and its the destination node
                {
                    this.isDestroying = true;
                    //KeySystem.startDestroy();
                }
                reachNextNode(); //If the sprite is within 5 pixels of the node, it has reached the node
            }
        }
    }

    /**
     * Starts destroying a random keySystem, moves towards it, sets isDestroying to true
     */
    public void destroyKeySystem()
    {
        if(!GraphCreator.keySystemsNodes.isEmpty())
        {
            Node keySystem = GraphCreator.keySystemsNodes.random();
            this.setGoal(keySystem);
        }
        else
        {
            reachDestination();
        }
    }

    /**
     * Causes the infiltrator to use a random ability
     */
    public void useAbility()
    {
        double chance = Math.random() * 3;

        if(chance < 1)
        {
            goInvisible();
        }
        else if(chance >= 1 && chance < 2)
        {
            damageAuber((int) chance);
        }
        else
        {
            //
        }
    }

    /**
     * Sets the sprite alpha to 0, records the time the invisibility started, sets isInvisible to true
     */
    private void goInvisible()
    {
        this.isInvisible = true;
        this.timeInvisibleStart = System.currentTimeMillis();

        this.setAlpha(0);
    }

    /**
     * Damages Auber by an amount
     * @param amount Int amount of damage to deal
     */
    private void damageAuber(int amount)
    {
        Player.takeDamage(amount);
    }

}

