package com.minelands.pvp.SheepCanon;

import com.minelands.core.Events.System.SystemTickEvent;
import com.minelands.core.MinePlayers.Connection.Player.PlayerManager;
import com.minelands.core.MinePlayers.MinePlayer;
import com.minelands.core.Util.Items.ItemUtil;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Sheep;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.*;

/**
 * Copyright Joey Gallegos {c} 2014. All Rights Reserved.
 * Any code contained within this document, and any associated APIs with similar branding
 * are the sole property of Joey Gallegos. Distribution, reproduction, taking snippets, or
 * claiming any contents as your own will break the terms of the License, and void any
 * agreements with you, the third party.
 */
public class SheepCannon implements Listener {

    private final long rmtime = 8 * 1000;
    private Map<Long, List<Sheep>> launchedSheep = new HashMap<>();

    private static ItemStack launcher = ItemUtil.createItem("§d§lSheep Canon".toUpperCase(), Arrays.asList("§fUse this to launch your sheep into the air!"), Material.WOOL, (byte) 10, 1);

    public static ItemStack getLauncher() {
        return launcher;
    }

    @EventHandler
    public void onTick(SystemTickEvent event) {
        for (Map.Entry<Long, List<Sheep>> entry : launchedSheep.entrySet()) {
            long active = System.currentTimeMillis() - entry.getKey();
            if (active > this.rmtime) {
                for (Sheep sheep : entry.getValue()) {
                    if (sheep == null) continue;

                    sheep.remove();
                }
                launchedSheep.remove(entry.getKey());
            }
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getItem() != null) {
            if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (event.getItem().equals(getLauncher())) {
                    MinePlayer player = PlayerManager.getPlayer(event.getPlayer());
                    List<Sheep> sheep = launchSheep(3, player);
                    launchedSheep.put(System.currentTimeMillis(), sheep);
                }
            }
        }
    }

    private List<Sheep> launchSheep(int i, MinePlayer player) {
        List<Sheep> allSheep = new ArrayList<>();
        for (int x = 0; x < i; x++) {
            // LAUNCH
            Sheep sheep = player.getBukkitPlayer().getWorld().spawn(player.getBukkitPlayer().getLocation(), Sheep.class);
            sheep.setColor(DyeColor.PURPLE);
            sheep.setAdult();
            sheep.setAgeLock(true);
            sheep.setRemoveWhenFarAway(true);
            allSheep.add(sheep);
        }

        Vector vector = player.getBukkitPlayer().getLocation().getDirection().multiply(3.0);
        for (Sheep sheep : allSheep) {
            sheep.setVelocity(vector);
        }
        return allSheep;
    }
}
