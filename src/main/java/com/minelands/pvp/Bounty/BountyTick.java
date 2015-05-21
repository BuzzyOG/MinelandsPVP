package com.minelands.pvp.Bounty;

import com.minelands.core.Util.MessageType;
import com.minelands.core.Util.MessageUtil;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Copyright Joey Gallegos {c} 2014. All Rights Reserved.
 * Any code contained within this document, and any associated APIs with similar branding
 * are the sole property of Joey Gallegos. Distribution, reproduction, taking snippets, or
 * claiming any contents as your own will break the terms of the License, and void any
 * agreements with you, the third party.
 */
public class BountyTick extends BukkitRunnable {

    private int retryAttempts = 0;

    @Override
    public void run() {
        if (BountySystem.getCurrentBounty() != null) {
            int ticks = BountySystem.getTicks();

            if (ticks > 0) {
                // TODO: Announce the bounty start
                if (!BountySystem.isReady()) {
                    // NEED MORE VOTES
                    if (ticks % 20 == 0 && ticks > 0) {
                        int needed = BountySystem.MIN_VOTES - BountySystem.getVoted().size();
                        MessageUtil.messageAll(MessageType.GREY_ARROW, "§eThe current bounty needs " + needed + " more votes to start!");
                        MessageUtil.messageAll(MessageType.GREY_ARROW, "§eEach vote adds to the bounty reward!");
                    }
                }
            } else {
                if (BountySystem.isReady()) {

                } else {
                    if (this.retryAttempts <= 0) {
                        // RETRY COUNT
                    } else {
                        this.retryAttempts++;
                    }
                }
            }

            // Keep updating
            BountySystem.setTicks(BountySystem.getTicks() - 1);
        }
    }
}
