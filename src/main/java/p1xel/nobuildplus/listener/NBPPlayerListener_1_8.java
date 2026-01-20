package p1xel.nobuildplus.listener;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import p1xel.nobuildplus.Flags;
import p1xel.nobuildplus.hook.HookedPlugins;
import p1xel.nobuildplus.NoBuildPlus;
import p1xel.nobuildplus.storage.FlagsManager;
import p1xel.nobuildplus.world.ProtectedWorld;
import p1xel.nobuildplus.world.WorldManager;

public class NBPPlayerListener_1_8 implements Listener {

    // Flag: armorstand
    @EventHandler(ignoreCancelled = true)
    public void onArmorStandPlace(PlayerInteractEvent e) {

        Block block = e.getClickedBlock();

        if (e.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        if (HookedPlugins.cancel(block)) {
            return;
        }

        ItemStack item = e.getItem();
        if (item == null) {
            return;
        }

        Material mat = item.getType();
        if (mat != Material.ARMOR_STAND) {
            return;
        }

        String worldName = block.getWorld().getName();
        ProtectedWorld world = WorldManager.getWorld(worldName);

        if (!Flags.armorstand.isEnabled(world)) {
            return;
        }

        Player player = e.getPlayer();
        if (player.hasPermission(world.getPermission())) {
            return;
        }

        if (FlagsManager.getBoolInFlag("armorstand", "placement")) {

            WorldManager.sendMessage(player, world);
            e.setCancelled(true);
        }

    }

    // Flag: minecart
    @EventHandler(ignoreCancelled = true)
    public void onMinecartPlaced(PlayerInteractEvent e) {

        Block block = e.getClickedBlock();

        if (e.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        if (HookedPlugins.cancel(block)) {
            return;
        }

        ItemStack item = e.getItem();
        if (item == null) {
            return;
        }

        Material mat = item.getType();
        if (!Flags.minecart.getList().contains(mat.toString().toUpperCase())) {
            return;
        }

        String worldName = block.getWorld().getName();
        ProtectedWorld world = WorldManager.getWorld(worldName);

        if (!Flags.minecart.isEnabled(world)) {
            return;
        }

        Player player = e.getPlayer();
        if (player.hasPermission(world.getPermission())) {
            return;
        }

        WorldManager.sendMessage(player, world);

        e.setCancelled(true);

    }

    // Flag: crystal
    @EventHandler(ignoreCancelled = true)
    public void onCrystalPlaced(PlayerInteractEvent e) {

        if (NoBuildPlus.getInstance().getBukkitVersion() <= 8) {
            return;
        }
        Block block = e.getClickedBlock();

        if (e.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        if (HookedPlugins.cancel(block)) {
            return;
        }

        ItemStack item = e.getItem();
        if (item == null) {
            return;
        }

        Material mat = item.getType();
        if (mat != Material.END_CRYSTAL) {
            return;
        }

        String worldName = block.getWorld().getName();
        ProtectedWorld world = WorldManager.getWorld(worldName);

        if (!Flags.crystal.isEnabled(world)) {
            return;
        }

        Player player = e.getPlayer();
        if (player.hasPermission(world.getPermission())) {
            return;
        }

        WorldManager.sendMessage(player, world);

        e.setCancelled(true);

    }

    // Flag: boat (being placed)
    @EventHandler(ignoreCancelled = true)
    public void onBoatPlaced(PlayerInteractEvent e) {

        Block block = e.getClickedBlock();

        if (e.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        if (HookedPlugins.cancel(block)) {
            return;
        }

        ItemStack item = e.getItem();
        if (item == null) {
            return;
        }

        Material mat = item.getType();
        if (!Flags.boat.getList().contains(mat.toString().toUpperCase())) {
            return;
        }

        String worldName = block.getWorld().getName();
        ProtectedWorld world = WorldManager.getWorld(worldName);

        if (!Flags.boat.isEnabled(world)) {
            return;
        }

        Player player = e.getPlayer();
        if (player.hasPermission(world.getPermission())) {
            return;
        }

        WorldManager.sendMessage(player, world);

        e.setCancelled(true);
    }

}
