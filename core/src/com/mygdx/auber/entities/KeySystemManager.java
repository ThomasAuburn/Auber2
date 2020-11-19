package com.mygdx.auber.entities;

import com.badlogic.gdx.utils.Array;

public class KeySystemManager {
    public static Array<KeySystem> keySystems = new Array<>();

    KeySystemManager(Array<KeySystem> keySystems) {
        KeySystemManager.keySystems = keySystems;
    }

    public static int safeKeySystemsCount() {
        int remaining = 0;

        for (KeySystem keySystem : keySystems) {
            if (keySystem.isSafe()) {
                remaining += 1;
            }
        }
        return remaining;
    }

    public static int beingDestroyedKeySystemsCount() {
        int beingDestroyed = 0;

        for (KeySystem keySystem : keySystems) {
            if (keySystem.isBeingDestroyed()) {
                beingDestroyed += 1;
            }
        }
        return beingDestroyed;
    }

    public static int destroyedKeySystemsCount() {
        int destroyed = 0;

        for (KeySystem keySystem : keySystems) {
            if (keySystem.isDestroyed()) {
                destroyed += 1;
            }
        }
        return destroyed;
    }
}
