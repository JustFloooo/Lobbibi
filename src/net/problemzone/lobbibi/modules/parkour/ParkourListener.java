package net.problemzone.lobbibi.modules.parkour;

import net.problemzone.lobbibi.modules.LobbyManager;
import net.problemzone.lobbibi.wrapper.ListenerWrapper;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ParkourListener extends ListenerWrapper implements Listener {

    private static final int MAX_DISTANCE_TO_FAIL = 10;
    private static final int PORT_BACK_HEIGHT = 130;

    private final ParkourManager parkourManager;
    private final LobbyManager lobbyManager;

    public ParkourListener(ParkourManager parkourManager, LobbyManager lobbyManager) {
        this.parkourManager = parkourManager;
        this.lobbyManager = lobbyManager;
    }

    @EventHandler
    public void onPlayerParkour(PlayerMoveEvent e) {

        if (!isLobbyWorld(e.getPlayer().getWorld()) && lobbyManager.getLobbyState() == LobbyManager.LobbyState.RUNNING) return;

        Player player = e.getPlayer();
        if (e.getTo() == null) return;

        if (e.getTo().getBlockY() < PORT_BACK_HEIGHT) e.setTo(player.getWorld().getSpawnLocation());

        if (parkourManager.isStartBlock((e.getTo()).getBlock())) {
            e.setTo(parkourManager.newParkour(e.getPlayer()));
            return;
        }

        if (!parkourManager.hasPlayer(player)) return;
        if (e.getTo().getBlock().getRelative(BlockFace.DOWN).getLocation().equals(parkourManager.getPlayerTarget(player).getLocation())) {
            parkourManager.generateNewBlock(player);
            return;
        }

        if (e.getTo().distance(parkourManager.getPlayerTarget(player).getLocation()) > MAX_DISTANCE_TO_FAIL) {
            e.setTo(parkourManager.cancelParkour(player));
        }
    }

    @EventHandler
    public void onPlayerParkourQuit(PlayerQuitEvent e) {
        parkourManager.removePlayer(e.getPlayer());
    }

}
