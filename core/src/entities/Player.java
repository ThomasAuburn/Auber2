package entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;

public class Player extends Sprite implements InputProcessor {



    /**The movement velocity */
    private Vector2 velocity = new Vector2(0,0);

    private float speed = 1;
    private float elapsedTime = 0;
    private float interpolationTime = 1;


    private TiledMapTileLayer collisionLayer;

    public Player(Sprite sprite, TiledMapTileLayer collisionLayer)
    {
        super(sprite);
        this.collisionLayer = collisionLayer;
    }

    @Override
    public void draw(Batch batch) {
        update(Gdx.graphics.getDeltaTime());
        super.draw(batch);
    }

    public void update(float delta) {
        float oldX = getX(), oldY = getY(), tileWidth = collisionLayer.getTileWidth(), tileHeight = collisionLayer.getTileHeight();
        boolean collideX = false, collideY = false;

        //move on x
        if(velocity.x < 0)
        {
            collideX = collidesLeft();
        }
        else if(velocity.x > 0)
        {
            collideX = collidesRight();
        }

        //react to x
        if(collideX)
        {
            setX(oldX);
            velocity.x = 0;
        }

        //move on y
        if(velocity.y < 0)
        {
            collideY = collidesBottom();
        }
        else if(velocity.y > 0)
        {
            collideY = collidesTop();
        }

        //react to y
        if(collideY)
        {
            setY(oldY);
            velocity.y = 0;
        }


        setX((float) getX() + velocity.x);
        setY((float) getY() + velocity.y);

    }

    public boolean collidesRight()
    {
        boolean collides = false;
        for(float step = collisionLayer.getTileHeight()/2; step < getHeight(); step += collisionLayer.getTileHeight()/2)
        {
            collides = isCellBlocked(getX() + getWidth(), getY() + step);
            if(collides)
                break;
        }

        return collides;
    }

    public boolean collidesLeft()
    {
        boolean collides = false;
        for(float step = collisionLayer.getTileHeight()/2; step < getHeight(); step += collisionLayer.getTileHeight()/2)
        {
            collides = isCellBlocked(getX(), getY() + step);
            if(collides)
                break;
        }
        return collides;
    }

    public boolean collidesTop()
    {
        boolean collides = false;
        for(float step = collisionLayer.getTileWidth()/2; step < getWidth(); step += collisionLayer.getTileWidth()/2)
        {
            collides = isCellBlocked(getX() + step, getY() + getHeight());
            if(collides)
                break;
        }

        return collides;
    }

    public boolean collidesBottom()
    {
        boolean collides = false;
        for(float step = collisionLayer.getTileWidth()/2; step < getWidth(); step += collisionLayer.getTileWidth()/2)
        {
            collides = isCellBlocked(getX() + step, getY());
            if(collides)
                break;
        }

        return collides;
    }


    private boolean isCellBlocked(float x,float y)
    {
        TiledMapTileLayer.Cell cell = collisionLayer.getCell((int) (x / collisionLayer.getTileWidth()), (int) (y / collisionLayer.getTileHeight()));
        return cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey("blocked");
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.W:
                velocity.y = speed;
                break;
            case Input.Keys.A:
                velocity.x = -speed;
                break;
            case Input.Keys.D:
                velocity.x = speed;
                break;
            case Input.Keys.S:
                velocity.y = -speed;
                break;
        }
        System.out.println(velocity);
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.W:
                if(velocity.y > 0)
                {velocity.y = 0;}
                break;
            case Input.Keys.S:
                if(velocity.y < 0)
                {velocity.y = 0;}
                break;
            case Input.Keys.A:
                if(velocity.x < 0)
                {velocity.x = 0;}
                break;
            case Input.Keys.D:
                if(velocity.x > 0)
                {velocity.x = 0;}
                break;
        }
        System.out.println(velocity);
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
}
