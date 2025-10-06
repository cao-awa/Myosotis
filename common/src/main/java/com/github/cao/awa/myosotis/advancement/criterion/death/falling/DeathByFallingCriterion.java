package com.github.cao.awa.myosotis.advancement.criterion.death.falling;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancement.criterion.AbstractCriterion;
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

public class DeathByFallingCriterion extends AbstractCriterion<DeathByFallingCriterion.Conditions> {
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

    public record Conditions(Optional<LootContextPredicate> player) implements AbstractCriterion.Conditions {
        public static final Codec<Conditions> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                EntityPredicate.LOOT_CONTEXT_PREDICATE_CODEC.optionalFieldOf("player").forGetter(Conditions::player)
        ).apply(instance, Conditions::new));

        public boolean test(LootContext context) {
            DamageSource damageSource = context.get(LootContextParameters.DAMAGE_SOURCE);
            assert damageSource != null;
            return damageSource.isOf(DamageTypes.FALL);
        }

        @Override
        public void validate(LootContextPredicateValidator validator) {
            AbstractCriterion.Conditions.super.validate(validator);
        }
    }
}
