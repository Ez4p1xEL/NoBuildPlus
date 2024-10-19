package p1xel.nobuildplus.Listener;

import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;
import p1xel.nobuildplus.Flags;
import p1xel.nobuildplus.Hook.Hooks;
import p1xel.nobuildplus.Storage.FlagsManager;
import p1xel.nobuildplus.Storage.Worlds;

public class NoBuildPlusHangingListener implements Listener {

    // Flag: frame (Break)
    @EventHandler
    public void onFrameBroke(HangingBreakByEntityEvent e) {

        Entity entity = e.getRemover();

        if (Hooks.cancel(entity)) {
            return;
        }

        String world = entity.getWorld().getName();

        if (!Flags.frame.isEnabled(world)) {
            return;
        }

        Player p = (Player) entity;
        if (p.hasPermission(Worlds.getPermission(world))) {
            return;
        }


        if (FlagsManager.FrameIsIncludingGlowFrame()) {

            if (e.getEntity() instanceof GlowItemFrame) {

                Worlds.sendMessage(p, world);
                e.setCancelled(true);
                return;

            }

        }

        if (e.getEntity() instanceof ItemFrame) {

            Worlds.sendMessage(p, world);
            e.setCancelled(true);

        }
    }

    // Flag: painting
    @EventHandler
    public void onPaintingBroke(HangingBreakByEntityEvent e) {

        Entity entity = e.getRemover();

        if (Hooks.cancel(entity)) {
            return;
        }

        if (!(entity instanceof Painting)) {
            return;
        }

        String world = entity.getWorld().getName();

        if (!Flags.painting.isEnabled(world)) {
            return;
        }

        if (entity instanceof Player) {
            Player p = (Player) entity;
            if (p.hasPermission(Worlds.getPermission(world))) {
                return;
            }

            Worlds.sendMessage(p, world);
        }
        e.setCancelled(true);
    }

    // Flag: frame (being placed)
    @EventHandler
    public void onFramePlaced(HangingPlaceEvent e) {

        Entity entity = e.getEntity();

        if (Hooks.cancel(entity)) {
            return;
        }

        String world = entity.getWorld().getName();

        if (!Flags.frame.isEnabled(world)) {
            return;
        }

        Player p = e.getPlayer();

        if (p.hasPermission(Worlds.getPermission(world))) {
            return;
        }

        EntityType type = entity.getType();

        if (FlagsManager.FrameIsIncludingGlowFrame()) {

            if (type == EntityType.GLOW_ITEM_FRAME) {

                Worlds.sendMessage(p, world);
                e.setCancelled(true);
                return;

            }

        }

        if (type == EntityType.ITEM_FRAME) {

            Worlds.sendMessage(p, world);
            e.setCancelled(true);

        }

    }

    // Flag: painting
    @EventHandler
    public void onPaintingPlaced(HangingPlaceEvent e) {

        Entity entity = e.getEntity();

        if (Hooks.cancel(entity)) {
            return;
        }

        if (entity.getType() != EntityType.PAINTING) {
            return;
        }

        String world = entity.getWorld().getName();

        if (!Flags.painting.isEnabled(world)) {
            return;
        }

        Player p = e.getPlayer();

        if (p.hasPermission(Worlds.getPermission(world))) {
            return;
        }

        Worlds.sendMessage(p, world);
        e.setCancelled(true);

    }

}
