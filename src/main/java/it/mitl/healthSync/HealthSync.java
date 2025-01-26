package it.mitl.healthSync;

import it.mitl.healthSync.Listeners.Syncing;
import org.bukkit.plugin.java.JavaPlugin;

public final class HealthSync extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.getServer().getPluginManager().registerEvents(new Syncing(this), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
