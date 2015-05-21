package com.minelands.pvp.Kits.Free;

import com.minelands.core.MinePlayers.Ranks.Rank;
import com.minelands.pvp.Kits.Kit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Copyright Joey Gallegos {c} 2014. All Rights Reserved.
 * Any code contained within this document, and any associated APIs with similar branding
 * are the sole property of Joey Gallegos. Distribution, reproduction, taking snippets, or
 * claiming any contents as your own will break the terms of the License, and void any
 * agreements with you, the third party.
 */
public class Slave extends Kit {
    @Override
    public boolean isLocked() {
        return false;
    }

    @Override
    public String getName() {
        return "Slave";
    }

    @Override
    public String getPermissionSuffix() {
        return null;
    }

    @Override
    public List<String> getDescription() {
        return Arrays.asList("", "");
    }

    @Override
    public Rank minRank() {
        return Rank.ELITE;
    }

    @Override
    public Material getDisplayIcon() {
        return Material.IRON_HOE;
    }

    @Override
    public int getArrowAmount() {
        return 6;
    }

    @Override
    public ItemStack getBow() {
        return toItemStack(Material.BOW);
    }

    @Override
    public ItemStack getHelmet() {
        return toItemStack(Material.LEATHER_HELMET);
    }

    @Override
    public ItemStack getChestplate() {
        return toItemStack(Material.IRON_CHESTPLATE);
    }

    @Override
    public ItemStack getLeggings() {
        return toItemStack(Material.LEATHER_LEGGINGS);
    }

    @Override
    public ItemStack getBoots() {
        return toItemStack(Material.IRON_BOOTS);
    }

    @Override
    public ItemStack getPrimaryWeapon() {
        ItemStack stack = new ItemStack(Material.IRON_AXE);
        return stack;
    }

    @Override
    public List<PotionEffect> getKitEffects() {
        return null;
    }

    @Override
    public List<ItemStack> getExtraItems() {
        List<ItemStack> items = new ArrayList<>();
        {
            ItemStack item = new ItemStack(Material.GOLDEN_CARROT, 4);
            items.add(item);
        }
        return items;
    }

    @Override
    public boolean hasFishingPole() {
        return true;
    }

    @Override
    public boolean hasFlintAndSteel() {
        return false;
    }
}
