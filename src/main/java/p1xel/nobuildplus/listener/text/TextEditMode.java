package p1xel.nobuildplus.listener.text;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.Nullable;
import p1xel.nobuildplus.NoBuildPlus;
import p1xel.nobuildplus.gamerule.GameRuleRegistry;
import p1xel.nobuildplus.listener.gui.GUIAbstract;
import p1xel.nobuildplus.listener.gui.GUIDefaultTemplate;
import p1xel.nobuildplus.listener.gui.GUIType;
import p1xel.nobuildplus.listener.gui.GUIWorld;
import p1xel.nobuildplus.storage.*;
import p1xel.nobuildplus.world.WorldManager;

import java.util.HashMap;

public class TextEditMode implements Listener {

    private final HashMap<Player, String> status = new HashMap<>();
    private final HashMap<Player, GUIAbstract> last_viewed_inventories = new HashMap<>();
    public String cancelWord = Config.getString("text-edit-mode.cancel");

    @Nullable
    public String getPlayerAction(Player player) {
        return status.get(player);
    }

    public void setPlayerAction(Player player, String action, GUIAbstract gui) {
        status.put(player, action);
        last_viewed_inventories.put(player, gui);
    }

    public void removePlayer(Player player) {
        status.remove(player);
        last_viewed_inventories.remove(player);
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
            GUIAbstract last_viewed_gui = last_viewed_inventories.get(player);
            Logger.debug("Last_viewed_inventory: " + last_viewed_gui.getInventory());
            if (last_viewed_gui.getInventory() != null) {
                player.playSound(player, Sound.BLOCK_IRON_DOOR_CLOSE, 0.4f, 0.4f);
                openInventory(player);
            }
            //removePlayer(player);
            event.setCancelled(true);
            return;
        }

        String action = status.get(player);
        switch (action) {

            case "edit-default-permission": {

                Settings.setPermission(text);
                player.sendMessage(Locale.getMessage("edit-default-permission").replaceAll("%permission%", text));
                player.sendMessage(Locale.getMessage("quit-mode"));
                player.playSound(player, Sound.BLOCK_IRON_DOOR_CLOSE, 0.4f, 0.4f);
                openInventory(player);

                break;

            }

            case "edit-default-deny-message": {

                Settings.setDenyMessageString(text);
                player.sendMessage(Locale.getMessage("edit-default-deny-message").replaceAll("%message%", text));
                player.sendMessage(Locale.getMessage("quit-mode"));
                player.playSound(player, Sound.BLOCK_IRON_DOOR_CLOSE, 0.4f, 0.4f);
                openInventory(player);
                break;

            }

        }

        event.setCancelled(true);

        if (action.startsWith("edit-permission:")) {

            String worldName = action.split(":")[1];
            WorldManager.setPermission(worldName, text);
            player.sendMessage(Locale.getMessage("edit-permission").replaceAll("%world%", worldName).replaceAll("%permission%", text));
            player.sendMessage(Locale.getMessage("quit-mode"));
            player.playSound(player, Sound.BLOCK_IRON_DOOR_CLOSE, 0.4f, 0.4f);
            openInventory(player);
            return;

        }

        if (action.startsWith("edit-deny-message:")) {

            String worldName = action.split(":")[1];
            WorldManager.setDenyMessage(worldName, text);
            player.sendMessage(Locale.getMessage("edit-deny-message").replaceAll("%world%", worldName).replaceAll("%message%", text));
            player.sendMessage(Locale.getMessage("quit-mode"));
            player.playSound(player, Sound.BLOCK_IRON_DOOR_CLOSE, 0.4f, 0.4f);
            openInventory(player);
            return;

        }

        if (action.startsWith("edit-default-gamerule:")) {

            String gameruleName = action.split(":")[1];
            int number;
            try {
                number = Integer.parseInt(text);
            } catch (NumberFormatException e) {
                player.sendMessage(Locale.getMessage("invalid-amount"));
                return;
            }
            Settings.setDefaultGameRule(gameruleName, number);
            player.sendMessage(Locale.getMessage("default-gamerule-set-success").replace("%gamerule%", gameruleName).replace("%value%", text));
            player.sendMessage(Locale.getMessage("quit-mode"));
            player.playSound(player, Sound.BLOCK_IRON_DOOR_CLOSE, 0.4f, 0.4f);
            openInventory(player);
            return;

        }

        if (action.startsWith("edit-gamerule:")) {

            String[] split = action.split(":");
            String gameruleName = split[1];
            String worldName = split[2];
            int number;
            try {
                number = Integer.parseInt(text);
            } catch (NumberFormatException e) {
                player.sendMessage(Locale.getMessage("invalid-amount"));
                return;
            }
            try {

                if (NoBuildPlus.getFoliaLib().isFolia()) {
                    NoBuildPlus.getFoliaLib().getScheduler().runNextTick(wrappedTask -> GameRuleRegistry.setWorldGameRule(worldName, gameruleName, number));
                } else {
                    Bukkit.getScheduler().runTask(NoBuildPlus.getInstance(), task -> GameRuleRegistry.setWorldGameRule(worldName, gameruleName, number));
                }
            } catch (IllegalArgumentException exception) {
                player.sendMessage(Locale.getMessage("invalid-gamerule").replace("%gamerule%", gameruleName));
                return;
            }
            player.sendMessage(Locale.getMessage("gamerule-set-success").replace("%world%", worldName).replace("%gamerule%", gameruleName).replace("%value%", text));
            player.sendMessage(Locale.getMessage("quit-mode"));
            player.playSound(player, Sound.BLOCK_IRON_DOOR_CLOSE, 0.4f, 0.4f);

            openInventory(player);

        }

    }

    public void openInventory(Player player) {

        //Inventory inventory = gui.getInventory();
        if (NoBuildPlus.getFoliaLib().isFolia()) {
            NoBuildPlus.getFoliaLib().getScheduler().runNextTick(task -> {
                GUIAbstract gui = last_viewed_inventories.get(player);
                gui.init();
                player.openInventory(gui.getInventory());
                removePlayer(player);
            });
            return;
        }

        Bukkit.getScheduler().runTask(NoBuildPlus.getInstance(), () -> {
            GUIAbstract gui = last_viewed_inventories.get(player);
            gui.init();
            player.openInventory(gui.getInventory());
            removePlayer(player);
        });
    }



}
