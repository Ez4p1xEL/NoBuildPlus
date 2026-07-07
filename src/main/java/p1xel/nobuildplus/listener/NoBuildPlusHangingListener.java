package p1xel.nobuildplus.listener;

import org.bukkit.block.data.type.TNT;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.hanging.HangingBreakEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;
import p1xel.nobuildplus.Flags;
import p1xel.nobuildplus.hook.HookedPlugins;
import p1xel.nobuildplus.NoBuildPlus;
import p1xel.nobuildplus.world.ProtectedWorld;
import p1xel.nobuildplus.world.WorldManager;

public class NoBuildPlusHangingListener implements Listener {

    // Flag: frame (Break)
    @EventHandler(ignoreCancelled = true)
    public void onFrameBroke(HangingBreakByEntityEvent e) {

        Entity entity = e.getRemover();

        if (HookedPlugins.cancel(e.getEntity())) {
            return;
        }

        String worldName = entity.getWorld().getName();
        ProtectedWorld world = WorldManager.getWorld(worldName);

        if (!Flags.frame.isEnabled(world)) {
            return;
        }

        Player player = null;
        boolean isPlayer = entity instanceof Player;

        if (isPlayer) {
            player = (Player) entity;
            if (player.hasPermission(world.getPermission())) {
                return;
            }
        }


        if ((v[0] == 1 && v[1]>=17) || v[0] > 1) {

            if (e.getEntity() instanceof GlowItemFrame) {

                if (isPlayer) {
                WorldManager.sendMessage(player, world);
                }
                e.setCancelled(true);
                return;

            }

        }

        if (e.getEntity() instanceof ItemFrame) {

            if (isPlayer) {
                WorldManager.sendMessage(player, world);
            }
            e.setCancelled(true);

        }
    }

    // Flag: painting
    @EventHandler(ignoreCancelled = true)
    public void onPaintingBroke(HangingBreakByEntityEvent e) {

        Entity entity = e.getRemover();

        if (HookedPlugins.cancel(entity)) {
            return;
        }

        if (!(entity instanceof Painting)) {
            return;
        }

        String worldName = entity.getWorld().getName();
        ProtectedWorld world = WorldManager.getWorld(worldName);

        if (!Flags.painting.isEnabled(world)) {
            return;
        }

        if (entity instanceof Player) {
            Player player = (Player) entity;
            if (player.hasPermission(world.getPermission())) {
                return;
            }

            WorldManager.sendMessage(player, world);
        }
        e.setCancelled(true);
    }

    int[] v = NoBuildPlus.getInstance().getBukkitVersion();

    // Flag: frame (being placed)
    @EventHandler(ignoreCancelled = true)
    public void onFramePlaced(HangingPlaceEvent e) {

        Entity entity = e.getEntity();

        if (HookedPlugins.cancel(entity)) {
            return;
        }

        String worldName = entity.getWorld().getName();
        ProtectedWorld world = WorldManager.getWorld(worldName);

        if (!Flags.frame.isEnabled(world)) {
            return;
        }

        Player player = e.getPlayer();

        if (player.hasPermission(world.getPermission())) {
            return;
        }

        EntityType type = entity.getType();

        if ((v[0] == 1 &&v[1] >= 17) || v[0] > 1) {

            if (type == EntityType.GLOW_ITEM_FRAME) {

                WorldManager.sendMessage(player, world);
                e.setCancelled(true);
                return;

            }

        }

        if (type == EntityType.ITEM_FRAME) {

            WorldManager.sendMessage(player, world);
            e.setCancelled(true);

        }

    }

    // Flag: painting
    @EventHandler(ignoreCancelled = true)
    public void onPaintingPlaced(HangingPlaceEvent e) {

        Entity entity = e.getEntity();

        if (HookedPlugins.cancel(entity)) {
            return;
        }

        if (entity.getType() != EntityType.PAINTING) {
            return;
        }

        String worldName = entity.getWorld().getName();
        ProtectedWorld world = WorldManager.getWorld(worldName);

        if (!Flags.painting.isEnabled(world)) {
            return;
        }

        Player player = e.getPlayer();

        if (player.hasPermission(world.getPermission())) {
            return;
        }

        WorldManager.sendMessage(player, world);
        e.setCancelled(true);

    }

    // Flag: frame
    @EventHandler(ignoreCancelled = true)
    public void onHangingBreak(HangingBreakEvent event) {

        Entity entity = event.getEntity();

        if (event.getCause() != HangingBreakEvent.RemoveCause.EXPLOSION) {
            return;
        }

        if (HookedPlugins.cancel(entity)) {
            return;
        }

        String worldName = entity.getWorld().getName();
        ProtectedWorld world = WorldManager.getWorld(worldName);

        if (!Flags.frame.isEnabled(world)) {
            return;
        }

        if ((v[0] == 1 && v[1]>=17) || v[0] > 1) {

            if (entity instanceof GlowItemFrame) {
                event.setCancelled(true);
                return;

            }

        }

        if (entity instanceof ItemFrame) {

            event.setCancelled(true);

        }
    }

    // Flag: painting
    @EventHandler(ignoreCancelled = true)
    public void onPaintingBreak(HangingBreakEvent event) {

        Entity entity = event.getEntity();

        if (event.getCause() != HangingBreakEvent.RemoveCause.EXPLOSION) {
            return;
        }

        if (entity.getType() != EntityType.PAINTING) {
            return;
        }

        if (HookedPlugins.cancel(entity)) {
            return;
        }

        String worldName = entity.getWorld().getName();
        ProtectedWorld world = WorldManager.getWorld(worldName);

        if (!Flags.painting.isEnabled(world)) {
            return;
        }

        event.setCancelled(true);
    }

    @Flag("armorstand")
    @EventHandler(ignoreCancelled = true)
    public void onBreakArmorstand(HangingBreakEvent event) {

        Entity entity = event.getEntity();

        if (event.getCause() != HangingBreakEvent.RemoveCause.EXPLOSION) {
            return;
        }

        if (entity.getType() != EntityType.ARMOR_STAND) {
            return;
        }

        if (HookedPlugins.cancel(entity)) {
            return;
        }

        String worldName = entity.getWorld().getName();
        ProtectedWorld world = WorldManager.getWorld(worldName);

        if (!Flags.armorstand.isEnabled(world)) {
            return;
        }

        event.setCancelled(true);
    }

    @Flag("minecart")
    @EventHandler(ignoreCancelled = true)
    public void onBreakMinecart(HangingBreakEvent event) {

        Entity entity = event.getEntity();

        if (event.getCause() != HangingBreakEvent.RemoveCause.EXPLOSION) {
            return;
        }

        if (!(entity instanceof Minecart)) {
            return;
        }

        if (HookedPlugins.cancel(entity)) {
            return;
        }

        String worldName = entity.getWorld().getName();
        ProtectedWorld world = WorldManager.getWorld(worldName);

        if (!Flags.minecart.isEnabled(world)) {
            return;
        }

        event.setCancelled(true);
    }


    @Flag("mob-explode-damage")
    @EventHandler(ignoreCancelled = true)
    public void onMobExplode(HangingBreakByEntityEvent event) {

        Entity remover = event.getRemover();
        Entity entity = event.getEntity();

        if (HookedPlugins.cancel(entity)) {
            return;
        }

        if (event.getCause() != HangingBreakEvent.RemoveCause.EXPLOSION && (remover instanceof Player || remover instanceof TNTPrimed)) {
            return;
        }

        String worldName = entity.getWorld().getName();
        ProtectedWorld world = WorldManager.getWorld(worldName);

        if (!Flags.mob_explode_damage.isEnabled(world)) {
            return;
        }

        event.setCancelled(true);
    }


}
