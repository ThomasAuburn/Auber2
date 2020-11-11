package com.mygdx.auber;

import com.badlogic.gdx.graphics.g2d.Sprite;

public abstract class GameObject {


    protected int posX, posY;
    protected ID id;
    protected int velX, velY;

    public GameObject(int posX, int posY, ID id){
        this.posX = posX;
        this.posY = posY;
        this.id = id;

    }

    public abstract void render(Sprite AtlasReference);

    public void setX(int posX){
        this.posX = posX;
    }

    public void setY(int posY){
        this.posY = posY;
    }

    public int getX(){
        return posX;
    }

    public int getY(){
        return posY;
    }

    public void setId(ID id){
        this.id = id;
    }

    public ID getId(){
        return id;
    }

    public void setVelX(int velX){
        this.velX = velX;
    }

    public void setVelY(int velY){
        this.velY = velY;
    }

    public int getVelX(){
        return velX;
    }

    public int getVelY(){
        return velY;
    }

}
