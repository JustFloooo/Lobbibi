package net.problemzone.lobbibi.modules;

import net.problemzone.lobbibi.Main;
import net.problemzone.lobbibi.util.Countdown;
import net.problemzone.lobbibi.util.Language;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.Objects;

public class LobbyManager {

    private static final int STARTING_LOBBY_TIME = 60;
    private static final String GAME_LOBBY = "Void";
    // Use Config here!

    public enum LobbyState {
        IDLING,
        STARTING,
        RUNNING,
        FINISHED
    }

    private LobbyState lobbyState = LobbyState.IDLING;
    private BukkitTask currentScheduledTask;

    public void initiateTimer() {
        initiateTimer(STARTING_LOBBY_TIME, false);
    }

    public void initiateTimer(int seconds, boolean overwrite){

        if (overwrite) cancelTimer();

        if (lobbyState != LobbyState.IDLING) return;

        lobbyState = LobbyState.STARTING;

        //Initialize Countdown
        Countdown.createXpBarCountdown(seconds);
        Countdown.createLevelCountdown(seconds, Language.GAME_START_TITLE);
        Countdown.createChatCountdown(seconds, Language.GAME_START);

        if (currentScheduledTask != null && !currentScheduledTask.isCancelled()) currentScheduledTask.cancel();

        currentScheduledTask = new BukkitRunnable() {
            @Override
            public void run() {
                startGame();
            }
        }.runTaskLater(Main.getJavaPlugin(), seconds * 20L);

    }

    public void cancelTimer(){
        if (lobbyState != LobbyState.STARTING) return;
        Countdown.cancelXpBarCountdown();
        Countdown.cancelLevelCountdown();
        Countdown.cancelChatCountdown();
        if (currentScheduledTask != null && !currentScheduledTask.isCancelled()) currentScheduledTask.cancel();
        lobbyState = LobbyState.IDLING;
    }

    public void startGame(){
        Bukkit.broadcastMessage("Game Started!");
        lobbyState = LobbyState.RUNNING;
        Bukkit.getOnlinePlayers().forEach(player -> player.teleport(Objects.requireNonNull(Bukkit.getWorld(GAME_LOBBY)).getSpawnLocation()));
    }

    // Public Getter Methods
    public LobbyState getLobbyState() {
        return lobbyState;
    }
}
