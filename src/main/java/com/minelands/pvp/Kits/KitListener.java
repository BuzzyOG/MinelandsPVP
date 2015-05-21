package com.minelands.pvp.Kits;

import com.minelands.core.MinePlayers.Connection.Player.PlayerManager;
import com.minelands.core.MinePlayers.MinePlayer;
import com.minelands.core.Util.MessageType;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
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
public class KitListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onSignClick(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Block block = event.getClickedBlock();
            if (block.getType() == Material.WALL_SIGN || block.getType() == Material.SIGN_POST) {
                Sign sign = (Sign) block.getState();

                MinePlayer clicker = PlayerManager.getPlayer(event.getPlayer());

                String secondLine = ChatColor.stripColor(sign.getLine(1));
                if (secondLine != null) {
                    if (secondLine.equalsIgnoreCase("Kit")) {
                        String kitName = ChatColor.stripColor(sign.getLine(2));

                        if (kitName.equalsIgnoreCase("")) {
                            clicker.message(MessageType.ERROR, "§cSorry, this sign hasn't been set up yet!");
                            return;
                        }

                        if (KitManager.getKit(kitName) != null) {
                            Kit kit = KitManager.getKit(kitName);

                            if (KitManager.getUserKits().containsKey(clicker) && KitManager.getUserKits().get(clicker) != null) {
                                Kit activeKit = KitManager.getUserKits().get(clicker);
                                if (kit.getName().equalsIgnoreCase(activeKit.getName())) {
                                    clicker.message(MessageType.ERROR, "§cYou're already using this kit!");
                                    return;
                                }
                            }

                            if (!kit.isLocked()) {
                                KitManager.getUserKits().remove(clicker);
                                KitManager.getUserKits().put(clicker, kit);
                                kit.give(clicker);
                                clicker.message(MessageType.GREY_ARROW, "§aYou set your kit to §e" + kit.getName() + "§a!");
                                clicker.playSound(Sound.CHICKEN_EGG_POP, 3, 5);
                            } else {
                                clicker.message(MessageType.GREY_ARROW, "§cSorry, this kit isn't unlocked yet!");
                            }
                        } else {
                            clicker.message(MessageType.ERROR, "§cSorry, that doesn't seem to be a valid kit!");
                        }
                    }
                }
            }
        }
    }
}
