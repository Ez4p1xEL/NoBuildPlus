package p1xel.nobuildplus.Listener;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleDamageEvent;
import org.bukkit.event.vehicle.VehicleDestroyEvent;
import p1xel.nobuildplus.Hook.HRes;
import p1xel.nobuildplus.Storage.FlagsManager;
import p1xel.nobuildplus.Storage.Settings;
import p1xel.nobuildplus.Storage.Worlds;

public class NoBuildPlusVehicleListener implements Listener {

    @EventHandler
    public void onVehicleDamage(VehicleDamageEvent e) {

        String world = e.getVehicle().getWorld().getName();
        Entity p = e.getAttacker();
        Vehicle vehicle = e.getVehicle();

        if (HRes.isInRes(vehicle)) {
            return;
        }

        // Flag: Boat
        if (FlagsManager.getFlagsIsEnabled("boat")) {

            if (Settings.getEnableWorldList().contains(world)) {

                if (!Worlds.getFlag(world, "boat")) {

                    if (p instanceof Player) {

                        if (!p.hasPermission(Worlds.getPermission(world))) {

                            if (vehicle.getType() == EntityType.BOAT || vehicle.getType() == EntityType.CHEST_BOAT) {

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
    public void onVehicleDestroy(VehicleDestroyEvent e) {

        String world = e.getVehicle().getWorld().getName();
        Entity p = e.getAttacker();
        Vehicle vehicle = e.getVehicle();

        if (HRes.isInRes(vehicle)) {
            return;
        }

        // Flag: Boat
        if (FlagsManager.getFlagsIsEnabled("boat")) {

            if (Settings.getEnableWorldList().contains(world)) {

                if (!Worlds.getFlag(world, "boat")) {

                    if (p instanceof Player) {

                        if (!p.hasPermission(Worlds.getPermission(world))) {

                            if (vehicle.getType() == EntityType.BOAT || vehicle.getType() == EntityType.CHEST_BOAT) {

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

}
