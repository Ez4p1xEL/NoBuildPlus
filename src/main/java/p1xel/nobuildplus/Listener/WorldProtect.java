package p1xel.nobuildplus.Listener;

import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import p1xel.nobuildplus.Storage.FlagsManager;
import p1xel.nobuildplus.Storage.Settings;
import p1xel.nobuildplus.Storage.Worlds;

public class WorldProtect implements Listener {

    // Flag: break
    @EventHandler
    public void onBreak(BlockBreakEvent e) {

        String world = e.getPlayer().getWorld().getName();
        Player p = e.getPlayer();

        if (!FlagsManager.getFlagsIsEnabled("break")) {
            return;
        }

        if (!Settings.getEnableWorldList().contains(world)) {
            return;
        }

        if (Worlds.getFlag(world, "break")) {
            return;
        }

        if (p.hasPermission(Worlds.getPermission(world))) {
            return;
        }

        if (FlagsManager.getFlagsType("break").equalsIgnoreCase("all")) {
            p.sendMessage(Worlds.getDenyMessage(world));
            e.setCancelled(true);
        }

        if (FlagsManager.getFlagsType("break").equalsIgnoreCase("list")) {

            for (String blockname : FlagsManager.getFlagsList("break")) {
                Material block = Material.matchMaterial(blockname);
                if (block != null) {
                    if (e.getBlock().getType() == block) {
                        p.sendMessage(Worlds.getDenyMessage(world));
                        e.setCancelled(true);
                    }
                }
            }

        }

    }

    // Flag: Build
    @EventHandler
    public void onBuild(BlockPlaceEvent e) {

        String world = e.getPlayer().getWorld().getName();
        Player p = e.getPlayer();

        if (!FlagsManager.getFlagsIsEnabled("build")) {
            return;
        }

        if (!Settings.getEnableWorldList().contains(world)) {
            return;
        }

        if (Worlds.getFlag(world, "build")) {
            return;
        }

        if (p.hasPermission(Worlds.getPermission(world))) {
            return;
        }

        if (FlagsManager.getFlagsType("build").equalsIgnoreCase("all")) {
            p.sendMessage(Worlds.getDenyMessage(world));
            e.setCancelled(true);
        }

        if (FlagsManager.getFlagsType("build").equalsIgnoreCase("list")) {

            for (String blockname : FlagsManager.getFlagsList("build")) {
                Material block = Material.matchMaterial(blockname);
                if (block != null) {
                    if (e.getBlock().getType() == block) {
                        p.sendMessage(Worlds.getDenyMessage(world));
                        e.setCancelled(true);
                    }
                }
            }

        }

    }

    // Flag: Use
    // Flag: Container
    @EventHandler
    public void onInteract(PlayerInteractEvent e) {

        String world = e.getPlayer().getWorld().getName();
        Player p = e.getPlayer();

        // Flag: Use
        if (FlagsManager.getFlagsIsEnabled("use")) {

            if (Settings.getEnableWorldList().contains(world)) {

                if (!Worlds.getFlag(world, "use")) {

                    if (!p.hasPermission(Worlds.getPermission(world))) {

                        if (FlagsManager.getFlagsType("use").equalsIgnoreCase("list")) {

                            for (String name : FlagsManager.getFlagsList("use")) {
                                Material block = Material.matchMaterial(name);
                                if (block != null) {
                                    if (e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getClickedBlock().getType() == block) {
                                        p.sendMessage(Worlds.getDenyMessage(world));
                                        e.setCancelled(true);
                                    }
                                }

                            }

                        }
                    }
                }
            }

        }



        // Flag: Container
        if (FlagsManager.getFlagsIsEnabled("container")) {

            if (!Settings.getEnableWorldList().contains(world)) {
                return;
            }

            if (Worlds.getFlag(world, "container")) {
                return;
            }

            if (p.hasPermission(Worlds.getPermission(world))) {
                return;
            }

            if (FlagsManager.getFlagsType("container").equalsIgnoreCase("list")) {

                for (String name : FlagsManager.getFlagsList("container")) {
                    Material block = Material.matchMaterial(name);
                    if (block != null) {
                        if (e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getClickedBlock().getType() == block) {
                            p.sendMessage(Worlds.getDenyMessage(world));
                            e.setCancelled(true);
                        }
                    }

                }

            }

        }
    }

    // Flag: move
    @EventHandler
    public void onMove(PlayerMoveEvent e) {

        String world = e.getPlayer().getWorld().getName();
        Player p = e.getPlayer();

        if (!FlagsManager.getFlagsIsEnabled("move")) {
            return;
        }

        if (!Settings.getEnableWorldList().contains(world)) {
            return;
        }

        if (Worlds.getFlag(world, "move")) {
            return;
        }

        if (p.hasPermission(Worlds.getPermission(world))) {
            return;
        }

        if (!FlagsManager.getFlagsList("move").contains(p)) {
            p.sendMessage(Worlds.getDenyMessage(world));
            e.setCancelled(true);
        }

    }

    // Flag: Mob Damage
    // Flag: Pvp
    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {

        String world = e.getEntity().getWorld().getName();
        Entity p = e.getDamager();
        Entity target = e.getEntity();
        // Flag: Mob Damage
        if (FlagsManager.getFlagsIsEnabled("mob-damage")) {

            if (Settings.getEnableWorldList().contains(world)) {

                if (!Worlds.getFlag(world, "mob-damage")) {

                    if (!p.hasPermission(Worlds.getPermission(world))) {

                        if (FlagsManager.getFlagsType("mob-damage").equalsIgnoreCase("all")) {

                            if (p instanceof Player && !(target instanceof Player)) {

                                p.sendMessage(Worlds.getDenyMessage(world));
                                e.setCancelled(true);

                            }

                        }

                        if (FlagsManager.getFlagsType("mob-damage").equalsIgnoreCase("list")) {

                            if (p instanceof Player && !(target instanceof Player)) {

                                for (String string : FlagsManager.getFlagsList("mob-damage")) {

                                    if (e.getEntityType() == EntityType.valueOf(string)) {

                                        p.sendMessage(Worlds.getDenyMessage(world));
                                        e.setCancelled(true);

                                    }

                                }

                            }

                        }
                    }
                }
            }

        }

        // Flag: Pvp
        if (FlagsManager.getFlagsIsEnabled("pvp")) {

            if (!Settings.getEnableWorldList().contains(world)) {
                return;
            }

            if (Worlds.getFlag(world, "pvp")) {
                return;
            }

            if (p.hasPermission(Worlds.getPermission(world))) {
                return;
            }

            if (p instanceof Player && target instanceof Player) {

                p.sendMessage(Worlds.getDenyMessage(world));
                e.setCancelled(true);

            }

        }


    }

    // Flag: Mob Explode
    @EventHandler
    public void onMobExplode(EntityExplodeEvent e) {

        String world = e.getEntity().getWorld().getName();

        if (FlagsManager.getFlagsIsEnabled("mob-explode")) {

            if (Settings.getEnableWorldList().contains(world)) {

                if (!Worlds.getFlag(world, "mob-explode")) {

                        if (FlagsManager.getFlagsType("mob-explode").equalsIgnoreCase("all")) {

                                e.setCancelled(true);

                        }

                        if (FlagsManager.getFlagsType("mob-explode").equalsIgnoreCase("list")) {

                                for (String string : FlagsManager.getFlagsList("mob-explode")) {

                                    if (e.getEntityType() == EntityType.valueOf(string)) {

                                        e.setCancelled(true);

                                    }

                                }


                        }
                }
            }

        }

        // Flag: Tnt
        if (FlagsManager.getFlagsIsEnabled("tnt")) {

            if (Settings.getEnableWorldList().contains(world)) {

                if (!Worlds.getFlag(world, "tnt")) {

                    if (e.getEntityType() == EntityType.PRIMED_TNT || e.getEntityType() == EntityType.MINECART_TNT) {
                        e.setCancelled(true);
                    }

                }
            }

        }

    }

    // Flag: frame(move)
    @EventHandler
    public void onInteractEntity(PlayerInteractEntityEvent e) {

        String world = e.getPlayer().getWorld().getName();
        Player p = e.getPlayer();

        if (FlagsManager.getFlagsIsEnabled("frame")) {

            if (Settings.getEnableWorldList().contains(world)) {

                if (!Worlds.getFlag(world, "frame")) {

                    if (!p.hasPermission(Worlds.getPermission(world))) {

                        if (FlagsManager.FrameIsIncludingGlowFrame()) {

                            if (e.getRightClicked().getType() == EntityType.GLOW_ITEM_FRAME) {

                                p.sendMessage(Worlds.getDenyMessage(world));
                                e.setCancelled(true);

                            }

                        }

                        if (e.getRightClicked().getType() == EntityType.ITEM_FRAME) {

                            p.sendMessage(Worlds.getDenyMessage(world));
                            e.setCancelled(true);

                        }

                    }
                }

            }

        }
    }

    // Flag: frame (Place)
    @EventHandler
    public void onEntityPlace(EntitySpawnEvent e) {

        String world = e.getEntity().getWorld().getName();

        if (FlagsManager.getFlagsIsEnabled("frame")) {

            if (Settings.getEnableWorldList().contains(world)) {

                if (!Worlds.getFlag(world, "frame")) {

                    if (FlagsManager.FrameIsIncludingGlowFrame()) {

                        if (e.getEntityType() == EntityType.GLOW_ITEM_FRAME) {

                            e.setCancelled(true);

                        }

                    }

                    if (e.getEntityType() == EntityType.ITEM_FRAME) {

                        e.setCancelled(true);

                    }
                }

            }

        }

    }

    // Flag: frame (Break)
    @EventHandler
    public void onHangingBreakByEntity(HangingBreakByEntityEvent e) {

        String world = e.getEntity().getWorld().getName();

        if (FlagsManager.getFlagsIsEnabled("frame")) {

            if (Settings.getEnableWorldList().contains(world)) {

                if (!Worlds.getFlag(world, "frame")) {

                    if (FlagsManager.FrameIsIncludingGlowFrame()) {

                        if (e.getEntity() instanceof GlowItemFrame) {

                            e.setCancelled(true);

                        }

                    }

                    if (e.getEntity() instanceof ItemFrame) {

                        e.setCancelled(true);

                    }
                }

            }

        }

    }

    // Flag: Bed
    @EventHandler
    public void onBed(PlayerBedEnterEvent e) {

        String world = e.getPlayer().getWorld().getName();
        Player p = e.getPlayer();

        if (FlagsManager.getFlagsIsEnabled("bed")) {

            if (Settings.getEnableWorldList().contains(world)) {

                if (!Worlds.getFlag(world, "bed")) {

                    if (!p.hasPermission(Worlds.getPermission(world))) {

                        p.sendMessage(Worlds.getDenyMessage(world));
                        e.setCancelled(true);

                    }
                }

            }

        }

    }

    // Flag: Void Teleport
    @EventHandler
    public void onDamage(EntityDamageEvent e) {

        String world = e.getEntity().getWorld().getName();
        Entity entity = e.getEntity();

        if (e.getCause() == EntityDamageEvent.DamageCause.VOID) {
            if (FlagsManager.getFlagsIsEnabled("voidtp")) {
                if (Settings.getEnableWorldList().contains(world)) {
                    if (Worlds.getFlag(world, "voidtp")) {
                        if (Worlds.isSpawnLocationSet(world)) {
                            if (entity instanceof Player) {
                                Player p = (Player) e.getEntity();
                                p.teleport(Worlds.getSpawnLocation(world));
                                p.setFallDistance(0);
                            }
                        }
                    }
                }
            }
        }


    }

}
