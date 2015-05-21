package com.minelands.pvp.Listeners;


import com.minelands.core.Events.System.SystemTickEvent;
import com.minelands.core.MinePlayers.Connection.Player.PlayerManager;
import com.minelands.core.MinePlayers.MinePlayer;
import com.minelands.core.Statistics.StatManager;
import com.minelands.core.Statistics.StatValue;
import com.minelands.core.Util.Sidebar.SideBar;
import org.bukkit.event.Listener;

/**
 * Copyright Joey Gallegos {c} 2014. All Rights Reserved.
 * Any code contained within this document, and any associated APIs with similar branding
 * are the sole property of Joey Gallegos. Distribution, reproduction, taking snippets, or
 * claiming any contents as your own will break the terms of the License, and void any
 * agreements with you, the third party.
 */
public class ScoreboardListener implements Listener {

    private void replenish(MinePlayer player) {
        SideBar sidebar = new SideBar("§a§lMINELANDS PVP");

        sidebar.blankLine();
        sidebar.add("§e§lOnline");
        sidebar.add(String.valueOf(PlayerManager.getOnlinePlayers().size()));

        sidebar.blankLine();
        sidebar.add("§d§lStaff");
        sidebar.add(String.valueOf(PlayerManager.getOnlineStaff().size()));

        StatValue value = StatManager.getPlayerStat(player, StatManager.getStat("power"));
        sidebar.blankLine();
        sidebar.add("§3§lPower");
        sidebar.add(String.valueOf(value.getValue()));

        sidebar.build();
        player.setSideBar(sidebar);
    }

    // TODO: Event handler
    public void onTick(SystemTickEvent event) {
        for (MinePlayer player : PlayerManager.getOnlinePlayers()) {
            if (player.getSideBar() == null) {
                player.setSideBar(new SideBar("§c§lLoading.."));
                continue;
            }

            SideBar sidebar = player.getSideBar();
            sidebar.reset();
            replenish(player);
        }
    }
}
