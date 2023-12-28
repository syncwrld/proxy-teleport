package me.syncwrld.proxyteleport.velocity.command;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ServerConnection;
import me.syncwrld.proxyteleport.velocity.ProxyTeleportVelocity;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class TeleportCommandVelocity implements SimpleCommand {

    private final ProxyTeleportVelocity plugin;

    public TeleportCommandVelocity(ProxyTeleportVelocity plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(Invocation invocation) {
        if (!this.canExecute(invocation)) {
            invocation.source().sendMessage(Component.text("You can't use this command."));
            return;
        }

        String[] args = invocation.arguments();

        if (args.length != 1) {
            invocation.source().sendMessage(Component.text("Usage: /teleport <target>"));
            return;
        }

        Player player = (Player) invocation.source();
        Optional<ServerConnection> playerConnectionOptional = player.getCurrentServer();

        if (playerConnectionOptional.isEmpty()) {
            player.sendMessage(Component.text("You must be connected to a server to use this command."));
            return;
        }

        Optional<Player> target = this.plugin.getInstance().getPlayer(args[0]);

        if (target.isEmpty()) {
            player.sendMessage(Component.text("The target player was not found."));
            return;
        }

        Optional<ServerConnection> targetConnectionOptional = target.get().getCurrentServer();
        if (playerConnectionOptional != targetConnectionOptional) {
            if (targetConnectionOptional.isPresent()) {
                player.createConnectionRequest(
                        targetConnectionOptional.get().getServer()
                ).connect();
            } else {
                player.sendMessage(Component.text("The target player is not connected to a server."));
            }
        }

        player.sendMessage(
                Component.text("Teleporting you to " + target.get().getGameProfile().getName() + ".")
                        .color(TextColor.color(70, 70, 70))
        );

        this.plugin.getInstance().getScheduler()
                .buildTask(
                        this.plugin, () ->
                        this.plugin.getMessenger().sendTeleportMessage(player, target.get())
                )
                .delay(2, TimeUnit.SECONDS)
                .schedule();
   }

    @Override
    public List<String> suggest(Invocation invocation) {
        CommandSource source = invocation.source();
        if (source instanceof Player) {
            Optional<ServerConnection> connection = ((Player) source).getCurrentServer();
            if (connection.isPresent()) {
                return this.plugin.getInstance().getAllPlayers().stream().map(player ->
                        player.getGameProfile().getName()).toList();
            }
        }
        return new ArrayList<>();
    }

    public CompletableFuture<List<String>> suggestAsync(Invocation invocation) {
        return CompletableFuture.completedFuture(this.suggest(invocation));
    }

    @Override
    public boolean hasPermission(Invocation invocation) {
        return invocation.source().hasPermission("proxy-teleport.teleport");
    }

    public boolean canExecute(Invocation invocation) {
        return invocation.source() instanceof Player && this.hasPermission(invocation);
    }

}
