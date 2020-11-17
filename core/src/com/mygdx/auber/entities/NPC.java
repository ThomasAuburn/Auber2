package com.mygdx.auber.entities;

import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Queue;
import com.mygdx.auber.Pathfinding.MapGraph;
import com.mygdx.auber.Pathfinding.Node;

public class NPC extends Sprite {
    private TiledMapTileLayer collisionLayer;
    private boolean arrested;
    public Vector2 velocity = new Vector2(0,0);
    private final Collision collision;

    public int index;
    public final float SPEED = 1;
    float elapsedTime = 0f;

    private MapGraph mapGraph;
    Node previousNode;
    private Queue<Node> pathQueue = new Queue<>();

    public NPC(Sprite sprite, TiledMapTileLayer collisionLayer, Node start, MapGraph mapGraph){
        super(sprite);

        this.collisionLayer = collisionLayer;
        this.mapGraph = mapGraph;
        this.previousNode = start;
        this.setGoal(MapGraph.getRandomNode());
        this.collision = new Collision();

        sprite.setPosition(start.x ,start.y);
    }

    public static void updateNPC(float delta)
    {
        for (CrewMembers crewMember:
             NPCCreator.crew) {
            crewMember.step(delta);
        }
        for (Infiltrator infiltrator:
                NPCCreator.infiltrators) {
            infiltrator.step(delta);
        }
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
            this.pathQueue.addLast(graphPath.get(i));
        }
        setSpeedToNextNode();
    }

    /**
     * Checks whether the NPC has made it to the next node
     */
    public void checkCollision()
    {
        if(this.pathQueue.size > 0){
            Node targetNode = this.pathQueue.first();
            if(Vector2.dst(this.getX(),this.getY(),targetNode.x,targetNode.y) < 5)
            {
                reachNextNode(); //If the sprite is within 5 pixels of the node, it has reached the node
            }
            else
            {
                setSpeedToNextNode(); //Else keep moving towards it
            }
        }
    }

    /**
     * Called when NPC has reached a node, sets the next node to be moved to, or if the path queue is empty, destination is reached
     */
    private void reachNextNode()
    {
        Node nextNode = this.pathQueue.first();

        this.previousNode = nextNode;
        this.pathQueue.removeFirst();

        if(this.pathQueue.size != 0) {
            setSpeedToNextNode(); //If there are items in the queue, set the velocity towards the next node
        }
        else
        {
            this.velocity.x = 0;
            this.velocity.y = 0;
        }
    }

    /**
     * Sets the velocity towards the next node
     */
    private void setSpeedToNextNode()
    {
        this.velocity.x = 0;
        this.velocity.y = 0;

        if(!pathQueue.isEmpty())
        {
            Node nextNode = this.pathQueue.first();
        }
        else
        {
            Node newGoal;
            newGoal = MapGraph.nodes.random();
            this.setGoal(newGoal);
        }

        Node nextNode = this.pathQueue.first();
        float angle = MathUtils.atan2(nextNode.y - previousNode.y, nextNode.x - previousNode.x);
        this.velocity.x += MathUtils.cos(angle) * SPEED;
        this.velocity.y += MathUtils.sin(angle) * SPEED;
    }

    /** Render method for rendering all NPCs */
    public static void render(Batch batch)
    {
        for (Infiltrator infiltrator: NPCCreator.infiltrators)
        {
            infiltrator.draw(batch);

        }

        for (CrewMembers crewMember: NPCCreator.crew)
        {
            crewMember.draw(batch);
        }
    }

    public static void dispose()
    {
        for (Infiltrator infiltrator: NPCCreator.infiltrators)
        {
            infiltrator.dispose();

        }
        for (CrewMembers crewMember: NPCCreator.crew)
        {
            crewMember.dispose();
        }
    }

    public void reachDestination()
    {}
}
