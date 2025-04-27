package p1xel.nobuildplus.Listener.HookedPluginListeners;

import com.bekvon.bukkit.residence.event.ResidenceChangedEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import p1xel.nobuildplus.Flags;
import p1xel.nobuildplus.Storage.Worlds;

public class ResidenceListener implements Listener {

    @EventHandler
    public void onResidenceChanged(ResidenceChangedEvent e) {

        Player p = e.getPlayer();

        if (e.getTo() == null) {

            if (p.getAllowFlight()) {

                String world = p.getWorld().getName();

                if (Flags.fly.isEnabled(world)) {

                    if (!p.hasPermission(Worlds.getPermission(world))) {

                        Worlds.sendMessage(p, world);
                        p.setAllowFlight(false);

                    }
                }

            }
        }

    }



}
