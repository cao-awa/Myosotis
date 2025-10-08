package com.github.cao.awa.myosotis.util.mob;

import com.github.cao.awa.myosotis.util.data.PlayerDeathDataUtil;
import com.github.cao.awa.sinuatum.manipulate.Manipulate;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.Map;

public class DeathByMobUtil {
    private static final Map<Class<? extends MobEntity>, String> MOB_DEATH_COUNT_MAP = Manipulate.make(new Object2ObjectOpenHashMap<>(), map -> {
        map.put(ZombieEntity.class, "zombie");
        map.put(SkeletonEntity.class, "skeleton");
        map.put(WolfEntity.class, "wolf");
        map.put(SpiderEntity.class, "spider");
        map.put(CreeperEntity.class, "creeper");
    });

    public static int getDeathCount(ServerPlayerEntity player, DamageSource damageSource, MobEntity mob) {
        return PlayerDeathDataUtil.getPlayerDeathCount(player, getDeathCountName(damageSource, mob));
    }

    public static String getDeathCountName(DamageSource damageSource, MobEntity mob) {
        return damageSource.getName() + "_" + MOB_DEATH_COUNT_MAP.get(mob.getClass());
    }

    public static boolean isFirstDeathBy(ServerPlayerEntity player, DamageSource damageSource, MobEntity mob) {
        return getDeathCount(player, damageSource, mob) < 2;
    }
}
