package p1xel.nobuildplus.listener;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import p1xel.nobuildplus.Flags;
import p1xel.nobuildplus.hook.HookedPlugins;
import p1xel.nobuildplus.world.ProtectedWorld;
import p1xel.nobuildplus.world.WorldManager;

public class NoBuildPlusBlockListener implements Listener {

    /*
    Example for flag listener
    规则事件监听示例
    規則事件監聽示例
    */
    // Flag: break
    @EventHandler(ignoreCancelled = true)
    public void onBreak(BlockBreakEvent e) {

        Block block = e.getBlock();

        // Check if the block location is in the controlled area of other plugins. e.g. Residence / Dominion
        // If it is in the area, discontinue the listener.
        if (HookedPlugins.cancel(block)) {
            return;
        }

        String worldName = block.getWorld().getName();
        ProtectedWorld world = WorldManager.getWorld(worldName);
        // Check if the flag listener should continue its work
        // If the flag is not enabled, then it is FALSE
        // If the flag in designated world is TRUE(ALLOW), then it is FALSE here.
        if (!Flags.destroy.isEnabled(world)) {
            return;
        }

        Player player = e.getPlayer();

        // Check if the player has permission to bypass that.
        if (player.hasPermission(world.getPermission())) {
            return;
        }

        // If the flag type is ALL, then the listener will prevent all blocks breaking.
        if (Flags.destroy.getType().equalsIgnoreCase("all")) {
            WorldManager.sendMessage(player, world);
            e.setCancelled(true);
        }

        // If the flag type is LIST, then the listener will only prevent the blocks existed in the string list.
        if (Flags.destroy.getType().equalsIgnoreCase("list")) {

            Material mat = block.getType();
            if (Flags.destroy.getList().contains(mat.toString().toUpperCase())) {
                WorldManager.sendMessage(player, world);
                e.setCancelled(true);
            }
        }

    }


    // Flag: Build
    @EventHandler(ignoreCancelled = true)
    public void onBuild(BlockPlaceEvent e) {

        Block block = e.getBlock();

        // Cancel if the block location is controlled by other plugins.
        if (HookedPlugins.cancel(block)) {
            return;
        }

        String worldName = block.getWorld().getName();
        ProtectedWorld world = WorldManager.getWorld(worldName);
        if (!Flags.build.isEnabled(world)) {
            return;
        }

        Player player = e.getPlayer();

        if (player.hasPermission(world.getPermission())) {
            return;
        }

        if (Flags.build.getType().equalsIgnoreCase("all")) {
            WorldManager.sendMessage(player, world);
            e.setCancelled(true);
        }

        if (Flags.build.getType().equalsIgnoreCase("list")) {

            Material mat = block.getType();

            if (Flags.build.getList().contains(mat.toString().toUpperCase())) {

                WorldManager.sendMessage(player, world);
                e.setCancelled(true);
            }

        }

    }

    // Flag: leaf-decay
    @EventHandler(ignoreCancelled = true)
    public void onLeafDecay(LeavesDecayEvent e) {

        Block block = e.getBlock();

        if (HookedPlugins.cancel(block)) {
            return;
        }

        String worldName = block.getWorld().getName();
        ProtectedWorld world = WorldManager.getWorld(worldName);
        if (!Flags.leaf_decay.isEnabled(world)) {
            return;
        }

        e.setCancelled(true);

    }

    // Flag: melt
    @EventHandler(ignoreCancelled = true)
    public void onSnowMelt(BlockFadeEvent e) {
        Block block = e.getBlock();
        Material mat = block.getType();
        // Flag: melt
        if (mat != Material.SNOW && mat != Material.ICE && mat != Material.PACKED_ICE) {
            return;
        }

        String worldName = block.getWorld().getName();
        ProtectedWorld world = WorldManager.getWorld(worldName);

        if (!Flags.melt.isEnabled(world)) {
            return;
        }

        if (HookedPlugins.cancel(block)) {
            return;
        }
        e.setCancelled(true);

    }

    // Coral Decay
    @EventHandler(ignoreCancelled = true)
    public void onCoralDecay(BlockFadeEvent e) {
        Block block = e.getBlock();

        // Flag: coral-decay
        String worldName = block.getWorld().getName();
        ProtectedWorld world = WorldManager.getWorld(worldName);
        if (!Flags.coral_decay.isEnabled(world)) {
            return;
        }

        Material mat = block.getType();

        if (!Flags.coral_decay.getList().contains(mat.toString())) {
            return;
        }

        if (HookedPlugins.cancel(block)) {
            return;
        }

        e.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true)
    public void onWaterSpread(BlockFromToEvent e) {

        Block block = e.getBlock();

        if (HookedPlugins.cancel(block)) {
            return;
        }

        Material mat = block.getType();

        // Flag: water-spread
        if (mat == Material.WATER) {

            String worldName = block.getWorld().getName();
            ProtectedWorld world = WorldManager.getWorld(worldName);
            if (!Flags.water_spread.isEnabled(world)) {
                return;
            }

            e.setCancelled(true);

        }


    }

    // Flag: lava-spread
    @EventHandler(ignoreCancelled = true)
    public void onLavaSpread(BlockFromToEvent e) {

        Block block = e.getBlock();

        if (HookedPlugins.cancel(block)) {
            return;
        }

        Material mat = block.getType();

        // Flag: lava-spread
        if (mat == Material.LAVA) {

            String worldName = block.getWorld().getName();
            ProtectedWorld world = WorldManager.getWorld(worldName);
            if (!Flags.lava_spread.isEnabled(world)) {
                return;
            }

            e.setCancelled(true);

        }
    }

    // Flag: ice-form
    @EventHandler(ignoreCancelled = true)
    public void onIceFormation(BlockFromToEvent e) {

        Block block = e.getBlock();

        if (HookedPlugins.cancel(block)) {
            return;
        }

        Material newMat = e.getToBlock().getType();

        if (newMat == Material.ICE) {

            String worldName = block.getWorld().getName();
            ProtectedWorld world = WorldManager.getWorld(worldName);
            if (!Flags.ice_form.isEnabled(world)) {
                return;
            }

            e.setCancelled(true);

        }
    }

    // Flag: piston
    @EventHandler(ignoreCancelled = true)
    public void onPistonTrigger(BlockPistonRetractEvent e) {
        Block block = e.getBlock();

        if (HookedPlugins.cancel(block)) {
            return;
        }

        String worldName = block.getWorld().getName();
        ProtectedWorld world = WorldManager.getWorld(worldName);
        if (!Flags.piston.isEnabled(world)) {
            return;
        }

        e.setCancelled(true);

    }

    // Flag: fire spawn (block igniting)
    @EventHandler(ignoreCancelled = true)
    public void onIgnite(BlockIgniteEvent e) {

        Block block = e.getBlock();

        if (HookedPlugins.cancel(block)) {
            return;
        }

        String worldName = block.getWorld().getName();
        ProtectedWorld world = WorldManager.getWorld(worldName);

        if (!Flags.fire_spawn.isEnabled(world)) {
            return;
        }

        e.setCancelled(true);

    }

    // Flag: fire spread
    @EventHandler(ignoreCancelled = true)
    public void onSpread(BlockIgniteEvent e) {

        if (e.getCause() != BlockIgniteEvent.IgniteCause.SPREAD) {
            return;
        }

        Block block = e.getBlock();
        String worldName = block.getWorld().getName();
        ProtectedWorld world = WorldManager.getWorld(worldName);

        if (!Flags.fire_spread.isEnabled(world)) {
            return;
        }

        if (HookedPlugins.cancel(block)) {
            return;
        }

        e.setCancelled(true);

    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockBurn(BlockBurnEvent e) {
        Block block = e.getIgnitingBlock();
        String worldName = block.getWorld().getName();
        ProtectedWorld world = WorldManager.getWorld(worldName);

        if (!Flags.fire_spread.isEnabled(world)) {
            return;
        }

        if (HookedPlugins.cancel(block)) {
            return;
        }

        e.setCancelled(true);
    }

}