package me.syncwrld.proxyteleport.bungee.command;

import me.syncwrld.proxyteleport.bungee.ProxyTeleportBungee;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ServerConnectRequest;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.connection.Server;
import net.md_5.bungee.api.plugin.Command;

import static org.bukkit.Bukkit.getPlayer;

public class TeleportCommandBungee extends Command {

    private final ProxyTeleportBungee plugin;

    public TeleportCommandBungee(ProxyTeleportBungee plugin) {
        super("teleport", "proxyteleport.teleport", "tpb");
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        if (!this.canExecute(commandSender)) {
            commandSender.sendMessage("You can't use this command.");
            return;
        }

        if (args.length != 1) {
            commandSender.sendMessage("Usage: /teleport <target>");
            return;
        }

        ProxiedPlayer player = (ProxiedPlayer) commandSender;
        Server currentServer = player.getServer();

        if (currentServer == null) {
            commandSender.sendMessage("You must be connected to a server to use this command.");
            return;
        }

        ProxiedPlayer target = this.plugin.getProxy().getPlayer(args[0]);

        if (target == null || !(target.isConnected())) {
            commandSender.sendMessage("The target player was not found.");
            return;
        }

        ServerConnectRequest request = ServerConnectRequest.builder()
                .target(target.getServer().getInfo())
                .retry(true)
                .build();

        player.connect(request);

    }

    @Override
    public boolean hasPermission(CommandSender sender) {
        return sender.hasPermission("proxyteleport.teleport");
    }

    public boolean canExecute(CommandSender sender) {
        return sender instanceof ProxiedPlayer && this.hasPermission(sender);
    }

}
