package p1xel.nobuildplus.Listener;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import p1xel.nobuildplus.Flags;
import p1xel.nobuildplus.Hook.Hooks;
import p1xel.nobuildplus.Storage.Worlds;

public class NBPEntityListener_1_9 implements Listener {

    // Flag: elytra
    @EventHandler
    public void onGlide(EntityToggleGlideEvent e) {

        Entity entity = e.getEntity();

        if (!(entity instanceof Player)) {
            return;
        }

        if (Hooks.cancel(entity)) {
            return;
        }

        String world = entity.getWorld().getName();

        if (!Flags.elytra.isEnabled(world)) {
            return;
        }

        Player p = (Player) entity;

        if (p.hasPermission(Worlds.getPermission(world))) {
            return;
        }


        if (e.isGliding()) {
            if (p.getInventory().getChestplate().getType() == Material.ELYTRA) {

                if (Worlds.isDenyMessageExist(world)) {
                    p.sendMessage(Worlds.getDenyMessage(world));
                }
                e.setCancelled(true);

            }

        }


    }
}
