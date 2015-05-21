package com.minelands.pvp.Pets;

import org.bukkit.entity.Sheep;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

/**
 * Copyright Joey Gallegos {c} 2014. All Rights Reserved.
 * Any code contained within this document, and any associated APIs with similar branding
 * are the sole property of Joey Gallegos. Distribution, reproduction, taking snippets, or
 * claiming any contents as your own will break the terms of the License, and void any
 * agreements with you, the third party.
 */
public class PetListener implements Listener {

    @EventHandler
    public void touchEntity(PlayerInteractEntityEvent event) {
        // TODO: List of spawned entities per player in a hashmap
        // TODO: Check if that entity belongs to that player
        if (event.getRightClicked() instanceof Sheep) {
            event.getRightClicked().setPassenger(event.getPlayer());
        }
    }

}
