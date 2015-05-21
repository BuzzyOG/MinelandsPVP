package com.minelands.pvp.SpawnPoints;

import com.minelands.core.MinePlayers.MinePlayer;
import com.minelands.core.Util.LocationUtil;
import com.minelands.core.Util.Network.DB;
import com.minelands.pvp.PVPCore;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Copyright Joey Gallegos {c} 2014. All Rights Reserved.
 * Any code contained within this document, and any associated APIs with similar branding
 * are the sole property of Joey Gallegos. Distribution, reproduction, taking snippets, or
 * claiming any contents as your own will break the terms of the License, and void any
 * agreements with you, the third party.
 */
public class SpawnSystem {

    private static List<SpawnPoint> spawns = new ArrayList<>();

    public static List<SpawnPoint> getSpawns() {
        return spawns;
    }


    public static void downloadSpawns() throws SQLException, SpawnException {
        PreparedStatement stmt = DB.getConnection().prepareStatement("SELECT * FROM pvp_spawns;");
        ResultSet set = stmt.executeQuery();

        while (set.next()) {
            if (set.getInt("id") <= 0) throw new SpawnException("ID can't be less then 1");
            if (set.getString("name").length() <= 3) throw new SpawnException("The name should be longer then 3 chars");

            SpawnPoint spawn = new SpawnPoint(set.getInt("id"), LocationUtil.fromString(set.getString("location")), set.getString("name"));
            SpawnSystem.getSpawns().add(spawn);
        }
        set.close();

        int size = SpawnSystem.getSpawns().size();
        PVPCore.getInstance().getLogger().info("There are currently " + size + " spawns active");
    }

    public static SpawnPoint getRandomSpawn() {
        Random random = new Random();
        return getSpawns().get(random.nextInt(getSpawns().size()));
    }
}
