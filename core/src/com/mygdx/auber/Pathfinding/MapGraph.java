package com.mygdx.auber.Pathfinding;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.DefaultGraphPath;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.ai.pfa.indexed.IndexedGraph;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

public class MapGraph implements IndexedGraph<Node> {

    PathHeuristic pathHeuristic = new PathHeuristic();
    Array<Node> nodes = new Array<>();
    Array<Path> paths = new Array<>();

    ObjectMap<Node, Array<Connection<Node>>> pathsMap = new ObjectMap<>();

    private int lastNodeIndex = 0;

    public void addNode(Node node)
    {
        node.index = lastNodeIndex;
        lastNodeIndex++;

        nodes.add(node);
    }

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

    public GraphPath<Node> findPath(Node startNode, Node goalNode)
    {
        GraphPath<Node> nodePath = new DefaultGraphPath<>();
        new IndexedAStarPathFinder<>(this).searchNodePath(startNode, goalNode, pathHeuristic, nodePath);
        return nodePath;
    }

    @Override
    public int getIndex(Node node) {
        return node.index;
    }

    @Override
    public int getNodeCount() {
        return lastNodeIndex;
    }

    @Override
    public Array<Connection<Node>> getConnections(Node fromNode) {
        if(pathsMap.containsKey(fromNode))
        {
            return pathsMap.get(fromNode);
        }
        return new Array<>(0);
    }
}
