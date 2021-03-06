package net.zerotoil.cyberworldreset.events;

import net.zerotoil.cyberworldreset.CyberWorldReset;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import static org.bukkit.event.entity.EntityDamageEvent.DamageCause.*;

public class OnDamage implements Listener {

    private CyberWorldReset main;
    private boolean enabled;

    public OnDamage(CyberWorldReset main) {
        this.main = main;
        Bukkit.getPluginManager().registerEvents(this, main);
        main.addEvent();
        enabled = false;
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        // fixes suffocation bug on 1.8/1.9
        if (main.getVersion() > 9) return;
        if (!main.config().isSuffocationFix()) return;
        if (!enabled) return;
        if (!(event.getEntity() instanceof Player)) return;
        if (event.getEntity().getWorld().getEnvironment() == World.Environment.NETHER) return; // ignore nether top
        if ((event.getCause() != SUFFOCATION) && (event.getCause() != LAVA) && (event.getCause() != FIRE) && (event.getCause() != FIRE_TICK)) return;
        Player player = (Player) event.getEntity();
        World world = player.getWorld();
        Location location = world.getSpawnLocation();
        double preX = location.getX() - 1, preZ = location.getZ() - 1, postX = location.getX() + 1, postZ = location.getZ() + 1;
        double playerX = player.getLocation().getX(), playerZ = player.getLocation().getZ();
        if ((playerX < postX) && (playerX > preX) && (playerZ < postZ) && (playerZ > preZ)) {
            player.teleport(new Location(world, location.getBlockX(), world.getHighestBlockYAt(location.getBlockX(), location.getBlockZ()), location.getZ()));
            event.setCancelled(true);
        }
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

}
