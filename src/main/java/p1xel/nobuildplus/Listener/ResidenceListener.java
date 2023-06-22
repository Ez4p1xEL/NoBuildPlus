package p1xel.nobuildplus.Listener;

import com.bekvon.bukkit.residence.event.ResidenceChangedEvent;
import com.bekvon.bukkit.residence.event.ResidenceTPEvent;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import p1xel.nobuildplus.Storage.FlagsManager;
import p1xel.nobuildplus.Storage.Settings;
import p1xel.nobuildplus.Storage.Worlds;

public class ResidenceListener implements Listener {

    @EventHandler
    public void onResidenceChanged(ResidenceChangedEvent e) {

        String world = e.getPlayer().getWorld().getName();
        Player p = e.getPlayer();

        if (e.getTo() == null) {

            if (p.getAllowFlight()) {

                if (FlagsManager.getFlagsIsEnabled("fly")) {

                    if (Settings.getEnableWorldList().contains(world)) {

                        if (!Worlds.getFlag(world, "fly")) {

                            if (!p.hasPermission(Worlds.getPermission(world))) {

                                if (Worlds.isDenyMessageExist(world)) {
                                    p.sendMessage(Worlds.getDenyMessage(world));
                                }
                                p.setAllowFlight(false);

                            }
                        }
                    }

                }

            }
        }

    }

    @EventHandler
    public void onUseBoat(PlayerInteractEntityEvent e){
        Player player = e.getPlayer();
        World world = player.getWorld();

        if (!e.getRightClicked().getType().equals(EntityType.BOAT)) {
            if (FlagsManager.getFlagsIsEnabled("boat")) {
                if (Settings.getEnableWorldList().contains(world.getName())) {
                    if (!Worlds.getFlag(world.getName(), "fly")) {

                        if (!player.hasPermission(Worlds.getPermission(world.getName()))) {

                            if (Worlds.isDenyMessageExist(world.getName())) {
                                player.sendMessage(Worlds.getDenyMessage(world.getName()));
                            }

                            e.setCancelled(true);
                        }
                    }
                }

            }
        }
    }

}
