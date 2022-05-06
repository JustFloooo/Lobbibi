package net.problemzone.lobbibi.modules;

import net.problemzone.lobbibi.wrapper.ListenerWrapper;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.hanging.HangingBreakEvent;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class WorldProtectionListener extends ListenerWrapper implements Listener {

    @EventHandler
    //Cancels Block Breaks
    public void onBlockBreak(BlockBreakEvent e) {
        if (isLobbyWorld(e.getPlayer().getWorld())) e.setCancelled(true);
    }

    @EventHandler
    //Cancels Block Placing
    public void onBlockPlace(BlockPlaceEvent e) {
        if (isLobbyWorld(e.getPlayer().getWorld())) e.setCancelled(true);
    }

    @EventHandler
    //Cancels all Interactions
    public void onPlayerInteract(PlayerInteractEvent e) {
        if (isLobbyWorld(e.getPlayer().getWorld())) e.setUseInteractedBlock(Event.Result.DENY);
    }

    @EventHandler
    //Cancels all Block Changes
    public void onEntityBlockChange(EntityChangeBlockEvent e) {
        if (isLobbyWorld(e.getEntity().getWorld())) e.setCancelled(true);
    }

    @EventHandler
    //Cancel Hunger
    public void onHungerDrain(FoodLevelChangeEvent e) {
        if (isLobbyWorld(e.getEntity().getWorld())) e.setCancelled(true);
    }

    @EventHandler
    //Cancel Entity Spawns
    public void onEntitySpawn(CreatureSpawnEvent e) {
        if (isLobbyWorld(e.getEntity().getWorld())) e.setCancelled(true);
    }

    @EventHandler
    //Cancel Entity Interacts
    public void onEntityInteract(PlayerInteractEntityEvent e) {
        if (isLobbyWorld(e.getPlayer().getWorld())) e.setCancelled(true);
    }

    @EventHandler
    //Cancel Armor Stands
    public void onArmorStandInteract(PlayerArmorStandManipulateEvent e) {
        if (isLobbyWorld(e.getPlayer().getWorld())) e.setCancelled(true);
    }

    @EventHandler
    //Cancel Paintings and Item Frames
    public void onHangingEntityBreak(HangingBreakEvent e){
        if (isLobbyWorld(e.getEntity().getWorld())) e.setCancelled(true);
    }

    @EventHandler
    //Cancel all Damage
    public void preGameDamageListener(EntityDamageEvent e) {
        if (isLobbyWorld(e.getEntity().getWorld())) e.setCancelled(true);
    }
}
