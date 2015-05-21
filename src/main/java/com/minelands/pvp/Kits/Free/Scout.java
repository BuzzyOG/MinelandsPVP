package com.minelands.pvp.Kits.Free;

import com.minelands.core.MinePlayers.MinePlayer;
import com.minelands.core.MinePlayers.Ranks.Rank;
import com.minelands.core.Util.Items.ItemUtil;
import com.minelands.pvp.Kits.Kit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.List;

/**
 * Copyright Joey Gallegos {c} 2014. All Rights Reserved.
 * Any code contained within this document, and any associated APIs with similar branding
 * are the sole property of Joey Gallegos. Distribution, reproduction, taking snippets, or
 * claiming any contents as your own will break the terms of the License, and void any
 * agreements with you, the third party.
 */
public class Scout extends Kit {

    @Override
    public boolean isLocked() {
        return false;
    }

    @Override
    public String getName() {
        return "Scout";
    }

    @Override
    public String getPermissionSuffix() {
        return null;
    }

    @Override
    public List<String> getDescription() {
        return Arrays.asList("A scout has the ability to hunt",
                "players from afar and attack",
                "with fire like no other can",
                "and can start fire anywhere");
    }

    @Override
    public Rank minRank() {
        return Rank.ELITE;
    }

    @Override
    public Material getDisplayIcon() {
        return Material.CAULDRON;
    }

    @Override
    public int getArrowAmount() {
        return 14;
    }

    @Override
    public ItemStack getBow() {
        ItemStack bow = ItemUtil.createItem("§eBow", Arrays.asList(""), Material.BOW, (byte)0, 1);
        bow.addEnchantment(Enchantment.ARROW_INFINITE, 1);
        bow.addEnchantment(Enchantment.ARROW_FIRE, 1);
        return bow;
    }

    @Override
    public ItemStack getHelmet() {
        return toItemStack(Material.IRON_HELMET);
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
        return toItemStack(Material.IRON_BOOTS);
    }

    @Override
    public ItemStack getPrimaryWeapon() {
        ItemStack item = toItemStack(Material.STONE_SWORD);
        item.addEnchantment(Enchantment.DAMAGE_ARTHROPODS, 2);
        item.addEnchantment(Enchantment.FIRE_ASPECT, 1);
        return item;
    }

    @Override
    public List<PotionEffect> getKitEffects() {
        return Arrays.asList(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, Integer.MAX_VALUE, 1), new PotionEffect(PotionEffectType.HEALTH_BOOST, Integer.MAX_VALUE, 1));
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
