package com.minelands.pvp.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

/**
 * Copyright Joey Gallegos {c} 2014. All Rights Reserved.
 * Any code contained within this document, and any associated APIs with similar branding
 * are the sole property of Joey Gallegos. Distribution, reproduction, taking snippets, or
 * claiming any contents as your own will break the terms of the License, and void any
 * agreements with you, the third party.
 */
public class PingListener implements Listener {

    @EventHandler
    public void onPing(ServerListPingEvent event) {
        event.setMotd("§e§lMinelands PVP Network\n" + "§b§l§k||| §c§lPRIVATE BETA SOON §b§l§k|||");
    }

}
