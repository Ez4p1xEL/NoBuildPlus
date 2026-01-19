package p1xel.nobuildplus.listener;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.*;
import org.bukkit.entity.minecart.ExplosiveMinecart;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.weather.LightningStrikeEvent;
import org.bukkit.event.world.PortalCreateEvent;
import p1xel.nobuildplus.Flags;
import p1xel.nobuildplus.hook.HookedPlugins;
import p1xel.nobuildplus.storage.FlagsManager;
import p1xel.nobuildplus.storage.Worlds;

import java.util.List;

public class NoBuildPlusEntityListener implements Listener {

    // Flag: Mob Damage
    @EventHandler
    public void onMobDamage(EntityDamageByEntityEvent e) {

        Entity p = e.getDamager();
        Entity target = e.getEntity();

        if (HookedPlugins.cancel(p)) {
            return;
        }

        if (HookedPlugins.cancel(target)) {
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

                Worlds.sendMessage((Player) p, world);
                e.setCancelled(true);
                return;

            }

        }

        if (Flags.mob_damage.getType().equalsIgnoreCase("list")) {

            if (p instanceof Player && !(target instanceof Player)) {

                EntityType type = e.getEntityType();

                if (Flags.mob_damage.getList().contains(type.toString().toUpperCase())) {

                    Worlds.sendMessage((Player) p, world);
                    e.setCancelled(true);

                }

            }


        }

    }

    // Flag: frame (Damage)
    @EventHandler
    public void onFrameDamage(EntityDamageByEntityEvent e) {

        Entity p = e.getDamager();

        if (HookedPlugins.cancel(p)) {
            return;
        }

        Entity target = e.getEntity();

        if (HookedPlugins.cancel(target)) {
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
                    Worlds.sendMessage((Player) p, world);
                }
                e.setCancelled(true);
                return;

            }

        }

        if (target instanceof ItemFrame) {

            if (p instanceof Player) {
                Worlds.sendMessage((Player) p, world);
            }
            e.setCancelled(true);

        }



    }

    // Flag: tnt-damage
    @EventHandler
    public void onTNTDamage(EntityDamageByEntityEvent e) {

        Entity p = e.getDamager();

        if (HookedPlugins.cancel(p)) {
            return;
        }

        Entity target = e.getEntity();

        if (HookedPlugins.cancel(target)) {
            return;
        }

        String world = target.getWorld().getName();

        if (!Flags.tnt_damage.isEnabled(world)) {
            return;
        }

        if (p instanceof TNTPrimed || p instanceof ExplosiveMinecart) {

            e.setCancelled(true);

        }
    }

    // Flag: armorstand (Damage)
    @EventHandler
    public void onArmorStandDamage(EntityDamageByEntityEvent e) {

        Entity p = e.getDamager();

        if (HookedPlugins.cancel(p)) {
            return;
        }

        Entity target = e.getEntity();

        if (HookedPlugins.cancel(target)) {
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

                Worlds.sendMessage((Player) p, world);
                e.setCancelled(true);

            }

        }

    }

    // Flag: crystal
    @EventHandler
    public void onCrystalDamage(EntityDamageByEntityEvent e) {

        Entity p = e.getDamager();

        if (HookedPlugins.cancel(p)) {
            return;
        }

        Entity target = e.getEntity();

        if (HookedPlugins.cancel(target)) {
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
            if (target instanceof EnderCrystal) {
                Worlds.sendMessage((Player) p, world);
                e.setCancelled(true);
            }
        }

    }

    // Flag: Pvp
    @EventHandler
    public void onPVP(EntityDamageByEntityEvent e) {

        Entity p = e.getDamager();

        if (HookedPlugins.cancel(p)) {
            return;
        }

        Entity target = e.getEntity();

        if (HookedPlugins.cancel(target)) {
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

            Worlds.sendMessage((Player)p, world);
            e.setCancelled(true);

        }
    }

    // Flag: Mob Explode
    @EventHandler
    public void onMobExplode(EntityExplodeEvent e) {

        Entity entity = e.getEntity();

        if (HookedPlugins.cancel(entity)) {
            return;
        }

        if (entity instanceof TNTPrimed || entity instanceof ExplosiveMinecart) {
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
    public void onTNTExplode(EntityExplodeEvent e) {

        Entity entity = e.getEntity();

        if (HookedPlugins.cancel(entity)) {
            return;
        }

        if (!(entity instanceof TNTPrimed || entity instanceof ExplosiveMinecart)) {
            return;
        }

        String world = entity.getWorld().getName();

        if (!Flags.tnt.isEnabled(world)) {
            return;
        }

        e.blockList().clear();

    }

    // Flag: frame(move)
    @EventHandler
    public void onInteractEntity(PlayerInteractEntityEvent e) {

        Player p = e.getPlayer();

        if (HookedPlugins.cancel(p)) {
            return;
        }

        Entity clicked = e.getRightClicked();

        if (HookedPlugins.cancel(clicked)) {
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

                Worlds.sendMessage(p, world);
                e.setCancelled(true);
                return;

            }

        }

        if (clicked.getType() == EntityType.ITEM_FRAME) {

            Worlds.sendMessage(p, world);
            e.setCancelled(true);

        }



    }

    // Flag: ride
    @EventHandler
    public void onRide(PlayerInteractEntityEvent e) {

        Player p = e.getPlayer();

        if (HookedPlugins.cancel(p)) {
            return;
        }

        Entity clicked = e.getRightClicked();

        if (HookedPlugins.cancel(clicked)) {
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
                Worlds.sendMessage(p, world);
                e.setCancelled(true);
            }


        }

    }

    // Flag: armor stand
    @EventHandler
    public void onInteractArmorStand(PlayerInteractEntityEvent e) {

        Player p = e.getPlayer();

        if (HookedPlugins.cancel(p)) {
            return;
        }

        Entity clicked = e.getRightClicked();

        if (HookedPlugins.cancel(clicked)) {
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

            Worlds.sendMessage(p, world);
            e.setCancelled(true);

        }

    }

    // Flag: villager
    @EventHandler
    public void onInteractVillager(PlayerInteractEntityEvent e) {

        Player p = e.getPlayer();

        if (HookedPlugins.cancel(p)) {
            return;
        }

        Entity clicked = e.getRightClicked();

        if (HookedPlugins.cancel(clicked)) {
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

        Worlds.sendMessage(p, world);
        e.setCancelled(true);

    }

    // Flag: Void Teleport
    @EventHandler
    public void onVoidDamage(EntityDamageEvent e) {

        Entity entity = e.getEntity();

        if (entity instanceof Player) {
            if (HookedPlugins.cancel(entity)) {
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
            if (HookedPlugins.cancel(entity)) {
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

        if (HookedPlugins.cancel(entity)) {
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
        Material crossbow = Material.matchMaterial("CROSSBOW");

        if (mat == Material.BOW || (crossbow != null && mat == crossbow)) {
            Worlds.sendMessage(p, world);
            e.setCancelled(true);
        }
    }

    // Flag: armor stand
    @EventHandler
    public void onArmorStandSpawn(CreatureSpawnEvent e) {

        Entity entity = e.getEntity();

        if (HookedPlugins.cancel(entity)) {
            return;
        }

        String world = entity.getWorld().getName();

        if (!Flags.armorstand.isEnabled(world)) {
            return;
        }

        if (e.getEntityType() == EntityType.ARMOR_STAND) {

            if (FlagsManager.getBoolInFlag("armorstand", "placement")) {
                e.setCancelled(true);
            }

        }

    }



    // Flag: farmbreak (by entity)
    @EventHandler
    public void onFarmBreak(EntityInteractEvent e) {

        Entity entity = e.getEntity();

        if (HookedPlugins.cancel(entity)) {
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

        if (HookedPlugins.cancel(entity)) {
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

    // Flag: nether
    @EventHandler
    public void onPortalCreate(PortalCreateEvent e) {

        Entity entity = e.getEntity();

        if (!(entity instanceof Player)) {
            return;
        }

        if (HookedPlugins.cancel(entity)) {
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

                Worlds.sendMessage(p, world);
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

        if (HookedPlugins.cancel(entity)) {
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

    // Flag: heal
    @EventHandler
    public void onPlayerRegain(EntityRegainHealthEvent e) {

        Entity entity = e.getEntity();
        if (HookedPlugins.cancel(entity)) {
            return;
        }

        if (!(entity instanceof Player)) {
            return;
        }

        String world = entity.getWorld().getName();

        if (!Flags.heal.isEnabled(world)) {
            return;
        }

        e.setCancelled(true);

    }

    // Flag: lightning
    @EventHandler
    public void onLightning(LightningStrikeEvent e) {

        LightningStrike lightning = e.getLightning();
        Location loc = lightning.getLocation();

        if (HookedPlugins.cancel(loc)) {
            return;
        }

        String world = loc.getWorld().getName();

        if (!Flags.lightning.isEnabled(world)) {
            return;
        }

        if (FlagsManager.getBoolInFlag("lightning", "lightning-only")) {
            lightning.setFireTicks(0);
        } else {
            e.setCancelled(true);
        }

    }

    // Flag: pressure-plate
    @EventHandler
    public void onInteractEntity(EntityInteractEvent e) {
        Block block = e.getBlock();
        if (!Flags.pressure_plate.getList().contains(block.getType().toString())) {
            return;
        }

        if (HookedPlugins.cancel(block)) {
            return;
        }

        String world = block.getWorld().getName();

        if (!Flags.pressure_plate.isEnabled(world)) {
            return;
        }

        e.setCancelled(true);

    }
}
