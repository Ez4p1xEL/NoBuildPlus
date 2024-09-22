package p1xel.nobuildplus.Listener;

import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;
import p1xel.nobuildplus.Hook.Hooks;
import p1xel.nobuildplus.Storage.FlagsManager;
import p1xel.nobuildplus.Storage.Settings;
import p1xel.nobuildplus.Storage.Worlds;

public class NoBuildPlusHangingListener implements Listener {

    // Flag: frame (Break)
    // Flag: Armor Stand (Break)
    @EventHandler
    public void onHangingBreakByEntity(HangingBreakByEntityEvent e) {

        String world = e.getEntity().getWorld().getName();
        Entity p = e.getRemover();

        if (Hooks.cancel(e.getEntity())) {
            return;
        }

        if (Settings.canExecute(world, "frame")) {

            if (!Worlds.getFlag(world, "frame")) {

                if (!p.hasPermission(Worlds.getPermission(world))) {

                    if (FlagsManager.FrameIsIncludingGlowFrame()) {

                        if (e.getEntity() instanceof GlowItemFrame) {

                            if (p instanceof Player) {
                                if (Worlds.isDenyMessageExist(world)) {
                                    p.sendMessage(Worlds.getDenyMessage(world));
                                }
                            }
                            e.setCancelled(true);
                            return;

                        }

                    }

                    if (e.getEntity() instanceof ItemFrame) {

                        if (p instanceof Player) {
                            if (Worlds.isDenyMessageExist(world)) {
                                p.sendMessage(Worlds.getDenyMessage(world));
                            }
                        }
                        e.setCancelled(true);
                        return;

                    }
                }

            }
        }

        // Flag: Painting
        if (Settings.canExecute(world, "painting")) {

            if (!Worlds.getFlag(world, "painting")) {

                if (e.getEntity() instanceof Painting) {

                    if (p instanceof Player) {

                        if (!p.hasPermission(Worlds.getPermission(world))) {

                            if (Worlds.isDenyMessageExist(world)) {
                                p.sendMessage(Worlds.getDenyMessage(world));
                            }
                            e.setCancelled(true);

                        }


                    }

                } else {
                    e.setCancelled(true);
                }
            }
        }

    }

    @EventHandler
    public void onHangingPlace(HangingPlaceEvent e) {

        String world = e.getEntity().getWorld().getName();
        Player p = e.getPlayer();

        if (Hooks.cancel(e.getEntity())) {
            return;
        }

        if (Settings.canExecute(world, "frame")) {

            if (!Worlds.getFlag(world, "frame")) {

                if (!p.hasPermission(Worlds.getPermission(world))) {

                    if (FlagsManager.FrameIsIncludingGlowFrame()) {

                        if (e.getEntity().getType() == EntityType.GLOW_ITEM_FRAME) {

                            if (Worlds.isDenyMessageExist(world)) {
                                p.sendMessage(Worlds.getDenyMessage(world));
                            }
                            e.setCancelled(true);

                        }

                    }

                    if (e.getEntity().getType() == EntityType.ITEM_FRAME) {

                        if (Worlds.isDenyMessageExist(world)) {
                            p.sendMessage(Worlds.getDenyMessage(world));
                        }
                        e.setCancelled(true);
                        return;

                    }
                }
            }


        }

        if (Settings.canExecute(world, "painting")) {

            if (!Worlds.getFlag(world, "painting")) {

                if (!p.hasPermission(Worlds.getPermission(world))) {

                    if (e.getEntity().getType() == EntityType.PAINTING) {

                        if (Worlds.isDenyMessageExist(world)) {
                            p.sendMessage(Worlds.getDenyMessage(world));
                        }
                        e.setCancelled(true);
                        return;

                    }
                }

            }

        }





    }

}
