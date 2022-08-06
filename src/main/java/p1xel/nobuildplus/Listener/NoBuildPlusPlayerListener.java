package p1xel.nobuildplus.Listener;

import org.bukkit.Material;
import org.bukkit.entity.Boat;
import org.bukkit.entity.ChestBoat;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.*;
import p1xel.nobuildplus.Hook.HRes;
import p1xel.nobuildplus.Storage.FlagsManager;
import p1xel.nobuildplus.Storage.Settings;
import p1xel.nobuildplus.Storage.Worlds;

public class NoBuildPlusPlayerListener implements Listener {

    // Flag: Use
    // Flag: Container
    @EventHandler
    public void onInteract(PlayerInteractEvent e) {

        String world = e.getPlayer().getWorld().getName();
        Player p = e.getPlayer();

        if (e.getClickedBlock() != null) {
            if (HRes.isInRes(e.getClickedBlock())) {
                return;
            }
        }

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

        if (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR) {

            if (e.getItem() != null) {

                for (String key : FlagsManager.getFlagsList("boat")) {

                    if (e.getItem().getType() == Material.matchMaterial(key)) {
                        if (FlagsManager.getFlagsIsEnabled("boat")) {

                            if (Settings.getEnableWorldList().contains(world)) {

                                if (!Worlds.getFlag(world, "boat")) {

                                    if (!p.hasPermission(Worlds.getPermission(world))) {

                                        p.sendMessage(Worlds.getDenyMessage(world));
                                        e.setCancelled(true);
                                        return;

                                    }
                                }
                            }

                        }
                    }
                }
            }
        }

        // Flag: farmbreak
        if (e.getAction() == Action.PHYSICAL) {
                if (FlagsManager.getFlagsIsEnabled("farmbreak")) {

                    if (Settings.getEnableWorldList().contains(world)) {

                        if (!Worlds.getFlag(world, "farmbreak")) {

                            if (e.getClickedBlock().getType() == Material.matchMaterial("SOIL") || e.getClickedBlock().getType() == Material.matchMaterial("FARMLAND")) {
                                    e.setCancelled(true);
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

    @EventHandler
    public void onInteractEntity(PlayerInteractEntityEvent e) {

        String world = e.getPlayer().getWorld().getName();
        Player p = e.getPlayer();

        // Flag: boat (Use)
                if (FlagsManager.getFlagsIsEnabled("boat")) {

                    if (Settings.getEnableWorldList().contains(world)) {

                        if (!Worlds.getFlag(world, "boat")) {

                            if (!p.hasPermission(Worlds.getPermission(world))) {

                                if (e.getRightClicked() instanceof Boat) {

                                    p.sendMessage(Worlds.getDenyMessage(world));
                                    e.setCancelled(true);
                                    return;
                                }

                                if (FlagsManager.BoatIsIncludingChestBoat()) {
                                    if (e.getRightClicked() instanceof ChestBoat) {
                                        p.sendMessage(Worlds.getDenyMessage(world));
                                        e.setCancelled(true);
                                        return;
                                    }
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

        if (HRes.isInRes(p)) {
            return;
        }

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

    // Flag: Bed
    @EventHandler
    public void onBed(PlayerBedEnterEvent e) {

        String world = e.getPlayer().getWorld().getName();
        Player p = e.getPlayer();

        if (HRes.isInRes(p)) {
            return;
        }

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

    // Flag: Chat
    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {

        String world = e.getPlayer().getWorld().getName();
        Player p = e.getPlayer();

        if (HRes.isInRes(p)) {
            return;
        }

        if (FlagsManager.getFlagsIsEnabled("chat")) {

            if (Settings.getEnableWorldList().contains(world)) {

                if (!Worlds.getFlag(world, "chat")) {

                    if (!p.hasPermission(Worlds.getPermission(world))) {

                        p.sendMessage(Worlds.getDenyMessage(world));
                        e.setCancelled(true);

                    }
                }

            }
        }

    }

    // Flag: Command
    @EventHandler
    public void onCommandExecute(PlayerCommandPreprocessEvent e) {

        String world = e.getPlayer().getWorld().getName();
        Player p = e.getPlayer();

        if (HRes.isInRes(p)) {
            return;
        }

        if (FlagsManager.getFlagsIsEnabled("command")) {

            if (Settings.getEnableWorldList().contains(world)) {

                if (!Worlds.getFlag(world, "command")) {

                    if (!p.hasPermission(Worlds.getPermission(world))) {

                        if (FlagsManager.getFlagsType("command").equalsIgnoreCase("all")) {
                            p.sendMessage(Worlds.getDenyMessage(world));
                            e.setCancelled(true);
                        }

                        if (FlagsManager.getFlagsType("command").equalsIgnoreCase("list")) {
                            for (String cmd : FlagsManager.getFlagsList("command")) {
                                if (e.getMessage().equalsIgnoreCase("/" + cmd)) {
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

    @EventHandler
    public void ArmorStand(PlayerArmorStandManipulateEvent e) {

        String world = e.getPlayer().getWorld().getName();
        Player p = e.getPlayer();

        if (HRes.isInRes(p)) {
            return;
        }

        if (FlagsManager.getFlagsIsEnabled("armorstand")) {

            if (Settings.getEnableWorldList().contains(world)) {

                if (!Worlds.getFlag(world, "armorstand")) {

                    if (!p.hasPermission(Worlds.getPermission(world))) {

                        p.sendMessage(Worlds.getDenyMessage(world));
                        e.setCancelled(true);

                    }

                }

            }

        }

    }

    @EventHandler
    public void onBucketUse(PlayerBucketEmptyEvent e) {

        String world = e.getPlayer().getWorld().getName();
        Player p = e.getPlayer();

        if (HRes.isInRes(p)) {
            return;
        }

        if (FlagsManager.getFlagsIsEnabled("bucket-place")) {

            if (Settings.getEnableWorldList().contains(world)) {

                if (!Worlds.getFlag(world, "bucket-place")) {

                    if (!p.hasPermission(Worlds.getPermission(world))) {

                        p.sendMessage(Worlds.getDenyMessage(world));
                        e.setCancelled(true);

                    }

                }

            }

        }

    }

    @EventHandler
    public void onBucketFill(PlayerBucketFillEvent e) {

        String world = e.getPlayer().getWorld().getName();
        Player p = e.getPlayer();

        if (HRes.isInRes(p)) {
            return;
        }

        if (FlagsManager.getFlagsIsEnabled("bucket-fill")) {

            if (Settings.getEnableWorldList().contains(world)) {

                if (!Worlds.getFlag(world, "bucket-fill")) {

                    if (!p.hasPermission(Worlds.getPermission(world))) {

                        p.sendMessage(Worlds.getDenyMessage(world));
                        e.setCancelled(true);

                    }

                }

            }

        }

    }

}
