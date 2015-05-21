package com.minelands.pvp.Kits;


import com.minelands.core.Events.Inventory.MinelandInventoryClickEvent;
import com.minelands.core.Events.Inventory.MinelandInventoryRequestEvent;
import com.minelands.core.Inventories.InventorySize;
import com.minelands.core.Inventories.MinelandInventory;
import com.minelands.core.MinePlayers.MinePlayer;
import com.minelands.core.Regions.Region;
import com.minelands.core.Regions.RegionManager;
import com.minelands.core.Util.MessageType;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright Joey Gallegos {c} 2014. All Rights Reserved.
 * Any code contained within this document, and any associated APIs with similar branding
 * are the sole property of Joey Gallegos. Distribution, reproduction, taking snippets, or
 * claiming any contents as your own will break the terms of the License, and void any
 * agreements with you, the third party.
 */
public class KitInventory implements Listener {
    private static MinelandInventory inventory;
    private static Map<Integer, ItemStack> items = new HashMap<>();

    public static MinelandInventory getInventory() {
        return inventory;
    }

    public static void setInventory(MinelandInventory inventory) {
        KitInventory.inventory = inventory;
    }

    public static Map<Integer, ItemStack> getItems() {
        return items;
    }

    public static void setupKitInventory() {
        {
            ItemStack item = new ItemStack(Material.EMERALD_BLOCK, 1);
            ItemMeta meta = item.getItemMeta();

            List<String> lore = new ArrayList<>();
            lore.add(" ");
            lore.add("§4§l> §7You don't have a kit booster applied!");

            meta.setLore(lore);
            meta.setDisplayName("§a§lKIT BOOSTERS");

            item.setItemMeta(meta);
            getItems().put(3, item);
        }
        {
            ItemStack book = new ItemStack(Material.BOOK, 1);
            ItemMeta meta = book.getItemMeta();

            List<String> lore = new ArrayList<>();
            lore.add(" ");
            lore.add("§7Kit's can be used to defend yourself against enemies");
            lore.add("§7and attack others. They can be unlocked by completing");
            lore.add("§7quests and goals or by being §a§opurchased §7in our store");

            meta.setLore(lore);
            meta.setDisplayName("§c§lAVAILABLE KITS");

            book.setItemMeta(meta);
            //ItemGlow.makeGlow(book); TODO: Fix lore being re-applied
            getItems().put(4, book);
        }
        {
            ItemStack book = new ItemStack(Material.CHEST, 1);
            ItemMeta meta = book.getItemMeta();

            List<String> lore = new ArrayList<>();
            lore.add(" ");
            lore.add("§4§l> §7You don't have any additional kit items!");

            meta.setLore(lore);
            meta.setDisplayName("§e§lKIT ITEMS");

            book.setItemMeta(meta);
            getItems().put(5, book);
        }
        {
            ItemStack item = new ItemStack(Material.ARROW, 1);
            ItemMeta meta = item.getItemMeta();

            List<String> lore = new ArrayList<>();
            lore.add(" ");
            lore.add("§4§l> §7There are no more pages to view");

            meta.setLore(lore);
            meta.setDisplayName("§e§lNEXT PAGE");

            item.setItemMeta(meta);
            getItems().put(8, item);
        }


        List<Integer> spots = new ArrayList<>();
        spots.add(19);
        spots.add(21);
        spots.add(23);
        spots.add(25);
        spots.add(29);
        spots.add(31);
        spots.add(33);

        for (Kit kit : KitManager.getPaidKits()) {
            if (kit.getDisplayIcon() == null) continue;

            ItemStack stack = new ItemStack(kit.getDisplayIcon(), 1);
            ItemMeta meta = stack.getItemMeta();
            meta.setDisplayName("§c§l" + kit.getName());

            List<String> lore = new ArrayList<>();
            if (kit.getDescription() == null) {
                lore.add(" ");
                lore.add("§c§oNo description");
            }
            else
            {
                lore.add(" ");
                lore.addAll(kit.getDescription());
            }
            meta.setLore(lore);
            stack.setItemMeta(meta);

            getItems().put(spots.get(0), stack);
            spots.remove(0);
        }

        for (Kit kit : KitManager.getFreeKits()) {
            if (kit.getDisplayIcon() == null) continue;

            ItemStack stack = new ItemStack(kit.getDisplayIcon(), 1);
            ItemMeta meta = stack.getItemMeta();
            meta.setDisplayName("§c§l" + kit.getName() + " §5§oFree");

            List<String> lore = new ArrayList<>();
            if (kit.getDescription() == null) {
                lore.add(" ");
                lore.add("§c§oNo description");
            }
            else
            {
                lore.add(" ");
                lore.addAll(kit.getDescription());
            }
            meta.setLore(lore);
            stack.setItemMeta(meta);

            getItems().put(spots.get(0), stack);
            spots.remove(0);
        }

        setInventory(new MinelandInventory("§c§lKITS", InventorySize.S_45, getItems()));
        getInventory().fillWithGlass(true);
        getInventory().setAllowDrag(false);
        getInventory().setRefresh(true);
    }

    @EventHandler
    public void onInventoryRequest(MinelandInventoryRequestEvent event) {
        if (event.getInventory().is(getInventory())) {
            event.getPlayer().message(MessageType.GREY_ARROW, "§aShowing you the currently unlocked kits!");
        }
    }

    @EventHandler
    public void onClick(MinelandInventoryClickEvent event) {
        if (event.getInventory().is(getInventory())) {
            if (event.getCurrent() == null) return;

            if (event.getCurrent().getType() != Material.STAINED_GLASS_PANE) {
                Material mat = event.getCurrent().getType();

                Kit kit = null;
                for (Kit k : KitManager.getPaidKits()) {
                    if (k.getDisplayIcon().equals(mat)) {
                        kit = k;
                    }
                }
                for (Kit k : KitManager.getFreeKits()) {
                    if (k.getDisplayIcon().equals(mat)) {
                        kit = k;
                    }
                }

                MinePlayer player = event.getPlayer();
                if (kit != null) {
                    Region rg = RegionManager.getRegionAt(player.getBukkitPlayer().getLocation());
                    if (rg == null) {
                        player.message(MessageType.ERROR, "§cYou can't change kits right now!");
                        player.message(MessageType.ERROR, "§cYou can only change your kit at spawn!");
                    }
                    else {

                        if (kit.getPermissionSuffix() != null) {
                            String perm = "kit." + kit.getPermissionSuffix();
                            if (player.getBukkitPlayer().hasPermission(perm)) {
                                KitManager.getUserKits().remove(player);
                                KitManager.getUserKits().put(player, kit);
                                kit.give(player);
                                player.message(MessageType.GREY_ARROW, "§aYou changed your kit to §e" + kit.getName() + "§a!");
                                player.playSound(Sound.CHICKEN_EGG_POP, 3, 5);
                            } else {
                                player.playSound(Sound.EXPLODE, 3, 5);
                                player.message(MessageType.GREY_ARROW, "§cYou haven't purchased this kit! §7- §e" + perm);
                            }
                        }
                        else {
                            KitManager.getUserKits().remove(player);
                            KitManager.getUserKits().put(player, kit);
                            kit.give(player);
                            player.message(MessageType.GREY_ARROW, "§aYou changed your kit to §e" + kit.getName() + "§a!");
                            player.playSound(Sound.CHICKEN_EGG_POP, 3, 5);
                        }
                        player.getBukkitPlayer().closeInventory();

                    }
                } else {
                    // TODO: Logic for other icons in the inventory
                }
            }
        }
    }
}