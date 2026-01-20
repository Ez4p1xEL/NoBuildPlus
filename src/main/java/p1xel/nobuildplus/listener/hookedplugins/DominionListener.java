package p1xel.nobuildplus.listener.hookedplugins;

import cn.lunadeer.dominion.events.PlayerMoveOutDominionEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import p1xel.nobuildplus.Flags;
import p1xel.nobuildplus.storage.Worlds;
import p1xel.nobuildplus.world.ProtectedWorld;
import p1xel.nobuildplus.world.WorldManager;

public class DominionListener implements Listener {

    @EventHandler
    public void onLeavingDominion(PlayerMoveOutDominionEvent e) {

        Player player = e.getPlayer();

        if (player.getAllowFlight()) {

            String worldName = player.getWorld().getName();
            ProtectedWorld world = WorldManager.getWorld(worldName);
            if (Flags.fly.isEnabled(world) && !player.hasPermission(world.getPermission())) {

                WorldManager.sendMessage(player, world);
                player.setAllowFlight(false);

            }

        }

    }

}
