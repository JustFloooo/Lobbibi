package net.problemzone.lobbibi;

import net.problemzone.lobbibi.modules.parkour.ParkourListener;
import net.problemzone.lobbibi.modules.parkour.ParkourManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private final ParkourManager parkourManager = new ParkourManager();

    @Override
    public void onEnable() {
        getLogger().info("Loading Lobbibi Plugin.");

        //getLogger().info("Loading Lobbibi Commands.");
        //registerCommands();

        getLogger().info("Loading Lobbibi Listeners.");
        registerListeners();

        getLogger().info("Lobbibi primed and ready.");
    }

    @Override
    public void onDisable() {
        parkourManager.removeAllBlocks();
    }

    private void registerListeners() {
        //Event Listeners
        getServer().getPluginManager().registerEvents(new ParkourListener(parkourManager), this);
    }

}
