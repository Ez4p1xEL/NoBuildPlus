package p1xel.nobuildplus.Listener;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
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
        String flag = "break";

        if (HRes.isInRes(e.getBlock())) {
            return;
        }

        if (!Settings.canExecute(world, flag)) {
            return;
        }

        if (Worlds.getFlag(world, flag)) {
            return;
        }

        if (p.hasPermission(Worlds.getPermission(world))) {
            return;
        }

        if (FlagsManager.getFlagsType(flag).equalsIgnoreCase("all")) {
            if (Worlds.isDenyMessageExist(world)) {
                p.sendMessage(Worlds.getDenyMessage(world));
            }
            e.setCancelled(true);
        }

        if (FlagsManager.getFlagsType(flag).equalsIgnoreCase("list")) {

            Block block = e.getBlock();
            Material mat = block.getType();
            if (FlagsManager.getFlagsList("break").contains(mat.toString().toUpperCase())) {
                if (Worlds.isDenyMessageExist(world)) {
                    p.sendMessage(Worlds.getDenyMessage(world));
                }
                e.setCancelled(true);
            }
        }

    }


    // Flag: Build
    @EventHandler
    public void onBuild(BlockPlaceEvent e) {

        String world = e.getPlayer().getWorld().getName();
        Player p = e.getPlayer();
        String flag = "build";

        if (HRes.isInRes(e.getBlock())) {
            return;
        }

        if (!Settings.canExecute(world, flag)) {
            return;
        }

        if (Worlds.getFlag(world, flag)) {
            return;
        }

        if (p.hasPermission(Worlds.getPermission(world))) {
            return;
        }

        if (FlagsManager.getFlagsType(flag).equalsIgnoreCase("all")) {
            if (Worlds.isDenyMessageExist(world)) {
                p.sendMessage(Worlds.getDenyMessage(world));
            }
            e.setCancelled(true);
        }

        if (FlagsManager.getFlagsType(flag).equalsIgnoreCase("list")) {

            Material mat = e.getBlock().getType();

            if (FlagsManager.getFlagsList(flag).contains(mat.toString().toUpperCase())) {

                if (Worlds.isDenyMessageExist(world)) {
                    p.sendMessage(Worlds.getDenyMessage(world));
                }
                e.setCancelled(true);
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

        String flag = "leaf-decay";

        if (Settings.canExecute(world, flag)) {

            if (!Worlds.getFlag(world, flag)) {

                e.setCancelled(true);

            }

        }

    }

    // Flag: melt
    @EventHandler
    public void onBlockFade(BlockFadeEvent e) {
        Block block = e.getBlock();
        String world = block.getWorld().getName();

        if (HRes.isInRes(block)) {
            return;
        }

        Material mat = block.getType();

        // Flag: melt
        if (mat == Material.SNOW || mat == Material.ICE || mat == Material.PACKED_ICE) {

            if (Settings.canExecute(world, "melt")) {

                if (!Worlds.getFlag(world, "melt")) {

                    e.setCancelled(true);
                    return;

                }
                return;
            }
            return;
        }

        // Flag: coral-decay
        if (FlagsManager.getFlagsList("coral-decay").contains(mat.toString())) {

            if (Settings.canExecute(world, "coral-decay")) {

                if (!Worlds.getFlag(world, "coral-decay")) {
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onLiquidSpread(BlockFromToEvent e) {

        String world = e.getBlock().getWorld().getName();
        Block block = e.getBlock();
        Material mat = block.getType();

        if (HRes.isInRes(block)) {
            return;
        }

        // Flag: water-spread
        if (mat == Material.WATER) {
            String waterFlag = "water-spread";

            if (Settings.canExecute(world, waterFlag)) {

                if (!Worlds.getFlag(world, waterFlag)) {

                    e.setCancelled(true);
                    return;

                }
            }
        }

        // Flag: lava-spread
        if (mat == Material.LAVA) {

            String lavaFlag = "lava-spread";
            if (Settings.canExecute(world, lavaFlag)) {

                if (!Worlds.getFlag(world, lavaFlag)) {

                    e.setCancelled(true);

                }

            }
        }

    }

    // Flag: piston
    @EventHandler
    public void onPistonTrigger(BlockPistonRetractEvent e) {
        Block b = e.getBlock();
        String world = b.getWorld().getName();

        if (HRes.isInRes(b)) {
            return;
        }

        if (Settings.canExecute(world, "piston")) {

            if (!Worlds.getFlag(world, "piston")) {

                e.setCancelled(true);

            }

        }
    }

}