package p1xel.nobuildplus.listener.version.feature;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityEvent;
import p1xel.nobuildplus.Flags;
import p1xel.nobuildplus.hook.HookedPlugins;
import p1xel.nobuildplus.listener.Flag;
import p1xel.nobuildplus.listener.version.FeatureListener;
import p1xel.nobuildplus.storage.Logger;
import p1xel.nobuildplus.world.ProtectedWorld;
import p1xel.nobuildplus.world.WorldManager;

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;

/*
 * A listener for handling cushion break events in Paper.
 * This listener is designed to work with Paper versions 26.3 and above.
 * Paper introduced a new event called EntityBreakByEntityEvent, which is triggered when an entity is broken by another entity.
 * However, this event is only in Paper but not Spigot, while Paper moving Cushion off Hanging.
 *
 * BTW, Reflection is so ugly :(
 */

public class PaperCushionBreakListener implements FeatureListener {

    @Override
    public String getName() {
        return "Paper Cushion Break Listener 26.3+";
    }

    @Override
    public boolean matchRequirement(int[] version) {
        return version[0] >= 26 && version[1] >= 3;
    }

    @Flag("cushion")
    public void onBreakingCushion(EntityEvent event) {
        Entity cushion = event.getEntity();

        if (HookedPlugins.cancel(cushion)) {
            return;
        }

        EntityType entityType = cushion.getType();
        if (!entityType.equals(EntityType.CUSHION)) {
            return;
        }

        String worldName = cushion.getWorld().getName();
        ProtectedWorld world = WorldManager.getWorld(worldName);
        if (!Flags.cushion.isEnabled(world)) {
            return;
        }

        try {

            Entity remover = (Entity) event.getClass().getMethod("getRemover").invoke(event);

            if (remover instanceof Player) {
                Player player = (Player) remover;
                if (player.hasPermission(world.getPermission())) {
                    return;
                }
                WorldManager.sendMessage(player, world);
            }

            event.getClass().getMethod("setCancelled", boolean.class).invoke(event, true);
        } catch (NoSuchMethodException e) {
            Logger.log(Level.INFO, "The event class does not have a setCancelled method: " + event.getClass().getName());
        } catch (IllegalAccessException e) {
            Logger.log(Level.INFO, "Error occurred while accessing setCancelled method: " + e.getMessage());
        } catch (InvocationTargetException e) {
            Logger.log(Level.INFO, "Error occurred while invoking setCancelled method: " + e.getMessage());
        }


    }
}
