package p1xel.nobuildplus.Hook;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class Hooks {

    // true to cancel
    public static boolean cancel(Location loc) {

        if (HRes.isInRes(loc)) {
            return true;
        }

        if (HDom.isInDom(loc)) {
            return true;
        }

        return false;
    }

    // true to cancel
    public static boolean cancel(Block b) {

        if (HRes.isInRes(b)) {
            return true;
        }

        if (HDom.isInDom(b)) {
            return true;
        }

        return false;
    }

    // true to cancel
    public static boolean cancel(Player p) {

        if (HRes.isInRes(p)) {
            return true;
        }

        if (HDom.isInDom(p)) {
            return true;
        }

        return false;
    }

    // true to cancel
    public static boolean cancel(Entity e) {

        if (HRes.isInRes(e)) {
            return true;
        }

        if (HDom.isInDom(e)) {
            return true;
        }

        return false;
    }

}
