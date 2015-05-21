package com.minelands.pvp.SpawnPoints;

import org.bukkit.Location;

/**
 * Copyright Joey Gallegos {c} 2014. All Rights Reserved.
 * Any code contained within this document, and any associated APIs with similar branding
 * are the sole property of Joey Gallegos. Distribution, reproduction, taking snippets, or
 * claiming any contents as your own will break the terms of the License, and void any
 * agreements with you, the third party.
 */
public class SpawnPoint {

    private int id;
    private Location location = null;
    private String name = "";

    public SpawnPoint(int id, Location location, String name) {
        this.id = id;
        this.location = location;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public Location getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }
}
