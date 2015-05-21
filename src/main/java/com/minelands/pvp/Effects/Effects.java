package com.minelands.pvp.Effects;

import com.minelands.core.Core;
import com.minelands.core.Util.Particles.ParticleEffect;
import com.minelands.pvp.Util.CustomFirework;
import org.bukkit.*;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Copyright Joey Gallegos {c} 2014. All Rights Reserved.
 * Any code contained within this document, and any associated APIs with similar branding
 * are the sole property of Joey Gallegos. Distribution, reproduction, taking snippets, or
 * claiming any contents as your own will break the terms of the License, and void any
 * agreements with you, the third party.
 */
public class Effects {

    private static Map<Location, Integer> locations = new ConcurrentHashMap<>();
    private static List<UUID> entities = new ArrayList<>();

    public static void effect(final Location location, final ParticleEffect effect) {
        if (!locations.containsKey(location)) {
            int i = Bukkit.getServer().getScheduler().runTaskTimer(Core.getPlugin(), new Runnable() {
                float i = 0.0F;

                @Override
                public void run() {
                    for (int k = 0; k < 1; k++) {
                        double x = Math.sin(this.i * 3.7F);
                        double y = Math.cos(this.i * 3.7F);
                        double z = this.i * 0.4F;
                        Vector v = new Vector(x, z, y);
                        location.add(v);

                        effect.display(0.0F, 0.0F, 0.0F, 1.0F, 3, location, 15D);
                    }
                    this.i += 0.1F;
                    if (this.i > 5.0F) {
                        this.i = 0.0F;
                    }
                }
            }, 1L, 1L).getTaskId();
            locations.put(location, i);
        } else {
            stopRotation(location);
        }
    }

    private static void stopRotation(Location location) {
        if (locations.containsKey(location)) {
            Bukkit.getServer().getScheduler().cancelTask(locations.get(location));
            locations.remove(location);
        }
    }

    public static void explosion(final Location location) {
        new BukkitRunnable() {
            int times = 0;
            boolean running = true;
            boolean oposite = false;
            ItemStack item = new ItemStack(Material.IRON_INGOT, 1);

            @Override
            public void run() {
                if (this.running) {
                    if (this.times < 10) {
                        location.getWorld().playSound(location, Sound.FIZZ, 1.0F, 1.7F);
                        this.times += 1;
                    } else if (this.times >= 10) {
                        if (!this.oposite) {
                            this.oposite = true;

                            Item emy1 = location.getWorld().dropItem(location.add(3.0D, 0.0D, 0.0D), item);
                            Item emy2 = location.getWorld().dropItem(location.add(-3.0D, 0.0D, 0.0D), item);
                            Item emy3 = location.getWorld().dropItem(location.add(0.0D, 0.0D, 3.0D), item);
                            Item emy4 = location.getWorld().dropItem(location.add(0.0D, 0.0D, -3.0D), item);

                            emy1.setVelocity(new Vector(0.4D, 1.0D, 0.0D));
                            emy2.setVelocity(new Vector(-0.4D, 1.0D, 0.0D));
                            emy3.setVelocity(new Vector(0.0D, 1.0D, 0.4D));
                            emy4.setVelocity(new Vector(0.0D, 1.0D, -0.4D));

                            entities.add(emy1.getUniqueId());
                            entities.add(emy2.getUniqueId());
                            entities.add(emy3.getUniqueId());
                            entities.add(emy4.getUniqueId());

                            FireworkEffect effect = FireworkEffect.builder().trail(true).flicker(false).withColor(Color.PURPLE, Color.GRAY).with(FireworkEffect.Type.BURST).build();
                            CustomFirework.spawn(location.add(0.0D, 1.0D, 0.0D), effect, (Collection<Player>) Bukkit.getOnlinePlayers());

                            this.times += 1;
                        } else {
                            this.oposite = false;

                            Item emy1 = location.getWorld().dropItem(location.add(2.0D, 0.0D, 2.0D), item);
                            Item emy2 = location.getWorld().dropItem(location.add(-2.0D, 0.0D, -2.0D), item);
                            Item emy3 = location.getWorld().dropItem(location.add(-2.0D, 0.0D, 2.0D), item);
                            Item emy4 = location.getWorld().dropItem(location.add(2.0D, 0.0D, -2.0D), item);

                            emy1.setVelocity(new Vector(0.4D, 1.0D, 0.4D));
                            emy2.setVelocity(new Vector(-0.4D, 1.0D, -0.4D));
                            emy3.setVelocity(new Vector(-0.4D, 1.0D, 0.4D));
                            emy4.setVelocity(new Vector(0.4D, 1.0D, -0.4D));

                            entities.add(emy1.getUniqueId());
                            entities.add(emy2.getUniqueId());
                            entities.add(emy3.getUniqueId());
                            entities.add(emy4.getUniqueId());

                            FireworkEffect effect = FireworkEffect.builder().trail(true).flicker(false).withColor(Color.PURPLE, Color.GRAY).with(FireworkEffect.Type.BURST).build();
                            Location locplus = location.add(0.0D, 1.0D, 0.0D);
                            CustomFirework.spawn(locplus, effect, (Collection<Player>) Bukkit.getOnlinePlayers());

                            this.times += 1;
                        }
                    }
                }
            }
        }.runTaskTimer(Core.getPlugin(), 0, 20L).getTaskId();
    }

    public static void runHelix(Location loc, ParticleEffect effect, float speed) {

        double radius = 5;

        for (double y = 5; y >= 0; y -= 0.008) {
            radius = y / 3;
            double x = (radius * Math.cos(1.6 * y));
            double z = (radius * Math.sin(1.6 * y));

            double y2 = 5 - y;

            Location loc2 = new Location(loc.getWorld(), loc.getX() + x, loc.getY() + y2, loc.getZ() + z);
            effect.display(0, 0, 0, speed, 2, loc2, 15D);
        }

        for (double y = 5; y >= 0; y -= 0.008) {
            radius = y / 3;
            double x = (-(radius * Math.cos(1.6 * y)));
            double z = (-(radius * Math.sin(1.6 * y)));

            double y2 = 5 - y;

            Location loc2 = new Location(loc.getWorld(), loc.getX() + x, loc.getY() + y2, loc.getZ() + z);
            effect.display(0, 0, 0, speed, 2, loc2, 15D);
        }

    }

}
