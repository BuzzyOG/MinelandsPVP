package com.minelands.pvp.Threads;

import com.minelands.core.MinePlayers.Connection.Player.PlayerManager;
import com.minelands.core.MinePlayers.MinePlayer;
import com.minelands.pvp.GameMode.GameType;
import com.minelands.pvp.PVPCore;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright Joey Gallegos {c} 2014. All Rights Reserved.
 * Any code contained within this document, and any associated APIs with similar branding
 * are the sole property of Joey Gallegos. Distribution, reproduction, taking snippets, or
 * claiming any contents as your own will break the terms of the License, and void any
 * agreements with you, the third party.
 */
public class BarUpdater extends BukkitRunnable {
    public static int TIME = 10;

    private static int count = 0;
    private static List<String> normal = new ArrayList<>();

    public static List<String> getNormal() {
        return normal;
    }

    private static void updateBars(MinePlayer player) {
        GameType type = PVPCore.getInstance().getMode();
        if (type == GameType.NORMAL) {
            String a = normal.get(count);
            player.sendActionBar(a, TIME);
        }
        else if (type == GameType.RANDOM_RESPAWN) {
            // TODO: Different messages
        }
        // TODO: Show what kit we're currently using in the TopBar
    }

    @Override
    public void run() {
        for (MinePlayer player : PlayerManager.getOnlinePlayers()) {
            updateBars(player);
        }

        if (count < (normal.size() - 1)) {
            count++;
        } else {
            count = 0;
        }
    }

}
