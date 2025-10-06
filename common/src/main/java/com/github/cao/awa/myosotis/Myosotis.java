package com.github.cao.awa.myosotis;

import com.github.cao.awa.myosotis.advancement.criterion.crieria.MyosotisCriteria;

public final class Myosotis {
    public static final String MOD_ID = "myosotis";

    public static void init() {
        MyosotisCriteria.register();
    }
}
