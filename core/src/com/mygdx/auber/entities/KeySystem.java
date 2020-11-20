package com.mygdx.auber.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;

import java.util.Vector;

public class KeySystem {
    public String name;
    final TiledMapTileLayer.Cell cell;
    private Long destructionStartTime;
    public Vector2 position;

    KeySystem(TiledMapTileLayer.Cell cell, String name, Vector2 position) {
        this.cell = cell;
        this.name = name;
        this.position = position;
    }

    void startDestroy() {
        destructionStartTime = System.currentTimeMillis();
    }

    void stopDestroy() {
        if(!isDestroyed())
        {
            destructionStartTime = null;
        }
    }

    /**
     * Calculates time remaining for the system to be destroyed. Note: System is destroyed in 60 seconds.
     * @return Null if system isn't being/hasn't been destroyed. Time remaining in milliseconds.
     */
    Long timeRemaining() {
        if (destructionStartTime == null) {
            // System isn't being destroyed
            return null;
        }
        long timeElapsed = System.currentTimeMillis() - destructionStartTime;
        if (timeElapsed <= 60*100) {
            // System is being destroyed. Less than 60 seconds remaining.
            return timeElapsed;
        }
        // System has been destroyed
        return null;
    }

    boolean isSafe() {
        return timeRemaining() == null && destructionStartTime == null;
    }
    boolean isBeingDestroyed() {
        return timeRemaining() != null;
    }
    boolean isDestroyed() {
        return timeRemaining() == null && destructionStartTime != null;
    }
}
