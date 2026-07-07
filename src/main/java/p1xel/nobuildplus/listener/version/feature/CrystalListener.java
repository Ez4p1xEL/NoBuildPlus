package p1xel.nobuildplus.listener.version.feature;

import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityPlaceEvent;
import org.bukkit.event.hanging.HangingBreakEvent;
import p1xel.nobuildplus.Flags;
import p1xel.nobuildplus.hook.HookedPlugins;
import p1xel.nobuildplus.listener.Flag;
import p1xel.nobuildplus.listener.version.FeatureListener;
import p1xel.nobuildplus.world.ProtectedWorld;
import p1xel.nobuildplus.world.WorldManager;

public class CrystalListener implements FeatureListener {
    @Override
    public String getName() {
        return "CrystalListener";
    }

    @Override
    public boolean matchRequirement(int[] version) {
        return (version[0] == 1 && version[1] >= 13) || version[0] > 1;
    }


    @Flag("crystal")
    @EventHandler(ignoreCancelled = true)
    public void onCrystalDestroyEntity(EntityDamageByEntityEvent event) {

        Entity entity = event.getEntity();
        if (HookedPlugins.cancel(entity)) {
            return;
        }

        Entity damager = event.getDamager();
        EntityType type = damager.getType();
        if (type != EntityType.END_CRYSTAL) {
            return;
        }

        String worldName = entity.getWorld().getName();
        ProtectedWorld world = WorldManager.getWorld(worldName);
        if (!Flags.crystal.isEnabled(world)) {
            return;
        }

        event.setCancelled(true);

    }

    @Flag("crystal")
    @EventHandler(ignoreCancelled = true)
    public void onCrystalBreak(HangingBreakEvent event) {

        Entity entity = event.getEntity();

        if (event.getCause() != HangingBreakEvent.RemoveCause.EXPLOSION) {
            return;
        }

        if (entity.getType() != EntityType.END_CRYSTAL) {
            return;
        }

        if (HookedPlugins.cancel(entity)) {
            return;
        }

        String worldName = entity.getWorld().getName();
        ProtectedWorld world = WorldManager.getWorld(worldName);

        if (!Flags.crystal.isEnabled(world)) {
            return;
        }

        event.setCancelled(true);
    }

    @Flag("crystal")
    @EventHandler(ignoreCancelled = true)
    public void onCrystalPlaced(EntityPlaceEvent e) {

        Entity entity = e.getEntity();

        if (HookedPlugins.cancel(entity)) {
            return;
        }

        if (!(entity instanceof EnderCrystal)) {
            return;
        }

        String worldName = entity.getWorld().getName();
        ProtectedWorld world = WorldManager.getWorld(worldName);

        if (!Flags.crystal.isEnabled(world)) {
            return;
        }

        Player player = e.getPlayer();
        if (player != null) {
            if (player.hasPermission(world.getPermission())) {
                return;
            }
            WorldManager.sendMessage(player, world);
        }

        e.setCancelled(true);
    }


}
