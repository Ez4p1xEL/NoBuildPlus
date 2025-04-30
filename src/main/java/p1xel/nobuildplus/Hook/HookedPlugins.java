package p1xel.nobuildplus.Hook;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class HookedPlugins {

    private static List<Hooks> hooks = new ArrayList<>();

    public static void addHookPlugin(Hooks hook) {
        hooks.add(hook);
    }

    public static boolean cancel(Player player) {
        for (Hooks hook : hooks) {
            boolean result = hook.cancel(player);
            if (result) {
                return true;
            }
        }
        return false;
    }

    public static boolean cancel(Block block) {
        for (Hooks hook : hooks) {
            boolean result = hook.cancel(block);
            if (result) {
                return true;
            }
        }
        return false;
    }

    public static boolean cancel(Entity entity) {
        for (Hooks hook : hooks) {
            boolean result = hook.cancel(entity);
            if (result) {
                return true;
            }
        }
        return false;
    }

    public static boolean cancel(Location loc) {
        for (Hooks hook : hooks) {
            boolean result = hook.cancel(loc);
            if (result) {
                return true;
            }
        }
        return false;
    }


}
