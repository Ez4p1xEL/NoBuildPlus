package p1xel.nobuildplus.listener;

import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleDamageEvent;
import org.bukkit.event.vehicle.VehicleDestroyEvent;
import p1xel.nobuildplus.Flags;
import p1xel.nobuildplus.hook.HookedPlugins;
import p1xel.nobuildplus.world.ProtectedWorld;
import p1xel.nobuildplus.world.WorldManager;

public class NoBuildPlusVehicleListener implements Listener {

    // Flag: Boat
    @EventHandler(ignoreCancelled = true)
    public void onBoatDamaged(VehicleDamageEvent e) {

        Vehicle vehicle = e.getVehicle();

        if (HookedPlugins.cancel(vehicle)) {
            return;
        }

        String worldName = vehicle.getWorld().getName();
        ProtectedWorld world = WorldManager.getWorld(worldName);

        if (!Flags.boat.isEnabled(world)) {
            return;
        }

        if (vehicle instanceof Boat) {

            Entity player = e.getAttacker();

            if (player instanceof Player) {

                if (player.hasPermission(world.getPermission())) {
                    return;
                }

                WorldManager.sendMessage((Player)player, world);

            }

            e.setCancelled(true);

        }

    }

    // Flag: minecart
    @EventHandler(ignoreCancelled = true)
    public void onMinecartDamaged(VehicleDamageEvent e) {

        Vehicle vehicle = e.getVehicle();

        if (HookedPlugins.cancel(vehicle)) {
            return;
        }

        String worldName = vehicle.getWorld().getName();
        ProtectedWorld world = WorldManager.getWorld(worldName);

        if (!Flags.minecart.isEnabled(world)) {
            return;
        }

        for (String stype : Flags.minecart.getList()) {
            if (vehicle.getType() == EntityType.valueOf(stype)) {

                Entity player = e.getAttacker();

                if (player instanceof Player) {
                    if (player.hasPermission(world.getPermission())) {
                        return;
                    }
                    WorldManager.sendMessage((Player)player, world);
                }

                e.setCancelled(true);
                break;

            }

        }

    }

    // Flag: boat
    @EventHandler(ignoreCancelled = true)
    public void onBoatDestroyed(VehicleDestroyEvent e) {

        Vehicle vehicle = e.getVehicle();

        if (HookedPlugins.cancel(vehicle)) {
            return;
        }

        String worldName = vehicle.getWorld().getName();
        ProtectedWorld world = WorldManager.getWorld(worldName);

        if (!Flags.boat.isEnabled(world)) {
            return;
        }

        if (vehicle instanceof Boat) {

            Entity player = e.getAttacker();

            if (player instanceof Player) {

                if (player.hasPermission(world.getPermission())) {
                    return;
                }
                WorldManager.sendMessage((Player)player, world);

            }

            e.setCancelled(true);

        }
    }

    // Flag: minecart
    @EventHandler(ignoreCancelled = true)
    public void onMinecartDestroyed(VehicleDestroyEvent e) {

        Vehicle vehicle = e.getVehicle();

        if (HookedPlugins.cancel(vehicle)) {
            return;
        }

        String worldName = vehicle.getWorld().getName();
        ProtectedWorld world = WorldManager.getWorld(worldName);

        if (!Flags.minecart.isEnabled(world)) {
            return;
        }


        if (Flags.minecart.getList().contains(vehicle.getType().getName())) {

            Entity player = e.getAttacker();

            if (player instanceof Player) {

                if (player.hasPermission(world.getPermission())) {
                    return;
                }

                WorldManager.sendMessage((Player)player, world);

            }

            e.setCancelled(true);
        }



    }

}
