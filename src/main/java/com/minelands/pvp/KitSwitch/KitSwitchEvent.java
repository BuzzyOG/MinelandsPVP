package com.minelands.pvp.KitSwitch;

import com.minelands.core.Events.MinelandEvent;
import com.minelands.core.MinePlayers.MinePlayer;
import org.bukkit.Location;

/**
 * Copyright Joey Gallegos {c} 2014. All Rights Reserved.
 * Any code contained within this document, and any associated APIs with similar branding
 * are the sole property of Joey Gallegos. Distribution, reproduction, taking snippets, or
 * claiming any contents as your own will break the terms of the License, and void any
 * agreements with you, the third party.
 */
public class KitSwitchEvent extends MinelandEvent {

    private MinePlayer player;
    private Location location;

    public KitSwitchEvent(MinePlayer player, Location location) {
        this.player = player;
        this.location = location;
    }

    public MinePlayer getPlayer() {
        return player;
    }

    public Location getLocation() {
        return location;
    }
}
