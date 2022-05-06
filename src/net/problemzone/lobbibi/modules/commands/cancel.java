package net.problemzone.lobbibi.modules.commands;

import net.problemzone.lobbibi.modules.LobbyManager;
import net.problemzone.lobbibi.util.Language;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import javax.annotation.Nonnull;

public class cancel implements CommandExecutor {

    private final LobbyManager lobbyManager;

    public cancel(LobbyManager lobbyManager) {
        this.lobbyManager = lobbyManager;
    }

    @Override
    public boolean onCommand(@Nonnull CommandSender commandSender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {

        if (args.length != 0) return false;
        lobbyManager.cancelTimer();
        Bukkit.broadcastMessage(Language.GAME_START_CANCEL.getFormattedText());
        return true;
    }

}
