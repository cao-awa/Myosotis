package com.github.cao.awa.myosotis.advancement.criterion.death.explosion;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.loot.context.LootWorldContext;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.entity.LootContextPredicate;
import net.minecraft.predicate.entity.LootContextPredicateValidator;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.Optional;

public class ExplosionByEntityCriterion extends AbstractCriterion<ExplosionByEntityCriterion.Conditions> {
    @Override
    public Codec<Conditions> getConditionsCodec() {
        return Conditions.CODEC;
    }

    public void trigger(ServerPlayerEntity player, DamageSource damageSource) {
        LootContext lootcontext = createAdvancementEntityLootContext(player, damageSource);
        this.trigger(player, conditions -> conditions.test(lootcontext));
    }

    public static LootContext createAdvancementEntityLootContext(ServerPlayerEntity player, DamageSource damageSource) {
        LootWorldContext lootWorldContext = new LootWorldContext.Builder(
                player.getEntityWorld()
        )
                .add(LootContextParameters.DAMAGE_SOURCE, damageSource)
                .add(LootContextParameters.ORIGIN, player.getEntityPos())
                .add(LootContextParameters.THIS_ENTITY, player)
                .build(LootContextTypes.ENTITY);
        return (new LootContext.Builder(lootWorldContext)).build(Optional.empty());
    }

    public record Conditions(Optional<LootContextPredicate> player, Optional<EntityType<?>> source,
                             Optional<EntityType<?>> attacker) implements AbstractCriterion.Conditions {
        public static final Codec<Conditions> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                EntityPredicate.LOOT_CONTEXT_PREDICATE_CODEC.optionalFieldOf("player").forGetter(Conditions::player),
                EntityType.CODEC.optionalFieldOf("source").forGetter(Conditions::source),
                EntityType.CODEC.optionalFieldOf("causer").forGetter(Conditions::attacker)
        ).apply(instance, Conditions::new));

        public boolean test(LootContext context) {
            return this.attacker.map(
                    entityType -> {
                        DamageSource damageSource = context.get(LootContextParameters.DAMAGE_SOURCE);
                        assert damageSource != null;
                        if (!damageSource.isOf(DamageTypes.PLAYER_EXPLOSION) && !damageSource.isOf(DamageTypes.EXPLOSION)) {
                            return false;
                        }
                        Entity attackerToPlayer = damageSource.getAttacker();
                        assert attackerToPlayer != null;
                        return entityType == attackerToPlayer.getType();
                    }
            ).orElse(this.source.map(
                    entityType -> {
                        DamageSource damageSource = context.get(LootContextParameters.DAMAGE_SOURCE);
                        assert damageSource != null;
                        if (!damageSource.isOf(DamageTypes.PLAYER_EXPLOSION) && !damageSource.isOf(DamageTypes.EXPLOSION)) {
                            return false;
                        }
                        Entity attackerToPlayer = damageSource.getAttacker();
                        assert attackerToPlayer != null;
                        return entityType == attackerToPlayer.getType();
                    }
            ).orElse(false));
        }

        @Override
        public void validate(LootContextPredicateValidator validator) {
            AbstractCriterion.Conditions.super.validate(validator);
        }
    }
}
