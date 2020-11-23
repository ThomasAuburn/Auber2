package com.mygdx.auber.entities;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.auber.Scenes.Hud;
import com.mygdx.auber.Screens.PlayScreen;


public class Player extends Sprite implements InputProcessor {
    public Vector2 velocity = new Vector2(0,0);

    private final Collision collision;
    public final TiledMapTileLayer collisionLayer;
    public static float x,y;
    public boolean demo;

    public static int health;
    float SPEED = 1.3f;

    public static boolean canHeal = true;
    public static float healStopTime;

    private boolean isWHeld;
    private boolean isAHeld;
    private boolean isSHeld;
    private boolean isDHeld;

    Sprite arrow;

    public Player(Sprite sprite, TiledMapTileLayer collisionLayer, boolean demo) {
        super(sprite);
        this.collisionLayer = collisionLayer;
        this.collision = new Collision();
        this.demo = demo;
        this.arrow = new Sprite(new Texture("arrow.png"));
        arrow.setOrigin(arrow.getWidth()/2, 0);

        health = 100;
    }

    /**
     * Used to draw the player to the screen
     * @param batch Batch for the player to be drawn in
     */
    public void draw(Batch batch)
    {
        super.draw(batch);
    }

    /**
     * Draws arrows pointing in the direction of key systems being destroyed
     * @param batch Batch for the arrow to be rendered in
     */
    public void drawArrow(Batch batch)
    {
        for (KeySystem keySystem:
             KeySystemManager.getBeingDestroyedKeySystems()) {

            Vector2 position = new Vector2(this.getX(), this.getY());
            double angle = Math.atan((keySystem.position.x - position.x) / (keySystem.position.y - position.y));;

            angle = Math.toDegrees(angle);

            if(this.getY() > keySystem.position.y)
            {
                angle = angle - 180;
            }

            arrow.setRotation((float) -angle);
            arrow.setPosition(this.getX() + this.getWidth()/2, this.getY() + this.getHeight()/2);
            arrow.draw(batch);
        }
    }

    /**
     * Used to update the player, move in direction, change scale, and check for collision
     */
    public void update(float delta) {
        velocity.x = 0; velocity.y = 0;
        Player.x = getX(); Player.y = getY(); //Set the velocity to 0 and set the current x/y to x and y

        if(!canHeal)
        {
            healStopTime += delta;
        } //If cant heal, add time to healStopTime

        if(isWHeld) {
            velocity.y += SPEED;
        }
        if(isSHeld) {
            velocity.y -= SPEED;
        } //Add or subtract speed from the y velocity depending on which key is held (if both held velocity.y = 0)
        if(isAHeld) {
            velocity.x -= SPEED;
            this.setScale(-1,1);
        }
        if(isDHeld) {
            velocity.x += SPEED;
            this.setScale(1,1);
        } //Add or subtract speed from the x velocity depending on which key is held (if both held velocity.x = 0) and set the scale to flip the sprite depending on movement

        velocity = collision.checkForCollision(this, collisionLayer, velocity, collision); //Checks for collision in the direction of movement

        setX(getX() + velocity.x);
        setY(getY() + velocity.y); //Set the player position to current position + velocity
    }

    /**
     * When a key is pressed, this method is called
     * @param keycode Code of key that was pressed
     * @return true if successful
     */
    @Override
    public boolean keyDown(int keycode) {
        if (demo) {
            return false;
        }
        switch (keycode) {
            case Input.Keys.W:
                isWHeld = true;
                break;
            case Input.Keys.A:
                isAHeld = true;
                break;
            case Input.Keys.D:
                isDHeld = true;
                break;
            case Input.Keys.S:
                isSHeld = true;
                break;
        } //If key is pressed, set isKeyHeld to true
        return true;
    }

    /**
     * When a key is lifted, this method is called
     * @param keycode Code of key that was lifted
     * @return true if successful
     */
    @Override
    public boolean keyUp(int keycode) {
        if (demo) {
            return false;
        }
        switch (keycode) {
            case Input.Keys.W:
                isWHeld = false;
                break;
            case Input.Keys.S:
                isSHeld = false;
                break;
            case Input.Keys.A:
                isAHeld = false;
                break;
            case Input.Keys.D:
                isDHeld = false;
                break;
        } //Set key lifted to false
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    /**
     * Called when a mouse left click is clicked
     * @param screenX X Screen coordinate of mouse press
     * @param screenY Y Screen coordinate of mouse press
     * @param pointer
     * @param button
     * @return True if successful
     */
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (demo) {
            return false;
        }
        Vector3 vec=new Vector3(screenX,screenY,0);
        PlayScreen.camera.unproject(vec);
        Vector2 point = new Vector2(vec.x,vec.y); //Gets the x,y coordinate of mouse press and converts it to world coordinates

        for (Infiltrator infiltrator: NPCCreator.infiltrators)
        {
            if(infiltrator.getBoundingRectangle().contains(point))
            {
                NPCCreator.removeInfiltrator(infiltrator.index);
                Hud.ImposterCount += 1;
                return true;
            }
        } //If an infiltrator was clicked, remove it from the list

        for(CrewMembers crewMember: NPCCreator.crew) {
            if(crewMember.getBoundingRectangle().contains(point))
            {
                NPCCreator.removeCrewmember(crewMember.index);
                Hud.CrewmateCount += 1;
                return true;
            }
        }//If an crewmember was clicked, remove it from the list
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

    /**
     * Heal Auber for a certain amount
     * @param amount Amount to heal by
     */
    public void heal(int amount) {
        if(canHeal)
        {
            health += amount;
            if (health > 100) {
                health = 100;
            }
        } //If he can heal, add health
        else
        {
            if(System.currentTimeMillis() - healStopTime > 20 * 100)
            {
                canHeal = true;
                heal(amount);
            }
        } //If he cant heal, check if time has passed, if it has set canHeal to true and heal for the amount
    }

    /**
     * Heal Auber for the full amount
     */
    public void heal() {
        if(canHeal)
        {
            health = 100;
        } //If can heal, heal
        else
        {
            if(System.currentTimeMillis() - healStopTime > 20 * 100)
            {
                canHeal = true;
                heal();
            }
        } //If he cant heal, check if time has passed, if it has set canHeal to true and heal
    }

    /**
     * Take damage for amount given
     * @param amount Amount of damage to deal
     */
    public static void takeDamage(int amount) {
        health -= amount;
    }

    public void dispose()
    {
    }
}
