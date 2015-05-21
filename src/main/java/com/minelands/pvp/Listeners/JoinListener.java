package com.minelands.pvp.Listeners;

import com.minelands.core.Events.Players.MinelandPlayerJoinEvent;
import com.minelands.core.Events.Stats.StatsDownloadedEvent;
import com.minelands.core.MinePlayers.Ranks.Rank;
import com.minelands.core.Util.MessageType;
import com.minelands.pvp.PVPCore;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Copyright Joey Gallegos {c} 2014. All Rights Reserved.
 * Any code contained within this document, and any associated APIs with similar branding
 * are the sole property of Joey Gallegos. Distribution, reproduction, taking snippets, or
 * claiming any contents as your own will break the terms of the License, and void any
 * agreements with you, the third party.
 */
public class JoinListener implements Listener {

    @EventHandler
    public void onLogin(PlayerJoinEvent event) {
        event.getPlayer().teleport(PVPCore.getSpawn());
        event.getPlayer().getInventory().clear();
        event.getPlayer().updateInventory();

        // TODO: Heal too but yeah no mineland player
    }

    @EventHandler
    public void onLogin(MinelandPlayerJoinEvent event) {
        PVPCore.downloadStats(event.getPlayer());

        Rank rank = event.getPlayer().getRank();
        event.getPlayer().setPlayerListName(rank.getColor() + event.getPlayer().getName());
        event.getPlayer().setDisplayName(rank.getColor() + event.getPlayer().getName());

        // TODO: Find a better style to show off
        event.getPlayer().sendActionBar("§2Welcome to §aMinelands §2PVP Network!", 3);
        event.getPlayer().reset();
        event.getPlayer().removeArmour();
    }

    @EventHandler
    public void onStatsDownloaded(StatsDownloadedEvent event) {
        event.getPlayer().message(MessageType.GREY_ARROW, "§aYour latest stats have been downloaded!");
    }
}
