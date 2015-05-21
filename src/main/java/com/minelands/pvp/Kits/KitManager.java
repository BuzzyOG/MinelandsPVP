package com.minelands.pvp.Kits;

import com.minelands.core.MinePlayers.MinePlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright Joey Gallegos {c} 2014. All Rights Reserved.
 * Any code contained within this document, and any associated APIs with similar branding
 * are the sole property of Joey Gallegos. Distribution, reproduction, taking snippets, or
 * claiming any contents as your own will break the terms of the License, and void any
 * agreements with you, the third party.
 */
public class KitManager {

    private static Map<MinePlayer, Kit> userKits = new HashMap<>();
    private static List<Kit> freeKits = new ArrayList<>();
    private static List<Kit> paidKits = new ArrayList<>();

    public static Map<MinePlayer, Kit> getUserKits() {
        return userKits;
    }

    public static List<Kit> getFreeKits() {
        return freeKits;
    }

    public static List<Kit> getPaidKits() {
        return paidKits;
    }

    public static Kit getKit(String name) {

        // NORMAL KITS
        for (Kit kit : getFreeKits()) {
            if (kit.getName().equalsIgnoreCase(name)) return kit;
        }

        // YOUTUBE KITS
        for (Kit kit : getPaidKits()) {
            if (kit.getName().equalsIgnoreCase(name)) return kit;
        }

        // FINALLY
        return null;
    }
}
