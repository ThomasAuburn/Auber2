package com.mygdx.auber.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class KeySystem {
    String name;
    boolean isDestroyed = false;
    final Sprite sprite;

    KeySystem(Sprite sprite, String name) {
        this.sprite = sprite;
        this.name = name;
    }

    void destroy() {
        isDestroyed = true;
    }
}
