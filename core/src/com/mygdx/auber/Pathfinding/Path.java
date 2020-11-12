package com.mygdx.auber.Pathfinding;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.auber.Pathfinding.Node;

public class Path implements Connection<Node> {

    Node fromNode,toNode;
    float cost;

    public Path(Node fromNode, Node toNode)
    {
         this.fromNode = fromNode;
         this.toNode = toNode;
         cost = Vector2.dst(fromNode.x,fromNode.y,toNode.x,toNode.y);
    }

    public void render(ShapeRenderer shapeRenderer)
    {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0, 0, 0, 1);
        shapeRenderer.rectLine(fromNode.x, fromNode.y, toNode.x, toNode.y, 4);
        shapeRenderer.end();
    }

    @Override
    public float getCost() {
        return cost;
    }

    @Override
    public Node getFromNode() {
        return fromNode;
    }

    @Override
    public Node getToNode() {
        return toNode;
    }
}
