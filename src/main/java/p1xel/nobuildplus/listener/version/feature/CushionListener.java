package p1xel.nobuildplus.listener.version.feature;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityPlaceEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.hanging.HangingBreakEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;
import p1xel.nobuildplus.Flags;
import p1xel.nobuildplus.hook.HookedPlugins;
import p1xel.nobuildplus.listener.Flag;
import p1xel.nobuildplus.listener.version.FeatureListener;
import p1xel.nobuildplus.world.ProtectedWorld;
import p1xel.nobuildplus.world.WorldManager;

import java.util.Arrays;
import java.util.List;

public class CushionListener implements FeatureListener {
    private final List<Material> cushionMaterials = Arrays.asList(
            Material.WHITE_CUSHION,
            Material.ORANGE_CUSHION,
            Material.MAGENTA_CUSHION,
            Material.LIGHT_BLUE_CUSHION,
            Material.YELLOW_CUSHION,
            Material.LIME_CUSHION,
            Material.PINK_CUSHION,
            Material.GRAY_CUSHION,
            Material.LIGHT_GRAY_CUSHION,
            Material.CYAN_CUSHION,
            Material.PURPLE_CUSHION,
            Material.BLUE_CUSHION,
            Material.BROWN_CUSHION,
            Material.GREEN_CUSHION,
            Material.RED_CUSHION,
            Material.BLACK_CUSHION);

    @Override
    public String getName() {
        return "CushionListener 26.3+";
    }

    @Override
    public boolean matchRequirement(int[] version) {
        return version[0] >= 26 && version[1] >= 3;
    }

    @Flag("cushion")
    @EventHandler(ignoreCancelled = true)
    public void onUsingCushion(PlayerInteractEntityEvent event) {

        Player player = event.getPlayer();

        if (HookedPlugins.cancel(player)) {
            return;
        }

        EntityType entityType = event.getRightClicked().getType();
        if (!entityType.equals(EntityType.CUSHION)) {
            return;
        }

        String worldName = player.getWorld().getName();
        ProtectedWorld world = WorldManager.getWorld(worldName);
        if (!Flags.cushion.isEnabled(world)) {
            return;
        }

//        Action action = event.getAction();
//        if (!(action == Action.RIGHT_CLICK_BLOCK || action == Action.RIGHT_CLICK_AIR)) {
//            return;
//        }

        if (player.hasPermission(world.getPermission())) {
            return;
        }

        WorldManager.sendMessage(player, world);
        event.setCancelled(true);

    }

    @Flag("cushion")
    @EventHandler(ignoreCancelled = true)
    public void onBreakingCushion(HangingBreakByEntityEvent event) {
        System.out.println("HangingBreakByEntityEvent triggered");

        Entity cushion = event.getEntity();

        if (HookedPlugins.cancel(cushion)) {
            return;
        }

        EntityType entityType = cushion.getType();
        if (!entityType.equals(EntityType.CUSHION)) {
            return;
        }

        String worldName = cushion.getWorld().getName();
        ProtectedWorld world = WorldManager.getWorld(worldName);
        if (!Flags.destroy.isEnabled(world)) {
            return;
        }

        event.setCancelled(true);

    }

    @Flag("cushion")
    @EventHandler(ignoreCancelled = true)
    public void onPlacingCushion(EntityPlaceEvent event) {

        Entity entity = event.getEntity();

        if (HookedPlugins.cancel(entity)) {
            return;
        }

        if (entity.getType() != EntityType.CUSHION) {
            return;
        }

        String worldName = entity.getWorld().getName();
        ProtectedWorld world = WorldManager.getWorld(worldName);

        if (!Flags.build.isEnabled(world)) {
            return;
        }

        Player player = event.getPlayer();
        if (player != null) {
            if (player.hasPermission(world.getPermission())) {
                return;
            }
            WorldManager.sendMessage(player, world);
        }

        event.setCancelled(true);

    }

}
