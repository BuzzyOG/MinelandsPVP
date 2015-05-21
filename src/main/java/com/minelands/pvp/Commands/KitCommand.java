package com.minelands.pvp.Commands;

import com.minelands.core.Commands.CommandSource;
import com.minelands.core.Commands.MinelandCommand;
import com.minelands.core.MinePlayers.Ranks.Rank;
import com.minelands.core.Regions.RegionManager;
import com.minelands.core.Util.MessageType;
import com.minelands.pvp.Kits.Kit;
import com.minelands.pvp.Kits.KitInventory;
import com.minelands.pvp.Kits.KitManager;

/**
 * Copyright Joey Gallegos {c} 2014. All Rights Reserved.
 * Any code contained within this document, and any associated APIs with similar branding
 * are the sole property of Joey Gallegos. Distribution, reproduction, taking snippets, or
 * claiming any contents as your own will break the terms of the License, and void any
 * agreements with you, the third party.
 */
public class KitCommand extends MinelandCommand {

    public KitCommand() {
        super("kit");
        setName("kit");
        setDescription("Change or view kits");
        setMinRank(Rank.DEFAULT);
    }

    @Override
    public void run(CommandSource source, String[] args) {
        if (args.length == 0) {
            if (RegionManager.getRegionAt(source.getPlayer().getLocation()) != null) {
                KitInventory.getInventory().showTo(source.getMinelandPlayer());
            } else {
                source.message(MessageType.ERROR, "§cYou can only open this in spawn!");
            }
        } else {
            String cmd = args[0];
            if (cmd.equalsIgnoreCase("info")) {
                if (args.length == 2) {
                    Kit kit = KitManager.getKit(args[1]);
                    if (kit != null) {
                        // TODO: Display kit info and permission
                    }
                    else {
                        source.message(MessageType.GREY_ARROW, "§eSorry, that doesn't seem to be a valid kit!");
                        source.message(MessageType.GREY_ARROW, "§eMaybe try §3/kit list§e?");
                    }
                }
                else {
                    source.message(MessageType.GREY_ARROW, "§cYou did that wrong! Use §3/kit info §e<kit>");
                }
            }
            else if (cmd.equalsIgnoreCase("list")){
                for (Kit k : KitManager.getPaidKits()) {
                    source.message(MessageType.GREY_ARROW, "§c" + k.getName() + " §7- §3 " + k.getPermissionSuffix());
                }
                for (Kit k : KitManager.getFreeKits()) {
                    source.message(MessageType.GREY_ARROW, "§c" + k.getName());
                }
            }
        }
    }
}
