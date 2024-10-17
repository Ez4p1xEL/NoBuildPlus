package p1xel.nobuildplus.Listener;

import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleDamageEvent;
import org.bukkit.event.vehicle.VehicleDestroyEvent;
import p1xel.nobuildplus.Flags;
import p1xel.nobuildplus.Hook.HRes;
import p1xel.nobuildplus.Hook.Hooks;
import p1xel.nobuildplus.Storage.FlagsManager;
import p1xel.nobuildplus.Storage.Settings;
import p1xel.nobuildplus.Storage.Worlds;

public class NoBuildPlusVehicleListener implements Listener {

    // Flag: Boat
    @EventHandler
    public void onBoatDamaged(VehicleDamageEvent e) {

        Vehicle vehicle = e.getVehicle();

        if (Hooks.cancel(vehicle)) {
            return;
        }

        String world = vehicle.getWorld().getName();

        if (!Flags.boat.isEnabled(world)) {
            return;
        }

        if (vehicle.getType() == EntityType.BOAT || vehicle.getType() == EntityType.CHEST_BOAT) {

            Entity p = e.getAttacker();

            if (p instanceof Player) {

                if (p.hasPermission(Worlds.getPermission(world))) {
                    return;
                }

                Worlds.sendMessage((Player)p, world);

            }

            e.setCancelled(true);

        }

    }

    // Flag: minecart
    @EventHandler
    public void onMinecartDamaged(VehicleDamageEvent e) {

        Vehicle vehicle = e.getVehicle();

        if (Hooks.cancel(vehicle)) {
            return;
        }

        String world = vehicle.getWorld().getName();

        if (!Flags.minecart.isEnabled(world)) {
            return;
        }

        for (String stype : Flags.minecart.getList()) {
            if (vehicle.getType() == EntityType.valueOf(stype)) {

                Entity p = e.getAttacker();

                if (p instanceof Player) {
                    if (p.hasPermission(Worlds.getPermission(world))) {
                        return;
                    }
                    Worlds.sendMessage((Player)p, world);
                }

                e.setCancelled(true);
                break;

            }

        }

    }

    // Flag: boat
    @EventHandler
    public void onBoatDestroyed(VehicleDestroyEvent e) {

        Vehicle vehicle = e.getVehicle();

        if (Hooks.cancel(vehicle)) {
            return;
        }

        String world = vehicle.getWorld().getName();

        if (!Flags.boat.isEnabled(world)) {
            return;
        }

        if (vehicle.getType() == EntityType.BOAT || vehicle.getType() == EntityType.CHEST_BOAT) {

            Entity p = e.getAttacker();

            if (p instanceof Player) {

                if (p.hasPermission(Worlds.getPermission(world))) {
                    return;
                }
                Worlds.sendMessage((Player)p, world);

            }

            e.setCancelled(true);

        }
    }

    // Flag: minecart
    @EventHandler
    public void onMinecartDestroyed(VehicleDestroyEvent e) {

        Vehicle vehicle = e.getVehicle();

        if (Hooks.cancel(vehicle)) {
            return;
        }

        String world = vehicle.getWorld().getName();

        if (!Flags.minecart.isEnabled(world)) {
            return;
        }

        for (String stype : Flags.minecart.getList()) {

            if (vehicle.getType() == EntityType.valueOf(stype)) {

                Entity p = e.getAttacker();

                if (p instanceof Player) {

                    if (p.hasPermission(Worlds.getPermission(world))) {
                        return;
                    }

                    Worlds.sendMessage((Player)p, world);

                }

                e.setCancelled(true);
                break;
            }

        }


    }

}
