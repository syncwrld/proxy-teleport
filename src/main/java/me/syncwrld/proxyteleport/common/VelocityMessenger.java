package me.syncwrld.proxyteleport.common;

import com.google.common.collect.Maps;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ServerConnection;
import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier;

import java.util.Map;
import java.util.Optional;

public class VelocityMessenger {

    private final MinecraftChannelIdentifier channelID = MinecraftChannelIdentifier.from(Constants.CHANNEL_IDENTIFIER);

    public void sendMessage(Player player, String message) {
        Optional<ServerConnection> connection = player.getCurrentServer();
        connection.ifPresent(serverConnection -> serverConnection
                .sendPluginMessage(channelID, message.getBytes()));
    }

    public void sendTeleportMessage(Player from, Player target) {
        Map<String, Object> messageMap = Maps.newConcurrentMap();
        messageMap.put("action", "teleport");
        messageMap.put("teleported", from.getGameProfile().getName());
        messageMap.put("target", target.getGameProfile().getName());

        this.sendMessage(from, Constants.GSON.toJson(messageMap));
    }

}
