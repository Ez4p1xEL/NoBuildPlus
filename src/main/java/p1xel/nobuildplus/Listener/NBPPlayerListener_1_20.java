package p1xel.nobuildplus.Listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerSignOpenEvent;
import p1xel.nobuildplus.Flags;
import p1xel.nobuildplus.Hook.Hooks;
import p1xel.nobuildplus.Storage.Worlds;

public class NBPPlayerListener_1_20 implements Listener {

    // Flag: sign-edit
    @EventHandler
    public void onSignOpen(PlayerSignOpenEvent e) {

        Player p = e.getPlayer();

        if (Hooks.cancel(p)) {
            return;
        }

        if (e.getCause() != PlayerSignOpenEvent.Cause.INTERACT) {
            return;
        }

        String world = p.getWorld().getName();
        if (!Flags.sign_edit.isEnabled(world)) {
            return;
        }

        if (p.hasPermission(Worlds.getPermission(world))) {
            return;
        }

        if (Worlds.isDenyMessageExist(world)) {
            p.sendMessage(Worlds.getDenyMessage(world));
        }
        e.setCancelled(true);

    }

}
