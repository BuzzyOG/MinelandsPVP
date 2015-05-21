package com.minelands.pvp.Util;

import net.minecraft.server.v1_8_R1.EntityFireworks;
import net.minecraft.server.v1_8_R1.PacketPlayOutEntityStatus;
import net.minecraft.server.v1_8_R1.World;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

import java.util.Collection;


/**
 * Copyright Joey Gallegos {c} 2014. All Rights Reserved.
 * Any code contained within this document, and any associated APIs with similar branding
 * are the sole property of Joey Gallegos. Distribution, reproduction, taking snippets, or
 * claiming any contents as your own will break the terms of the License, and void any
 * agreements with you, the third party.
 */
public class CustomFirework extends EntityFireworks {

    private Collection<Player> players;
    private boolean gone = false;

    public CustomFirework(World world, Collection<Player> p) {
        super(world);
        this.players = p;
        this.a(0.25F, 0.25F);
    }

    public static void spawn(Location location, FireworkEffect effect, Collection<Player> players) {
        try {
            CustomFirework firework = new CustomFirework(((CraftWorld) location.getWorld()).getHandle(), players);
            FireworkMeta meta = ((Firework) firework.getBukkitEntity()).getFireworkMeta();
            meta.addEffect(effect);
            ((Firework) firework.getBukkitEntity()).setFireworkMeta(meta);
            firework.setPosition(location.getX(), location.getY(), location.getZ());

            if ((((CraftWorld) location.getWorld()).getHandle()).addEntity(firework)) {
                firework.setInvisible(true);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void s_() {
        if (gone) {
            return;
        }

        if (!this.world.isStatic) {
            this.gone = true;
            if (players != null) {
                if (players.size() > 0) {
                    for (Player player : players) {
                        (((CraftPlayer) player).getHandle()).playerConnection.sendPacket(new PacketPlayOutEntityStatus(this, (byte) 17));
                    }
                } else {
                    world.broadcastEntityEffect(this, (byte) 17);
                }
                this.die();
            }
        }
    }
}
