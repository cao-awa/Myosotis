package com.github.cao.awa.myosotis.fabric.client;

import com.github.cao.awa.myosotis.Myosotis;
import net.fabricmc.api.ClientModInitializer;

public final class MyosotisFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        Myosotis.init();
    }
}
