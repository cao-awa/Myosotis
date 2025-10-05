package com.github.cao.awa.myosotis.mixin.server.world;

import com.github.cao.awa.myosotis.death.type.explosion.PlayerDeathByExplosion;
import com.github.cao.awa.myosotis.death.type.fall.PlayerDeathByFalling;
import com.github.cao.awa.myosotis.death.type.mob.skeleton.PlayerDeathBySkeleton;
import com.github.cao.awa.myosotis.death.type.mob.zombie.PlayerDeathByZombie;
import com.github.cao.awa.myosotis.server.MyosotisServer;
import com.github.cao.awa.myosotis.server.world.session.ServerWorldSessionAccessor;
import com.github.cao.awa.myosotis.util.data.PlayerDeathDataUtil;
import com.github.cao.awa.myosotis.util.mob.DeathByMobUtil;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.random.RandomSequencesState;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.level.ServerWorldProperties;
import net.minecraft.world.level.storage.LevelStorage;
import net.minecraft.world.spawner.SpecialSpawner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.concurrent.Executor;

@Mixin(ServerWorld.class)
public class ServerWorldMixin implements ServerWorldSessionAccessor {
    @Unique
    public LevelStorage.Session myosotis$session;

    @Inject(
            method = "<init>",
            at = @At("RETURN")
    )
    public void onServerInit(MinecraftServer server, Executor workerExecutor, LevelStorage.Session session, ServerWorldProperties properties, RegistryKey<World> worldKey, DimensionOptions dimensionOptions, boolean debugWorld, long seed, List<SpecialSpawner> spawners, boolean shouldTickTime, RandomSequencesState randomSequenceState, CallbackInfo ci) {
        this.myosotis$session = session;
    }

    @Inject(
            method = "onPlayerRespawned",
            at = @At("HEAD")
    )
    public void onPlayerRespawned(ServerPlayerEntity player, CallbackInfo ci) {
        DamageSource damageSource = MyosotisServer.deaths.get(player.getStringifiedName());
        if (damageSource != null) {
            if (damageSource.isOf(DamageTypes.FALL) && PlayerDeathDataUtil.isFirstDeathBy(player, damageSource)) {
                PlayerDeathByFalling.tryRespawnWithFeather(player);
            }

            if (damageSource.isOf(DamageTypes.PLAYER_EXPLOSION)) {
                if (damageSource.getAttacker() instanceof CreeperEntity creeper && PlayerDeathDataUtil.isFirstDeathBy(player, damageSource, creeper)) {
                    PlayerDeathByExplosion.tryRespawnWithFirework(player);
                }
            }

            if (damageSource.isOf(DamageTypes.MOB_ATTACK)) {
                if (damageSource.getAttacker() instanceof ZombieEntity zombie && DeathByMobUtil.isFirstDeathBy(player, damageSource, zombie)) {
                    PlayerDeathByZombie.tryRespawnWithWoodenSword(player);
                }
            }

            if (damageSource.isOf(DamageTypes.ARROW)) {
                if (damageSource.getAttacker() instanceof SkeletonEntity skeleton && DeathByMobUtil.isFirstDeathBy(player, damageSource, skeleton)) {
                    PlayerDeathBySkeleton.tryRespawnWithSecurity(player);
                }
            }
            
            MyosotisServer.deaths.remove(player.getStringifiedName());
        }
    }

    @Override
    public LevelStorage.Session myosotis$getSession() {
        return this.myosotis$session;
    }
}
