package p1xel.nobuildplus.Listener;

import com.bekvon.bukkit.residence.event.ResidenceChangedEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
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
}
