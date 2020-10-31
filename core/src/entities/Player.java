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
        /**
         * "Function that scans the blocks directly right of the sprite, for the height of the sprite, and returns a bool based on if they are blocked or not"
         *
         * Args: None
         *
         * Returns:
         *  bool: True if there is a on the right of the sprite that contains "blocked", else returns false

         */
        boolean collides = false; //By default, no collision is detected
        for(float step = collisionLayer.getTileHeight()/2; step < getHeight(); step += collisionLayer.getTileHeight()/2) //A for loop iterating across the amount of tiles tall the sprite is
        {
            collides = isCellBlocked(getX() + getWidth()  - 5, getY() + step); //Calls isCellBlocked for each tile, if the cell contains "blocked" sets collides = true
            if(collides)
                break; //If collides is true, no longer need to run the loop, break and return collides
        }

        return collides;
    }

    public boolean collidesLeft()
    {
        boolean collides = false;
        for(float step = collisionLayer.getTileHeight()/2; step < getHeight(); step += collisionLayer.getTileHeight()/2)
        {
            collides = isCellBlocked(getX()  + 5, getY() + step);
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
            collides = isCellBlocked(getX() + step, getY() + getHeight()  - 5);
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
            collides = isCellBlocked(getX() + step, getY() + 5);
            if(collides)
                break;
        }

        return collides;
    }


    private boolean isCellBlocked(float x,float y)
    {
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
        TiledMapTileLayer.Cell cell = collisionLayer.getCell((int) (x / collisionLayer.getTileWidth()), (int) (y / collisionLayer.getTileHeight())); //Set variable cell to the cell at specified x,y coordinate
        return cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey("blocked"); //If cell is not null, and the cell contains "blocked", return true, else false
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
