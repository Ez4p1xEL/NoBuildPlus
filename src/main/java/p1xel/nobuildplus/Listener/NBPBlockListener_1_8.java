package p1xel.nobuildplus.Listener;

import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import p1xel.nobuildplus.Flags;
import p1xel.nobuildplus.Hook.HookedPlugins;

public class NBPBlockListener_1_8 implements Listener {

    // flag: tnt-prime
    // This event cannot get the player instance
    @EventHandler
    public void onExplode(ExplosionPrimeEvent e) {

        Entity entity = e.getEntity();
        if (HookedPlugins.cancel(entity)) {
            return;
        }

        String world = entity.getWorld().getName();

        if (!Flags.tnt_prime.isEnabled(world)) {
            return;
        }

        e.setCancelled(true);

    }

}
