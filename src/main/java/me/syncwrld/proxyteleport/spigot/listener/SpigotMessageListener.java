package me.syncwrld.proxyteleport.spigot.listener;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import me.syncwrld.proxyteleport.common.Constants;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.jetbrains.annotations.NotNull;

public class SpigotMessageListener implements PluginMessageListener {

    @Override
    public void onPluginMessageReceived(@NotNull String s, @NotNull Player from, @NotNull byte[] bytes) {
        if (bytes.length == 0) {
            return;
        }

        String message = new String(bytes);
        JsonElement element = Constants.GSON.fromJson(message, JsonElement.class);
        JsonObject object = element.getAsJsonObject();

        String action = object.get("action").getAsString();
        String teleported = object.get("teleported").getAsString();
        String target = object.get("target").getAsString();

        if (!action.equals("teleport"))
            return;

        Player fromPlayer = Bukkit.getPlayerExact(teleported);
        Player targetPlayer = Bukkit.getPlayerExact(target);

        if (fromPlayer == null || targetPlayer == null) {
            return;
        }

        fromPlayer.teleport(targetPlayer);
    }

}
