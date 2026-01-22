package p1xel.nobuildplus.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import p1xel.nobuildplus.NoBuildPlus;
import p1xel.nobuildplus.listener.gui.GUIType;
import p1xel.nobuildplus.listener.gui.GUIWorld;
import p1xel.nobuildplus.storage.Config;
import p1xel.nobuildplus.storage.Locale;
import p1xel.nobuildplus.world.ProtectedWorld;
import p1xel.nobuildplus.world.WorldManager;

public class NBPListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onExecuteGameRule(PlayerCommandPreprocessEvent event) {

        String message = event.getMessage().trim();

        if (!message.equalsIgnoreCase("/gamerule")) {
            return;
        }

        if (NoBuildPlus.getInstance().getBukkitVersion() < 15) {
            return;
        }

        if (Config.getBool("open-menu-if-gamerule.enable")) {
            Player player = event.getPlayer();

            String worldName = player.getWorld().getName();
            ProtectedWorld world = WorldManager.getWorld(worldName);
            if (world == null && Config.getBool("open-menu-if-gamerule.disable-in-non-protected-world")) {
                return;
            }

            if (Config.getBool("open-menu-if-gamerule.notification")) {
                player.sendMessage(Locale.getMessage("gamerule-notice"));
            }

            player.openInventory(new GUIWorld(worldName, 1, GUIType.GAMERULE).getInventory());
            event.setCancelled(true);
        }

    }

}
