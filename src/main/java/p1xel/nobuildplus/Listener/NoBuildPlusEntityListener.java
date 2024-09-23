package p1xel.nobuildplus.Listener;

import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.world.PortalCreateEvent;
import p1xel.nobuildplus.Flags;
import p1xel.nobuildplus.Hook.Hooks;
import p1xel.nobuildplus.Storage.FlagsManager;
import p1xel.nobuildplus.Storage.Worlds;

import java.util.List;

public class NoBuildPlusEntityListener implements Listener {

    // Flag: Mob Damage
    // Flag: Pvp
    @EventHandler
    public void onMobDamage(EntityDamageByEntityEvent e) {

        Entity p = e.getDamager();
        Entity target = e.getEntity();

        if (Hooks.cancel(p)) {
            return;
        }

        if (Hooks.cancel(target)) {
            return;
        }

        String world = target.getWorld().getName();

        if (!Flags.mob_damage.isEnabled(world)) {
            return;
        }

        if (p.hasPermission(Worlds.getPermission(world))) {
            return;
        }

        if (Flags.mob_damage.getType().equalsIgnoreCase("all")) {

            if (p instanceof Player && !(target instanceof Player)) {

                if (Worlds.isDenyMessageExist(world)) {
                    p.sendMessage(Worlds.getDenyMessage(world));
                }
                e.setCancelled(true);
                return;

            }

        }

        if (Flags.mob_damage.getType().equalsIgnoreCase("list")) {

            if (p instanceof Player && !(target instanceof Player)) {

                EntityType type = e.getEntityType();

                if (Flags.mob_damage.getList().contains(type.toString().toUpperCase())) {

                    if (Worlds.isDenyMessageExist(world)) {
                        p.sendMessage(Worlds.getDenyMessage(world));
                    }
                    e.setCancelled(true);
                    return;

                }

            }


        }

//        // Flag: boat
//        if (Settings.canExecute(world, "boat")) {
//            if (!Worlds.getFlag(world, "boat")) {
//                if (p instanceof Player) {
//                    if (target.getType() == ) {
//                        if (!p.hasPermission(Worlds.getPermission(world))) {
//                            if (Worlds.isDenyMessageExist(world)) {
//                                p.sendMessage(Worlds.getDenyMessage(world));
//                            }
//                            e.setCancelled(true);
//                        }
//                    }
//                }
//
//            }
//            return;
//        }
//
//        // Flag: minecart
//        if (Settings.canExecute(world, "minecart")) {
//            if (!Worlds.getFlag(world, "minecart")) {
//                if (p instanceof Player) {
//                    if (target instanceof Minecart) {
//                        if (!p.hasPermission(Worlds.getPermission(world))) {
//                            if (Worlds.isDenyMessageExist(world)) {
//                                p.sendMessage(Worlds.getDenyMessage(world));
//                            }
//                            e.setCancelled(true);
//                        }
//                    }
//                }
//
//            }
//            return;
//        }

    }

    // Flag: frame (Damage)
    @EventHandler
    public void onFrameDamage(EntityDamageByEntityEvent e) {

        Entity p = e.getDamager();

        if (Hooks.cancel(p)) {
            return;
        }

        Entity target = e.getEntity();

        if (Hooks.cancel(target)) {
            return;
        }

        String world = target.getWorld().getName();

        if (!Flags.frame.isEnabled(world)) {
            return;
        }

        if (p.hasPermission(Worlds.getPermission(world))) {
            return;
        }



        if (FlagsManager.FrameIsIncludingGlowFrame()) {

            if (target instanceof GlowItemFrame) {

                if (p instanceof Player) {
                    if (Worlds.isDenyMessageExist(world)) {
                        p.sendMessage(Worlds.getDenyMessage(world));
                    }
                }
                e.setCancelled(true);
                return;

            }

        }

        if (target instanceof ItemFrame) {

            if (p instanceof Player) {
                if (Worlds.isDenyMessageExist(world)) {
                    p.sendMessage(Worlds.getDenyMessage(world));
                }
            }
            e.setCancelled(true);

        }



    }

    // Flag: tnt-damage
    @EventHandler
    public void onTNTDamage(EntityDamageByEntityEvent e) {

        Entity p = e.getDamager();

        if (Hooks.cancel(p)) {
            return;
        }

        Entity target = e.getEntity();

        if (Hooks.cancel(target)) {
            return;
        }

        String world = target.getWorld().getName();

        if (!Flags.tnt_damage.isEnabled(world)) {
            return;
        }

        if (p.getType() == EntityType.PRIMED_TNT || p.getType() == EntityType.MINECART_TNT) {

            if (Worlds.isDenyMessageExist(world)) {
                p.sendMessage(Worlds.getDenyMessage(world));
            }
            e.setCancelled(true);

        }
    }

    // Flag: armorstand (Damage)
    @EventHandler
    public void onArmorStandDamage(EntityDamageByEntityEvent e) {

        Entity p = e.getDamager();

        if (Hooks.cancel(p)) {
            return;
        }

        Entity target = e.getEntity();

        if (Hooks.cancel(target)) {
            return;
        }

        String world = target.getWorld().getName();

        if (!Flags.armorstand.isEnabled(world)) {
            return;
        }

        if (p.hasPermission(Worlds.getPermission(world))) {
            return;
        }


        if (p instanceof Player) {

            if (target instanceof ArmorStand || target.getType() == EntityType.ARMOR_STAND) {

                if (Worlds.isDenyMessageExist(world)) {
                    p.sendMessage(Worlds.getDenyMessage(world));
                }
                e.setCancelled(true);

            }

        }

    }

    // Flag: crystal
    @EventHandler
    public void onCrystalDamage(EntityDamageByEntityEvent e) {

        Entity p = e.getDamager();

        if (Hooks.cancel(p)) {
            return;
        }

        Entity target = e.getEntity();

        if (Hooks.cancel(target)) {
            return;
        }

        String world = target.getWorld().getName();

        if (!Flags.crystal.isEnabled(world)) {
            return;
        }

        if (p.hasPermission(Worlds.getPermission(world))) {
            return;
        }

        if (p instanceof Player) {
            if (target.getType() == EntityType.ENDER_CRYSTAL || target instanceof EnderCrystal) {
                if (Worlds.isDenyMessageExist(world)) {
                    p.sendMessage(Worlds.getDenyMessage(world));
                }
                e.setCancelled(true);
            }
        }

    }

    // Flag: Pvp
    @EventHandler
    public void onPVP(EntityDamageByEntityEvent e) {

        Entity p = e.getDamager();

        if (Hooks.cancel(p)) {
            return;
        }

        Entity target = e.getEntity();

        if (Hooks.cancel(target)) {
            return;
        }

        String world = target.getWorld().getName();

        if (!Flags.pvp.isEnabled(world)) {
            return;
        }

        if (p.hasPermission(Worlds.getPermission(world))) {
            return;
        }

        if (p instanceof Player && target instanceof Player) {

            if (Worlds.isDenyMessageExist(world)) {
                p.sendMessage(Worlds.getDenyMessage(world));
            }
            e.setCancelled(true);

        }
    }

    // Flag: Mob Explode
    @EventHandler
    public void onMobExplode(EntityExplodeEvent e) {

        Entity entity = e.getEntity();

        if (Hooks.cancel(entity)) {
            return;
        }

        String world = entity.getWorld().getName();

        if (!Flags.mob_explode.isEnabled(world)) {
            return;
        }

        if (Flags.mob_explode.getType().equalsIgnoreCase("all")) {

            e.setCancelled(true);
            return;

        }

        if (Flags.mob_explode.getType().equalsIgnoreCase("list")) {

            EntityType type = e.getEntityType();

            if (Flags.mob_explode.getList().contains(type.toString().toUpperCase())) {

                e.setCancelled(true);

            }


        }

    }

    // Flag: tnt
    @EventHandler
    public void onTNTExplokde(EntityExplodeEvent e) {

        Entity entity = e.getEntity();

        if (Hooks.cancel(entity)) {
            return;
        }

        String world = entity.getWorld().getName();

        if (!Flags.tnt.isEnabled(world)) {
            return;
        }

        EntityType type = e.getEntityType();

        if (type == EntityType.PRIMED_TNT || type == EntityType.MINECART_TNT) {
            e.setCancelled(true);
        }

    }

    // Flag: frame(move)
    @EventHandler
    public void onInteractEntity(PlayerInteractEntityEvent e) {

        Player p = e.getPlayer();

        if (Hooks.cancel(p)) {
            return;
        }

        Entity clicked = e.getRightClicked();

        if (Hooks.cancel(clicked)) {
            return;
        }

        String world = p.getWorld().getName();

        if (!Flags.frame.isEnabled(world)) {
            return;
        }

        if (p.hasPermission(Worlds.getPermission(world))) {
            return;
        }


        if (FlagsManager.FrameIsIncludingGlowFrame()) {

            if (clicked.getType() == EntityType.GLOW_ITEM_FRAME) {

                if (Worlds.isDenyMessageExist(world)) {
                    p.sendMessage(Worlds.getDenyMessage(world));
                }
                e.setCancelled(true);
                return;

            }

        }

        if (clicked.getType() == EntityType.ITEM_FRAME) {

            if (Worlds.isDenyMessageExist(world)) {
                p.sendMessage(Worlds.getDenyMessage(world));
            }
            e.setCancelled(true);

        }



    }

    // Flag: ride
    @EventHandler
    public void onRide(PlayerInteractEntityEvent e) {

        Player p = e.getPlayer();

        if (Hooks.cancel(p)) {
            return;
        }

        Entity clicked = e.getRightClicked();

        if (Hooks.cancel(clicked)) {
            return;
        }

        String world = p.getWorld().getName();

        if (!Flags.ride.isEnabled(world)) {
            return;
        }

        if (p.hasPermission(Worlds.getPermission(world))) {
            return;
        }

        if (Flags.ride.getType().equalsIgnoreCase("list")) {

            EntityType type = clicked.getType();
            if (Flags.ride.getList().contains(type.toString().toUpperCase())) {
                if (Worlds.isDenyMessageExist(world)) {
                    p.sendMessage(Worlds.getDenyMessage(world));
                }
                e.setCancelled(true);
            }


        }

    }

    // Flag: armor stand
    @EventHandler
    public void onInteractArmorStand(PlayerInteractEntityEvent e) {

        Player p = e.getPlayer();

        if (Hooks.cancel(p)) {
            return;
        }

        Entity clicked = e.getRightClicked();

        if (Hooks.cancel(clicked)) {
            return;
        }

        String world = p.getWorld().getName();

        if (!Flags.armorstand.isEnabled(world)) {
            return;
        }

        if (p.hasPermission(Worlds.getPermission(world))) {
            return;
        }


        if (clicked.getType() == EntityType.ARMOR_STAND || clicked instanceof ArmorStand) {

            if (Worlds.isDenyMessageExist(world)) {
                p.sendMessage(Worlds.getDenyMessage(world));
            }
            e.setCancelled(true);

        }

    }

    // Flag: villager
    @EventHandler
    public void onInteractVillager(PlayerInteractEntityEvent e) {

        Player p = e.getPlayer();

        if (Hooks.cancel(p)) {
            return;
        }

        Entity clicked = e.getRightClicked();

        if (Hooks.cancel(clicked)) {
            return;
        }

        if (clicked.getType() != EntityType.VILLAGER) {
            return;
        }

        String world = p.getWorld().getName();

        if (!Flags.villager.isEnabled(world)) {
            return;
        }

        if (p.hasPermission(Worlds.getPermission(world))) {
            return;
        }

        if (Worlds.isDenyMessageExist(world)) {
            p.sendMessage(Worlds.getDenyMessage(world));
        }
        e.setCancelled(true);

    }

    // Flag: Void Teleport
    @EventHandler
    public void onVoidDamage(EntityDamageEvent e) {

        Entity entity = e.getEntity();

        if (entity instanceof Player) {
            if (Hooks.cancel(entity)) {
                return;
            }
        }

        if (e.getCause() != EntityDamageEvent.DamageCause.VOID) {
            return;
        }

        String world = entity.getWorld().getName();

        if (!Flags.voidtp.isEnabled(world)) {
            return;
        }

        if (Worlds.isSpawnLocationSet(world)) {
            if (entity instanceof Player) {
                Player p = (Player) entity;
                p.teleport(Worlds.getSpawnLocation(world));
                p.setFallDistance(0);
            }
        }

    }

    // Flag: fall damage
    @EventHandler
    public void onFallDamage(EntityDamageEvent e) {
        Entity entity = e.getEntity();

        if (entity instanceof Player) {
            if (Hooks.cancel(entity)) {
                return;
            }
        }

        if (e.getCause() != EntityDamageEvent.DamageCause.FALL) {
            return;
        }

        String world = entity.getWorld().getName();

        if (!Flags.fall_damage.isEnabled(world)) {
            return;
        }

        if (entity instanceof Player) {
            e.setCancelled(true);
        }
    }


    // Flag: shoot
    @EventHandler
    public void onShoot(EntityShootBowEvent e) {

        Entity entity = e.getEntity();

        if (!(entity instanceof Player)) {
            return;
        }

        if (Hooks.cancel(entity)) {
            return;
        }

        String world = entity.getWorld().getName();

        if (!Flags.shoot.isEnabled(world)) {
            return;
        }

        Player p = (Player) entity;

        if (p.hasPermission(Worlds.getPermission(world))) {
            return;
        }

        Material mat = e.getBow().getType();

        if (mat == Material.BOW) {
            if (Worlds.isDenyMessageExist(world)) {
                p.sendMessage(Worlds.getDenyMessage(world));
            }
            e.setCancelled(true);
        }
        if (FlagsManager.getBoolInFlag("shoot", "include-crossbow")) {
            if (mat == Material.CROSSBOW) {
                if (Worlds.isDenyMessageExist(world)) {
                    p.sendMessage(Worlds.getDenyMessage(world));
                }
                e.setCancelled(true);
            }
        }
    }

    // Flag: armor stand
    @EventHandler
    public void onArmorStandSpawn(CreatureSpawnEvent e) {

        Entity entity = e.getEntity();

        if (Hooks.cancel(entity)) {
            return;
        }

        String world = entity.getWorld().getName();

        if (!Flags.armorstand.isEnabled(world)) {
            return;
        }

        if (e.getEntityType() == EntityType.ARMOR_STAND) {

            e.setCancelled(true);

        }

    }

    // Flag: armor stand (being created)
    @EventHandler
    public void onArmorStandPlaced(EntityPlaceEvent e) {

        Entity entity = e.getEntity();

        if (Hooks.cancel(entity)) {
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


            if (Worlds.isDenyMessageExist(world)) {
                p.sendMessage(Worlds.getDenyMessage(world));
            }
        }
        e.setCancelled(true);

    }

    // Flag: minecart (being created)
    @EventHandler
    public void onMinecartPlaced(EntityPlaceEvent e) {

        Entity entity = e.getEntity();

        if (Hooks.cancel(entity)) {
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

            if (Worlds.isDenyMessageExist(world)) {
                p.sendMessage(Worlds.getDenyMessage(world));
            }
        }

        e.setCancelled(true);

    }

    // Flag: crystal
    @EventHandler
    public void onCrystalPlaced(EntityPlaceEvent e) {

        Entity entity = e.getEntity();

        if (Hooks.cancel(entity)) {
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

            if (Worlds.isDenyMessageExist(world)) {
                p.sendMessage(Worlds.getDenyMessage(world));
            }
        }

        e.setCancelled(true);
    }

    // Flag: boat (being placed)
    @EventHandler
    public void onBoatPlaced(EntityPlaceEvent e) {

        Entity entity = e.getEntity();

        if (Hooks.cancel(entity)) {
            return;
        }

        String world = entity.getWorld().getName();

        if (!Flags.boat.isEnabled(world)) {
            return;
        }

        if (FlagsManager.BoatIsIncludingChestBoat()) {
            if (entity instanceof ChestBoat) {

                Player p = e.getPlayer();
                if (p != null) {
                    if (p.hasPermission(Worlds.getPermission(world))) {
                        return;
                    }
                    if (Worlds.isDenyMessageExist(world)) {
                        p.sendMessage(Worlds.getDenyMessage(world));
                    }
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
                if (Worlds.isDenyMessageExist(world)) {
                    p.sendMessage(Worlds.getDenyMessage(world));
                }
            }
            e.setCancelled(true);
        }
    }

    // Flag: farmbreak (by entity)
    @EventHandler
    public void onFarmBreak(EntityInteractEvent e) {

        Entity entity = e.getEntity();

        if (Hooks.cancel(entity)) {
            return;
        }

        String world = entity.getWorld().getName();

        if (!Flags.farmbreak.isEnabled(world)) {
            return;
        }

        Material mat = e.getBlock().getType();

        if (mat == Material.matchMaterial("SOIL") || mat == Material.matchMaterial("FARMLAND")) {

            e.setCancelled(true);

        }

    }

    // Flag: mob spawn
    @EventHandler
    public void onMobSpawn(EntitySpawnEvent e) {

        Entity entity = e.getEntity();

        if (Hooks.cancel(entity)) {
            return;
        }

        String world = entity.getWorld().getName();

        if (!Flags.mob_spawn.isEnabled(world)) {
            return;
        }

        if (Flags.mob_spawn.getType().equalsIgnoreCase("list")) {

            EntityType et = e.getEntityType();

            if (Flags.mob_spawn.getList().contains(et.toString().toUpperCase())) {

                e.setCancelled(true);

            }

        }

    }

    // Flag: potion
    @EventHandler
    public void onPotionEffect(EntityPotionEffectEvent e) {

        Entity entity = e.getEntity();

        if (!(entity instanceof Player)) {
            return;
        }

        if (Hooks.cancel(entity)) {
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

        if (Worlds.isDenyMessageExist(world)) {
            p.sendMessage(Worlds.getDenyMessage(world));
        }

        e.setCancelled(true);

    }

    // Flag: elytra
    @EventHandler
    public void onGlide(EntityToggleGlideEvent e) {

        Entity entity = e.getEntity();

        if (!(entity instanceof Player)) {
            return;
        }

        if (Hooks.cancel(entity)) {
            return;
        }

        String world = entity.getWorld().getName();

        if (!Flags.elytra.isEnabled(world)) {
            return;
        }

        Player p = (Player) entity;

        if (p.hasPermission(Worlds.getPermission(world))) {
            return;
        }


        if (e.isGliding()) {
            if (p.getInventory().getChestplate().getType() == Material.ELYTRA) {

                if (Worlds.isDenyMessageExist(world)) {
                    p.sendMessage(Worlds.getDenyMessage(world));
                }
                e.setCancelled(true);

            }

        }


    }

    // Flag: nether
    @EventHandler
    public void onPortalCreate(PortalCreateEvent e) {

        Entity entity = e.getEntity();

        if (!(entity instanceof Player)) {
            return;
        }

        if (Hooks.cancel(entity)) {
            return;
        }

        String world = entity.getWorld().getName();

        if (!Flags.nether.isEnabled(world)) {
            return;
        }

        Player p = (Player) entity;

        if (p.hasPermission(Worlds.getPermission(world))) {
            return;
        }

        List<BlockState> blocks = e.getBlocks();

        for (BlockState block : blocks) {

            if (block.getType() == Material.OBSIDIAN) {

                if (Worlds.isDenyMessageExist(world)) {
                    p.sendMessage(Worlds.getDenyMessage(world));
                }
                e.setCancelled(true);
                return;
            }
            return;
        }

    }

    // Flag: fire-spawn (blaze hitting)
    @EventHandler
    public void onProjectileHit(ProjectileHitEvent e) {
        Entity entity = e.getEntity();

        if (Hooks.cancel(entity)) {
            return;
        }

        if (!(entity instanceof Blaze)) {
            return;
        }

        String world = entity.getWorld().getName();

        if (!Flags.fire_spawn.isEnabled(world)) {
            return;
        }

        e.setCancelled(true);

    }
}
