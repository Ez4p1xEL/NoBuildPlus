package p1xel.nobuildplus.Listener;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import p1xel.nobuildplus.Hook.HRes;
import p1xel.nobuildplus.Storage.FlagsManager;
import p1xel.nobuildplus.Storage.Settings;
import p1xel.nobuildplus.Storage.Worlds;

public class NoBuildPlusBlockListener implements Listener {

    // Flag: break
    @EventHandler
    public void onBreak(BlockBreakEvent e) {

        String world = e.getPlayer().getWorld().getName();
        Player p = e.getPlayer();

        if (HRes.isInRes(e.getBlock())) {
            return;
        }

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

        if (HRes.isInRes(e.getBlock())) {
            return;
        }

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

    // Flag: leaf-decay
    @EventHandler
    public void onLeafDecay(LeavesDecayEvent e) {

        String world = e.getBlock().getWorld().getName();

        if (HRes.isInRes(e.getBlock())) {
            return;
        }

        if (FlagsManager.getFlagsIsEnabled("leaf-decay")) {

            if (Settings.getEnableWorldList().contains(world)) {

                if (!Worlds.getFlag(world, "leaf-decay")) {

                    e.setCancelled(true);

                }

            }
        }

    }

    // Flag: melt
    @EventHandler
    public void onBlockFade(BlockFadeEvent e) {

        String world = e.getBlock().getWorld().getName();

        if (HRes.isInRes(e.getBlock())) {
            return;
        }

        if (e.getBlock().getType() == Material.SNOW || e.getBlock().getType() == Material.ICE || e.getBlock().getType() == Material.PACKED_ICE) {

            if (FlagsManager.getFlagsIsEnabled("melt")) {

                if (Settings.getEnableWorldList().contains(world)) {

                    if (!Worlds.getFlag(world, "melt")) {

                        e.setCancelled(true);

                    }

                }
            }

        }
    }

}
