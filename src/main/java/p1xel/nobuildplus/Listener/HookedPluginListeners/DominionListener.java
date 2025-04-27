package p1xel.nobuildplus.Listener.HookedPluginListeners;

import cn.lunadeer.dominion.events.PlayerMoveOutDominionEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import p1xel.nobuildplus.Flags;
import p1xel.nobuildplus.Storage.Worlds;

public class DominionListener implements Listener {

    @EventHandler
    public void onLeavingDominion(PlayerMoveOutDominionEvent e) {

        Player p = e.getPlayer();

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
