package com.github.cao.awa.myosotis.fabric;

import com.github.cao.awa.myosotis.Myosotis;
import net.fabricmc.api.ModInitializer;

public final class MyosotisFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        // Run our common setup.
        Myosotis.init();
    }
}
