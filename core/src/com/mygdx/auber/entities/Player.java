package com.mygdx.auber.entities;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.auber.Scenes.Hud;
import com.mygdx.auber.Screens.PlayScreen;


public class Player extends Sprite implements InputProcessor {
    /**The movement velocity */
    public Vector2 velocity = new Vector2(0,0);

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
            this.setScale(-1,1);
        }
        if(isDHeld) {
            velocity.x += SPEED;
            this.setScale(1,1);
        }

        velocity = collision.checkForCollision(this, collisionLayer, velocity, collision);
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
        Vector3 vec=new Vector3(screenX,screenY,0);
        PlayScreen.camera.unproject(vec);

        for (Infiltrator infiltrator: NPCCreator.infiltrators)
        {
            float centreX = infiltrator.getX() + infiltrator.getWidth()/2;
            float centreY = infiltrator.getY() + infiltrator.getHeight()/2;

            if(centreX < vec.x + 10 && centreX > vec.x - 10)
            {
                if(centreY < vec.y + 10 && centreY > vec.y - 10)
                {
                    NPCCreator.removeInfiltrator(infiltrator.index);
                    Hud.ImposterCount += 1;
                }
            }
        }
        return true;
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
