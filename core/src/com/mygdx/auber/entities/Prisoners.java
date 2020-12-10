package com.mygdx.auber.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Prisoners{
    private static Array<Vector2> positions;
    private static Array<NPC> prisoners;

    public Prisoners(TiledMapTileLayer layer)
    {
        positions = findBrigLocations(layer);
        prisoners = new Array<NPC>();
    }

    /**
     * Scans every tile and adds a vector 2 with the position of the tile with the property "prison"
     * @param tileLayer Layer to scan for tiles
     * @return Array of positions of tiles
     */
    public static Array<Vector2> findBrigLocations(TiledMapTileLayer tileLayer)
    {
        Array<Vector2> positions = new Array<>();
        for (int i = 0; i < tileLayer.getWidth(); i++) {
            //Scan every tile
            for (int j = 0; j < tileLayer.getHeight(); j++) {
                int x = (i * tileLayer.getTileWidth()) + tileLayer.getTileWidth()/2;
                int y = (j * tileLayer.getTileHeight()) + tileLayer.getTileHeight()/2; //x,y coord of the centre of the tile
                TiledMapTileLayer.Cell cell = tileLayer.getCell(i, j); //Returns the cell at the x,y coord
                if(cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey("prison")) //If ID matches floor/corridor tiles, and is not null
                {
                    positions.add(new Vector2(x,y));
                }
            }
        }
        return positions;
    }

    /**
     * Adds a prisoner to the list of prisoners and spawns them in the brig
     * @param npc npc to put in prison
     */
    public static void addPrisoner(NPC npc)
    {
        prisoners.add(npc);
        npc.setPosition(positions.random().x, positions.random().y);
    }

    /**
     * Renders the sprites in the prison
     * @param batch Batch to draw the sprites in
     */
    public static void render(Batch batch)
    {
        if(!prisoners.isEmpty())
        {
            for (NPC prisoner:
                    prisoners) {
                prisoner.draw(batch);
            }
        }
    }
}
