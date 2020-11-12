package com.mygdx.auber.Pathfinding;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;

public class GraphCreator extends ApplicationAdapter {
    ShapeRenderer shapeRenderer;
    SpriteBatch batch;
    BitmapFont font;

    MapGraph mapGraph;
    GraphPath<Node> nodeGraphPath;
    TiledMapTileLayer tileLayer;

    public GraphCreator(TiledMapTileLayer tileLayer)
    {
        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();
        font = new BitmapFont();

        mapGraph = new MapGraph();
        this.tileLayer = tileLayer;

        generateNodeMap();
    }

    @Override
    public void render() {

        //for(Path path : MapGraph.paths)
        //{
        //    path.render(shapeRenderer);
        //}

        for(Node node : MapGraph.nodes)
        {
            node.render(shapeRenderer, batch, font);
        }
    }

    /**
     * Generates a node on every tile in the map, if it matches the id
     * Called when the GraphCreator object is instantiated
     */
    public void generateNodeMap()
    {
        for (int i = 0; i < tileLayer.getWidth(); i++)
        {
            for(int j = 0; j < tileLayer.getHeight(); j++)
            {
                int x = i * tileLayer.getTileWidth();
                int y = j * tileLayer.getTileHeight();
                TiledMapTileLayer.Cell cell = tileLayer.getCell(i, j);
                if(cell != null && cell.getTile() != null && (cell.getTile().getId() == 11 || cell.getTile().getId() == 12))
                {
                    Node node = new Node(x,y);
                    MapGraph.addNode(node);
                }
            }
        }
    }
    
    public void generateConnections()
    {
        for (Node node: MapGraph.nodes)
        {
            for (Node neighbourNode: getNeighbourNodes(node))
            {
                MapGraph.connectNodes(node, neighbourNode);
            }
        }
    }

    public Array<Node> getNeighbourNodes(Node node)
    {
        Array<Node> nodes = new Array<>();
        float x = node.x;
        float y = node.y;

        nodes.add(MapGraph.getNode(x + tileLayer.getTileWidth(), y));
        nodes.add(MapGraph.getNode(x - tileLayer.getTileWidth(), y));
        nodes.add(MapGraph.getNode(x, y + tileLayer.getTileHeight()));
        nodes.add(MapGraph.getNode(x, y - tileLayer.getTileHeight()));

        return nodes;
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
        batch.dispose();
        font.dispose();
    }
}
