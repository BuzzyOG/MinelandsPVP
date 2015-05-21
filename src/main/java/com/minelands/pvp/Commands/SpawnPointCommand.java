package com.minelands.pvp.Commands;

import com.minelands.core.Commands.CommandSource;
import com.minelands.core.Commands.MinelandCommand;
import com.minelands.core.Core;
import com.minelands.core.MinePlayers.MinePlayer;
import com.minelands.core.MinePlayers.Ranks.Rank;
import com.minelands.core.Util.LocationUtil;
import com.minelands.core.Util.MessageType;
import com.minelands.core.Util.Network.DB;
import com.minelands.pvp.SpawnPoints.SpawnPoint;
import com.minelands.pvp.SpawnPoints.SpawnSystem;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;

/**
 * Copyright Joey Gallegos {c} 2014. All Rights Reserved.
 * Any code contained within this document, and any associated APIs with similar branding
 * are the sole property of Joey Gallegos. Distribution, reproduction, taking snippets, or
 * claiming any contents as your own will break the terms of the License, and void any
 * agreements with you, the third party.
 */
public class SpawnPointCommand extends MinelandCommand {

    public SpawnPointCommand() {
        super("spawnpoint");
        setName("spawnpoint");
        setAliases(Arrays.asList("sp"));
        setDescription("Spawn point command");
        setMinRank(Rank.ADMIN);
    }

    @Override
    public void run(CommandSource source, String[] args) {
        if (args.length == 0) {
            SpawnPoint spawn = SpawnSystem.getRandomSpawn();
            source.getPlayer().teleport(spawn.getLocation());
        } else {
            String cmd = args[0];
            if (cmd.equalsIgnoreCase("add")) {
                addLocation(source.getPlayer().getLocation(), source.getMinelandPlayer());
            }
            else if (cmd.equalsIgnoreCase("reload")){

            }
        }
        // TODO: Re-download spawn points with a command
    }

    private void doIt(Location l1, Location l2) throws SQLException {
        PreparedStatement stmt = DB.getConnection().prepareStatement("INSERT INTO arenas (name, spawn1, spawn2) VALUES (?, ?, ?);");
        stmt.setString(1, "Exodus");
        stmt.setString(2, LocationUtil.fromLocation(l1, false));
        stmt.setString(3, LocationUtil.fromLocation(l2, false));
        stmt.executeUpdate();
    }

    private void addLocation(final Location location, final MinePlayer player) {
        new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    PreparedStatement stmt = DB.getConnection().prepareStatement("INSERT INTO pvp_spawns (name, location) VALUES ('blank', ?);");
                    stmt.setString(1, LocationUtil.fromLocation(location, false));
                    stmt.executeUpdate();
                    player.message(MessageType.GREY_ARROW, "§aInserted location to spawn system");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }.runTaskAsynchronously(Core.getPlugin());
    }

}
