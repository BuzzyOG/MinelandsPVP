package com.minelands.pvp.Listeners;

import com.minelands.core.Core;
import com.minelands.core.MinePlayers.Connection.Player.PlayerManager;
import com.minelands.core.MinePlayers.MinePlayer;
import com.minelands.core.Statistics.StatManager;
import com.minelands.core.Statistics.StatValue;
import com.minelands.core.Util.Hologram.Hologram;
import com.minelands.core.Util.MessageType;
import com.minelands.pvp.Kits.Kit;
import com.minelands.pvp.Kits.KitManager;
import com.minelands.pvp.Kits.PotionManager;
import com.minelands.pvp.PVPCore;
import com.minelands.pvp.Util.CustomFirework;
import net.minecraft.server.v1_8_R1.EnumClientCommand;
import net.minecraft.server.v1_8_R1.PacketPlayInClientCommand;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.text.DecimalFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Copyright Joey Gallegos {c} 2014. All Rights Reserved.
 * Any code contained within this document, and any associated APIs with similar branding
 * are the sole property of Joey Gallegos. Distribution, reproduction, taking snippets, or
 * claiming any contents as your own will break the terms of the License, and void any
 * agreements with you, the third party.
 */
public class DeathListener implements Listener {

    private static Set<MinePlayer> spectators = new HashSet<>();
    private static HashMap<MinePlayer, Integer> killStreak = new HashMap<>();

    public static Set<MinePlayer> getSpectators() {
        return spectators;
    }

    public static HashMap<MinePlayer, Integer> getKillStreak() {
        return killStreak;
    }

    private static void makeSpectator(final MinePlayer player, MinePlayer killer, final int respawnSeconds) {
        Player pl = player.getBukkitPlayer();
        pl.setGameMode(GameMode.ADVENTURE);
        pl.setMaxHealth(2.0);
        pl.setTotalExperience(0);
        pl.setVelocity(pl.getVelocity().multiply(new Vector(0, 2, 0)));
        pl.setCanPickupItems(false);
        pl.setCompassTarget(killer.getBukkitPlayer().getLocation());
        pl.getInventory().clear();
        pl.getInventory().addItem(new ItemStack(Material.COMPASS));

        // MOVE THEM TO ABOVE
        pl.teleport(killer.getBukkitPlayer().getLocation().add(0, 2, 0));

        pl.setAllowFlight(true);
        pl.setFlying(true);
        player.playSound(Sound.WITHER_DEATH, 1, 3);
        player.setHidden(true);
        getSpectators().add(player);

        Bukkit.getScheduler().runTaskLater(Core.getPlugin(), new BukkitRunnable() {
            @Override
            public void run() {
                respawn(player);
            }
        }, (long) (respawnSeconds * 20));
    }

    private static void respawn(MinePlayer player) {
        player.getBukkitPlayer().setMaxHealth(20.0);
        player.reset();

        player.playSound(Sound.AMBIENCE_THUNDER, 1, 4);
        player.getBukkitPlayer().setGameMode(GameMode.SURVIVAL);
        player.getBukkitPlayer().setAllowFlight(false);
        player.getBukkitPlayer().setFlying(false);
        player.setHidden(false);

        // REMOVE OLD KIT
        KitManager.getUserKits().remove(player);
        player.getBukkitPlayer().getInventory().clear();
        player.getBukkitPlayer().updateInventory();

        player.message("");
        player.message("§2 * §aYou have now been respawned!");
        player.message("§8 - §7All damage and effects have been removed");
        player.message("§8 - §7Please select a §a/kit §7before you start fighting!");
        player.message("");

        PotionManager.canGetPotions(player);

        // REMOVE FROM SPECTATORS
        getSpectators().remove(player);

        // TODO: Take to respawn location defined in the main class
        player.getBukkitPlayer().teleport(PVPCore.getSpawn(), PlayerTeleportEvent.TeleportCause.PLUGIN);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        MinePlayer player = PlayerManager.getPlayer(event.getEntity());
        event.getDrops().clear();
        event.setDroppedExp(0);
        event.setDeathMessage("");
    }

    @EventHandler
    public void onKill(EntityDeathEvent event) {
        if (event.getEntity() instanceof Player && event.getEntity().getKiller() != null) {

            // TODO: Clean some of this up because this long event is causing massive lag on server
            Location deathLocation = event.getEntity().getLocation();

            Hologram hologram = new Hologram(deathLocation.add(0, 1, 0), "§a" + ((Player) event.getEntity()).getName() + " §7has just died", "§a§l+1 KILLS");
            hologram.show(5);

            FireworkEffect effect = FireworkEffect.builder().flicker(false).trail(false).with(FireworkEffect.Type.BALL).withColor(Color.RED).withFade(Color.RED).build();
            CustomFirework.spawn(deathLocation, effect, (Collection<Player>) Bukkit.getOnlinePlayers());

            final MinePlayer player = PlayerManager.getPlayer((Player) event.getEntity());
            final MinePlayer killer = PlayerManager.getPlayer(player.getBukkitPlayer().getKiller());

            double hp = Double.valueOf(new DecimalFormat("##.##").format(((Damageable) killer.getBukkitPlayer()).getHealth()));
            killer.playSound(Sound.AMBIENCE_THUNDER, 1, 4);

            player.message(" ");
            player.message("§4 * §cYou have just died!");
            player.message("§8 - §7You were killed by " + killer.getName() + "§8(§e§l" + killer.getCardinalDirection() + "§r§8)" + "§7!");
            player.message("§8 - §7Your killer has §e§l" + hp + "HP §7left!");
            player.message(" ");

            // TODO: Can we do some of this Async and stop lag just a bit?
            StatValue kills = StatManager.getPlayerStat(killer, StatManager.getStat("kills"));
            kills.addStat(1);
            killer.getBukkitPlayer().addPotionEffect(new PotionEffect(PotionEffectType.HEAL, 3, 2));

            int pow = (int) hp / 2;
            if (pow >= 1) {
                StatValue power = StatManager.getPlayerStat(killer, StatManager.getStat("power"));
                power.addStat(pow);
            }

            StatValue deaths = StatManager.getPlayerStat(player, StatManager.getStat("deaths"));
            deaths.addStat(1);

            Kit kit = KitManager.getUserKits().get(killer);
            if (kit != null) {
                if (kit.getArrowAmount() > 0) {
                    for (int i = 0; i < kit.getArrowAmount(); i++) {
                        killer.getBukkitPlayer().getInventory().addItem(new ItemStack(Material.ARROW));
                    }
                    // UPDATE SO THEY GET THEM
                    killer.updateInventory();
                    killer.message(MessageType.GREY_ARROW, "§cYou have been " + kit.getArrowAmount() + " arrows for your kill!");
                }
            }

            // RESPAWN
            player.getBukkitPlayer().spigot().respawn();
            Bukkit.getScheduler().scheduleSyncDelayedTask(Core.getPlugin(), new Runnable() {
                @Override
                public void run() {
                    PacketPlayInClientCommand packet = new PacketPlayInClientCommand(EnumClientCommand.PERFORM_RESPAWN);
                    ((CraftPlayer) player.getBukkitPlayer()).getHandle().playerConnection.a(packet);
                }
            }, 1L);
            makeSpectator(player, killer, 8);
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            if (event.getEntity() instanceof Player) {
                MinePlayer damager = PlayerManager.getPlayer((Player) event.getDamager());
                MinePlayer entity = PlayerManager.getPlayer((Player) event.getEntity());

                if (getSpectators().contains(entity) || getSpectators().contains(damager)) {
                    event.setCancelled(true);
                }
            }
        }
    }
}
