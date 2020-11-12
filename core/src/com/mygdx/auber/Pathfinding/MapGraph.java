package com.mygdx.auber.Pathfinding;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.DefaultGraphPath;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.ai.pfa.indexed.IndexedGraph;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

public class MapGraph implements IndexedGraph<Node> {

    PathHeuristic pathHeuristic = new PathHeuristic();
    static Array<Node> nodes = new Array<>();
    static Array<Path> paths = new Array<>();

    ObjectMap<Node, Array<Connection<Node>>> pathsMap = new ObjectMap<>();

    private static int lastNodeIndex = 0;

    /**
     * Adds a node to the list of nodes in the graph, sets the node index and increases the index by one
     */
    public static void addNode(Node node)
    {
        node.index = lastNodeIndex;
        lastNodeIndex++;

        nodes.add(node);
    }

    /**
     * Creates a path from one node to another
     * @param fromNode
     * @param toNode
     */
    public void connectNodes(Node fromNode, Node toNode)
    {
        Path path = new Path(fromNode,toNode);
        if(!pathsMap.containsKey(fromNode))
        {
            pathsMap.put(fromNode, new Array<Connection<Node>>());
        }
        pathsMap.get(fromNode).add(path);
        paths.add(path);
    }

    /**
     * Calculates a path from one node to another, populates the nodePath variable with the path it finds
     * @param startNode
     * @param goalNode
     * @return
     */
    public GraphPath<Node> findPath(Node startNode, Node goalNode)
    {
        GraphPath<Node> nodePath = new DefaultGraphPath<>();
        new IndexedAStarPathFinder<>(this).searchNodePath(startNode, goalNode, pathHeuristic, nodePath);
        return nodePath;
    }

    /**
     * Returns the index of a particular Node instance.
     * @param node
     * @return
     */
    @Override
    public int getIndex(Node node) {
        return node.index;
    }

    /**
     * Return the count of how many nodes are in our search space
     * @return
     */
    @Override
    public int getNodeCount() {
        return lastNodeIndex;
    }

    /**
     * Returns the list of paths that start at a particular node
     * @param fromNode
     * @return
     */
    @Override
    public Array<Connection<Node>> getConnections(Node fromNode) {
        if(pathsMap.containsKey(fromNode))
        {
            return pathsMap.get(fromNode);
        }
        return new Array<>(0);
    }
}
