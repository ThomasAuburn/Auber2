package com.mygdx.auber;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Character {
    Texture texture;
    Sprite sprite;
    Batch batch;

    boolean isMainCharacter = false;

    Character(String name, Batch batch) {
        this.texture = new Texture(name);
        this.sprite = new Sprite(texture);
        this.batch = batch;
    }

    public void translate(int x, int y) {
        sprite.translate(x, y);
    }
}
