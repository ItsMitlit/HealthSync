package it.mitl.healthSync;

import it.mitl.healthSync.Listeners.Syncing;
import org.bstats.bukkit.Metrics;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class HealthSync extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic

        // Configuration Setup
        saveDefaultConfig();
        FileConfiguration config = getConfig();

        // bStats
        int pluginId = 24929;
        Metrics metrics = new Metrics(this, pluginId);

        this.getServer().getPluginManager().registerEvents(new Syncing(this), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
