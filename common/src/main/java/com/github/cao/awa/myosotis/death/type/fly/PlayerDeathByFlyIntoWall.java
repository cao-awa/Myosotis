package com.github.cao.awa.myosotis.death.type.fly;

import com.github.cao.awa.sinuatum.manipulate.Manipulate;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class PlayerDeathByFlyIntoWall {
    public static void tryRespawnWithRocket(ServerPlayerEntity player) {
        player.giveOrDropStack(
                Manipulate.make(
                        new ItemStack(Items.FIREWORK_ROCKET, 3),
                        item -> {
                            item.set(DataComponentTypes.CUSTOM_NAME, Text.translatable("myosotis.death.item.elytra_fly_into_wall"));
                        }
                )
        );
    }
}
