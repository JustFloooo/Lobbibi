package net.problemzone.lobbibi.wrapper;

import org.bukkit.Bukkit;
import org.bukkit.World;

public class ListenerWrapper {

    private static final String LOBBY_NAME = "Lobby";

    protected boolean isLobbyWorld(World world){
        World bukkitWorld = Bukkit.getWorld(LOBBY_NAME);
        if (bukkitWorld == null) return false;
        return world.getUID().equals(bukkitWorld.getUID());
    }

}
