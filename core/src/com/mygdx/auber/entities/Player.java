package com.mygdx.auber.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.auber.Scenes.Hud;


public class Player extends Sprite implements InputProcessor {
    /**The movement velocity */
    private final Vector2 velocity = new Vector2(0,0);

    public static int health = 10;

    private boolean isWHeld;
    private boolean isAHeld;
    private boolean isSHeld;
    private boolean isDHeld;


    private final TiledMapTileLayer collisionLayer;


    public Player(Sprite sprite, TiledMapTileLayer collisionLayer) {
        super(sprite);
        this.collisionLayer = collisionLayer;
        this.setPosition(16*35, 16*31);
    }

    @Override
    public void draw(Batch batch) {
        update();
        super.draw(batch);
    }

    public void update() {
        float oldX = getX(), oldY = getY();
        boolean collideX = false, collideY = false;

        velocity.x = 0; velocity.y = 0;

        float SPEED = 1;
        if(isWHeld) {
            velocity.y += SPEED;
        }
        if(isSHeld) {
            velocity.y -= SPEED;
        }
        if(isAHeld) {
            velocity.x -= SPEED;
        }
        if(isDHeld) {
            velocity.x += SPEED;
        }

        // Move on x
        if(velocity.x < 0) {
            collideX = collidesLeft();
        }
        else if(velocity.x > 0) {
            collideX = collidesRight();
        }

        // React to x
        if (collideX) {
            setX(oldX);
            velocity.x = 0;
        }

        // Move on y
        if (velocity.y < 0) {
            collideY = collidesBottom();
        }
        else if(velocity.y > 0) {
            collideY = collidesTop();
        }

        // React to y
        if(collideY) {
            setY(oldY);
            velocity.y = 0;
        }

        setX(getX() + velocity.x);
        setY(getY() + velocity.y);
    }

    /**
     * "Function that scans the blocks directly right of the sprite, for the height of the sprite, and returns a bool based on if they are blocked or not"
     *
     * Args: None
     *
     * Returns:
     *  bool: True if there is a on the right of the sprite that contains "blocked", else returns false
     */
    public boolean collidesRight() {
        // Iterate across the amount of tiles tall the sprite is
        for (float step = collisionLayer.getTileHeight() / 2f; step < getHeight(); step += collisionLayer.getTileHeight() / 2f) {
            // Check if cell contains blocked property
            boolean collides = isCellBlocked(getX() + getWidth(), getY() + step);
            if (collides)
                return true;
        }
        return false;
    }

    public boolean collidesLeft() {
        // Iterate across the amount of tiles tall the sprite is
        for (float step = collisionLayer.getTileHeight() / 2f; step < getHeight(); step += collisionLayer.getTileHeight() / 2f) {
            // Check if cell contains blocked property
            boolean collides = isCellBlocked(getX() , getY() + step);
            if(collides)
                return true;
        }
        return false;
    }

    public boolean collidesTop() {
        // Iterate across the amount of tiles tall the sprite is
        for (float step = collisionLayer.getTileWidth() / 2f; step < getWidth(); step += collisionLayer.getTileWidth() / 2f) {
            // Check if cell contains blocked property
            boolean collides = isCellBlocked(getX() + step, getY() + getHeight());
            if (collides)
                return true;
        }
        return false;
    }

    public boolean collidesBottom() {
        // Iterate across the amount of tiles tall the sprite is
        for (float step = collisionLayer.getTileWidth()/2f; step < getWidth(); step += collisionLayer.getTileWidth()/2f) {
            // Check if cell contains blocked property
            boolean collides = isCellBlocked(getX() + step, getY());
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
    private boolean isCellBlocked(float x,float y) {
        TiledMapTileLayer.Cell cell = collisionLayer.getCell((int) (x / collisionLayer.getTileWidth()), (int) (y / collisionLayer.getTileHeight())); //Set variable cell to the cell at specified x,y coordinate
        return cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey("blocked"); //If cell is not null, and the cell contains "blocked", return true, else false
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.W:
                //velocity.y = SPEED;
                isWHeld = true;
                break;
            case Input.Keys.A:
                //velocity.x = -SPEED;
                isAHeld = true;
                break;
            case Input.Keys.D:
                //velocity.x = SPEED;
                isDHeld = true;
                break;
            case Input.Keys.S:
                //velocity.y = -SPEED;
                isSHeld = true;
                break;
            case Input.Keys.X:
                //Test Health values
                health -= 1;
                break;
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.W:
                isWHeld = false;
                //if(velocity.y > 0)
                //{velocity.y = 0;}
                break;
            case Input.Keys.S:
                isSHeld = false;
                //if(velocity.y < 0)
                //{velocity.y = 0;}
                break;
            case Input.Keys.A:
                isAHeld = false;
                //if(velocity.x < 0)
                //{velocity.x = 0;}
                break;
            case Input.Keys.D:
                isDHeld = false;
                //if(velocity.x > 0)
                //{velocity.x = 0;}
                break;
        }
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }


    public void heal(int amount) {
        health += amount;
        if (health > 100) {
            health = 100;
        }
    }

    public void heal() {
        health = 100;
    }
}
