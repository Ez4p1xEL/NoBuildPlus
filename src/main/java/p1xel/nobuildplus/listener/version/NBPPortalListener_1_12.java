package p1xel.nobuildplus.listener.version;

import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPortalEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import p1xel.nobuildplus.Flags;
import p1xel.nobuildplus.hook.HookedPlugins;
import p1xel.nobuildplus.world.ProtectedWorld;
import p1xel.nobuildplus.world.WorldManager;

public class NBPPortalListener_1_12 implements Listener {

    @EventHandler
    public void onTeleportNether(EntityPortalEvent event) {

        // Flag: nether

        Entity entity = event.getEntity();

        String worldName = entity.getWorld().getName();
        ProtectedWorld world = WorldManager.getWorld(worldName);

        if (!Flags.nether.isEnabled(world)) {
            return;
        }

        if (event.getTo().getWorld().getEnvironment() == World.Environment.NETHER || event.getFrom().getWorld().getEnvironment() == World.Environment.NETHER) {
            return;
        }

        if (HookedPlugins.cancel(entity)) {
            return;
        }

        event.setCancelled(true);

    }

    @EventHandler
    public void onPlayerTeleportToNether(PlayerPortalEvent event) {

        // Flag: nether

        Player player = event.getPlayer();

        String fromWorldName = event.getFrom().getWorld().getName();
        ProtectedWorld fromWorld = WorldManager.getWorld(fromWorldName);

        String toWorldName = event.getTo().getWorld().getName();
        ProtectedWorld toWorld = WorldManager.getWorld(toWorldName);

        if (!Flags.nether.isEnabled(fromWorld) && !Flags.nether.isEnabled(toWorld)) {
            return;
        }

        if (!(event.getTo().getWorld().getEnvironment() == World.Environment.NETHER || event.getFrom().getWorld().getEnvironment() == World.Environment.NETHER)) {
            return;
        }

        ProtectedWorld world = fromWorld == null ? toWorld : fromWorld;

        if (player.hasPermission(world.getPermission())) {
            return;
        }

        WorldManager.sendMessage(player, world);
        event.setCancelled(true);



    }

}
