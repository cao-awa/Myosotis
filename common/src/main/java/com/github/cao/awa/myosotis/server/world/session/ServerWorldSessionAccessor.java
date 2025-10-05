package com.github.cao.awa.myosotis.server.world.session;

import net.minecraft.world.level.storage.LevelStorage;

public interface ServerWorldSessionAccessor {
    default LevelStorage.Session getSession() {
        return myosotis$getSession();
    }

    LevelStorage.Session myosotis$getSession();
}
