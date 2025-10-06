package com.github.cao.awa.myosotis.fabric;

import com.github.cao.awa.myosotis.Myosotis;
import net.fabricmc.api.ModInitializer;

public final class MyosotisFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        // Run our common setup.
        Myosotis.init();
    }
}
