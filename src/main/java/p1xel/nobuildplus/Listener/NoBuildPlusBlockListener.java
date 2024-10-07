package p1xel.nobuildplus.Listener;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.player.PlayerHarvestBlockEvent;
import p1xel.nobuildplus.Flags;
import p1xel.nobuildplus.Hook.Hooks;
import p1xel.nobuildplus.Storage.Worlds;

public class NoBuildPlusBlockListener implements Listener {

    /*
    Example for flag listener
    规则事件监听示例
    規則事件監聽示例
    */
    // Flag: break
    @EventHandler
    public void onBreak(BlockBreakEvent e) {

        Block block = e.getBlock();

        // Check if the block location is in the controlled area of other plugins. e.g. Residence / Dominion
        // If it is in the area, discontinue the listener.
        if (Hooks.cancel(block)) {
            return;
        }

        String world = block.getWorld().getName();

        // Check if the flag listener should continue its work
        // If the flag is not enabled, then it is FALSE
        // If the flag in designated world is TRUE(ALLOW), then it is FALSE here.
        if (!Flags.destroy.isEnabled(world)) {
            return;
        }

        Player p = e.getPlayer();

        // Check if the player has permission to bypass that.
        if (p.hasPermission(Worlds.getPermission(world))) {
            return;
        }

        // If the flag type is ALL, then the listener will prevent all blocks breaking.
        if (Flags.destroy.getType().equalsIgnoreCase("all")) {
            if (Worlds.isDenyMessageExist(world)) {
                p.sendMessage(Worlds.getDenyMessage(world));
            }
            e.setCancelled(true);
        }

        // If the flag type is LIST, then the listener will only prevent the blocks existed in the string list.
        if (Flags.destroy.getType().equalsIgnoreCase("list")) {

            Material mat = block.getType();
            if (Flags.destroy.getList().contains(mat.toString().toUpperCase())) {
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

        Block block = e.getBlock();

        // Cancel if the block location is controlled by other plugins.
        if (Hooks.cancel(block)) {
            return;
        }

        String world = block.getWorld().getName();

        if (!Flags.build.isEnabled(world)) {
            return;
        }

        Player p = e.getPlayer();

        if (p.hasPermission(Worlds.getPermission(world))) {
            return;
        }

        if (Flags.build.getType().equalsIgnoreCase("all")) {
            if (Worlds.isDenyMessageExist(world)) {
                p.sendMessage(Worlds.getDenyMessage(world));
            }
            e.setCancelled(true);
        }

        if (Flags.build.getType().equalsIgnoreCase("list")) {

            Material mat = block.getType();

            if (Flags.build.getList().contains(mat.toString().toUpperCase())) {

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

        Block block = e.getBlock();

        if (Hooks.cancel(block)) {
            return;
        }

        String world = block.getWorld().getName();

        if (!Flags.leaf_decay.isEnabled(world)) {
            return;
        }

        e.setCancelled(true);

    }

    // Flag: melt
    @EventHandler
    public void onSnowMelt(BlockFadeEvent e) {
        Block block = e.getBlock();
        String world = block.getWorld().getName();

        if (Hooks.cancel(block)) {
            return;
        }

        Material mat = block.getType();

        // Flag: melt
        if (mat == Material.SNOW || mat == Material.ICE || mat == Material.PACKED_ICE) {

            if (!Flags.melt.isEnabled(world)) {
                return;
            }

            e.setCancelled(true);
        }

    }

    // Coral Decay
    @EventHandler
    public void onCoralDecay(BlockFadeEvent e) {
        Block block = e.getBlock();

        if (Hooks.cancel(block)) {
            return;
        }

        Material mat = block.getType();
        // Flag: coral-decay
        if (Flags.coral_decay.getList().contains(mat.toString())) {

            String world = block.getWorld().getName();

            if (!Flags.coral_decay.isEnabled(world)) {
                return;
            }

            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onWaterSpread(BlockFromToEvent e) {

        Block block = e.getBlock();

        if (Hooks.cancel(block)) {
            return;
        }

        Material mat = block.getType();

        // Flag: water-spread
        if (mat == Material.WATER) {

            String world = block.getWorld().getName();
            if (!Flags.water_spread.isEnabled(world)) {
                return;
            }

            e.setCancelled(true);

        }


    }

    // Flag: lava-spread
    @EventHandler
    public void onLavaSpread(BlockFromToEvent e) {

        Block block = e.getBlock();

        if (Hooks.cancel(block)) {
            return;
        }

        Material mat = block.getType();

        // Flag: lava-spread
        if (mat == Material.LAVA) {

            String world = block.getWorld().getName();
            if (!Flags.lava_spread.isEnabled(world)) {
                return;
            }

            e.setCancelled(true);

        }
    }

    // Flag: ice-form
    @EventHandler
    public void onIceFormation(BlockFromToEvent e) {

        Block block = e.getBlock();

        if (Hooks.cancel(block)) {
            return;
        }

        Material newMat = e.getToBlock().getType();

        if (newMat == Material.ICE) {

            String world = block.getWorld().getName();

            if (!Flags.ice_form.isEnabled(world)) {
                return;
            }

            e.setCancelled(true);

        }
    }

    // Flag: piston
    @EventHandler
    public void onPistonTrigger(BlockPistonRetractEvent e) {
        Block block = e.getBlock();

        if (Hooks.cancel(block)) {
            return;
        }

        String world = block.getWorld().getName();

        if (!Flags.piston.isEnabled(world)) {
            return;
        }

        e.setCancelled(true);

    }

    // Flag: berries
    @EventHandler
    public void onHarvest(PlayerHarvestBlockEvent e) {

        Block block = e.getHarvestedBlock();

        if (Hooks.cancel(block)) {
            return;
        }

        String world = block.getWorld().getName();

        if (!Flags.berries.isEnabled(world)) {
            return;
        }

        Player p = e.getPlayer();

        if (p.hasPermission(Worlds.getPermission(world))) {
            return;
        }

        Material harvested = block.getType();

        if (harvested.equals(Material.matchMaterial("CAVE_VINES")) || harvested.equals(Material.matchMaterial("CAVE_VINES_PLANT"))) {
            if (Worlds.isDenyMessageExist(world)) {
                p.sendMessage(Worlds.getDenyMessage(world));
            }
            e.setCancelled(true);
        }

    }

}