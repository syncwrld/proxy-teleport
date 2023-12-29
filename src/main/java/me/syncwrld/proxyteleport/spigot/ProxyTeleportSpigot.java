package me.syncwrld.proxyteleport.spigot;

import me.syncwrld.proxyteleport.common.Constants;
import me.syncwrld.proxyteleport.spigot.listener.SpigotMessageListener;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.Messenger;

public final class ProxyTeleportSpigot extends JavaPlugin {

    private final Messenger messenger = this.getServer().getMessenger();

    @Override
    public void onEnable() {
        Metrics metrics = new Metrics(this, 20597);
        this.messenger.registerIncomingPluginChannel(this, Constants.CHANNEL_IDENTIFIER, new SpigotMessageListener());
        this.messenger.registerOutgoingPluginChannel(this, Constants.CHANNEL_IDENTIFIER);

        this.getLogger().info("The plugin has been enabled!");
    }

    @Override
    public void onDisable() {
    }

}
