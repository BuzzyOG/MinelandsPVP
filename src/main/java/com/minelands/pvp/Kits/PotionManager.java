package com.minelands.pvp.Kits;

import com.minelands.core.MinePlayers.MinePlayer;
import com.minelands.core.Util.MessageType;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

import java.util.Random;

/**
 * Copyright Joey Gallegos {c} 2014. All Rights Reserved.
 * Any code contained within this document, and any associated APIs with similar branding
 * are the sole property of Joey Gallegos. Distribution, reproduction, taking snippets, or
 * claiming any contents as your own will break the terms of the License, and void any
 * agreements with you, the third party.
 */
public class PotionManager {

    private static boolean hasChance(float percent) {
        return new Random().nextFloat() <= percent;
    }

    public static void canGetPotions(MinePlayer player) {
        if (hasChance(0.2F)) {
            Potion potion = new Potion(PotionType.INSTANT_HEAL, 2);
            potion.setSplash(true);
            player.getBukkitPlayer().getInventory().addItem(potion.toItemStack(1));
            player.message(MessageType.GREY_ARROW, "§dYou found a healing potion!");
        }
    }
}
