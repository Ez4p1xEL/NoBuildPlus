package p1xel.nobuildplus.listener.version;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.TNTPrimeEvent;
import org.bukkit.event.player.PlayerHarvestBlockEvent;
import p1xel.nobuildplus.Flags;
import p1xel.nobuildplus.hook.HookedPlugins;
import p1xel.nobuildplus.world.ProtectedWorld;
import p1xel.nobuildplus.world.WorldManager;

public class NBPBlockListener_1_17 implements Listener {

    // Flag: berries
    @EventHandler(ignoreCancelled = true)
    public void onHarvest(PlayerHarvestBlockEvent e) {

        Block block = e.getHarvestedBlock();

        if (HookedPlugins.cancel(block)) {
            return;
        }

        String worldName = block.getWorld().getName();
        ProtectedWorld world = WorldManager.getWorld(worldName);

        if (!Flags.berries.isEnabled(world)) {
            return;
        }

        Player player = e.getPlayer();

        if (player.hasPermission(world.getPermission())) {
            return;
        }

        Material harvested = block.getType();

        if (harvested.equals(Material.matchMaterial("CAVE_VINES")) || harvested.equals(Material.matchMaterial("CAVE_VINES_PLANT"))) {
            WorldManager.sendMessage(player, world);
            e.setCancelled(true);
        }

    }

    // Flag: tnt-prime
    @EventHandler(ignoreCancelled = true)
    public void onPrimeTNT(TNTPrimeEvent e) {
        Block block = e.getBlock();
        if (HookedPlugins.cancel(block)) {
            return;
        }

        String worldName = block.getWorld().getName();
        ProtectedWorld world = WorldManager.getWorld(worldName);

        if (!Flags.tnt_prime.isEnabled(world)) {
            return;
        }

        Entity entity = e.getPrimingEntity();

        if (entity instanceof Player) {
            Player player = (Player) entity;
            if (player.hasPermission(world.getPermission())) {
                return;
            }

            WorldManager.sendMessage(player, world);
        }

        e.setCancelled(true);

    }

}
