package com.github.cao.awa.myosotis.advancement.criterion.crieria;

import com.github.cao.awa.myosotis.advancement.criterion.death.DeathCriterion;
import com.github.cao.awa.myosotis.advancement.criterion.death.KillByEntityCriterion;
import net.minecraft.advancement.criterion.Criterion;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class MyosotisCriteria {
    public static final DeathCriterion DEATH = register("myosotis:death", new DeathCriterion());
    public static final KillByEntityCriterion KILL_BY_ENTITY = register("myosotis:kill_by_entity", new KillByEntityCriterion());

    private static <T extends Criterion<?>> T register(String id, T criterion) {
        return Registry.register(Registries.CRITERION, id, criterion);
    }

    public static void register() {

    }
}
