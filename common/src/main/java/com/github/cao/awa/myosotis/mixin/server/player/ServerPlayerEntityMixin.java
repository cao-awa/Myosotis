package com.github.cao.awa.myosotis.mixin.server.player;

import com.github.cao.awa.myosotis.death.data.PlayerDeathDataAccessor;
import com.github.cao.awa.myosotis.server.MyosotisServer;
import com.github.cao.awa.myosotis.util.data.PlayerDeathDataUtil;
import com.github.cao.awa.myosotis.util.mob.DeathByMobUtil;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.mojang.authlib.GameProfile;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.c2s.common.SyncedClientOptions;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin extends PlayerEntity implements PlayerDeathDataAccessor {
    private GameProfile profile;

    @Unique
    private final JsonObject myosotis$deathData = PlayerDeathDataUtil.getPlayerDeathDataFromFile((ServerPlayerEntity) asLivingEntity());

    public ServerPlayerEntityMixin(World world, GameProfile profile) {
        super(world, profile);
        this.profile = profile;
    }

    @Inject(
            method = "<init>",
            at = @At("RETURN")
    )
    public void onInit(MinecraftServer server, ServerWorld world, GameProfile profile, SyncedClientOptions clientOptions, CallbackInfo ci) {
        this.profile = profile;
    }

    @Inject(
            method = "onDeath",
            at = @At("HEAD")
    )
    public void onDeath(DamageSource damageSource, CallbackInfo ci) {
        MyosotisServer.deaths.put(this.profile.name(), damageSource);
        ServerPlayerEntity player = (ServerPlayerEntity) asLivingEntity();
        if (damageSource.isOf(DamageTypes.MOB_ATTACK)) {
            myosotis$mobAttackDeathCount(player, damageSource);
        } else if (damageSource.isOf(DamageTypes.ARROW)) {
            myosotis$mobAttackDeathCount(player, damageSource);
        } else {
            int deathCount = PlayerDeathDataUtil.getPlayerDeathCount(player, damageSource) + 1;
            this.myosotis$deathData.add(damageSource.getName(), new JsonPrimitive(deathCount));
        }
        PlayerDeathDataUtil.updatePayerDeathData(player);
    }

    @Unique
    public void myosotis$mobAttackDeathCount(ServerPlayerEntity player, DamageSource damageSource) {
        if (damageSource.getAttacker() instanceof MobEntity mob) {
            int deathCount = DeathByMobUtil.getDeathCount(player, damageSource, mob) + 1;
            this.myosotis$deathData.add(DeathByMobUtil.getDeathCountName(damageSource, mob), new JsonPrimitive(deathCount));
        }
    }

    @Override
    public JsonObject myosotis$getDeathData() {
        return this.myosotis$deathData;
    }
}
