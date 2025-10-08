package com.github.cao.awa.myosotis.death.type.mob.skeleton;

import com.github.cao.awa.sinuatum.manipulate.Manipulate;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class PlayerDeathBySkeleton {
    public static void tryRespawnWithSecurity(ServerPlayerEntity player) {
        player.giveOrDropStack(
                Manipulate.make(
                        new ItemStack(Items.SHIELD),
                        item -> {
                            item.set(DataComponentTypes.CUSTOM_NAME, Text.translatable("myosotis.item.skeleton_shield"));
                            item.set(DataComponentTypes.MAX_DAMAGE, 441);
                        }
                )
        );
    }
}
