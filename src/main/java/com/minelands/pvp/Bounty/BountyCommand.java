package com.minelands.pvp.Bounty;

import com.minelands.core.Commands.CommandSource;
import com.minelands.core.Commands.MinelandCommand;
import com.minelands.core.Core;
import com.minelands.core.MinePlayers.Connection.Player.PlayerManager;
import com.minelands.core.MinePlayers.MinePlayer;
import com.minelands.core.MinePlayers.Ranks.Rank;
import com.minelands.core.Util.MessageType;
import com.minelands.core.Util.MessageUtil;
import org.bukkit.Bukkit;

/**
 * Copyright Joey Gallegos {c} 2014. All Rights Reserved.
 * Any code contained within this document, and any associated APIs with similar branding
 * are the sole property of Joey Gallegos. Distribution, reproduction, taking snippets, or
 * claiming any contents as your own will break the terms of the License, and void any
 * agreements with you, the third party.
 */
public class BountyCommand extends MinelandCommand {

    public BountyCommand() {
        super("bounty");
        setName("bounty");
        setDescription("Place or vote for a bounty on a player");
        setMinRank(Rank.DEFAULT);
    }

    @Override
    public void run(CommandSource source, String[] args) {
        if (!source.isPlayer()) return;
        if (args.length == 0) {
            source.message(MessageType.GREY_ARROW, "§3/bounty §cplace §7<player>");
            source.message(MessageType.GREY_ARROW, "§3/bounty §cvote");
        } else {
            String cmd = args[0];
            if (cmd.equalsIgnoreCase("place")) {
                if (args.length != 2) {
                    source.message(MessageType.GREY_ARROW, "§cYou didn't supply the right arguments for this command");
                } else {
                    long time = (1000 * 60) * 5; // This is a time constant: five minutes
                    long timeAgo = System.currentTimeMillis() - BountySystem.getLastBounty();
                    if (timeAgo >= time) {
                        MinePlayer hunter = source.getMinelandPlayer();
                        MinePlayer target = PlayerManager.getPlayer(args[1]);
                        Bounty bounty = new Bounty(target, hunter);

                        BountySystem.setCurrentBounty(bounty);
                        BountySystem.setReady(false);
                        BountySystem.setTicks(60);

                        Core.getPlugin().getServer().getScheduler().runTaskTimer(Core.getPlugin(), new BountyTick(), 0L, 20L);
                    } else {
                        int t = MessageUtil.timeAgo(System.currentTimeMillis(), BountySystem.getLastBounty());
                        source.message(MessageType.GREY_ARROW, "§3A new bounty can't be placed for another " + MessageUtil.calculateTime(t, false) + " seconds!");
                    }
                }
            } else if (cmd.equalsIgnoreCase("vote")) {
                if (BountySystem.getCurrentBounty() != null) {
                    if (!BountySystem.isReady()) {
                        if (!BountySystem.getVoted().contains(source.getMinelandPlayer())) {
                            source.message(MessageType.GREY_ARROW, "§aThank you for placing your vote!");

                            BountySystem.getCurrentBounty().setReward(BountySystem.getCurrentBounty().getReward() + BountySystem.INCREMENT_PRICE);
                            BountySystem.getVoted().add(source.getMinelandPlayer());
                            int size = BountySystem.getVoted().size();
                            if (size < BountySystem.MIN_VOTES) {
                                source.message(MessageType.GREY_ARROW, "§eWe need at least " + BountySystem.MIN_VOTES + " §eto start the bounty! §7(§c" + size + "§7/§c" + BountySystem.MIN_VOTES + "§7)");
                            }
                            source.message(MessageType.GREY_ARROW, "§aYour vote has increased the bounty reward by " + BountySystem.INCREMENT_PRICE + "§a!");
                            Bukkit.broadcast(MessageType.GREY_ARROW.getText() + "The bounty on " + BountySystem.getCurrentBounty().getTarget().getName() + " is now worth " + BountySystem.getCurrentBounty().getReward() + "!", "*");
                        } else {
                            source.message(MessageType.GREY_ARROW, "§cYou can't place another vote on this bounty!");
                        }
                    } else {
                        source.message(MessageType.GREY_ARROW, "§eSorry, no more votes can be accepted! Bounty has already started!");
                    }
                } else {
                    source.message(MessageType.GREY_ARROW, "§cThere is no current bounty set!");
                }

            }
        }
    }
}
