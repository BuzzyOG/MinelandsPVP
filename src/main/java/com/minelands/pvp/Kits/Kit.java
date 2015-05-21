package com.minelands.pvp.Kits;

import com.minelands.core.MinePlayers.MinePlayer;
import com.minelands.core.MinePlayers.Ranks.Rank;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.Collections;
import java.util.List;

/**
 * Copyright Joey Gallegos {c} 2014. All Rights Reserved.
 * Any code contained within this document, and any associated APIs with similar branding
 * are the sole property of Joey Gallegos. Distribution, reproduction, taking snippets, or
 * claiming any contents as your own will break the terms of the License, and void any
 * agreements with you, the third party.
 */
public abstract class Kit {

    public abstract boolean isLocked();

    public abstract String getName();

    public abstract String getPermissionSuffix();

    public abstract List<String> getDescription();

    public abstract Rank minRank();

    public abstract Material getDisplayIcon();

    public abstract int getArrowAmount();
    public abstract ItemStack getBow();

    public abstract ItemStack getHelmet();

    public abstract ItemStack getChestplate();

    public abstract ItemStack getLeggings();

    public abstract ItemStack getBoots();

    public abstract ItemStack getPrimaryWeapon();

    public abstract List<PotionEffect> getKitEffects();

    public abstract List<ItemStack> getExtraItems();

    public abstract boolean hasFishingPole();

    public abstract boolean hasFlintAndSteel();

    public void give(MinePlayer player) {
        player.reset();
        Player pl = player.getBukkitPlayer();

        if (pl.getGameMode() != GameMode.SURVIVAL) {
            pl.setGameMode(GameMode.SURVIVAL);
        }

        // CLEAR
        pl.getInventory().clear();

        // ENCHANTED ARMOUR
        if (getHelmet() != null) pl.getInventory().setHelmet(getHelmet());
        if (getChestplate() != null) pl.getInventory().setChestplate(getChestplate());
        if (getLeggings() != null) pl.getInventory().setLeggings(getLeggings());
        if (getBoots() != null) pl.getInventory().setBoots(getBoots());

        for (ItemStack item : pl.getInventory().getArmorContents()) {
            if (item == null) continue;
            item.addUnsafeEnchantment(Enchantment.DURABILITY, 8);
        }

        for (ItemStack item : pl.getInventory().getContents()) {
            if (item == null) continue;
            item.addUnsafeEnchantment(Enchantment.DURABILITY, 8);
        }

        // PRIMARY WEAPON
        if (getPrimaryWeapon() != null) {
            ItemStack weapon = getPrimaryWeapon();
            weapon.getItemMeta().setDisplayName("§2Weapon");
            pl.getInventory().setItem(0, getPrimaryWeapon());
        }

        // ARROWS
        if (getArrowAmount() > 0 && getBow() != null) {
            pl.getInventory().addItem(getBow());
            for (int i = 0; i < getArrowAmount(); i++) {
                pl.getInventory().addItem(toItemStack(Material.ARROW));
            }
        }

        // FISHING POLE
        if (hasFishingPole()) {
            pl.getInventory().addItem(toItemStack(Material.FISHING_ROD));
        }

        if (hasFlintAndSteel()) {
            pl.getInventory().addItem(toItemStack(Material.FLINT_AND_STEEL));
        }

        // OTHER COOL ITEMS
        List<ItemStack> extraItems = getExtraItems();
        if (extraItems != null) {
            Collections.shuffle(extraItems);
            for (ItemStack item : extraItems) {
                pl.getInventory().addItem(item);
            }
        }

        // EFFECTS
        List<PotionEffect> effects = getKitEffects();
        if (effects != null) {
            pl.addPotionEffects(getKitEffects());
        }

        pl.updateInventory();
    }

    // HELPER METHOD
    public ItemStack toItemStack(Material material) {



        return new ItemStack(material, 1);
    }

}
