package com.minelands.pvp.KitSwitch;

import com.minelands.core.Util.Hologram.Hologram;
import com.minelands.core.Util.LocationUtil;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright Joey Gallegos {c} 2014. All Rights Reserved.
 * Any code contained within this document, and any associated APIs with similar branding
 * are the sole property of Joey Gallegos. Distribution, reproduction, taking snippets, or
 * claiming any contents as your own will break the terms of the License, and void any
 * agreements with you, the third party.
 */
public class KitSwitchCore {

    private static List<Location> locations = new ArrayList<>();

    public static List<Location> getLocations() {
        return locations;
    }

    public static void instantiate(Location location) {
        getLocations().add(location);

        Hologram hologram = new Hologram(LocationUtil.getCenter(location.add(0, 1, 0)), "§c§lNEW! " + "§a§l" + "KIT SWITCH".toUpperCase(), "Soon you will be able to change your kit", "or redeem extra items at this kiosk");
        hologram.show();
    }
}
