package p1xel.nobuildplus.listener;

import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;
import p1xel.nobuildplus.Flags;
import p1xel.nobuildplus.hook.HookedPlugins;
import p1xel.nobuildplus.NoBuildPlus;
import p1xel.nobuildplus.world.ProtectedWorld;
import p1xel.nobuildplus.world.WorldManager;

public class NoBuildPlusHangingListener implements Listener {

    // Flag: frame (Break)
    @EventHandler(ignoreCancelled = true)
    public void onFrameBroke(HangingBreakByEntityEvent e) {

        Entity entity = e.getRemover();

        if (HookedPlugins.cancel(entity)) {
            return;
        }

        String worldName = entity.getWorld().getName();
        ProtectedWorld world = WorldManager.getWorld(worldName);

        if (!Flags.frame.isEnabled(world)) {
            return;
        }

        Player player = (Player) entity;
        if (player.hasPermission(world.getPermission())) {
            return;
        }


        if (v >= 17) {

            if (e.getEntity() instanceof GlowItemFrame) {

                WorldManager.sendMessage(player, world);
                e.setCancelled(true);
                return;

            }

        }

        if (e.getEntity() instanceof ItemFrame) {

            WorldManager.sendMessage(player, world);
            e.setCancelled(true);

        }
    }

    // Flag: painting
    @EventHandler(ignoreCancelled = true)
    public void onPaintingBroke(HangingBreakByEntityEvent e) {

        Entity entity = e.getRemover();

        if (HookedPlugins.cancel(entity)) {
            return;
        }

        if (!(entity instanceof Painting)) {
            return;
        }

        String worldName = entity.getWorld().getName();
        ProtectedWorld world = WorldManager.getWorld(worldName);

        if (!Flags.painting.isEnabled(world)) {
            return;
        }

        if (entity instanceof Player) {
            Player player = (Player) entity;
            if (player.hasPermission(world.getPermission())) {
                return;
            }

            WorldManager.sendMessage(player, world);
        }
        e.setCancelled(true);
    }

    int v = NoBuildPlus.getInstance().getBukkitVersion();

    // Flag: frame (being placed)
    @EventHandler(ignoreCancelled = true)
    public void onFramePlaced(HangingPlaceEvent e) {

        Entity entity = e.getEntity();

        if (HookedPlugins.cancel(entity)) {
            return;
        }

        String worldName = entity.getWorld().getName();
        ProtectedWorld world = WorldManager.getWorld(worldName);

        if (!Flags.frame.isEnabled(world)) {
            return;
        }

        Player player = e.getPlayer();

        if (player.hasPermission(world.getPermission())) {
            return;
        }

        EntityType type = entity.getType();

        if (v >= 17) {

            if (type == EntityType.GLOW_ITEM_FRAME) {

                WorldManager.sendMessage(player, world);
                e.setCancelled(true);
                return;

            }

        }

        if (type == EntityType.ITEM_FRAME) {

            WorldManager.sendMessage(player, world);
            e.setCancelled(true);

        }

    }

    // Flag: painting
    @EventHandler(ignoreCancelled = true)
    public void onPaintingPlaced(HangingPlaceEvent e) {

        Entity entity = e.getEntity();

        if (HookedPlugins.cancel(entity)) {
            return;
        }

        if (entity.getType() != EntityType.PAINTING) {
            return;
        }

        String worldName = entity.getWorld().getName();
        ProtectedWorld world = WorldManager.getWorld(worldName);

        if (!Flags.painting.isEnabled(world)) {
            return;
        }

        Player player = e.getPlayer();

        if (player.hasPermission(world.getPermission())) {
            return;
        }

        WorldManager.sendMessage(player, world);
        e.setCancelled(true);

    }

}
