package me.syncwrld.proxyteleport.bungee;

import me.syncwrld.proxyteleport.bungee.command.TeleportCommandBungee;
import me.syncwrld.proxyteleport.common.BungeecordMessenger;
import me.syncwrld.proxyteleport.common.Constants;
import net.md_5.bungee.api.plugin.Plugin;
import org.bstats.bungeecord.Metrics;

public class ProxyTeleportBungee extends Plugin {

    private final BungeecordMessenger messenger = new BungeecordMessenger();

    @Override
    public void onEnable() {
        Metrics metrics = new Metrics(this, 20598);
        this.getProxy().registerChannel(Constants.CHANNEL_IDENTIFIER);
        this.getProxy().getPluginManager().registerCommand(this, new TeleportCommandBungee(this));

        this.getLogger().info("The plugin has been enabled!");
    }

    public BungeecordMessenger getMessenger() {
        return this.messenger;
    }

}
