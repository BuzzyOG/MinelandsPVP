package com.minelands.pvp.Kits.Paid;

import com.minelands.core.MinePlayers.Ranks.Rank;
import com.minelands.core.Util.Items.ItemUtil;
import com.minelands.pvp.Kits.Kit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
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
public class Defender extends Kit {

    @Override
    public boolean isLocked() {
        return false;
    }

    @Override
    public String getName() {
        return "Defender";
    }

    @Override
    public String getPermissionSuffix() {
        return "defender";
    }

    @Override
    public List<String> getDescription() {
        List<String> lore = new ArrayList<>();
        lore.add("§7A kit mostly used for defence and for standing your ground!");
        lore.add("§7Should not be used if you need a lot of agility and mobility");
        return lore;
    }

    @Override
    public Rank minRank() {
        return Rank.ADMIN;
    }

    @Override
    public Material getDisplayIcon() {
        return Material.IRON_CHESTPLATE;
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
        ItemStack stack = new ItemStack(Material.DIAMOND_HELMET);
        return stack;
    }

    @Override
    public ItemStack getChestplate() {
        ItemStack stack = new ItemStack(Material.DIAMOND_CHESTPLATE);
        return stack;
    }

    @Override
    public ItemStack getLeggings() {
        ItemStack stack = new ItemStack(Material.IRON_LEGGINGS);
        return stack;
    }

    @Override
    public ItemStack getBoots() {
        ItemStack stack = new ItemStack(Material.DIAMOND_BOOTS);
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
        effects.add(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 2));
        effects.add(new PotionEffect(PotionEffectType.ABSORPTION, Integer.MAX_VALUE, 2));
        return effects;
    }

    @Override
    public List<ItemStack> getExtraItems() {
        List<ItemStack> items = new ArrayList<>();
        {
            List<String> lore = new ArrayList<>();
            lore.add("§7You found this dropped sword! Yay!");
            lore.add("§7Be careful with it! It's very sharp");
            lore.add("§7but doesn't last very long!");

            ItemStack item = ItemUtil.createItem("§6§lLEGION SWORD", lore, Material.DIAMOND_SWORD, (byte) 0, 1);
            item.setDurability((short) 1530);
            item.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 1);
            items.add(item);
        }
        return items;
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
