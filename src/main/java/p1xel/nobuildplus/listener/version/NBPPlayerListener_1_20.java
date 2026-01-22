package p1xel.nobuildplus.listener.version;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerSignOpenEvent;
import p1xel.nobuildplus.Flags;
import p1xel.nobuildplus.hook.HookedPlugins;
import p1xel.nobuildplus.world.ProtectedWorld;
import p1xel.nobuildplus.world.WorldManager;

public class NBPPlayerListener_1_20 implements Listener {

    // Flag: sign-edit
    @EventHandler(ignoreCancelled = true)
    public void onSignOpen(PlayerSignOpenEvent e) {

        Player player = e.getPlayer();

        if (HookedPlugins.cancel(player)) {
            return;
        }

        if (e.getCause() != PlayerSignOpenEvent.Cause.INTERACT) {
            return;
        }

        String worldName = player.getWorld().getName();
        ProtectedWorld world = WorldManager.getWorld(worldName);
        if (!Flags.sign_edit.isEnabled(world)) {
            return;
        }

        if (player.hasPermission(world.getPermission())) {
            return;
        }

        WorldManager.sendMessage(player, world);
        e.setCancelled(true);

    }

    // Flag: books(helf) interact
    @EventHandler(ignoreCancelled = true)
    public void onBookShelfInteract(PlayerInteractEvent e) {
        Action action = e.getAction();
        Block block = e.getClickedBlock();

        if (action != Action.RIGHT_CLICK_BLOCK || block == null || block.getType() != Material.CHISELED_BOOKSHELF) {
            return;
        }

        if (HookedPlugins.cancel(block)) {
            return;
        }

        String worldName = block.getWorld().getName();
        ProtectedWorld world = WorldManager.getWorld(worldName);
        if (!Flags.books_interact.isEnabled(world)) {
            return;
        }

        Player player = e.getPlayer();
        if (player.hasPermission(world.getPermission())) {
            return;
        }

        WorldManager.sendMessage(player, world);

        e.setCancelled(true);
    }

}
