package net.problemzone.lobbibi.modules;

import net.problemzone.lobbibi.Main;
import net.problemzone.lobbibi.util.Language;
import net.problemzone.lobbibi.wrapper.ListenerWrapper;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class LobbyListener extends ListenerWrapper implements Listener {

    private static final int PLAYER_START_COUNT = 3;
    // TODO: Config Setting

    private final LobbyManager lobbyManager;

    public LobbyListener(LobbyManager lobbyManager) {
        this.lobbyManager = lobbyManager;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        if(lobbyManager.getLobbyState() == LobbyManager.LobbyState.RUNNING) return;

        e.setJoinMessage(Language.PLAYER_JOIN.getText() + e.getPlayer().getDisplayName());
        e.getPlayer().teleport(e.getPlayer().getWorld().getSpawnLocation());

        if (lobbyManager.getLobbyState() != LobbyManager.LobbyState.IDLING) return;

        if (Bukkit.getOnlinePlayers().size() < PLAYER_START_COUNT) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    Bukkit.broadcastMessage(PLAYER_START_COUNT - Bukkit.getOnlinePlayers().size() == 1 ? Language.PLAYERS_NEEDED_ONE.getFormattedText() : String.format(Language.PLAYERS_NEEDED.getFormattedText(), PLAYER_START_COUNT - Bukkit.getOnlinePlayers().size()));
                }
            }.runTaskLater(Main.getJavaPlugin(), 5);
            return;
        }

        lobbyManager.initiateTimer();
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e) {
        if(lobbyManager.getLobbyState() == LobbyManager.LobbyState.RUNNING) return;

        e.setQuitMessage(Language.PLAYER_LEAVE.getText() + e.getPlayer().getDisplayName());

        if (lobbyManager.getLobbyState() != LobbyManager.LobbyState.STARTING) return;

        if (Bukkit.getOnlinePlayers().size() >= PLAYER_START_COUNT) return;
        new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.broadcastMessage(PLAYER_START_COUNT - Bukkit.getOnlinePlayers().size() == 1 ? Language.PLAYERS_NEEDED_ONE.getFormattedText() : String.format(Language.PLAYERS_NEEDED.getFormattedText(), PLAYER_START_COUNT - Bukkit.getOnlinePlayers().size()));
            }
        }.runTaskLater(Main.getJavaPlugin(), 5);

        if (lobbyManager.getLobbyState() == LobbyManager.LobbyState.STARTING) lobbyManager.cancelTimer();
    }
}
