package com.mygdx.auber.Pathfinding;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

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

    public void generateNodeMap()
    {
        for (int i = 0; i < tileLayer.getWidth(); i++)
        {
            for(int j = 0; j < tileLayer.getHeight(); j++)
            {
                TiledMapTileLayer.Cell cell = tileLayer.getCell((int) (i * tileLayer.getTileWidth()), (int) (j * tileLayer.getTileHeight()));
                if(cell != null && cell.getTile() != null && (cell.getTile().getId() == 12))
                {
                    Node node = new Node((int) (i * tileLayer.getTileWidth()), (int) (j * tileLayer.getTileHeight()));
                    MapGraph.addNode(node);
                }
            }
        }
    }
    
    public void generateConnections()
    {
        for (Node node: MapGraph.nodes)
        {
            return;
        }
    }

    public void getNeighbourNodes()
    {
        return;
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
        batch.dispose();
        font.dispose();
    }
}
