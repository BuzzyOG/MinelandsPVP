package com.minelands.pvp.KitSwitch;

import com.minelands.core.Events.Events;
import com.minelands.core.Events.System.SystemTickEvent;
import com.minelands.core.MinePlayers.Connection.Player.PlayerManager;
import com.minelands.core.MinePlayers.MinePlayer;
import com.minelands.core.Util.LocationUtil;
import com.minelands.core.Util.MessageType;
import com.minelands.core.Util.Particles.ParticleEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Copyright Joey Gallegos {c} 2014. All Rights Reserved.
 * Any code contained within this document, and any associated APIs with similar branding
 * are the sole property of Joey Gallegos. Distribution, reproduction, taking snippets, or
 * claiming any contents as your own will break the terms of the License, and void any
 * agreements with you, the third party.
 */
public class KitSwitchListener implements Listener {

    @EventHandler
    public void clickTradePost(PlayerInteractEvent event) {
        MinePlayer player = PlayerManager.getPlayer(event.getPlayer());
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (event.getClickedBlock() != null && event.getClickedBlock().getType() == Material.EMERALD_BLOCK) {
                if (KitSwitchCore.getLocations().contains(event.getClickedBlock().getLocation())) {
                    Events.callEvent(new KitSwitchEvent(player, event.getClickedBlock().getLocation()));
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void kitSwitchEvent(KitSwitchEvent event) {
        event.getPlayer().playSound(Sound.NOTE_PLING, 1, 1);
        event.getPlayer().message(MessageType.ERROR, "§cThis feature is disabled for now!");
    }

    @EventHandler
    public void particleEffect(SystemTickEvent event) {
        if (KitSwitchCore.getLocations().size() > 0) {
            for (Location location : KitSwitchCore.getLocations()) {
                if (location.getBlock() != null && location.getBlock().getType() == Material.EMERALD_BLOCK) {
                    float base = 0.8F;

                    for (Location loc : LocationUtil.circle(location, 1, 1, true, false, 0)) {
                        ParticleEffect.VILLAGER_HAPPY.display(base, base, base, 10F, 9, LocationUtil.getCenter(loc), 18.0D);
                    }
                }
            }
        }
    }
}
