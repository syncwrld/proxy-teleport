package me.syncwrld.proxyteleport.common;

import com.google.common.collect.Maps;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;

public class BungeecordMessenger {

    private final String channelID = Constants.CHANNEL_IDENTIFIER;

    public void sendMessage(ProxiedPlayer player, String message) {
        ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(byteArrayOut);

        try {
            out.writeUTF(message);
            player.getServer().sendData(this.channelID, byteArrayOut.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendTeleportMessage(ProxiedPlayer from, ProxiedPlayer target) {
        Map<String, Object> messageMap = Maps.newConcurrentMap();
        messageMap.put("action", "teleport");
        messageMap.put("teleported", from.getName());
        messageMap.put("target", target.getName());

        this.sendMessage(from, Constants.GSON.toJson(messageMap));
    }

}
