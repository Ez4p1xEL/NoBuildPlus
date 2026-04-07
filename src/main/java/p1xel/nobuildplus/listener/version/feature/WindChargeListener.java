package p1xel.nobuildplus.listener.version.feature;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.WindCharge;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import p1xel.nobuildplus.Flags;
import p1xel.nobuildplus.hook.HookedPlugins;
import p1xel.nobuildplus.listener.version.FeatureListener;
import p1xel.nobuildplus.world.ProtectedWorld;
import p1xel.nobuildplus.world.WorldManager;

public class WindChargeListener implements FeatureListener {

    // flag: wind-charge
    @EventHandler(ignoreCancelled = true)
    public void onInteractWindCharge(PlayerInteractEvent event) {
        System.out.println("wind-charge");

        Player player = event.getPlayer();

        if (HookedPlugins.cancel(player)) {
            return;
        }

        ItemStack item = event.getItem();
        if (item == null || item.getType() != Material.WIND_CHARGE) {
            return;
        }

        if (!(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
            return;
        }

        String worldName = player.getWorld().getName();
        ProtectedWorld world = WorldManager.getWorld(worldName);
        if (!Flags.wind_charge.isEnabled(world)) {
            return;
        }

        if (player.hasPermission(world.getPermission())) {
            return;
        }

        WorldManager.sendMessage(player, world);
        event.setCancelled(true);

    }

    // This listener is used to prevent the explosion of wind charge causing by non-player object.
    @EventHandler(ignoreCancelled = true)
    public void onWindChargeExplosion(ExplosionPrimeEvent event) {

        Entity entity = event.getEntity();

        if (HookedPlugins.cancel(entity)) {
            return;
        }

        if (!(entity instanceof WindCharge)) {
            return;
        }

        String worldName = entity.getWorld().getName();
        ProtectedWorld world = WorldManager.getWorld(worldName);
        if (!Flags.wind_charge.isEnabled(world)) {
            return;
        }

        event.setCancelled(true);

    }

    @EventHandler(ignoreCancelled = true)
    public void onWindChargeThrow(ProjectileLaunchEvent event) {

        Entity entity = event.getEntity();

        if (HookedPlugins.cancel(entity)) {
            return;
        }

        if (!(entity instanceof WindCharge)) {
            return;
        }

        String worldName = entity.getWorld().getName();
        ProtectedWorld world = WorldManager.getWorld(worldName);
        if (!Flags.wind_charge.isEnabled(world)) {
            return;
        }

        event.setCancelled(true);

    }

    @Override
    public String getName() {
        return "WindChargeListener 1.21+";
    }

    @Override
    public boolean matchRequirement(int[] version) {
        return (version[0] == 1 && version[1] >= 21) || version[0] > 1;
    }
}
