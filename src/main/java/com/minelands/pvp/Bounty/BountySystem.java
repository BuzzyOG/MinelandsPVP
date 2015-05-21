package com.minelands.pvp.Bounty;

import com.minelands.core.MinePlayers.MinePlayer;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright Joey Gallegos {c} 2014. All Rights Reserved.
 * Any code contained within this document, and any associated APIs with similar branding
 * are the sole property of Joey Gallegos. Distribution, reproduction, taking snippets, or
 * claiming any contents as your own will break the terms of the License, and void any
 * agreements with you, the third party.
 */
public class BountySystem {

    public static final int MIN_VOTES = 2;
    public static final int INCREMENT_PRICE = 6;

    private static int ticks = 0;
    private static long lastBounty = 0; // When a bounty player has been killed
    private static boolean ready = false;
    private static Bounty currentBounty = null;
    private static List<MinePlayer> voted = new ArrayList<>();

    public static int getTicks() {
        return ticks;
    }

    public static void setTicks(int ticks) {
        BountySystem.ticks = ticks;
    }

    public static long getLastBounty() {
        return lastBounty;
    }

    public static void setLastBounty(long lastBounty) {
        BountySystem.lastBounty = lastBounty;
    }

    public static boolean isReady() {
        return ready;
    }

    public static void setReady(boolean ready) {
        BountySystem.ready = ready;
    }

    public static Bounty getCurrentBounty() {
        return currentBounty;
    }

    public static void setCurrentBounty(Bounty currentBounty) {
        BountySystem.currentBounty = currentBounty;
    }

    public static List<MinePlayer> getVoted() {
        return voted;
    }
}
