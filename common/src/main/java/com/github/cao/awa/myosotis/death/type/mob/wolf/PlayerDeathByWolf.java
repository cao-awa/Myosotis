package com.github.cao.awa.myosotis.death.type.mob.wolf;

import com.github.cao.awa.sinuatum.manipulate.Manipulate;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class PlayerDeathByWolf {
    public static void tryRespawnWithBones(ServerPlayerEntity player) {
        player.giveOrDropStack(
                Manipulate.make(
                        new ItemStack(Items.BONE, 5),
                        item -> {
                            item.set(DataComponentTypes.CUSTOM_NAME, Text.translatable("myosotis.item.wolf_bone"));
                        }
                )
        );
    }
}
