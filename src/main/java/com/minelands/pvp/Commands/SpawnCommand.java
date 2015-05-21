package com.minelands.pvp.Commands;

import com.minelands.core.Commands.CommandSource;
import com.minelands.core.Commands.MinelandCommand;
import com.minelands.core.MinePlayers.Ranks.Rank;
import com.minelands.core.Util.LocationUtil;
import com.minelands.core.Util.MessageType;
import com.minelands.pvp.PVPCore;
import org.bukkit.Location;

/**
 * Copyright Joey Gallegos {c} 2014. All Rights Reserved.
 * Any code contained within this document, and any associated APIs with similar branding
 * are the sole property of Joey Gallegos. Distribution, reproduction, taking snippets, or
 * claiming any contents as your own will break the terms of the License, and void any
 * agreements with you, the third party.
 */
public class SpawnCommand extends MinelandCommand {

    public SpawnCommand() {
        super("spawn");
        setName("spawn");
        setDescription("A basic spawn command");
        setMinRank(Rank.MOD);
    }

    @Override
    public void run(CommandSource source, String[] args) {
        if (args.length == 0) {
            source.getPlayer().teleport(PVPCore.getSpawn());
            source.message(MessageType.GREY_ARROW, "§aTaking you to spawn!");
        } else {
            String cmd = args[0];
            if (cmd.equalsIgnoreCase("set")) {
                Location loc = source.getPlayer().getLocation();
                PVPCore.updateSpawn(loc);

                PVPCore.getInstance().getConfig().set("spawn", LocationUtil.fromLocation(loc, false));
                PVPCore.getInstance().saveConfig();
                source.message(MessageType.GREY_ARROW, "§aYou just set spawn to §e" + LocationUtil.fromLocation(loc, false) + "§a!");
            }
        }
    }
}