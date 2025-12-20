package p1xel.nobuildplus.listener.text;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.scheduler.BukkitRunnable;
import p1xel.nobuildplus.NoBuildPlus;
import p1xel.nobuildplus.listener.gui.GUIDefaultTemplate;
import p1xel.nobuildplus.listener.gui.GUIWorld;
import p1xel.nobuildplus.storage.Config;
import p1xel.nobuildplus.storage.Locale;
import p1xel.nobuildplus.storage.Settings;
import p1xel.nobuildplus.storage.Worlds;

import javax.annotation.Nullable;
import java.util.HashMap;

public class TextEditMode implements Listener {

    private HashMap<Player, String> status = new HashMap<>();
    public String cancelWord = Config.getString("text-edit-mode.cancel");

    @Nullable
    public String getPlayerAction(Player player) {
        return status.get(player);
    }

    public void setPlayerAction(Player player, String action) {
        status.put(player, action);
    }

    public void removePlayer(Player player) {
        status.remove(player);
    }


    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {

        Player player = event.getPlayer();

        if (status.get(player) == null) {
            return;
        }

        TextEditMode mode = NoBuildPlus.getTextEditMode();
        String text = event.getMessage();
        if (text.equalsIgnoreCase(mode.cancelWord)) {
            player.sendMessage(Locale.getMessage("quit-mode"));
            removePlayer(player);
            event.setCancelled(true);
            return;
        }

        String action = status.get(player);
        switch (action) {

            case "edit-default-permission": {

                Settings.setPermission(text);
                player.sendMessage(Locale.getMessage("edit-default-permission").replaceAll("%permission%", text));
                player.sendMessage(Locale.getMessage("quit-mode"));
                removePlayer(player);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        player.openInventory(new GUIDefaultTemplate(1).getInventory());
                    }
                }.runTaskLater(NoBuildPlus.getInstance(), 10L);

                break;

            }

            case "edit-default-deny-message": {

                Settings.setDenyMessageString(text);
                player.sendMessage(Locale.getMessage("edit-default-deny-message").replaceAll("%message%", text));
                player.sendMessage(Locale.getMessage("quit-mode"));
                removePlayer(player);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        player.openInventory(new GUIDefaultTemplate(1).getInventory());
                    }
                }.runTaskLater(NoBuildPlus.getInstance(), 10L);
                break;

            }

        }

        event.setCancelled(true);

        if (action.startsWith("edit-permission:")) {

            String world = action.split(":")[1];
            Worlds.setPermission(world, text);
            player.sendMessage(Locale.getMessage("edit-permission").replaceAll("%world%", world).replaceAll("%permission%", text));
            player.sendMessage(Locale.getMessage("quit-mode"));
            removePlayer(player);
            new BukkitRunnable() {
                @Override
                public void run() {
                    player.openInventory(new GUIWorld(world, 1).getInventory());
                }
            }.runTaskLater(NoBuildPlus.getInstance(), 10L);
            return;

        }

        if (action.startsWith("edit-deny-message:")) {

            String world = action.split(":")[1];
            Worlds.setDenyMessage(world, text);
            player.sendMessage(Locale.getMessage("edit-deny-message").replaceAll("%world%", world).replaceAll("%message%", text));
            player.sendMessage(Locale.getMessage("quit-mode"));
            removePlayer(player);
            new BukkitRunnable() {
                @Override
                public void run() {
                    player.openInventory(new GUIWorld(world, 1).getInventory());
                }
            }.runTaskLater(NoBuildPlus.getInstance(), 10L);
            return;

        }

    }



}
