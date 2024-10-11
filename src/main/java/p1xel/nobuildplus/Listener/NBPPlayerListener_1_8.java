package p1xel.nobuildplus.Listener;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import p1xel.nobuildplus.Flags;
import p1xel.nobuildplus.Hook.Hooks;
import p1xel.nobuildplus.NoBuildPlus;
import p1xel.nobuildplus.Storage.FlagsManager;
import p1xel.nobuildplus.Storage.Worlds;

public class NBPPlayerListener_1_8 implements Listener {

    // Flag: sign-edit
    @EventHandler
    public void onArmorStandPlace(PlayerInteractEvent e) {

        Block b = e.getClickedBlock();

        if (e.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        if (Hooks.cancel(b)) {
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

        String world = b.getWorld().getName();

        if (!Flags.armorstand.isEnabled(world)) {
            return;
        }

        Player p = e.getPlayer();
        if (p.hasPermission(Worlds.getPermission(world))) {
            return;
        }


        if (Worlds.isDenyMessageExist(world)) {
            p.sendMessage(Worlds.getDenyMessage(world));
        }
        e.setCancelled(true);

    }

    // Flag: minecart
    @EventHandler
    public void onMinecartPlaced(PlayerInteractEvent e) {

        Block b = e.getClickedBlock();

        if (e.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        if (Hooks.cancel(b)) {
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

        String world = b.getWorld().getName();

        if (!Flags.minecart.isEnabled(world)) {
            return;
        }

        Player p = e.getPlayer();
        if (p.hasPermission(Worlds.getPermission(world))) {
            return;
        }

        if (Worlds.isDenyMessageExist(world)) {
            p.sendMessage(Worlds.getDenyMessage(world));
        }

        e.setCancelled(true);

    }

    // Flag: crystal
    @EventHandler
    public void onCrystalPlaced(PlayerInteractEvent e) {

        if (NoBuildPlus.getInstance().getBukkitVersion() <= 8) {
            return;
        }
        Block b = e.getClickedBlock();

        if (e.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        if (Hooks.cancel(b)) {
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

        String world = b.getWorld().getName();

        if (!Flags.crystal.isEnabled(world)) {
            return;
        }

        Player p = e.getPlayer();
        if (p.hasPermission(Worlds.getPermission(world))) {
            return;
        }

        if (Worlds.isDenyMessageExist(world)) {
            p.sendMessage(Worlds.getDenyMessage(world));
        }

        e.setCancelled(true);

    }

    // Flag: boat (being placed)
    @EventHandler
    public void onBoatPlaced(PlayerInteractEvent e) {

        Block b = e.getClickedBlock();

        if (e.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        if (Hooks.cancel(b)) {
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

        String world = b.getWorld().getName();

        if (!Flags.boat.isEnabled(world)) {
            return;
        }

        Player p = e.getPlayer();
        if (p.hasPermission(Worlds.getPermission(world))) {
            return;
        }

        if (Worlds.isDenyMessageExist(world)) {
            p.sendMessage(Worlds.getDenyMessage(world));
        }

        e.setCancelled(true);
    }

}
