package com.minelands.pvp;

import com.minelands.core.Commands.CommandManager;
import com.minelands.core.Core;
import com.minelands.core.Events.Events;
import com.minelands.core.Events.Stats.StatsCreatedEvent;
import com.minelands.core.Events.Stats.StatsDownloadedEvent;
import com.minelands.core.Exceptions.MinelandException;
import com.minelands.core.MinePlayers.MinePlayer;
import com.minelands.core.MinelandGame;
import com.minelands.core.MinelandPlugin;
import com.minelands.core.Regions.AdminRegion;
import com.minelands.core.Regions.RegionManager;
import com.minelands.core.Statistics.Stat;
import com.minelands.core.Statistics.StatManager;
import com.minelands.core.Statistics.StatValue;
import com.minelands.core.Util.CompassTool.CompassTool;
import com.minelands.core.Util.ExceptionUtil;
import com.minelands.core.Util.LocationUtil;
import com.minelands.core.Util.MessageType;
import com.minelands.core.Util.NMS.NMSUtils;
import com.minelands.core.Util.Network.DB;
import com.minelands.pvp.Bounty.BountyCommand;
import com.minelands.pvp.Bounty.BountyListener;
import com.minelands.pvp.Commands.ArrowsCommand;
import com.minelands.pvp.Commands.KitCommand;
import com.minelands.pvp.Commands.SpawnCommand;
import com.minelands.pvp.Commands.SpawnPointCommand;
import com.minelands.pvp.GameMode.GameType;
import com.minelands.pvp.KitSwitch.KitSwitchCore;
import com.minelands.pvp.KitSwitch.KitSwitchListener;
import com.minelands.pvp.Kits.Free.Scout;
import com.minelands.pvp.Kits.Free.Slave;
import com.minelands.pvp.Kits.KitInventory;
import com.minelands.pvp.Kits.KitListener;
import com.minelands.pvp.Kits.KitManager;
import com.minelands.pvp.Kits.Paid.Defender;
import com.minelands.pvp.Kits.Paid.Magician;
import com.minelands.pvp.Kits.Paid.Ninja;
import com.minelands.pvp.Kits.Paid.Skyward;
import com.minelands.pvp.Listeners.*;
import com.minelands.pvp.Pets.PetCommand;
import com.minelands.pvp.Pets.PetListener;
import com.minelands.pvp.SheepCanon.SheepCannon;
import com.minelands.pvp.SheepCanon.SheepCannonCommand;
import com.minelands.pvp.Spawn.SpawnListener;
import com.minelands.pvp.SpawnPoints.SpawnException;
import com.minelands.pvp.SpawnPoints.SpawnSystem;
import com.minelands.pvp.Threads.BarUpdater;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Copyright Joey Gallegos {c} 2014. All Rights Reserved.
 * Any code contained within this document, and any associated APIs with similar branding
 * are the sole property of Joey Gallegos. Distribution, reproduction, taking snippets, or
 * claiming any contents as your own will break the terms of the License, and void any
 * agreements with you, the third party.
 */
public class PVPCore extends MinelandPlugin {
    private static PVPCore instance;
    private static Location spawn;

    private MinelandGame game;
    private NMSUtils nmsutils;
    private GameType mode = GameType.NORMAL;

    public static PVPCore getInstance() {
        return instance;
    }

    public static Location getSpawn() {
        return spawn;
    }

    public static void updateSpawn(Location spawn) {
        PVPCore.spawn = spawn;
    }

    private static void createStats(final MinePlayer player) {
        new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    PreparedStatement s = DB.getConnection().prepareStatement("INSERT INTO pvp_stats (id) VALUES (?);");
                    s.setLong(1, player.getId());
                    s.executeUpdate();
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            Events.callEvent(new StatsCreatedEvent(player));
                        }
                    }.runTask(Core.getPlugin());
                    downloadStats(player);
                } catch (SQLException e) {
                    e.printStackTrace();
                    ExceptionUtil.alertStaff(e, ExceptionUtil.handleException(e));
                    player.message(MessageType.ERROR, "§cThere was a problem creating your stats on the server");
                }
            }
        }.runTaskAsynchronously(Core.getPlugin());
    }

    public static void downloadStats(final MinePlayer player) {
        new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    PreparedStatement stmt = DB.getConnection().prepareStatement("SELECT * FROM pvp_stats WHERE id = ?;");
                    stmt.setLong(1, player.getId());

                    ResultSet set = stmt.executeQuery();
                    if (set.next()) {
                        List<StatValue> values = new ArrayList<>();
                        for (Stat stat : StatManager.getStats()) {
                            String s = stat.getName();
                            values.add(new StatValue(player, stat, set.getInt(s)));
                        }
                        StatManager.getCachedStats().put(player, values);
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                Events.callEvent(new StatsDownloadedEvent(player));
                            }
                        }.runTask(Core.getPlugin());
                    } else {
                        createStats(player);
                    }
                    set.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    ExceptionUtil.alertStaff(e, ExceptionUtil.handleException(e));
                    player.message(MessageType.ERROR, "§cThere was a problem downloading your stats from the server");
                }
            }
        }.runTaskAsynchronously(Core.getPlugin());
    }

    public GameType getMode() {
        return mode;
    }

    public void setMode(GameType mode) {
        this.mode = mode;
    }

    public NMSUtils getNmsutils() {
        return nmsutils;
    }

    public MinelandGame getGame() {
        return game;
    }

    @Override
    public MinelandGame setupGame() {
        MinelandGame game = new MinelandGame("Kit PVP", "pvp");
        game.setColor(ChatColor.DARK_GREEN);
        return game;
    }

    @Override
    public void onEnable() {
        instance = this;
        nmsutils = new NMSUtils();
        game = setupGame();

        // REGISTER KITS
        KitManager.getPaidKits().add(new Defender());
        KitManager.getPaidKits().add(new Magician());
        KitManager.getPaidKits().add(new Ninja());
        KitManager.getPaidKits().add(new Skyward());

        KitManager.getFreeKits().add(new Slave());
        KitManager.getFreeKits().add(new Scout());

        // KIT SWITCH
        KitSwitchCore.instantiate(new Location(Bukkit.getWorld("world"), -34, 65, 50));

        // KIT INVENTORY
        KitInventory.setupKitInventory();

        CompassTool.setCanUpdate(true);
        CompassTool.setShowDistance(true);
        CompassTool.setShowDistance(true);

        // LISTENERS
        registerEvents(this, new KitListener(), new KitInventory(), new KitSwitchListener());
        registerEvents(this, new DeathListener(), new ArrowListener(), new GameListeners(), new JoinListener(), new PingListener());
        registerEvents(this, new PetListener(), new SheepCannon());
        registerEvents(this, new SpawnListener());
        registerEvents(this, new BountyListener());

        // OTHERS
        registerCommands();
        registerStats();

        try {
            downloadSpawn();
        } catch (SQLException | MinelandException e) {
            e.printStackTrace();
        }

        try {
            SpawnSystem.downloadSpawns();
        } catch (SQLException | SpawnException e) {
            e.printStackTrace();
            ExceptionUtil.handleException(e);
        }

        if (!new File(getDataFolder(), "config.yml").exists()) {
            saveDefaultConfig();
        }

        spawn = new Location(Bukkit.getWorld("world"), -132.586, 72.0, 132.552, -135.3F, -2.7F);
        if (getConfig().getString("spawn") == null) {
            getConfig().set("spawn", LocationUtil.fromLocation(spawn, false));
        }

        spawn = LocationUtil.fromString(getConfig().getString("spawn"));

        BarUpdater.getNormal().add("§c§lNEW: §fFollow us on our Twitter! §b§l@MinelandsMC");
        BarUpdater.getNormal().add("§c§lNEW: §fView your latest stats! §b/stats");
        BarUpdater.getNormal().add("§c§lNEW: §fNew and improved Rank system!");
        BarUpdater.getNormal().add("§c§lNEW: §fLive updating Stats and Ranks!");
        BarUpdater.getNormal().add("§c§lNEW: §fNew Kits and Kit Perks!");
        BarUpdater.getNormal().add("§c§lNEW: §fKilling somebody earns you more arrows.");
        BarUpdater.getNormal().add("§c§lNEW: §fUse §b/msg §fto private message players.");
        BarUpdater.getNormal().add("§d§lCOMING SOON: §fCustom combat log system.");
        BarUpdater.getNormal().add("§d§lCOMING SOON: §fDonator rank benefits!");
        BarUpdater.getNormal().add("§d§lCOMING SOON: §fNew Game Mode: §e§lHide and Seek §fcoming soon!");
        BarUpdater.getNormal().add("§d§lCOMING SOON: §fPVP Achievements!");
        BarUpdater.getNormal().add("§d§lCOMING SOON: §fUnlock extra kit items for your fight!");
        BarUpdater.getNormal().add("§d§lCOMING SOON: §fPower, a new advantage to the fight!");
        BarUpdater.getNormal().add("§d§lCOMING SOON: §fKit switching kiosks in map!");
        Collections.shuffle(BarUpdater.getNormal());

        Bukkit.getServer().getScheduler().runTaskTimer(this, new BarUpdater(), 40L, (long) 20 * 10);
    }

    @Override
    public void onDisable() {
        instance = null;
        KitInventory.getInventory().setItems(null);
        KitInventory.setInventory(null);
    }

    private void registerCommands() {
        CommandManager.registerCommand(new ArrowsCommand());
        CommandManager.registerCommand(new KitCommand());
        CommandManager.registerCommand(new SpawnCommand());
        CommandManager.registerCommand(new SpawnPointCommand());
        CommandManager.registerCommand(new PetCommand());
        CommandManager.registerCommand(new SheepCannonCommand());
        CommandManager.registerCommand(new BountyCommand());
    }

    private void registerStats() {
        StatManager.setStatsEnabled(true);
        List<Stat> stats = new ArrayList<>();
        {
            Stat stat = new Stat("Kills", "kills");
            stat.setDescription("How many kills you have accumulated");
            stat.setViewable(true);
            stat.setViewableInMenu(true);
            stats.add(stat);
        }
        {
            Stat stat = new Stat("Deaths", "deaths");
            stat.setDescription("How many deaths you have accumulated");
            stat.setViewable(true);
            stat.setViewableInMenu(true);
            stats.add(stat);
        }
        {
            Stat stat = new Stat("Power", "power");
            stat.setDescription("How much power you have from kills");
            stat.setViewable(true);
            stat.setViewableInMenu(true);
            stats.add(stat);
        }
        {
            Stat stat = new Stat("Arrows Fired", "arrows_fired");
            stat.setDescription("How many arrows you've fired from your bow");
            stat.setViewable(true);
            stat.setViewableInMenu(true);
            stats.add(stat);
        }
        {
            Stat stat = new Stat("Bow Kills", "bow_kills");
            stat.setDescription("How many players you've killed with your bow");
            stat.setViewable(true);
            stat.setViewableInMenu(true);
            stats.add(stat);
        }
        {
            Stat stat = new Stat("Kill Streak", "killstreak");
            stat.setDescription("The highest killstreak you have ever gotten");
            stat.setViewable(true);
            stat.setViewableInMenu(true);
            stats.add(stat);
        }
        {
            Stat stat = new Stat("Staff Kills", "staff_kills");
            stat.setDescription("How many staff you have killed");
            stat.setViewable(false);
            stat.setViewableInMenu(false);
            stats.add(stat);
        }
        StatManager.setStats(stats);
    }

    private void downloadSpawn() throws SQLException, MinelandException {
        PreparedStatement stmt = DB.getConnection().prepareStatement("SELECT * FROM pvp_spawn;");
        ResultSet set = stmt.executeQuery();

        while (set.next()) {
            String name = set.getString("name");
            String owner = set.getString("owner");

            String entry = set.getString("entry");
            String leave = set.getString("leave");


            World world = Bukkit.getWorld(set.getString("world"));

            if (world == null) throw new MinelandException("World can't be null!");

            Location max = LocationUtil.fromString(set.getString("max"));
            Location min = LocationUtil.fromString(set.getString("min"));

            if (!max.getWorld().getName().equals(min.getWorld().getName())) {
                throw new MinelandException("Points aren't in the same world!");
            }

            AdminRegion region = new AdminRegion(name, owner, max, min, entry, leave);
            region.setBreakAllowed(false);
            region.setPunchingAllowed(false);
            region.setInteractAllowed(false);
        }
        set.close();
        Core.getPlugin().getLogger().info("There are currently " + RegionManager.getAdminRegions().size() + " regions!");
    }
}
