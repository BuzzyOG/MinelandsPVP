package com.minelands.pvp.Kits.Paid;

import com.minelands.core.MinePlayers.Ranks.Rank;
import com.minelands.pvp.Kits.Kit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright Joey Gallegos {c} 2014. All Rights Reserved.
 * Any code contained within this document, and any associated APIs with similar branding
 * are the sole property of Joey Gallegos. Distribution, reproduction, taking snippets, or
 * claiming any contents as your own will break the terms of the License, and void any
 * agreements with you, the third party.
 */
public class Magician extends Kit {

    @Override
    public boolean isLocked() {
        return true;
    }

    @Override
    public String getName() {
        return "Magician";
    }

    @Override
    public String getPermissionSuffix() {
        return "magician";
    }

    @Override
    public List<String> getDescription() {
        List<String> lore = new ArrayList<>();
        lore.add("§7This kit allows you to throw potions at other players");
        lore.add("§7to inflict damage or cause confusion during battle!");
        return lore;
    }

    @Override
    public Rank minRank() {
        return Rank.ELITE;
    }

    @Override
    public Material getDisplayIcon() {
        return Material.BLAZE_ROD;
    }

    @Override
    public int getArrowAmount() {
        return 12;
    }

    @Override
    public ItemStack getBow() {
        return toItemStack(Material.BOW);
    }

    @Override
    public ItemStack getHelmet() {
        ItemStack stack = new ItemStack(Material.IRON_HELMET);
        return stack;
    }

    @Override
    public ItemStack getChestplate() {
        ItemStack stack = new ItemStack(Material.IRON_CHESTPLATE);
        return stack;
    }

    @Override
    public ItemStack getLeggings() {
        ItemStack stack = new ItemStack(Material.IRON_LEGGINGS);
        return stack;
    }

    @Override
    public ItemStack getBoots() {
        ItemStack stack = new ItemStack(Material.IRON_BOOTS);
        return stack;
    }

    @Override
    public ItemStack getPrimaryWeapon() {
        ItemStack stack = new ItemStack(Material.IRON_SWORD);
        return stack;
    }

    @Override
    public List<PotionEffect> getKitEffects() {
        List<PotionEffect> effects = new ArrayList<>();
        effects.add(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, 1));
        return effects;
    }

    @Override
    public List<ItemStack> getExtraItems() {
        return null;
    }

    @Override
    public boolean hasFishingPole() {
        return false;
    }

    @Override
    public boolean hasFlintAndSteel() {
        return true;
    }

}
