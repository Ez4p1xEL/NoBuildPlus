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

        if (!FlagsManager.getFlagsIsEnabled(flag)) {
            return;
        }

        if (!Settings.getEnableWorldList().contains(world)) {
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

            for (String blockname : FlagsManager.getFlagsList(flag)) {
                Material block = Material.matchMaterial(blockname);
                if (block != null) {
                    if (e.getBlock().getType() == block) {
                        if (Worlds.isDenyMessageExist(world)) {
                            if (Worlds.isDenyMessageExist(world)) {
                            p.sendMessage(Worlds.getDenyMessage(world));
                        }
                        }
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
        String flag = "build";

        if (HRes.isInRes(e.getBlock())) {
            return;
        }

        if (!FlagsManager.getFlagsIsEnabled(flag)) {
            return;
        }

        if (!Settings.getEnableWorldList().contains(world)) {
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

            for (String blockname : FlagsManager.getFlagsList(flag)) {
                Material block = Material.matchMaterial(blockname);
                if (block != null) {
                    if (e.getBlock().getType() == block) {
                        if (Worlds.isDenyMessageExist(world)) {
                            p.sendMessage(Worlds.getDenyMessage(world));
                        }
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

        String flag = "leaf-decay";

        if (FlagsManager.getFlagsIsEnabled(flag)) {

            if (Settings.getEnableWorldList().contains(world)) {

                if (!Worlds.getFlag(world, flag)) {

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

        String flag = "melt";

        if (e.getBlock().getType() == Material.SNOW || e.getBlock().getType() == Material.ICE || e.getBlock().getType() == Material.PACKED_ICE) {

            if (FlagsManager.getFlagsIsEnabled(flag)) {

                if (Settings.getEnableWorldList().contains(world)) {

                    if (!Worlds.getFlag(world, flag)) {

                        e.setCancelled(true);

                    }

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
            boolean enableWater = FlagsManager.getFlagsIsEnabled(waterFlag);
            if (enableWater) {

                if (Settings.getEnableWorldList().contains(world)) {

                    if (!Worlds.getFlag(world, waterFlag)) {

                        e.setCancelled(true);
                        return;

                    }

                }
            }
        }

        // Flag: lava-spread
        if (mat == Material.LAVA) {

            String lavaFlag = "lava-spread";
            boolean enableLava = FlagsManager.getFlagsIsEnabled(lavaFlag);
            if (enableLava) {

                if (Settings.getEnableWorldList().contains(world)) {

                    if (!Worlds.getFlag(world, lavaFlag)) {

                        e.setCancelled(true);

                    }

                }
            }
        }

    }

}