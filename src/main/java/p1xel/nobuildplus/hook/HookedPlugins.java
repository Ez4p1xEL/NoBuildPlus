package p1xel.nobuildplus.hook;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class HookedPlugins {

    private static Hooks hookedPlugin;

    public static void addHookPlugin(Hooks hook) {
        hookedPlugin = hook;
    }

    public static boolean cancel(Player player) {
        return hookedPlugin.cancel(player);
    }

    public static boolean cancel(Block block) {
        return hookedPlugin.cancel(block);
    }

    public static boolean cancel(Entity entity) {
        return hookedPlugin.cancel(entity);
    }

    public static boolean cancel(Location loc) {
        return hookedPlugin.cancel(loc);
    }


}
