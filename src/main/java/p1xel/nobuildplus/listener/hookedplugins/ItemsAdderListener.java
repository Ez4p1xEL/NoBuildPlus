package p1xel.nobuildplus.listener.hookedplugins;

import dev.lone.itemsadder.api.Events.FurnitureInteractEvent;
import dev.lone.itemsadder.api.Events.campfire.CampfirePutItemEvent;
import dev.lone.itemsadder.api.Events.campfire.CampfireRemoveItemEvent;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import p1xel.nobuildplus.flag.ItemsAdderFlags;
import p1xel.nobuildplus.hook.HookedPlugins;
import p1xel.nobuildplus.storage.Worlds;

public class ItemsAdderListener implements Listener {

    @EventHandler
    public void onFurnitureSit(FurnitureInteractEvent event) {
        Entity entity = event.getFurniture().getEntity();
        if (HookedPlugins.cancel(entity)) {
            return;
        }

        String world = entity.getWorld().getName();
        if (!ItemsAdderFlags.ia_furniture_sit.isEnabled(world)) {
            return;
        }

        Player player = event.getPlayer();
        if (player.hasPermission(Worlds.getPermission(world))) {
            return;
        }

        Worlds.sendMessage(player, world);
        event.setCancelled(true);

    }

    @EventHandler
    public void onAddItemToCampfire(CampfirePutItemEvent event) {
        Location location = event.getCampfire().getLocation();
        if (HookedPlugins.cancel(location)) {
            return;
        }

        String world = location.getWorld().getName();
        if (!ItemsAdderFlags.ia_campfire_interact.isEnabled(world)) {
            return;
        }

        Player player = event.getPlayer();
        if (player.hasPermission(Worlds.getPermission(world))) {
            return;
        }

        Worlds.sendMessage(player, world);
        event.setCancelled(true);

    }

    @EventHandler
    public void onRemoveItemFromCampfire(CampfireRemoveItemEvent event) {
        Location location = event.getCampfire().getLocation();
        if (HookedPlugins.cancel(location)) {
            return;
        }

        String world = location.getWorld().getName();
        if (!ItemsAdderFlags.ia_campfire_interact.isEnabled(world)) {
            return;
        }

        Player player = event.getPlayer();
        if (player.hasPermission(Worlds.getPermission(world))) {
            return;
        }

        Worlds.sendMessage(player, world);
        event.setCancelled(true);

    }

}
