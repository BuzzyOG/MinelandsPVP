package com.minelands.pvp.Spawn;

import com.minelands.core.Events.Players.Items.RightClickItemEvent;
import com.minelands.core.Events.Region.RegionEnterEvent;
import com.minelands.core.Events.Region.RegionLeaveEvent;
import com.minelands.core.Util.Items.ItemUtil;
import com.minelands.core.Util.MessageType;
import com.minelands.pvp.Kits.KitInventory;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

/**
 * Copyright Joey Gallegos {c} 2014. All Rights Reserved.
 * Any code contained within this document, and any associated APIs with similar branding
 * are the sole property of Joey Gallegos. Distribution, reproduction, taking snippets, or
 * claiming any contents as your own will break the terms of the License, and void any
 * agreements with you, the third party.
 */
public class SpawnListener implements Listener {

    private ItemStack kitSelector = ItemUtil.createItem("§c§lKIT SELECTOR §7(Right click)", Arrays.asList("§fUse this to select your kit"), Material.ENCHANTED_BOOK, (byte) 0, 1);
    private ItemStack spectatorItem = ItemUtil.createItem("§a§lSPECTATE §7(Right click)", Arrays.asList("§fUse this to spectate other players"), Material.MAGMA_CREAM, (byte) 0, 1);

    @EventHandler
    public void onEnterSpawn(RegionEnterEvent event) {
        if (event.getRegion().getName().equalsIgnoreCase("spawn")) {
            if (!event.getPlayer().getBukkitPlayer().getInventory().contains(kitSelector) || !event.getPlayer().getBukkitPlayer().getInventory().contains(spectatorItem)) {
                if (event.getPlayer().getBukkitPlayer().getInventory().getItem(0) == null && event.getPlayer().getBukkitPlayer().getInventory().getItem(1) == null) {
                    event.getPlayer().getBukkitPlayer().getInventory().setItem(0, kitSelector);
                    event.getPlayer().getBukkitPlayer().getInventory().setItem(1, spectatorItem);
                } else {
                    event.getPlayer().getBukkitPlayer().getInventory().addItem(kitSelector);
                    event.getPlayer().getBukkitPlayer().getInventory().addItem(spectatorItem);
                }
            }
            event.getPlayer().message(MessageType.GREY_ARROW, "§aUse the spawn items to change your kit or spectate!");
        }

    }

    @EventHandler
    public void onLeaveSpawn(RegionLeaveEvent event) {
        if (event.getRegion().getName().equalsIgnoreCase("spawn")) {
            if (event.getPlayer().getBukkitPlayer().getInventory().contains(kitSelector)) {
                event.getPlayer().getBukkitPlayer().getInventory().remove(kitSelector);
            }
            if (event.getPlayer().getBukkitPlayer().getInventory().contains(spectatorItem)) {
                event.getPlayer().getBukkitPlayer().getInventory().remove(spectatorItem);
            }
        }
    }

    @EventHandler
    public void rightClickItem(RightClickItemEvent event) {
        if (event.getItem() == null) return;
        if (event.getItem().isSimilar(kitSelector)) {
            KitInventory.getInventory().showTo(event.getPlayer());
        } else if (event.getItem().isSimilar(spectatorItem)) {
            if (event.getPlayer().getRank().getId() < 3) {
                // TODO: Logic for the spectator item
            }
            event.getPlayer().message(MessageType.GREY_ARROW, "§cThis feature is currently disabled!");
        }

    }

}
