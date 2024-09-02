package p1xel.nobuildplus.Listener;

import org.bukkit.entity.*;
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
        if (Settings.canExecute(world, "boat")) {

            if (!Worlds.getFlag(world, "boat")) {

                if (p instanceof Player) {


                    if (vehicle.getType() == EntityType.BOAT || vehicle.getType() == EntityType.CHEST_BOAT) {

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

        // Flag: minecart
        if (Settings.canExecute(world, "minecart")) {

            if (!Worlds.getFlag(world, "minecart")) {

                if (p instanceof Player) {

                    for (String stype : FlagsManager.getFlagsList("minecart")) {
                        if (vehicle.getType() == EntityType.valueOf(stype)) {

                            if (!p.hasPermission(Worlds.getPermission(world))) {

                                if (Worlds.isDenyMessageExist(world)) {
                                    p.sendMessage(Worlds.getDenyMessage(world));
                                }
                                e.setCancelled(true);


                            }
                        }

                        break;

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
        if (Settings.canExecute(world, "boat")) {

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

        // Flag: minecart
        if (Settings.canExecute(world, "minecart")) {

            if (!Worlds.getFlag(world, "boat")) {

                if (p instanceof Player) {

                    for (String stype : FlagsManager.getFlagsList("boat")) {

                        if (vehicle.getType() == EntityType.valueOf(stype)) {

                            if (!p.hasPermission(Worlds.getPermission(world))) {

                                if (Worlds.isDenyMessageExist(world)) {
                                    p.sendMessage(Worlds.getDenyMessage(world));
                                }
                                e.setCancelled(true);

                            }

                        }

                        break;
                    }
                }
            }

        }

    }

}
