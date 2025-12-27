package p1xel.nobuildplus.hook;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class HookedPlugins {

    private static final List<Hooks> hookedPlugin = new ArrayList<>();
    private static final HDefault defaultHook = new HDefault();

    public static void addHookPlugin(Hooks hook) {
        hookedPlugin.add(hook);
    }

    public static boolean cancel(Player player) {
        for (Hooks hook : hookedPlugin) {
            if (hook.cancel(player)) {
                return true;
            }
        }
        return defaultHook.cancel(player);

    }

    public static boolean cancel(Block block) {
        for (Hooks hook : hookedPlugin) {
            if (hook.cancel(block)) {
                return true;
            }
        }
        return defaultHook.cancel(block);
    }

    public static boolean cancel(Entity entity) {
        for (Hooks hook : hookedPlugin) {
            if (hook.cancel(entity)) {
                return true;
            }
        }
        return defaultHook.cancel(entity);
    }

    public static boolean cancel(Location loc) {
        for (Hooks hook : hookedPlugin) {
            if (hook.cancel(loc)) {
                return true;
            }
        }
        return defaultHook.cancel(loc);
    }


}
