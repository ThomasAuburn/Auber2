package com.mygdx.auber.entities;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;


class Collision {

    public boolean collideX, collideY;

    public Collision() {

    }

    /**
     * "Function that scans the blocks directly right of the sprite, for the height of the sprite, and returns a bool based on if they are blocked or not"
     *
     * Args: None
     *
     * Returns:
     *  bool: True if there is a on the right of the sprite that contains "blocked", else returns false
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

    public boolean collidesLeft(Sprite sprite, TiledMapTileLayer collisionLayer) {
        // Iterate across the amount of tiles tall the sprite is
        for (float step = collisionLayer.getTileHeight() / 2f; step < sprite.getHeight(); step += collisionLayer.getTileHeight() / 2f) {
            // Check if cell contains blocked property
            boolean collides = isCellBlocked(collisionLayer, sprite.getX() , sprite.getY() + step);
            if(collides)
                return true;
        }
        return false;
    }

    public boolean collidesTop(Sprite sprite, TiledMapTileLayer collisionLayer) {
        // Iterate across the amount of tiles tall the sprite is
        for (float step = collisionLayer.getTileWidth() / 2f; step < sprite.getWidth(); step += collisionLayer.getTileWidth() / 2f) {
            // Check if cell contains blocked property
            boolean collides = isCellBlocked(collisionLayer, sprite.getX() + step, sprite.getY() + sprite.getHeight());
            if (collides)
                return true;
        }
        return false;
    }

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
     * "A function that checks a cell and returns a bool for whether the cell contains the key "blocked" or not
     *
     * Args:
     *      x (float): X coordinate of the cell to check
     *      y(float): Y coordinate of the cell to check
     *
     * Returns:
     *      bool: True if cell contains "blocked", else false
     */
    private boolean isCellBlocked(TiledMapTileLayer collisionLayer, float x,float y) {
        TiledMapTileLayer.Cell cell = collisionLayer.getCell((int) (x / collisionLayer.getTileWidth()), (int) (y / collisionLayer.getTileHeight())); //Set variable cell to the cell at specified x,y coordinate
        return cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey("blocked"); //If cell is not null, and the cell contains "blocked", return true, else false
    }

    public void checkForCollision(Sprite sprite, TiledMapTileLayer collisionLayer, Vector2 velocity, Collision collision)
    {
        float oldX = sprite.getX(), oldY = sprite.getY();
        collideX = false; collideY = false;

        // Move on x
        if(velocity.x < 0) {
            collideX = collision.collidesLeft(sprite, collisionLayer);
        }
        else if(velocity.x > 0) {
            collideX = collision.collidesRight(sprite, collisionLayer);
        }
        // React to x
        if (collideX) {
            sprite.setX(oldX);
            velocity.x = 0;
        }

        // Move on y
        if (velocity.y < 0) {
            collideY = collision.collidesBottom(sprite, collisionLayer);
        }
        else if(velocity.y > 0) {
            collideY = collision.collidesTop(sprite, collisionLayer);
        }
        // React to y
        if(collideY) {
            sprite.setY(oldY);
            velocity.y = 0;
        }
    }
}