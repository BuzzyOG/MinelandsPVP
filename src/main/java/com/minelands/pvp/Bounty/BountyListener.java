package com.minelands.pvp.Bounty;

import com.minelands.core.Events.System.SystemTickEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * Copyright Joey Gallegos {c} 2014. All Rights Reserved.
 * Any code contained within this document, and any associated APIs with similar branding
 * are the sole property of Joey Gallegos. Distribution, reproduction, taking snippets, or
 * claiming any contents as your own will break the terms of the License, and void any
 * agreements with you, the third party.
 */
public class BountyListener implements Listener {

    // TODO: Listener for player leave server if bounty
    // TODO: Listener for player death event
    // TODO: Listener for other things

    @EventHandler
    public void onTick(SystemTickEvent event) {
        if (BountySystem.getCurrentBounty() != null) {
            // TODO: Particles around the bountied player
        }
    }

}
