package com.minelands.pvp.Kits;

import org.bukkit.potion.PotionEffect;

/**
 * Copyright Joey Gallegos {c} 2014. All Rights Reserved.
 * Any code contained within this document, and any associated APIs with similar branding
 * are the sole property of Joey Gallegos. Distribution, reproduction, taking snippets, or
 * claiming any contents as your own will break the terms of the License, and void any
 * agreements with you, the third party.
 */
public class KitEffect {

    private PotionEffect type;

    public KitEffect(PotionEffect type) {
        this.type = type;
    }

    public PotionEffect getType() {
        return type;
    }
}
