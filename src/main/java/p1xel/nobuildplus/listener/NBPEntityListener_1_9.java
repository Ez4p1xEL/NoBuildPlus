package p1xel.nobuildplus.listener;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import p1xel.nobuildplus.Flags;
import p1xel.nobuildplus.hook.HookedPlugins;
import p1xel.nobuildplus.world.ProtectedWorld;
import p1xel.nobuildplus.world.WorldManager;

public class NBPEntityListener_1_9 implements Listener {

    // Flag: elytra
    @EventHandler(ignoreCancelled = true)
    public void onGlide(EntityToggleGlideEvent e) {

        Entity entity = e.getEntity();

        if (!(entity instanceof Player)) {
            return;
        }

        if (HookedPlugins.cancel(entity)) {
            return;
        }

        String worldName = entity.getWorld().getName();
        ProtectedWorld world = WorldManager.getWorld(worldName);

        if (!Flags.elytra.isEnabled(world)) {
            return;
        }

        Player player = (Player) entity;

        if (player.hasPermission(world.getPermission())) {
            return;
        }


        if (e.isGliding()) {
            if (player.getInventory().getChestplate().getType() == Material.ELYTRA) {

                WorldManager.sendMessage(player, world);
                e.setCancelled(true);

            }

        }


    }
}
