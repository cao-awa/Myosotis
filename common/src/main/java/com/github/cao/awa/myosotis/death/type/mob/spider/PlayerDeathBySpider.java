package com.github.cao.awa.myosotis.death.type.mob.spider;

import com.github.cao.awa.sinuatum.manipulate.Manipulate;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class PlayerDeathBySpider {
    public static void tryRespawnWithString(ServerPlayerEntity player) {
        player.giveOrDropStack(
                Manipulate.make(
                        new ItemStack(Items.STRING),
                        item -> {
                            item.set(DataComponentTypes.CUSTOM_NAME, Text.translatable("myosotis.item.spider_string"));
                        }
                )
        );
    }
}
