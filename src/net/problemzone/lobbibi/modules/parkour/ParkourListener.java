package net.problemzone.lobbibi.modules.parkour;

import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class ParkourListener implements Listener {

    private static final int MAX_DISTANCE_TO_FAIL = 10;
    private static final int PORT_BACK_HEIGHT = 100; //vorher 130

    private final ParkourManager parkourManager;

    public ParkourListener(ParkourManager parkourManager) {
        this.parkourManager = parkourManager;
    }

    @EventHandler
    public void onPlayerParkour(PlayerMoveEvent e) {

        Player player = e.getPlayer();
        if(!player.getWorld().getName().equals("Lobby")) return;
        if (e.getTo() == null) return;

        if(e.getTo().getBlockY() < PORT_BACK_HEIGHT) e.setTo(player.getWorld().getSpawnLocation());

        if(parkourManager.isStartBlock((e.getTo()).getBlock())){
            e.setTo(parkourManager.newParkour(e.getPlayer()));
            return;
        }

        if (!parkourManager.hasPlayer(player)) return;
        if(e.getTo().getBlock().getRelative(BlockFace.DOWN).getLocation().equals(parkourManager.getPlayerTarget(player).getLocation())){
            parkourManager.generateNewBlock(player);
            return;
        }

        if (e.getTo().distance(parkourManager.getPlayerTarget(player).getLocation()) > MAX_DISTANCE_TO_FAIL){
            e.setTo(parkourManager.cancelParkour(player));
        }
    }

}
