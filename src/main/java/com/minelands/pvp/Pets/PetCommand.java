package com.minelands.pvp.Pets;

import com.minelands.core.Commands.CommandSource;
import com.minelands.core.Commands.MinelandCommand;
import com.minelands.core.MinePlayers.MinePlayer;
import com.minelands.core.MinePlayers.Ranks.Rank;
import com.minelands.core.Util.MessageType;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.entity.Sheep;

import java.util.Arrays;

/**
 * Copyright Joey Gallegos {c} 2014. All Rights Reserved.
 * Any code contained within this document, and any associated APIs with similar branding
 * are the sole property of Joey Gallegos. Distribution, reproduction, taking snippets, or
 * claiming any contents as your own will break the terms of the License, and void any
 * agreements with you, the third party.
 */
public class PetCommand extends MinelandCommand {

    public PetCommand() {
        super("pet");
        setName("pet");
        setAliases(Arrays.asList("pets"));
        setDescription("Use your pet before a fight");
        setMinRank(Rank.PLATINUM);
    }

    @Override
    public void run(CommandSource source, String[] args) {
        if (args.length == 0) {
            source.message(MessageType.GREY_ARROW, "§3/pet §clist");
            source.message(MessageType.GREY_ARROW, "§3/pet §cspawn §7sheep");
        } else {
            String cmd = args[0];
            if (cmd.equalsIgnoreCase("spawn")) {
                if (args.length == 2) {
                    MinePlayer player = source.getMinelandPlayer();
                    Location location = player.getBukkitPlayer().getLocation();

                    String pet = args[1];
                    if (pet.equalsIgnoreCase("sheep")) {
                        Sheep sheep = location.getWorld().spawn(location, Sheep.class);
                        sheep.setSheared(false);
                        sheep.setColor(DyeColor.PURPLE);
                        sheep.setRemoveWhenFarAway(true);
                        sheep.setPassenger(player.getBukkitPlayer());
                    }
                } else {
                    source.message(MessageType.GREY_ARROW, "§3/pet §cspawn §7sheep");
                }
            }
            else if (cmd.equalsIgnoreCase("list")){
                source.message(MessageType.GREY_ARROW, "§eSheep, Rabbit");
            }
        }
    }
}
