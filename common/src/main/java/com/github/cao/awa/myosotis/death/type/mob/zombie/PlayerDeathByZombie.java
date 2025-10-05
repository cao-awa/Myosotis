package com.github.cao.awa.myosotis.death.type.mob.zombie;

import com.github.cao.awa.sinuatum.manipulate.Manipulate;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.WeaponComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterial;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class PlayerDeathByZombie {
    public static void tryRespawnWithWoodenSword(ServerPlayerEntity player) {
        player.giveOrDropStack(
                Manipulate.make(
                        new ItemStack(Items.WOODEN_SWORD),
                        item -> {
                            item.set(DataComponentTypes.CUSTOM_NAME, Text.translatable("myosotis.death.item.zombie_sword"));
                            item.set(DataComponentTypes.MAX_DAMAGE, 98);
                        }
                )
        );
    }
}
