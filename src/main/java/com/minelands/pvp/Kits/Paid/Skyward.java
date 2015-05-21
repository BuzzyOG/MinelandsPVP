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
public class Skyward extends Kit {

    @Override
    public boolean isLocked() {
        return false;
    }

    @Override
    public String getName() {
        return "Skyward";
    }

    @Override
    public String getPermissionSuffix() {
        return "skyward";
    }

    @Override
    public List<String> getDescription() {
        List<String> lore = new ArrayList<>();
        lore.add("§7This kit contains boots crafted");
        lore.add("§7by a sky warrior from the past!");
        lore.add("§7These boots will make you run");
        lore.add("§7faster then the normal person");
        lore.add("§7and jump higher too!");
        return lore;
    }

    @Override
    public Rank minRank() {
        return Rank.PLATINUM;
    }

    @Override
    public Material getDisplayIcon() {
        return Material.DIAMOND_BOOTS;
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
        return toItemStack(Material.DIAMOND_HELMET);
    }

    @Override
    public ItemStack getChestplate() {
        return toItemStack(Material.CHAINMAIL_CHESTPLATE);
    }

    @Override
    public ItemStack getLeggings() {
        return toItemStack(Material.CHAINMAIL_LEGGINGS);
    }

    @Override
    public ItemStack getBoots() {
        return toItemStack(Material.DIAMOND_BOOTS);
    }

    @Override
    public ItemStack getPrimaryWeapon() {
        return toItemStack(Material.IRON_SWORD);
    }

    @Override
    public List<PotionEffect> getKitEffects() {
        List<PotionEffect> effects = new ArrayList<>();
        effects.add(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, 1));
        effects.add(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 1));
        return effects;
    }

    @Override
    public List<ItemStack> getExtraItems() {
        return null;
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
