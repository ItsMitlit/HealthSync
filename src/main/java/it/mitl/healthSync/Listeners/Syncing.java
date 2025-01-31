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
    private String deathGuiltyPlayer = "";

    public Syncing(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntityDamageEvent(EntityDamageEvent event) {
        // Handle the event
        if (event.getEntity() instanceof Player eventPlayer) { // Check if the entity is a player
            if (eventPlayer.isDead()) return; // Ignore the event if the player is dead
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
            if (eventPlayer.isDead()) return; // Ignore the event if the player is dead
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
        if (Bukkit.getOnlinePlayers().size() <= 1) { // Check if the server is empty
            return; // Return if the server is empty
        }

        Player joiningPlayer = event.getPlayer(); // Create a variable to store the joining player
        Bukkit.getOnlinePlayers().stream() // Get a stream of online players
                .filter(player -> !player.equals(joiningPlayer)) // Exclude the joining player
                .findAny().ifPresent(randomPlayer -> joiningPlayer.setHealth(randomPlayer.getHealth())); // Set the joining player's health to the random player's health

    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        // Handle the event
        if (isDeathSyncing) {
            // Check if a player is already dead
            event.setDeathMessage(event.getEntity().getPlayer().getName() + " died because of " + deathGuiltyPlayer); // Change the death message to mention the player who caused the death.
            return; // Return in order not to cause a death loop.
        }
        isDeathSyncing = true; // Set the death syncing flag to true
        deathGuiltyPlayer = event.getEntity().getPlayer().getName(); // Set the death guilty player
        String deathMessage = event.getDeathMessage(); // Create a variable to store the death message
        if (deathMessage != null) { // Check if the death message is not null
            plugin.getServer().broadcastMessage(ChatColor.RED + deathMessage); // Broadcast the death message in red
        }
        for (Player player : Bukkit.getOnlinePlayers()) { // Loop through all online players
            if (player.equals(event.getEntity())) continue; // Skip the player who died
            player.setHealth(0); // Set the player's health to 0
        }
        event.setDeathMessage(null); // Set the death message to null to prevent the default death message from being displayed
        isDeathSyncing = false; // Reset the death syncing flag
    }

}