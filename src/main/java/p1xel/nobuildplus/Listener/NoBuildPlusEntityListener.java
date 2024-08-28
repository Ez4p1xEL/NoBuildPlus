package p1xel.nobuildplus.Listener;

import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.world.PortalCreateEvent;
import p1xel.nobuildplus.Hook.HRes;
import p1xel.nobuildplus.Storage.FlagsManager;
import p1xel.nobuildplus.Storage.Settings;
import p1xel.nobuildplus.Storage.Worlds;

import java.util.List;

public class NoBuildPlusEntityListener implements Listener {

    // Flag: Mob Damage
    // Flag: Pvp
    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {

        String world = e.getEntity().getWorld().getName();
        Entity p = e.getDamager();
        Entity target = e.getEntity();

        if (HRes.isInRes(p)) {
            return;
        }

        if (HRes.isInRes(target)) {
            return;
        }

        String mobDamage = "mob-damage";
        // Flag: Mob Damage
        if (Settings.canExecute(world, mobDamage)) {

            if (!Worlds.getFlag(world, mobDamage)) {

                if (!p.hasPermission(Worlds.getPermission(world))) {

                    if (FlagsManager.getFlagsType(mobDamage).equalsIgnoreCase("all")) {

                        if (p instanceof Player && !(target instanceof Player)) {

                            if (Worlds.isDenyMessageExist(world)) {
                                p.sendMessage(Worlds.getDenyMessage(world));
                            }
                            e.setCancelled(true);
                            return;

                        }

                    }

                    if (FlagsManager.getFlagsType(mobDamage).equalsIgnoreCase("list")) {

                        if (p instanceof Player && !(target instanceof Player)) {

                            EntityType type = e.getEntityType();

                            if (FlagsManager.getFlagsList("mob-damage").contains(type.toString().toUpperCase())) {

                                if (Worlds.isDenyMessageExist(world)) {
                                    p.sendMessage(Worlds.getDenyMessage(world));
                                }
                                e.setCancelled(true);
                                return;

                            }

                        }

                    }

                }
            }
        }


        String frame = "frame";

        // Flag: Frame (Damage)
        if (Settings.canExecute(world, frame)) {

            if (!Worlds.getFlag(world, frame)) {

                if (!p.hasPermission(Worlds.getPermission(world))) {

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
                        return;

                    }


                }

            }
        }

        String tntDamage = "tnt-damage";
        // Flag: tnt-damage
        if (e.getDamager().getType() == EntityType.PRIMED_TNT || e.getDamager().getType() == EntityType.MINECART_TNT) {
            if (Settings.canExecute(world, tntDamage)) {

                if (!Worlds.getFlag(world, tntDamage)) {

                    if (Worlds.isDenyMessageExist(world)) {
                        p.sendMessage(Worlds.getDenyMessage(world));
                    }
                    e.setCancelled(true);
                    return;

                }
            }
        }

        String armorstand = "armorstand";

        // Flag: Armor Stand (Damage)
        if (Settings.canExecute(world, armorstand)) {

            if (!Worlds.getFlag(world, armorstand)) {

                if (!p.hasPermission(Worlds.getPermission(world))) {

                    if (p instanceof Player) {

                        if (target instanceof ArmorStand || target.getType() == EntityType.ARMOR_STAND) {

                            if (Worlds.isDenyMessageExist(world)) {
                                p.sendMessage(Worlds.getDenyMessage(world));
                            }
                            e.setCancelled(true);
                            return;

                        }

                    }

                }
            }

        }

        String pvp = "pvp";
        // Flag: Pvp
        if (Settings.canExecute(world, pvp)) {

            if (Worlds.getFlag(world, pvp)) {
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


    }

    // Flag: Mob Explode
    @EventHandler
    public void onMobExplode(EntityExplodeEvent e) {

        String world = e.getEntity().getWorld().getName();

        if (HRes.isInRes(e.getEntity())) {
            return;
        }

        String mobExplode = "mob-explode";

        if (Settings.canExecute(world, mobExplode)) {

            if (!Worlds.getFlag(world, mobExplode)) {

                if (FlagsManager.getFlagsType(mobExplode).equalsIgnoreCase("all")) {

                    e.setCancelled(true);
                    return;

                }

                if (FlagsManager.getFlagsType(mobExplode).equalsIgnoreCase("list")) {

                    EntityType type = e.getEntityType();

                    if (FlagsManager.getFlagsList("mob-explode").contains(type.toString().toUpperCase())) {

                        e.setCancelled(true);
                        return;

                    }

                }


            }

        }

        String tnt = "tnt";

        // Flag: Tnt
        if (Settings.canExecute(world, tnt)) {

            if (!Worlds.getFlag(world, tnt)) {

                if (e.getEntityType() == EntityType.PRIMED_TNT || e.getEntityType() == EntityType.MINECART_TNT) {
                    e.setCancelled(true);
                }

            }
        }

    }

    // Flag: frame(move)
    // Flag: Armor stand
    // Flag: villager
    @EventHandler
    public void onInteractEntity(PlayerInteractEntityEvent e) {

        String world = e.getPlayer().getWorld().getName();
        Player p = e.getPlayer();

        if (HRes.isInRes(p)) {
            return;
        }

        if (HRes.isInRes(e.getRightClicked())) {
            return;
        }

        String frame = "frame";

        if (Settings.canExecute(world, frame)) {

            if (!Worlds.getFlag(world, frame)) {

                if (!p.hasPermission(Worlds.getPermission(world))) {

                    if (FlagsManager.FrameIsIncludingGlowFrame()) {

                        if (e.getRightClicked().getType() == EntityType.GLOW_ITEM_FRAME) {

                            if (Worlds.isDenyMessageExist(world)) {
                                p.sendMessage(Worlds.getDenyMessage(world));
                            }
                            e.setCancelled(true);
                            return;

                        }

                    }

                    if (e.getRightClicked().getType() == EntityType.ITEM_FRAME) {

                        if (Worlds.isDenyMessageExist(world)) {
                            p.sendMessage(Worlds.getDenyMessage(world));
                        }
                        e.setCancelled(true);
                        return;

                    }

                }

            }

        }

        String ride = "ride";

        // Flag: Ride
        if (Settings.canExecute(world, ride)) {

            if (!Worlds.getFlag(world, ride)) {

                if (!p.hasPermission(Worlds.getPermission(world))) {

                    if (FlagsManager.getFlagsType(ride).equalsIgnoreCase("list")) {

                        EntityType type = e.getRightClicked().getType();
                        if (FlagsManager.getFlagsList("ride").contains(type.toString().toUpperCase())) {
                            if (Worlds.isDenyMessageExist(world)) {
                                p.sendMessage(Worlds.getDenyMessage(world));
                            }
                            e.setCancelled(true);
                            return;
                        }

                    }
                }
            }

        }

        String armorstand = "armorstand";

        if (Settings.canExecute(world, armorstand)) {

            if (!Worlds.getFlag(world, armorstand)) {

                if (!p.hasPermission(Worlds.getPermission(world))) {

                    if (e.getRightClicked().getType() == EntityType.ARMOR_STAND || e.getRightClicked() instanceof ArmorStand) {

                        if (Worlds.isDenyMessageExist(world)) {
                            p.sendMessage(Worlds.getDenyMessage(world));
                        }
                        e.setCancelled(true);
                        return;

                    }

                }

            }

        }

        if (e.getRightClicked().getType() == EntityType.VILLAGER) {

            String villager = "villager";

            if (Settings.canExecute(world, villager)) {

                if (!Worlds.getFlag(world, villager)) {

                    if (!p.hasPermission(Worlds.getPermission(world))) {

                        if (Worlds.isDenyMessageExist(world)) {
                            p.sendMessage(Worlds.getDenyMessage(world));
                        }
                        e.setCancelled(true);

                    }
                }

            }
        }
    }

    // Flag: Void Teleport
    @EventHandler
    public void onDamage(EntityDamageEvent e) {

        String world = e.getEntity().getWorld().getName();
        Entity entity = e.getEntity();

        String voidtpFlag = "voidtp";
        String falldamageFlag = "fall-damage";

        if (entity instanceof Player) {
            if (HRes.isInRes(entity)) {
                return;
            }
        }

        if (e.getCause() == EntityDamageEvent.DamageCause.VOID) {
            if (Settings.canExecute(world, voidtpFlag)) {
                if (Worlds.getFlag(world, voidtpFlag)) {
                    if (Worlds.isSpawnLocationSet(world)) {
                        if (entity instanceof Player) {
                            Player p = (Player) entity;
                            p.teleport(Worlds.getSpawnLocation(world));
                            p.setFallDistance(0);
                        }
                    }
                }
            }
        }

        if (e.getCause() == EntityDamageEvent.DamageCause.FALL) {
            if (entity instanceof Player) {
                if (Settings.canExecute(world, falldamageFlag)) {
                    if (!Worlds.getFlag(world, falldamageFlag)) {
                        Player p = (Player) entity;
                        e.setCancelled(true);
                    }
                }
            }
        }


    }


    // Flag: shoot
    @EventHandler
    public void onShoot(EntityShootBowEvent e) {

        String world = e.getEntity().getWorld().getName();
        Entity p = e.getEntity();

        String shootFlag = "shoot";

        if (p instanceof Player) {
            if (HRes.isInRes(p)) {
                return;
            }
        }

        if (Settings.canExecute(world, shootFlag)) {

            if (!Worlds.getFlag(world, shootFlag)) {

                if (p instanceof Player) {

                    if (!p.hasPermission(Worlds.getPermission(world))) {

                        if (e.getBow().getType() == Material.BOW) {
                            if (Worlds.isDenyMessageExist(world)) {
                                p.sendMessage(Worlds.getDenyMessage(world));
                            }
                            e.setCancelled(true);
                        }
                        if (FlagsManager.getBoolInFlag(shootFlag, "include-crossbow")) {
                            if (e.getBow().getType() == Material.CROSSBOW) {
                                if (Worlds.isDenyMessageExist(world)) {
                                    p.sendMessage(Worlds.getDenyMessage(world));
                                }
                                e.setCancelled(true);
                            }
                        }
                    }
                }


            }
        }


    }

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent e) {

        String world = e.getEntity().getWorld().getName();

        String armorstandFlag = "armorstand";

        if (HRes.isInRes(e.getEntity())) {
            return;
        }

        if (e.getEntityType() == EntityType.ARMOR_STAND) {

            if (Settings.canExecute(world, armorstandFlag)) {

                if (!Worlds.getFlag(world, armorstandFlag)) {

                    // 这个我弄不了给有权限的人放置
                    // 实在没有头绪了
                    e.setCancelled(true);


                }

            }

        }

    }

    @EventHandler
    public void onEntityInteract(EntityInteractEvent e) {

        Entity entity = e.getEntity();
        String world = entity.getWorld().getName();

        String farmbreakFlag = "farmbreak";

        if (HRes.isInRes(entity)) {
            return;
        }

        // Flag: farmbreak

        if (e.getBlock().getType() == Material.matchMaterial("SOIL") || e.getBlock().getType() == Material.matchMaterial("FARMLAND")) {

            if (Settings.canExecute(world, farmbreakFlag)) {

                if (!Worlds.getFlag(world, farmbreakFlag)) {

                    e.setCancelled(true);

                }


            }
        }

    }

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent e) {

        String mobSpawnFlag = "mob-spawn";
        Entity entity = e.getEntity();
        String world = entity.getWorld().getName();

        if (Settings.canExecute(world, mobSpawnFlag)) {

            if (!Worlds.getFlag(world, mobSpawnFlag)) {

                if (FlagsManager.getFlagsType(mobSpawnFlag).equalsIgnoreCase("list")) {

                    EntityType et = e.getEntityType();

                    if (FlagsManager.getFlagsList("mob-spawn").contains(et.toString().toUpperCase())) {

                        e.setCancelled(true);

                    }

                }

            }

        }

    }

    // Flag: potion
    @EventHandler
    public void onPotionEffect(EntityPotionEffectEvent e) {

        Entity entity = e.getEntity();
        String world = entity.getWorld().getName();

        if (!(entity instanceof Player)) {
            if (HRes.isInRes(entity)) {
                return;
            }
            return;
        }

        if (Settings.canExecute(world, "potion")) {

            if (!Worlds.getFlag(world, "potion")) {

                Player p = (Player) entity;

                if (!p.hasPermission(Worlds.getPermission(world))) {

                    e.setCancelled(true);
                    if (Worlds.isDenyMessageExist(world)) {
                        p.sendMessage(Worlds.getDenyMessage(world));
                    }

                }

            }

        }


    }

    // Flag: elytra
    @EventHandler
    public void onGlide(EntityToggleGlideEvent e) {

        Entity entity = e.getEntity();
        String world = entity.getWorld().getName();

        if (!(entity instanceof Player)) {
            return;
        }

        if (HRes.isInRes(entity)) {
            return;
        }

        Player p = (Player) entity;

        if (Settings.canExecute(world, "elytra")) {
            if (!Worlds.getFlag(world, "elytra")) {
                if (!p.hasPermission(Worlds.getPermission(world))) {

                    if (e.isGliding()) {
                        if (p.getInventory().getChestplate().getType() == Material.ELYTRA) {

                            e.setCancelled(true);
                            if (Worlds.isDenyMessageExist(world)) {
                                p.sendMessage(Worlds.getDenyMessage(world));
                            }

                        }
                    }

                }
            }
        }


    }

    // Flag: nether
    @EventHandler
    public void onPortalCreate(PortalCreateEvent e) {

        Entity entity = e.getEntity();
        String world = entity.getWorld().getName();

        if (HRes.isInRes(entity)) {
            return;
        }

        List<BlockState> blocks = e.getBlocks();

        for (BlockState block : blocks) {

            if (block.getType() == Material.OBSIDIAN) {
                if (Settings.canExecute(world, "nether")) {
                    if (!Worlds.getFlag(world, "nether")) {

                        if (entity instanceof Player) {
                            Player p = (Player) entity;

                            if (!p.hasPermission(Worlds.getPermission(world))) {
                                e.setCancelled(true);
                                if (Worlds.isDenyMessageExist(world)) {
                                    p.sendMessage(Worlds.getDenyMessage(world));
                                }
                                return;
                            }
                            return;
                        }

                        e.setCancelled(true);
                        return;

                    }
                    return;
                }
                return;
            }

        }

    }

    // Flag: fire-spawn
    @EventHandler
    public void onProjectileHit(ProjectileHitEvent e) {
        Entity entity = e.getEntity();
        String world = entity.getWorld().getName();

        if (Settings.canExecute(world, "fire-spawn")) {
            if (!Worlds.getFlag(world, "fire-spawn")) {
                if (e instanceof Blaze) {
                    e.setCancelled(true);
                }
            }
        }
    }
}
