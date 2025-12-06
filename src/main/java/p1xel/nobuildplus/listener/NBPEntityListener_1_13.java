package p1xel.nobuildplus.listener;

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
import p1xel.nobuildplus.storage.Worlds;

public class NBPEntityListener_1_13 implements Listener {

    int v = NoBuildPlus.getInstance().getBukkitVersion();

    // Flag: armor stand (being created)
    @EventHandler
    public void onArmorStandPlaced(EntityPlaceEvent e) {

        Entity entity = e.getEntity();

        if (HookedPlugins.cancel(entity)) {
            return;
        }

        EntityType type = e.getEntityType();
        if (type != EntityType.ARMOR_STAND) {
            return;
        }

        String world = entity.getWorld().getName();

        if (!Flags.armorstand.isEnabled(world)) {
            return;
        }

        Player p = e.getPlayer();
        if (p != null) {
            if (p.hasPermission(Worlds.getPermission(world))) {
                return;
            }


            Worlds.sendMessage(p, world);
        }
        e.setCancelled(true);

    }

    // Flag: minecart (being created)
    @EventHandler
    public void onMinecartPlaced(EntityPlaceEvent e) {

        Entity entity = e.getEntity();

        if (HookedPlugins.cancel(entity)) {
            return;
        }

        if (!(entity instanceof Minecart)) {
            return;
        }

        String world = entity.getWorld().getName();

        if (!Flags.minecart.isEnabled(world)) {
            return;
        }

        Player p = e.getPlayer();
        if (p != null) {
            if (p.hasPermission(Worlds.getPermission(world))) {
                return;
            }

            Worlds.sendMessage(p, world);
        }

        e.setCancelled(true);

    }

    // Flag: crystal
    @EventHandler
    public void onCrystalPlaced(EntityPlaceEvent e) {

        Entity entity = e.getEntity();

        if (HookedPlugins.cancel(entity)) {
            return;
        }

        if (!(entity instanceof EnderCrystal)) {
            return;
        }

        String world = entity.getWorld().getName();

        if (!Flags.crystal.isEnabled(world)) {
            return;
        }

        Player p = e.getPlayer();
        if (p != null) {
            if (p.hasPermission(Worlds.getPermission(world))) {
                return;
            }

            Worlds.sendMessage(p, world);
        }

        e.setCancelled(true);
    }

    // Flag: boat (being placed)
    @EventHandler
    public void onBoatPlaced(EntityPlaceEvent e) {

        Entity entity = e.getEntity();

        if (HookedPlugins.cancel(entity)) {
            return;
        }

        String world = entity.getWorld().getName();

        if (!Flags.boat.isEnabled(world)) {
            return;
        }

        if (v >= 19) {
            if (entity instanceof ChestBoat) {

                Player p = e.getPlayer();
                if (p != null) {
                    if (p.hasPermission(Worlds.getPermission(world))) {
                        return;
                    }
                    Worlds.sendMessage(p, world);
                }
                e.setCancelled(true);
                return;
            }
        }

        if (entity instanceof Boat) {

            Player p = e.getPlayer();
            if (p != null) {
                if (p.hasPermission(Worlds.getPermission(world))) {
                    return;
                }
                Worlds.sendMessage(p, world);
            }
            e.setCancelled(true);
        }
    }

    // Flag: potion
    @EventHandler
    public void onPotionEffect(EntityPotionEffectEvent e) {

        Entity entity = e.getEntity();

        if (!(entity instanceof Player)) {
            return;
        }

        if (HookedPlugins.cancel(entity)) {
            return;
        }

        String world = entity.getWorld().getName();

        if (!Flags.potion.isEnabled(world)) {
            return;
        }

        Player p = (Player) entity;

        if (p.hasPermission(Worlds.getPermission(world))) {
            return;
        }

        Worlds.sendMessage(p, world);

        e.setCancelled(true);

    }

    // Flag: turtle-egg
    @EventHandler
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

        String world = block.getWorld().getName();
        if (!Flags.turtle_egg.isEnabled(world)) {
            return;
        }

        Player p = e.getPlayer();
        if (p.hasPermission(Worlds.getPermission(world))) {
            return;
        }

        Worlds.sendMessage(p, world);

        e.setCancelled(true);

    }

}
