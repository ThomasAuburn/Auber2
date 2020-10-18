package com.mygdx.auber;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Room {
    Texture texture;
    Sprite sprite;
    Batch batch;

    Room(String name, Batch batch) {
        this.batch = batch;
        this.texture = new Texture(name);
        this.sprite = new Sprite(texture);
        this.sprite.setPosition(Gdx.graphics.getWidth()/2f - sprite.getWidth()/2,
                Gdx.graphics.getHeight()/2f - sprite.getHeight()/2);
        this.sprite.scale(0.1f);
    }
}
