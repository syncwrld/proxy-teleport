package me.syncwrld.proxyteleport.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier;
import me.syncwrld.proxyteleport.common.Constants;
import me.syncwrld.proxyteleport.common.VelocityMessenger;
import me.syncwrld.proxyteleport.velocity.command.TeleportCommandVelocity;

import java.util.logging.Logger;

@Plugin(id = "proxy-teleport", name = "Proxy Teleport", version = "${project.version}", url = "https://github.com/syncwrld/proxy-teleport", authors = "syncwrld")
public class ProxyTeleportVelocity {

    private final ProxyServer server;
    private final Logger logger;
    private final VelocityMessenger messenger = new VelocityMessenger();

    @Inject
    public ProxyTeleportVelocity(ProxyServer server, Logger logger) {
        this.server = server;
        this.logger = logger;
    }

    @Subscribe
    public void whenEnable(ProxyInitializeEvent event) {
        this.server.getChannelRegistrar().register(MinecraftChannelIdentifier.from(Constants.CHANNEL_IDENTIFIER));
        this.server.getCommandManager().register("teleport", new TeleportCommandVelocity(this));

        this.logger.info("The plugin has been enabled!");
    }

    public ProxyServer getInstance() {
        return this.server;
    }

    public VelocityMessenger getMessenger() {
        return this.messenger;
    }

}
