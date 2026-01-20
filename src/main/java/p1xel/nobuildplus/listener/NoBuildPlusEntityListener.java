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
import p1xel.nobuildplus.world.ProtectedWorld;
import p1xel.nobuildplus.world.WorldManager;

import java.util.List;

public class NoBuildPlusEntityListener implements Listener {

    // Flag: Mob Damage
    @EventHandler(ignoreCancelled = true)
    public void onMobDamage(EntityDamageByEntityEvent e) {

        Entity player = e.getDamager();
        Entity target = e.getEntity();

        if (HookedPlugins.cancel(target)) {
            return;
        }

        String worldName = target.getWorld().getName();
        ProtectedWorld world = WorldManager.getWorld(worldName);

        if (!Flags.mob_damage.isEnabled(world)) {
            return;
        }

        if (player.hasPermission(world.getPermission())) {
            return;
        }

        if (Flags.mob_damage.getType().equalsIgnoreCase("all")) {

            if (player instanceof Player && !(target instanceof Player)) {

                WorldManager.sendMessage((Player) player, world);
                e.setCancelled(true);
                return;

            }

        }

        if (Flags.mob_damage.getType().equalsIgnoreCase("list")) {

            if (player instanceof Player && !(target instanceof Player)) {

                EntityType type = e.getEntityType();

                if (Flags.mob_damage.getList().contains(type.toString().toUpperCase())) {

                    WorldManager.sendMessage((Player) player, world);
                    e.setCancelled(true);

                }

            }


        }

    }

    // Flag: frame (Damage)
    @EventHandler(ignoreCancelled = true)
    public void onFrameDamage(EntityDamageByEntityEvent e) {

        Entity player = e.getDamager();

        Entity target = e.getEntity();

        if (HookedPlugins.cancel(target)) {
            return;
        }

        String worldName = target.getWorld().getName();
        ProtectedWorld world = WorldManager.getWorld(worldName);

        if (!Flags.frame.isEnabled(world)) {
            return;
        }

        if (player.hasPermission(world.getPermission())) {
            return;
        }



        if (FlagsManager.FrameIsIncludingGlowFrame()) {

            if (target instanceof GlowItemFrame) {

                if (player instanceof Player) {
                    WorldManager.sendMessage((Player) player, world);
                }
                e.setCancelled(true);
                return;

            }

        }

        if (target instanceof ItemFrame) {

            if (player instanceof Player) {
                WorldManager.sendMessage((Player) player, world);
            }
            e.setCancelled(true);

        }



    }

    // Flag: tnt-damage
    @EventHandler(ignoreCancelled = true)
    public void onTNTDamage(EntityDamageByEntityEvent e) {

        Entity entity = e.getDamager();

        if (HookedPlugins.cancel(entity)) {
            return;
        }

        Entity target = e.getEntity();

        if (HookedPlugins.cancel(target)) {
            return;
        }

        String worldName = entity.getWorld().getName();
        ProtectedWorld world = WorldManager.getWorld(worldName);

        if (!Flags.tnt_damage.isEnabled(world)) {
            return;
        }

        if (entity instanceof TNTPrimed || entity instanceof ExplosiveMinecart) {

            e.setCancelled(true);

        }
    }

    // Flag: armorstand (Damage)
    @EventHandler(ignoreCancelled = true)
    public void onArmorStandDamage(EntityDamageByEntityEvent e) {

        Entity target = e.getEntity();

        if (HookedPlugins.cancel(target)) {
            return;
        }

        Entity player = e.getDamager();

        String worldName = player.getWorld().getName();
        ProtectedWorld world = WorldManager.getWorld(worldName);

        if (!Flags.armorstand.isEnabled(world)) {
            return;
        }

        if (player.hasPermission(world.getPermission())) {
            return;
        }


        if (player instanceof Player) {

            if (target instanceof ArmorStand || target.getType() == EntityType.ARMOR_STAND) {

                WorldManager.sendMessage((Player) player, world);
                e.setCancelled(true);

            }

        }

    }

    // Flag: crystal
    @EventHandler(ignoreCancelled = true)
    public void onCrystalDamage(EntityDamageByEntityEvent e) {

        Entity player = e.getDamager();

        if (HookedPlugins.cancel(player)) {
            return;
        }

        Entity target = e.getEntity();

        if (HookedPlugins.cancel(target)) {
            return;
        }

        String worldName = player.getWorld().getName();
        ProtectedWorld world = WorldManager.getWorld(worldName);

        if (!Flags.crystal.isEnabled(world)) {
            return;
        }

        if (player.hasPermission(world.getPermission())) {
            return;
        }

        if (player instanceof Player) {
            if (target instanceof EnderCrystal) {
                WorldManager.sendMessage((Player) player, world);
                e.setCancelled(true);
            }
        }

    }

    // Flag: Pvp
    @EventHandler(ignoreCancelled = true)
    public void onPVP(EntityDamageByEntityEvent e) {

        Entity player = e.getDamager();

        if (HookedPlugins.cancel(player)) {
            return;
        }

        Entity target = e.getEntity();

        if (HookedPlugins.cancel(target)) {
            return;
        }

        String worldName = player.getWorld().getName();
        ProtectedWorld world = WorldManager.getWorld(worldName);

        if (!Flags.pvp.isEnabled(world)) {
            return;
        }

        if (player.hasPermission(world.getPermission())) {
            return;
        }

        if (player instanceof Player && target instanceof Player) {

            WorldManager.sendMessage((Player)player, world);
            e.setCancelled(true);

        }
    }

    // Flag: Mob Explode
    @EventHandler(ignoreCancelled = true)
    public void onMobExplode(EntityExplodeEvent e) {

        Entity entity = e.getEntity();

        if (HookedPlugins.cancel(entity)) {
            return;
        }

        if (entity instanceof TNTPrimed || entity instanceof ExplosiveMinecart) {
            return;
        }

        String worldName = entity.getWorld().getName();
        ProtectedWorld world = WorldManager.getWorld(worldName);

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
    @EventHandler(ignoreCancelled = true)
    public void onTNTExplode(EntityExplodeEvent e) {

        Entity entity = e.getEntity();

        if (HookedPlugins.cancel(entity)) {
            return;
        }

        if (!(entity instanceof TNTPrimed || entity instanceof ExplosiveMinecart)) {
            return;
        }

        String worldName = entity.getWorld().getName();
        ProtectedWorld world = WorldManager.getWorld(worldName);

        if (!Flags.tnt.isEnabled(world)) {
            return;
        }

        e.blockList().clear();

    }

    // Flag: frame(move)
    @EventHandler(ignoreCancelled = true)
    public void onInteractEntity(PlayerInteractEntityEvent e) {

        Player player = e.getPlayer();

        if (HookedPlugins.cancel(player)) {
            return;
        }

        Entity clicked = e.getRightClicked();

        if (HookedPlugins.cancel(clicked)) {
            return;
        }

        String worldName = player.getWorld().getName();
        ProtectedWorld world = WorldManager.getWorld(worldName);

        if (!Flags.frame.isEnabled(world)) {
            return;
        }

        if (player.hasPermission(world.getPermission())) {
            return;
        }


        if (FlagsManager.FrameIsIncludingGlowFrame()) {

            if (clicked.getType() == EntityType.GLOW_ITEM_FRAME) {

                WorldManager.sendMessage(player, world);
                e.setCancelled(true);
                return;

            }

        }

        if (clicked.getType() == EntityType.ITEM_FRAME) {

            WorldManager.sendMessage(player, world);
            e.setCancelled(true);

        }



    }

    // Flag: ride
    @EventHandler(ignoreCancelled = true)
    public void onRide(PlayerInteractEntityEvent e) {

        Player player = e.getPlayer();

        if (HookedPlugins.cancel(player)) {
            return;
        }

        Entity clicked = e.getRightClicked();

        if (HookedPlugins.cancel(clicked)) {
            return;
        }

        String worldName = player.getWorld().getName();
        ProtectedWorld world = WorldManager.getWorld(worldName);

        if (!Flags.ride.isEnabled(world)) {
            return;
        }

        if (player.hasPermission(world.getPermission())) {
            return;
        }

        if (Flags.ride.getType().equalsIgnoreCase("list")) {

            EntityType type = clicked.getType();
            if (Flags.ride.getList().contains(type.toString().toUpperCase())) {
                WorldManager.sendMessage(player, world);
                e.setCancelled(true);
            }


        }

    }

    // Flag: armor stand
    @EventHandler(ignoreCancelled = true)
    public void onInteractArmorStand(PlayerInteractEntityEvent e) {

        Player player = e.getPlayer();

        if (HookedPlugins.cancel(player)) {
            return;
        }

        Entity clicked = e.getRightClicked();

        if (HookedPlugins.cancel(clicked)) {
            return;
        }

        String worldName = player.getWorld().getName();
        ProtectedWorld world = WorldManager.getWorld(worldName);

        if (!Flags.armorstand.isEnabled(world)) {
            return;
        }

        if (player.hasPermission(world.getPermission())) {
            return;
        }


        if (clicked.getType() == EntityType.ARMOR_STAND || clicked instanceof ArmorStand) {

            WorldManager.sendMessage(player, world);
            e.setCancelled(true);

        }

    }

    // Flag: villager
    @EventHandler(ignoreCancelled = true)
    public void onInteractVillager(PlayerInteractEntityEvent e) {

        Entity clicked = e.getRightClicked();

        if (HookedPlugins.cancel(clicked)) {
            return;
        }

        if (clicked.getType() != EntityType.VILLAGER) {
            return;
        }

        Player player = e.getPlayer();

        String worldName = player.getWorld().getName();
        ProtectedWorld world = WorldManager.getWorld(worldName);

        if (!Flags.villager.isEnabled(world)) {
            return;
        }

        if (player.hasPermission(world.getPermission())) {
            return;
        }

        WorldManager.sendMessage(player, world);
        e.setCancelled(true);

    }

    // Flag: Void Teleport
    @EventHandler(ignoreCancelled = true)
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

        String worldName = entity.getWorld().getName();
        ProtectedWorld world = WorldManager.getWorld(worldName);

        if (!Flags.voidtp.isEnabled(world)) {
            return;
        }

        if (world.getLocation() != null) {
            if (entity instanceof Player) {
                Player player = (Player) entity;
                player.teleport(world.getLocation());
                player.setFallDistance(0);
                e.setCancelled(true);
            }
        }

    }

    // Flag: fall damage
    @EventHandler(ignoreCancelled = true)
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

        String worldName = entity.getWorld().getName();
        ProtectedWorld world = WorldManager.getWorld(worldName);

        if (!Flags.fall_damage.isEnabled(world)) {
            return;
        }

        if (entity instanceof Player) {
            e.setCancelled(true);
        }
    }


    // Flag: shoot
    @EventHandler(ignoreCancelled = true)
    public void onShoot(EntityShootBowEvent e) {

        Entity entity = e.getEntity();

        if (!(entity instanceof Player)) {
            return;
        }

        if (HookedPlugins.cancel(entity)) {
            return;
        }

        String worldName = entity.getWorld().getName();
        ProtectedWorld world = WorldManager.getWorld(worldName);

        if (!Flags.shoot.isEnabled(world)) {
            return;
        }

        Player p = (Player) entity;

        if (p.hasPermission(world.getPermission())) {
            return;
        }

        Material mat = e.getBow().getType();
        Material crossbow = Material.matchMaterial("CROSSBOW");

        if (mat == Material.BOW || (crossbow != null && mat == crossbow)) {
            WorldManager.sendMessage(p, world);
            e.setCancelled(true);
        }
    }

    // Flag: armor stand
    @EventHandler(ignoreCancelled = true)
    public void onArmorStandSpawn(CreatureSpawnEvent e) {

        Entity entity = e.getEntity();

        if (HookedPlugins.cancel(entity)) {
            return;
        }

        String worldName = entity.getWorld().getName();
        ProtectedWorld world = WorldManager.getWorld(worldName);

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
    @EventHandler(ignoreCancelled = true)
    public void onFarmBreak(EntityInteractEvent e) {

        Entity entity = e.getEntity();

        if (HookedPlugins.cancel(entity)) {
            return;
        }

        String worldName = entity.getWorld().getName();
        ProtectedWorld world = WorldManager.getWorld(worldName);

        if (!Flags.farmbreak.isEnabled(world)) {
            return;
        }

        Material mat = e.getBlock().getType();

        if (mat == Material.matchMaterial("SOIL") || mat == Material.matchMaterial("FARMLAND")) {

            e.setCancelled(true);

        }

    }

    // Flag: mob spawn
    @EventHandler(ignoreCancelled = true)
    public void onMobSpawn(EntitySpawnEvent e) {

        Entity entity = e.getEntity();

        if (HookedPlugins.cancel(entity)) {
            return;
        }

        String worldName = entity.getWorld().getName();
        ProtectedWorld world = WorldManager.getWorld(worldName);

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
    @EventHandler(ignoreCancelled = true)
    public void onPortalCreate(PortalCreateEvent e) {

        Entity entity = e.getEntity();

        if (!(entity instanceof Player)) {
            return;
        }

        if (HookedPlugins.cancel(entity)) {
            return;
        }

        String worldName = entity.getWorld().getName();
        ProtectedWorld world = WorldManager.getWorld(worldName);

        if (!Flags.nether.isEnabled(world)) {
            return;
        }

        Player p = (Player) entity;

        if (p.hasPermission(world.getPermission())) {
            return;
        }

        List<BlockState> blocks = e.getBlocks();

        for (BlockState block : blocks) {

            if (block.getType() == Material.OBSIDIAN) {

                WorldManager.sendMessage(p, world);
                e.setCancelled(true);
                return;
            }
            return;
        }

    }

    // Flag: fire-spawn (blaze hitting)
    @EventHandler(ignoreCancelled = true)
    public void onProjectileHit(ProjectileHitEvent e) {
        Entity entity = e.getEntity();

        if (HookedPlugins.cancel(entity)) {
            return;
        }

        if (!(entity instanceof Blaze)) {
            return;
        }

        String worldName = entity.getWorld().getName();
        ProtectedWorld world = WorldManager.getWorld(worldName);

        if (!Flags.fire_spawn.isEnabled(world)) {
            return;
        }

        e.setCancelled(true);

    }

    // Flag: heal
    @EventHandler(ignoreCancelled = true)
    public void onPlayerRegain(EntityRegainHealthEvent e) {

        Entity entity = e.getEntity();
        if (HookedPlugins.cancel(entity)) {
            return;
        }

        if (!(entity instanceof Player)) {
            return;
        }

        String worldName = entity.getWorld().getName();
        ProtectedWorld world = WorldManager.getWorld(worldName);

        if (!Flags.heal.isEnabled(world)) {
            return;
        }

        e.setCancelled(true);

    }

    // Flag: lightning
    @EventHandler(ignoreCancelled = true)
    public void onLightning(LightningStrikeEvent e) {

        LightningStrike lightning = e.getLightning();
        Location location = lightning.getLocation();

        if (HookedPlugins.cancel(location)) {
            return;
        }

        String worldName = location.getWorld().getName();
        ProtectedWorld world = WorldManager.getWorld(worldName);

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
    @EventHandler(ignoreCancelled = true)
    public void onInteractEntity(EntityInteractEvent e) {
        Block block = e.getBlock();
        if (!Flags.pressure_plate.getList().contains(block.getType().toString())) {
            return;
        }

        if (HookedPlugins.cancel(block)) {
            return;
        }

        String worldName = block.getWorld().getName();
        ProtectedWorld world = WorldManager.getWorld(worldName);

        if (!Flags.pressure_plate.isEnabled(world)) {
            return;
        }

        e.setCancelled(true);

    }
}
