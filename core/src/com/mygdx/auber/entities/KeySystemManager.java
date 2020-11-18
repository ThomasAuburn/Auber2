package com.mygdx.auber.entities;

public class KeySystemManager {
    public final KeySystem[] keySystems;

    KeySystemManager(KeySystem[] keySystems) {
        this.keySystems = keySystems;
    }

    public int safeKeySystemsCount() {
        int remaining = 0;

        for (KeySystem keySystem : keySystems) {
            if (keySystem.isSafe()) {
                remaining += 1;
            }
        }
        return remaining;
    }

    public int beingDestroyedKeySystemsCount() {
        int beingDestroyed = 0;

        for (KeySystem keySystem : keySystems) {
            if (keySystem.isBeingDestroyed()) {
                beingDestroyed += 1;
            }
        }
        return beingDestroyed;
    }

    public int destroyedKeySystemsCount() {
        int destroyed = 0;

        for (KeySystem keySystem : keySystems) {
            if (keySystem.isDestroyed()) {
                destroyed += 1;
            }
        }
        return destroyed;
    }
}
