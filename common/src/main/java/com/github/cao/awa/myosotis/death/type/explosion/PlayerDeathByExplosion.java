package com.github.cao.awa.myosotis.death.type.explosion;

import com.github.cao.awa.sinuatum.manipulate.Manipulate;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ConsumableComponents;
import net.minecraft.component.type.FireworkExplosionComponent;
import net.minecraft.component.type.FireworksComponent;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.List;

public class PlayerDeathByExplosion {
    public static void tryRespawnWithFirework(ServerPlayerEntity player) {
        player.giveOrDropStack(
                Manipulate.make(
                        new ItemStack(Items.FIREWORK_ROCKET, 3),
                        item -> {
                            FireworkExplosionComponent explosionComponent = new FireworkExplosionComponent(
                                    FireworkExplosionComponent.Type.CREEPER,
                                    IntList.of(0x00FF00),
                                    IntList.of(0xFFFFFF),
                                    true,
                                    true
                            );
                            item.set(DataComponentTypes.CUSTOM_NAME, Text.translatable("myosotis.death.item.creeper"));
                            item.set(DataComponentTypes.FIREWORKS, new FireworksComponent(1, List.of(explosionComponent)));
                            item.set(DataComponentTypes.FIREWORK_EXPLOSION, explosionComponent);
                        }
                )
        );
    }
}
