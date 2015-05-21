package com.minelands.pvp.KitSwitch;

/**
 * Copyright Joey Gallegos {c} 2014. All Rights Reserved.
 * Any code contained within this document, and any associated APIs with similar branding
 * are the sole property of Joey Gallegos. Distribution, reproduction, taking snippets, or
 * claiming any contents as your own will break the terms of the License, and void any
 * agreements with you, the third party.
 */
public enum SwitchType {

    RARE(0.2F), NORMAL(0.6F);

    private float chance;

    SwitchType(float chance) {
        this.chance = chance;
    }

    public float getChance() {
        return chance;
    }
}
