package com.minelands.pvp.Listeners;

import com.minelands.core.MinePlayers.Connection.Player.PlayerManager;
import com.minelands.core.MinePlayers.MinePlayer;
import com.minelands.core.Statistics.StatManager;
import com.minelands.core.Statistics.StatValue;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;

import java.util.HashSet;
import java.util.Set;

/**
 * Copyright Joey Gallegos {c} 2014. All Rights Reserved.
 * Any code contained within this document, and any associated APIs with similar branding
 * are the sole property of Joey Gallegos. Distribution, reproduction, taking snippets, or
 * claiming any contents as your own will break the terms of the License, and void any
 * agreements with you, the third party.
 */
public class ArrowListener implements Listener {

    private static Set<Arrow> arrows = new HashSet<>();

    public static Set<Arrow> getArrows() {
        return arrows;
    }

    @EventHandler
    public void onArrowShoot(ProjectileLaunchEvent event) {
        if (!(event.getEntity() instanceof Arrow)) {
            event.setCancelled(true);
        }

        if (event.getEntity().getShooter() instanceof Player) {
            Player bp = (Player) event.getEntity().getShooter();
            MinePlayer player = PlayerManager.getPlayer(bp);

            StatValue shots = StatManager.getPlayerStat(player, StatManager.getStat("arrows_fired"));
            shots.addStat(1);
        }
    }

    @EventHandler
    public void onArrowExplode(ProjectileHitEvent event) {
        if (event.getEntity() instanceof Arrow) {
            // TODO: Make firework explode
        }
    }

}
