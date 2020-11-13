package com.mygdx.auber.entities;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;


public class Player extends Sprite implements InputProcessor {
    /**The movement velocity */
    private final Vector2 velocity = new Vector2(0,0);

    private final Collision collision;

    public static int health = 10;

    private boolean isWHeld;
    private boolean isAHeld;
    private boolean isSHeld;
    private boolean isDHeld;


    private final TiledMapTileLayer collisionLayer;


    public Player(Sprite sprite, TiledMapTileLayer collisionLayer) {
        super(sprite);
        this.collisionLayer = collisionLayer;
        this.collision = new Collision();
        this.setPosition(16*25, 16*25);
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
            collideX = collision.collidesLeft(this, collisionLayer);
        }
        else if(velocity.x > 0) {
            collideX = collision.collidesRight(this, collisionLayer);
        }

        // React to x
        if (collideX) {
            setX(oldX);
            velocity.x = 0;
        }

        // Move on y
        if (velocity.y < 0) {
            collideY = collision.collidesBottom(this, collisionLayer);
        }
        else if(velocity.y > 0) {
            collideY = collision.collidesTop(this, collisionLayer);
        }

        // React to y
        if(collideY) {
            setY(oldY);
            velocity.y = 0;
        }

        setX(getX() + velocity.x);
        setY(getY() + velocity.y);
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

    public void takeDamage(int amount) {
        health -= amount;
    }
}
