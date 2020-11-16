package com.mygdx.auber.entities;

public class KeySystemManager {
    public final KeySystem[] keySystems;

    KeySystemManager(KeySystem[] keySystems) {
        this.keySystems = keySystems;
    }

    public int keySystemsRemaining() {
        int remaining = keySystems.length;

        for (KeySystem keySystem : keySystems) {
            if (keySystem.isDestroyed) {
                remaining -= 1;
            }
        }
        return remaining;
    }

    public int keySystemsDestroyed() {
        int destroyed = 0;

        for (KeySystem keySystem : keySystems) {
            if (keySystem.isDestroyed) {
                destroyed += 1;
            }
        }
        return destroyed;
    }
}
