package p1xel.nobuildplus.listener.version;

import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import p1xel.nobuildplus.Flags;
import p1xel.nobuildplus.hook.HookedPlugins;
import p1xel.nobuildplus.world.ProtectedWorld;
import p1xel.nobuildplus.world.WorldManager;

public class NBPBlockListener_1_8 implements Listener {

    // flag: tnt-prime
    // This event cannot get the player instance
    @EventHandler(ignoreCancelled = true)
    public void onExplode(ExplosionPrimeEvent e) {

        Entity entity = e.getEntity();
        if (HookedPlugins.cancel(entity)) {
            return;
        }

        String worldName = entity.getWorld().getName();
        ProtectedWorld world = WorldManager.getWorld(worldName);

        if (!Flags.tnt_prime.isEnabled(world)) {
            return;
        }

        e.setCancelled(true);

    }

}
