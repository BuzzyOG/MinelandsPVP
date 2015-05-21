package com.minelands.pvp.KitSwitch;

import org.bukkit.Location;

/**
 * Copyright Joey Gallegos {c} 2014. All Rights Reserved.
 * Any code contained within this document, and any associated APIs with similar branding
 * are the sole property of Joey Gallegos. Distribution, reproduction, taking snippets, or
 * claiming any contents as your own will break the terms of the License, and void any
 * agreements with you, the third party.
 */
public class KitSwitch {

    private SwitchType type;
    private Location location;

    public KitSwitch(SwitchType type, Location location) {
        this.type = type;
        this.location = location;
    }

    public SwitchType getType() {
        return type;
    }

    public Location getLocation() {
        return location;
    }
}
