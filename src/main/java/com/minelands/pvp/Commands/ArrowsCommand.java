package com.minelands.pvp.Commands;

import com.minelands.core.Commands.CommandSource;
import com.minelands.core.Commands.MinelandCommand;
import com.minelands.core.MinePlayers.Ranks.Rank;
import com.minelands.core.Util.MessageType;
import com.minelands.pvp.Kits.Kit;
import com.minelands.pvp.Kits.KitManager;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * Copyright Joey Gallegos {c} 2014. All Rights Reserved.
 * Any code contained within this document, and any associated APIs with similar branding
 * are the sole property of Joey Gallegos. Distribution, reproduction, taking snippets, or
 * claiming any contents as your own will break the terms of the License, and void any
 * agreements with you, the third party.
 */
public class ArrowsCommand extends MinelandCommand {

    public ArrowsCommand() {
        super("arrows");
        setName("arrows");
        setMinRank(Rank.MOD); // Should be a VIP rank if we had one
        setDescription("Gives you half arrows that come from your kit");
    }

    @Override
    public void run(CommandSource source, String[] args) {
        if (source.isPlayer()) {
            if (args.length == 0) {
                Kit kit = KitManager.getUserKits().get(source.getMinelandPlayer());
                if (kit != null) {
                    if (kit.getArrowAmount() > 2) {
                        source.getPlayer().getInventory().addItem(new ItemStack(Material.ARROW, (kit.getArrowAmount() / 2)));
                        source.message(MessageType.GREY_ARROW, "§aYou were given §2" + (kit.getArrowAmount() / 2) + " §aarrows!");
                    }
                } else {
                    source.message(MessageType.ERROR, "§cYou don't have an active kit!");
                }
            }
        }
    }
}
