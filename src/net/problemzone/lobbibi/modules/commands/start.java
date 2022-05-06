package net.problemzone.lobbibi.modules.commands;

import net.problemzone.lobbibi.modules.LobbyManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.libs.org.apache.commons.lang3.math.NumberUtils;

import javax.annotation.Nonnull;

public class start implements CommandExecutor {

    private final static int START_COMMAND_TIME = 10;

    private final LobbyManager lobbyManager;

    public start(LobbyManager lobbyManager) {
        this.lobbyManager = lobbyManager;
    }

    @Override
    public boolean onCommand(@Nonnull CommandSender commandSender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {

        if (args.length == 0) {
            lobbyManager.initiateTimer(START_COMMAND_TIME, true);
            return true;
        }

        if (args.length != 1) return false;
        if (!NumberUtils.isParsable(args[0])) return false;

        int time = Integer.parseInt(args[0]);
        lobbyManager.initiateTimer(Math.max(time, 10), true);
        return true;
    }
}
