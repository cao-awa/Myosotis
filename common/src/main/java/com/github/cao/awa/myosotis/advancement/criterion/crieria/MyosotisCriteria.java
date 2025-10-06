package com.github.cao.awa.myosotis.advancement.criterion.crieria;

import com.github.cao.awa.myosotis.advancement.criterion.death.DeathCriterion;
import com.github.cao.awa.myosotis.advancement.criterion.death.explosion.ExplosionByEntityCriterion;
import com.github.cao.awa.myosotis.advancement.criterion.death.falling.DeathByFallingCriterion;
import com.github.cao.awa.myosotis.advancement.criterion.death.kill.KillByEntityCriterion;
import net.minecraft.advancement.criterion.Criterion;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class MyosotisCriteria {
    public static final DeathCriterion DEATH = register("myosotis:death", new DeathCriterion());
    public static final KillByEntityCriterion KILL_BY_ENTITY = register("myosotis:kill_by_entity", new KillByEntityCriterion());
    public static final ExplosionByEntityCriterion EXPLOSION_BY_ENTITY = register("myosotis:explosion_by_entity", new ExplosionByEntityCriterion());
    public static final DeathByFallingCriterion DEATH_BY_FALLING = register("myosotis:death_by_falling", new DeathByFallingCriterion());

    private static <T extends Criterion<?>> T register(String id, T criterion) {
        return Registry.register(Registries.CRITERION, id, criterion);
    }

    public static void register() {

    }
}
