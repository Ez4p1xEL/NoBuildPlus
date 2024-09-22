package p1xel.nobuildplus.Hook;

import cn.lunadeer.dominion.Cache;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import p1xel.nobuildplus.NoBuildPlus;
import p1xel.nobuildplus.Storage.Config;


public class HDom {

    public static boolean isInDom(Location loc) {
        if (!NoBuildPlus.isDominionEnabled() || !Config.isDominionEnabled()) {
            return false;
        }

        if (Cache.instance.getDominionByLoc(loc) != null) {
            return true;
        }

        return false;
    }

    public static boolean isInDom(Player p) {
        if (!NoBuildPlus.isDominionEnabled() || !Config.isDominionEnabled()) {
            return false;
        }

        Location loc = p.getLocation();
        if (Cache.instance.getDominionByLoc(loc) != null) {
            return true;
        }

        return false;
    }

    public static boolean isInDom(Block b) {
        if (!NoBuildPlus.isDominionEnabled() || !Config.isDominionEnabled()) {
            return false;
        }

        Location loc = b.getLocation();
        if (Cache.instance.getDominionByLoc(loc) != null) {
            return true;
        }

        return false;
    }

    public static boolean isInDom(Entity e) {
        if (!NoBuildPlus.isDominionEnabled() || !Config.isDominionEnabled()) {
            return false;
        }

        Location loc = e.getLocation();
        if (Cache.instance.getDominionByLoc(loc) != null) {
            return true;
        }

        return false;
    }


}
