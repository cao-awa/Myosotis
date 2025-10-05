package com.github.cao.awa.myosotis.mixin.server.world;

import com.github.cao.awa.myosotis.death.type.explosion.PlayerDeathByExplosion;
import com.github.cao.awa.myosotis.death.type.fall.PlayerDeathByFalling;
import com.github.cao.awa.myosotis.server.MyosotisServer;
import com.github.cao.awa.myosotis.server.world.session.ServerWorldSessionAccessor;
import com.github.cao.awa.myosotis.util.data.PlayerDeathDataUtil;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.mob.CreeperEntity;
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
                if (damageSource.getAttacker() instanceof CreeperEntity && PlayerDeathDataUtil.isFirstDeathBy(player, damageSource)) {
                    PlayerDeathByExplosion.tryRespawnWithFirework(player);
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
