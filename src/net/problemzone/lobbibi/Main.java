package net.problemzone.lobbibi;

import net.problemzone.lobbibi.modules.LobbyListener;
import net.problemzone.lobbibi.modules.LobbyManager;
import net.problemzone.lobbibi.modules.WorldProtectionListener;
import net.problemzone.lobbibi.modules.commands.cancel;
import net.problemzone.lobbibi.modules.commands.start;
import net.problemzone.lobbibi.modules.parkour.ParkourListener;
import net.problemzone.lobbibi.modules.parkour.ParkourManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class Main extends JavaPlugin {

    private static JavaPlugin javaPlugin;

    private final ParkourManager parkourManager = new ParkourManager();
    private final LobbyManager lobbyManager = new LobbyManager();

    public static JavaPlugin getJavaPlugin() {
        return javaPlugin;
    }

    @Override
    public void onEnable() {
        getLogger().info("Loading Lobbibi Plugin.");
        initiatePlugin();

        getLogger().info("Loading Lobbibi Commands.");
        registerCommands();

        getLogger().info("Loading Lobbibi Listeners.");
        registerListeners();

        getLogger().info("Lobbibi primed and ready.");
    }

    private void initiatePlugin() {
        javaPlugin = this;
    }

    private void registerCommands() {
        //Commands
        Objects.requireNonNull(getCommand("start")).setExecutor(new start(lobbyManager));
        Objects.requireNonNull(getCommand("cancel")).setExecutor(new cancel(lobbyManager));
    }

    private void registerListeners() {
        //Event Listeners
        getServer().getPluginManager().registerEvents(new WorldProtectionListener(), this);
        getServer().getPluginManager().registerEvents(new ParkourListener(parkourManager, lobbyManager), this);
        getServer().getPluginManager().registerEvents(new LobbyListener(lobbyManager), this);
    }

    @Override
    public void onDisable() {
        parkourManager.removeAllBlocks();
    }

}
