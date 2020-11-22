package com.mygdx.auber.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.utils.Array;
import com.mygdx.auber.Pathfinding.MapGraph;
import com.mygdx.auber.Pathfinding.Node;

import java.util.Random;

public class CrewMembers extends NPC {
    public double timeToWait = Math.random() * 15;
    public static Array<Sprite> crewSprites = new Array<>();

    public CrewMembers(Sprite sprite, Node node, MapGraph mapGraph) {
        super(sprite, node, mapGraph);
        this.setPosition(node.x, node.y);
    }

    /**
     * Step needs to be called in the update method, makes the NPC move and check if it has reached its next node
     */
    public void step(float delta, TiledMapTileLayer layer) {
        this.moveNPC();

        this.elapsedTime += delta;
        this.checkCollision();

        this.collision.checkForCollision(this, layer, this.velocity, this.collision);

        if ((this.elapsedTime >= timeToWait) && this.pathQueue.isEmpty()) {
            this.elapsedTime = 0;
            reachDestination();
        }

    }

    /**
     * Called when the path queue is empty
     */
    @Override
    public void reachDestination() {
        this.velocity.x = 0;
        this.velocity.y = 0;
        timeToWait = Math.random() * 15;

        Node newGoal;
        do {
            newGoal = MapGraph.nodes.random();
        } while (newGoal == previousNode);
        {
            setGoal(newGoal);

        }
    }

    /**
     * Generates the list of crewmate sprites the crewmates can be
     */
    public static void createCrewSprites()
    {
        CrewMembers.crewSprites.add(new Sprite(new Texture("AlienStand.png")));
        CrewMembers.crewSprites.add(new Sprite(new Texture("HumanStand.png")));
        CrewMembers.crewSprites.add(new Sprite(new Texture("Luffy.png")));
        //CrewMembers.crewSprites.add(new Sprite(new Texture("AlienInfiltratorStand.png")));
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public static void dispose() {
        return;
    }
}
