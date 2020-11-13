package com.mygdx.auber.entities;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.graphics.g2d.Sprite;


class Collision {

    public Collision() {

    }

    /**
     * Scans the blocks directly right of the sprite, for the height of the sprite, and returns a bool based on if they are blocked or not
     *
     * @param sprite A Sprite object of the character
     * @param collisionLayer Collision layer in the Tiled map
     * @return True if there is a on the right of the sprite that contains "blocked", else returns false
     */
    public boolean collidesRight(Sprite sprite, TiledMapTileLayer collisionLayer) {
        // Iterate across the amount of tiles tall the sprite is
        for (float step = collisionLayer.getTileHeight() / 2f; step < sprite.getHeight(); step += collisionLayer.getTileHeight() / 2f) {
            // Check if cell contains blocked property
            boolean collides = isCellBlocked(collisionLayer, sprite.getX() + sprite.getWidth(), sprite.getY() + step);
            if (collides)
                return true;
        }
        return false;
    }

    /**
     * Scans the blocks directly left of the sprite, for the height of the sprite, and returns a bool based on if they are blocked or not
     *
     * @param sprite A Sprite object of the character
     * @param collisionLayer Collision layer in the Tiled map
     * @return True if there is a on the left of the sprite that contains "blocked", else returns false
     */
    public boolean collidesLeft(Sprite sprite, TiledMapTileLayer collisionLayer) {
        // Iterate across the amount of tiles wide the sprite is
        for (float step = collisionLayer.getTileHeight() / 2f; step < sprite.getHeight(); step += collisionLayer.getTileHeight() / 2f) {
            // Check if cell contains blocked property
            boolean collides = isCellBlocked(collisionLayer, sprite.getX() , sprite.getY() + step);
            if(collides)
                return true;
        }
        return false;
    }

    /**
     * Scans the blocks directly top of the sprite, for the height of the sprite, and returns a bool based on if they are blocked or not
     *
     * @param sprite A Sprite object of the character
     * @param collisionLayer Collision layer in the Tiled map
     * @return True if there is a on the top of the sprite that contains "blocked", else returns false
     */
    public boolean collidesTop(Sprite sprite, TiledMapTileLayer collisionLayer) {
        // Iterate across the amount of tiles wide the sprite is
        for (float step = collisionLayer.getTileWidth() / 2f; step < sprite.getWidth(); step += collisionLayer.getTileWidth() / 2f) {
            // Check if cell contains blocked property
            boolean collides = isCellBlocked(collisionLayer, sprite.getX() + step, sprite.getY() + sprite.getHeight());
            if (collides)
                return true;
        }
        return false;
    }

    /**
     * Scans the blocks directly bottom of the sprite, for the height of the sprite, and returns a bool based on if they are blocked or not
     *
     * @param sprite A Sprite object of the character
     * @param collisionLayer Collision layer in the Tiled map
     * @return True if there is a on the bottom of the sprite that contains "blocked", else returns false
     */
    public boolean collidesBottom(Sprite sprite, TiledMapTileLayer collisionLayer) {
        // Iterate across the amount of tiles tall the sprite is
        for (float step = collisionLayer.getTileWidth()/2f; step < sprite.getWidth(); step += collisionLayer.getTileWidth()/2f) {
            // Check if cell contains blocked property
            boolean collides = isCellBlocked(collisionLayer, sprite.getX() + step, sprite.getY());
            if(collides)
                return true;
        }
        return false;
    }

    /**
     * "Checks a cell and returns a bool for whether the cell contains the key "blocked" or not
     *
     * @param collisionLayer Collision layer in the Tiled map
     * @param x X coordinate of the cell to check
     * @param y Y coordinate of the cell to check
     * @return True if cell contains "blocked", else false
     */
    private boolean isCellBlocked(TiledMapTileLayer collisionLayer, float x,float y) {
        TiledMapTileLayer.Cell cell = collisionLayer.getCell((int) (x / collisionLayer.getTileWidth()), (int) (y / collisionLayer.getTileHeight())); //Set variable cell to the cell at specified x,y coordinate
        return cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey("blocked"); //If cell is not null, and the cell contains "blocked", return true, else false
    }
}
