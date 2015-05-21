package com.minelands.pvp.Kits;

/**
 * Copyright Joey Gallegos {c} 2014. All Rights Reserved.
 * Any code contained within this document, and any associated APIs with similar branding
 * are the sole property of Joey Gallegos. Distribution, reproduction, taking snippets, or
 * claiming any contents as your own will break the terms of the License, and void any
 * agreements with you, the third party.
 */
public class UnlockableKits {

    private static Kit unlockedKit;
    private static long lastUnlocked;

    public static Kit getUnlockedKit() {
        return unlockedKit;
    }

    public static void setUnlockedKit(Kit unlockedKit) {
        UnlockableKits.unlockedKit = unlockedKit;
    }

    public static long getLastUnlocked() {
        return lastUnlocked;
    }

    public static void setLastUnlocked(long lastUnlocked) {
        UnlockableKits.lastUnlocked = lastUnlocked;
    }

    public void unlockKit(Kit kit) {
    }

}
