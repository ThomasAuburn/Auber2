package com.mygdx.auber.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class KeySystem {
    String name;
    final Sprite sprite;
    private Long destructionStartTime;

    KeySystem(Sprite sprite, String name) {
        this.sprite = sprite;
        this.name = name;
    }

    void startDestroy() {
        destructionStartTime = System.currentTimeMillis();
    }

    void stopDestroy() {
        destructionStartTime = null;
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
        if (timeElapsed <= 60*1000) {
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
