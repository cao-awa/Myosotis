package com.github.cao.awa.myosotis.death.data;

import com.google.gson.JsonObject;
import net.minecraft.world.level.storage.LevelStorage;

public interface PlayerDeathDataAccessor {
    default JsonObject getDeathData() {
        return myosotis$getDeathData();
    }

    JsonObject myosotis$getDeathData();
}
