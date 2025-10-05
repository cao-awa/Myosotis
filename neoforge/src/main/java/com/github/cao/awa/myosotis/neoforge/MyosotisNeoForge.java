package com.github.cao.awa.myosotis.neoforge;

import com.github.cao.awa.myosotis.Myosotis;
import net.neoforged.fml.common.Mod;

@Mod(Myosotis.MOD_ID)
public final class MyosotisNeoForge {
    public MyosotisNeoForge() {
        // Run our common setup.
        Myosotis.init();
    }
}
