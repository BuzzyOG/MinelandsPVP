package com.minelands.pvp.SheepCanon;

import com.minelands.core.Commands.CommandSource;
import com.minelands.core.Commands.MinelandCommand;
import com.minelands.core.MinePlayers.MinePlayer;
import com.minelands.core.MinePlayers.Ranks.Rank;

import java.util.Arrays;

/**
 * Copyright Joey Gallegos {c} 2014. All Rights Reserved.
 * Any code contained within this document, and any associated APIs with similar branding
 * are the sole property of Joey Gallegos. Distribution, reproduction, taking snippets, or
 * claiming any contents as your own will break the terms of the License, and void any
 * agreements with you, the third party.
 */
public class SheepCannonCommand extends MinelandCommand {

    public SheepCannonCommand() {
        super("sheepCannon");
        setName("sheepCannon");
        setAliases(Arrays.asList("sc"));
        setDescription("Gives you a sheep cannon to use in spawn");
        setMinRank(Rank.ELITE);
    }

    @Override
    public void run(CommandSource source, String[] args) {
        if (args.length == 0) {
            MinePlayer player = source.getMinelandPlayer();

            if (!player.getBukkitPlayer().getInventory().contains(SheepCannon.getLauncher())) {
                player.getBukkitPlayer().getInventory().addItem(SheepCannon.getLauncher());
            }
        }
    }
}
