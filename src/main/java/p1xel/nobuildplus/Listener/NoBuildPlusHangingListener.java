package p1xel.nobuildplus.Listener;

import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import p1xel.nobuildplus.Hook.HRes;
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

        if (HRes.isInRes(e.getEntity())) {
            return;
        }

        if (FlagsManager.getFlagsIsEnabled("frame")) {

            if (Settings.getEnableWorldList().contains(world)) {

                if (!Worlds.getFlag(world, "frame")) {

                        if (!p.hasPermission(Worlds.getPermission(world))) {

                            if (FlagsManager.FrameIsIncludingGlowFrame()) {

                                if (e.getEntity() instanceof GlowItemFrame) {

                                    if (p instanceof Player) {
                                        p.sendMessage(Worlds.getDenyMessage(world));
                                    }
                                    e.setCancelled(true);
                                    return;

                                }

                            }

                            if (e.getEntity() instanceof ItemFrame) {

                                if (p instanceof Player) {
                                    p.sendMessage(Worlds.getDenyMessage(world));
                                }
                                e.setCancelled(true);
                                return;

                            }
                        }

                }

            }
        }

        // Flag: Painting
        if (FlagsManager.getFlagsIsEnabled("painting")) {

            if (Settings.getEnableWorldList().contains(world)) {

                if (!Worlds.getFlag(world, "painting")) {

                    if (e.getEntity() instanceof Painting) {

                        if (p instanceof Player) {

                            if (!p.hasPermission(Worlds.getPermission(world))) {

                                p.sendMessage(Worlds.getDenyMessage(world));
                                e.setCancelled(true);

                            }


                        }

                    } else {
                        e.setCancelled(true);
                    }
                }

            }
        }

    }

    @EventHandler
    public void onHangingPlace(HangingPlaceEvent e) {

        String world = e.getEntity().getWorld().getName();
        Player p = e.getPlayer();

        if (HRes.isInRes(e.getEntity())) {
            return;
        }

        if (FlagsManager.getFlagsIsEnabled("frame")) {

            if (Settings.getEnableWorldList().contains(world)) {

                if (!Worlds.getFlag(world, "frame")) {

                    if (!p.hasPermission(Worlds.getPermission(world))) {

                        if (FlagsManager.FrameIsIncludingGlowFrame()) {

                            if (e.getEntity().getType() == EntityType.GLOW_ITEM_FRAME) {

                                p.sendMessage(Worlds.getDenyMessage(world));
                                e.setCancelled(true);

                            }

                        }

                        if (e.getEntity().getType() == EntityType.ITEM_FRAME) {

                            p.sendMessage(Worlds.getDenyMessage(world));
                            e.setCancelled(true);
                            return;

                        }
                    }
                }

            }

        }

        if (FlagsManager.getFlagsIsEnabled("painting")) {

            if (Settings.getEnableWorldList().contains(world)) {

                if (!Worlds.getFlag(world, "painting")) {

                    if (!p.hasPermission(Worlds.getPermission(world))) {

                        if (e.getEntity().getType() == EntityType.PAINTING) {

                            p.sendMessage(Worlds.getDenyMessage(world));
                            e.setCancelled(true);
                            return;

                        }
                    }
                }

            }

        }

        if (FlagsManager.getFlagsIsEnabled("boat")) {

            if (Settings.getEnableWorldList().contains(world)) {

                if (!Worlds.getFlag(world, "boat")) {

                    if (FlagsManager.BoatIsIncludingChestBoat()) {
                        if (e.getEntity() instanceof ChestBoat) {
                            e.setCancelled(true);
                            return;
                        }
                    }

                    if (e.getEntity() instanceof Boat) {
                        // 这个我弄不了给有权限的人放置
                        // 实在没有头绪了
                        e.setCancelled(true);
                    }


                }

            }

        }




    }

}
