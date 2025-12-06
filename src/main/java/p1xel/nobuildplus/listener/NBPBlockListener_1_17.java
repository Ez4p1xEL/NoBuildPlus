package p1xel.nobuildplus.listener;

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
import p1xel.nobuildplus.storage.Worlds;

public class NBPBlockListener_1_17 implements Listener {

    // Flag: berries
    @EventHandler
    public void onHarvest(PlayerHarvestBlockEvent e) {

        Block block = e.getHarvestedBlock();

        if (HookedPlugins.cancel(block)) {
            return;
        }

        String world = block.getWorld().getName();

        if (!Flags.berries.isEnabled(world)) {
            return;
        }

        Player p = e.getPlayer();

        if (p.hasPermission(Worlds.getPermission(world))) {
            return;
        }

        Material harvested = block.getType();

        if (harvested.equals(Material.matchMaterial("CAVE_VINES")) || harvested.equals(Material.matchMaterial("CAVE_VINES_PLANT"))) {
            Worlds.sendMessage(p, world);
            e.setCancelled(true);
        }

    }

    // Flag: tnt-prime
    @EventHandler
    public void onPrimeTNT(TNTPrimeEvent e) {
        Block block = e.getBlock();
        if (HookedPlugins.cancel(block)) {
            return;
        }

        String world = block.getWorld().getName();

        if (!Flags.tnt_prime.isEnabled(world)) {
            return;
        }

        Entity entity = e.getPrimingEntity();

        if (entity instanceof Player) {
            Player p = (Player) entity;
            if (p.hasPermission(Worlds.getPermission(world))) {
                return;
            }

            Worlds.sendMessage(p, world);
        }

        e.setCancelled(true);

    }

}
