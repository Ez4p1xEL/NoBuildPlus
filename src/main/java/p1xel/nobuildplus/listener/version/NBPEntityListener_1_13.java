package p1xel.nobuildplus.listener.version;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityPlaceEvent;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import p1xel.nobuildplus.Flags;
import p1xel.nobuildplus.hook.HookedPlugins;
import p1xel.nobuildplus.NoBuildPlus;
import p1xel.nobuildplus.storage.FlagsManager;
import p1xel.nobuildplus.world.ProtectedWorld;
import p1xel.nobuildplus.world.WorldManager;

public class NBPEntityListener_1_13 implements Listener {

    int v = NoBuildPlus.getInstance().getBukkitVersion();

    // Flag: armor stand (being created)
    @EventHandler(ignoreCancelled = true)
    public void onArmorStandPlaced(EntityPlaceEvent e) {

        Entity entity = e.getEntity();

        if (HookedPlugins.cancel(entity)) {
            return;
        }

        EntityType type = e.getEntityType();
        if (type != EntityType.ARMOR_STAND) {
            return;
        }

        String worldName = entity.getWorld().getName();
        ProtectedWorld world = WorldManager.getWorld(worldName);

        if (!Flags.armorstand.isEnabled(world)) {
            return;
        }

        if (!(FlagsManager.getBoolInFlag("armorstand", "placement"))) {
            return;
        }

        Player player = e.getPlayer();
        if (player != null) {
            if (player.hasPermission(world.getPermission())) {
                return;
            }
            WorldManager.sendMessage(player, world);
        }
        e.setCancelled(true);

    }

    // Flag: minecart (being created)
    @EventHandler(ignoreCancelled = true)
    public void onMinecartPlaced(EntityPlaceEvent e) {

        Entity entity = e.getEntity();

        if (HookedPlugins.cancel(entity)) {
            return;
        }

        if (!(entity instanceof Minecart)) {
            return;
        }

        String worldName = entity.getWorld().getName();
        ProtectedWorld world = WorldManager.getWorld(worldName);

        if (!Flags.minecart.isEnabled(world)) {
            return;
        }

        Player player = e.getPlayer();
        if (player != null) {
            if (player.hasPermission(world.getPermission())) {
                return;
            }
            WorldManager.sendMessage(player, world);
        }

        e.setCancelled(true);

    }

    // Flag: crystal
    @EventHandler(ignoreCancelled = true)
    public void onCrystalPlaced(EntityPlaceEvent e) {

        Entity entity = e.getEntity();

        if (HookedPlugins.cancel(entity)) {
            return;
        }

        if (!(entity instanceof EnderCrystal)) {
            return;
        }

        String worldName = entity.getWorld().getName();
        ProtectedWorld world = WorldManager.getWorld(worldName);

        if (!Flags.crystal.isEnabled(world)) {
            return;
        }

        Player player = e.getPlayer();
        if (player != null) {
            if (player.hasPermission(world.getPermission())) {
                return;
            }
            WorldManager.sendMessage(player, world);
        }

        e.setCancelled(true);
    }

    // Flag: boat (being placed)
    @EventHandler(ignoreCancelled = true)
    public void onBoatPlaced(EntityPlaceEvent e) {

        Entity entity = e.getEntity();

        if (HookedPlugins.cancel(entity)) {
            return;
        }

        String worldName = entity.getWorld().getName();
        ProtectedWorld world = WorldManager.getWorld(worldName);

        if (!Flags.boat.isEnabled(world)) {
            return;
        }

        if (v >= 19) {
            if (entity instanceof ChestBoat) {

                Player player = e.getPlayer();
                if (player != null) {
                    if (player.hasPermission(world.getPermission())) {
                        return;
                    }
                    WorldManager.sendMessage(player, world);
                }
                e.setCancelled(true);
                return;
            }
        }

        if (entity instanceof Boat) {

            Player player = e.getPlayer();
            if (player != null) {
                if (player.hasPermission(world.getPermission())) {
                    return;
                }
                WorldManager.sendMessage(player, world);
            }
            e.setCancelled(true);
        }
    }

    // Flag: potion
    @EventHandler(ignoreCancelled = true)
    public void onPotionEffect(EntityPotionEffectEvent e) {

        Entity entity = e.getEntity();

        if (!(entity instanceof Player)) {
            return;
        }

        if (HookedPlugins.cancel(entity)) {
            return;
        }

        String worldName = entity.getWorld().getName();
        ProtectedWorld world = WorldManager.getWorld(worldName);

        if (!Flags.potion.isEnabled(world)) {
            return;
        }

        Player player = (Player) entity;

        if (player.hasPermission(world.getPermission())) {
            return;
        }

        WorldManager.sendMessage(player, world);

        e.setCancelled(true);

    }

    // Flag: turtle-egg
    @EventHandler(ignoreCancelled = true)
    public void onTurtleEggInteract(PlayerInteractEvent e) {

        Action action = e.getAction();
        Block block = e.getClickedBlock();

        if (action != Action.PHYSICAL || block == null
                || block.getType() != Material.matchMaterial("TURTLE_EGG")) {
            return;
        }

        if (HookedPlugins.cancel(block)) {
            return;
        }

        String worldName = block.getWorld().getName();
        ProtectedWorld world = WorldManager.getWorld(worldName);
        if (!Flags.turtle_egg.isEnabled(world)) {
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
