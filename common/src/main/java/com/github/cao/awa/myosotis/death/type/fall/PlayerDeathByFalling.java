package com.github.cao.awa.myosotis.death.type.fall;

import com.github.cao.awa.sinuatum.manipulate.Manipulate;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ConsumableComponents;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class PlayerDeathByFalling {
    public static void tryRespawnWithFeather(ServerPlayerEntity player) {
        player.giveOrDropStack(
                Manipulate.make(
                        new ItemStack(Items.FEATHER),
                        item -> {
                            item.set(DataComponentTypes.CUSTOM_NAME, Text.translatable("myosotis.item.falls"));
                            item.set(DataComponentTypes.FOOD, new FoodComponent.Builder().nutrition(5).saturationModifier(0.6F).alwaysEdible().build());
                            item.set(DataComponentTypes.CONSUMABLE, ConsumableComponents.FOOD);
                        }
                )
        );
    }
}
