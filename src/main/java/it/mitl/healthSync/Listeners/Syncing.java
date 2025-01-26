package it.mitl.healthSync;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Sync implements Listener {

    private final JavaPlugin plugin;
    private boolean isSyncing = false;

    public Sync(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntityDamageEvent(EntityDamageEvent event) {
        // Handle the event
        if (event.getEntity() instanceof Player) { // Check if the entity is a player
            double damage = event.getDamage(); // Set a variable to the damage
            for (Player player : Bukkit.getOnlinePlayers()) { // Loop through all online players
                if (!player.equals(event.getEntity())) { // Exclude the player who triggered the event
                    player.setHealth(player.getHealth() - damage); // Set the player's health to the current health minus the damage
                }
            }
        }
        event.setCancelled(true); // Cancel the event
    }

    @EventHandler
    public void onEntityRegainHealthEvent(EntityRegainHealthEvent event) {
        // Handle the event
        if (event.getEntity() instanceof Player) { // Check if the entity is a player
            double health = event.getAmount(); // Set a variable to the health
            for (Player player : Bukkit.getOnlinePlayers()) { // Loop through all online players
                player.setHealth(player.getHealth() + health); // Set the player's health to the current health plus the health
            }
        }
        event.setCancelled(true); // Cancel the event
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        // Handle the event
        // Pick a random online player and set the new player's health to that player's health
        Player player = Bukkit.getOnlinePlayers().stream().findAny().orElse(null);
        event.getPlayer().setHealth(player.getHealth());
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        // Handle the event
        if (isSyncing) return; // Check if the player is already dead
        isSyncing = true;
        for (Player player : Bukkit.getOnlinePlayers()) { // Loop through all online players
            player.setHealth(0); // Set the player's health to 0
        }
        isSyncing = false;
        // Send a message to all players stating who died
        plugin.getServer().broadcastMessage(ChatColor.RED + "Whoops! Looks like " + event.getEntity().getName() + " died!");
    }

}