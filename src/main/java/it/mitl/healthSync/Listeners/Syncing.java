package it.mitl.healthSync.Listeners;

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

public class Syncing implements Listener {

    private final JavaPlugin plugin;
    private boolean isDeathSyncing = false;

    public Syncing(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntityDamageEvent(EntityDamageEvent event) {
        // Handle the event
        if (event.getEntity() instanceof Player eventPlayer) { // Check if the entity is a player
            double damage = event.getDamage(); // Set a variable to the damage
            if (eventPlayer.getHealth() - damage <= 0) return; // Check if the player will die
            for (Player player : Bukkit.getOnlinePlayers()) { // Loop through all online players
                if (!player.equals(eventPlayer)) { // Exclude the player who triggered the event
                    player.setHealth(player.getHealth() - damage); // Set the player's health to the current health minus the damage
                }
            }
        }
    }

    @EventHandler
    public void onEntityRegainHealthEvent(EntityRegainHealthEvent event) {
        // Handle the event
        if (event.getEntity() instanceof Player eventPlayer) { // Check if the entity is a player
            double health = event.getAmount(); // Set a variable to the health
            for (Player player : Bukkit.getOnlinePlayers()) { // Loop through all online players
                if (!player.equals(eventPlayer)) { // Exclude the player who triggered the event
                    player.setHealth(player.getHealth() + health); // Set the player's health to the current health plus the health
                }
            }
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        // Handle the event
        if (Bukkit.getOnlinePlayers().size() <= 1) {
            // If the server is empty or only the joining player is online, do nothing
            return;
        }

        // Pick a random online player that is not the joining player
        Player joiningPlayer = event.getPlayer();
        Bukkit.getOnlinePlayers().stream()
                .filter(player -> !player.equals(joiningPlayer))
                .findAny().ifPresent(randomPlayer -> joiningPlayer.setHealth(randomPlayer.getHealth()));

    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        // Handle the event
        if (isDeathSyncing) return; // Check if a player is already dead
        isDeathSyncing = true;
        String deathMessage = event.getDeathMessage();
        if (deathMessage != null) {
            plugin.getServer().broadcastMessage(ChatColor.RED + deathMessage);
        }
        for (Player player : Bukkit.getOnlinePlayers()) { // Loop through all online players
            if (player.equals(event.getEntity())) continue; // Skip the player who died
            player.setHealth(0); // Set the player's health to 0
        }
        event.setDeathMessage(null); // Set the death message to null to prevent the default death message from being displayed
        isDeathSyncing = false; // Reset the death syncing flag
    }

}