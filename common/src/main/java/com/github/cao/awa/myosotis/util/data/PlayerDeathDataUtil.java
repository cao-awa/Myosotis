package com.github.cao.awa.myosotis.util.data;

import com.github.cao.awa.myosotis.death.data.PlayerDeathDataAccessor;
import com.github.cao.awa.myosotis.server.world.session.ServerWorldSessionAccessor;
import com.github.cao.awa.sinuatum.util.io.IOUtil;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.JsonHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class PlayerDeathDataUtil {
    private static final Logger LOGGER = LogManager.getLogger("MyosotisPlayerDeathDataUtil");

    public static JsonObject getPlayerDeathData(ServerPlayerEntity player) {
        try {
            return ((PlayerDeathDataAccessor) player).getDeathData();
        } catch (Exception e) {
            return new JsonObject();
        }
    }

    public static int getPlayerDeathCount(ServerPlayerEntity player, DamageSource damageSource) {
        JsonElement deathCount = getPlayerDeathData(player).get(damageSource.getName());
        if (deathCount == null) {
            return 0;
        }
        return deathCount.getAsInt();
    }

    public static boolean isFirstDeathBy(ServerPlayerEntity player, DamageSource damageSource) {
        return getPlayerDeathCount(player, damageSource) < 2;
    }

    public static JsonObject getPlayerDeathDataFromFile(ServerPlayerEntity player) {
        try {
            return JsonHelper.deserialize(new FileReader(getPlayerDeathDataFile(player)));
        } catch (Exception e) {
            return new JsonObject();
        }
    }

    public static void updatePayerDeathData(ServerPlayerEntity player) {
        try {
            IOUtil.write(new FileWriter(getPlayerDeathDataFile(player)), getPlayerDeathData(player).toString());
        } catch (Exception e) {
            LOGGER.warn("Failed to update player death data file for player {}", player.getStringifiedName(), e);
        }
    }

    public static File getPlayerDeathDataFile(ServerPlayerEntity player) {
        String fileName = ((ServerWorldSessionAccessor) player.getEntityWorld()).getSession().getDirectory().path().toFile().getAbsoluteFile() + "/myosotis/death_data/" + player.getUuidAsString() + ".json";
        LOGGER.debug("Saving death data for player {} in {}", player.getStringifiedName(), fileName);
        File file = new File(fileName);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        if (!file.isFile()) {
            try {
                file.createNewFile();
            } catch (Exception e) {
                LOGGER.warn("Failed to create player death data file for player {}", player.getStringifiedName(), e);
            }
        }
        return file;
    }
}
