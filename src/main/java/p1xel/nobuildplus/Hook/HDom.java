package p1xel.nobuildplus.Hook;

import cn.lunadeer.dominion.api.DominionAPI;
import cn.lunadeer.dominion.api.dtos.DominionDTO;
import org.bukkit.Bukkit;
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

        try {
            DominionAPI dominionAPI = DominionAPI.getInstance();
            DominionDTO d = dominionAPI.getDominionByLoc(loc);
            return d != null;
        } catch (Exception e) {
            Bukkit.getLogger().info("There is some error");
        }

        return false;
    }

    public static boolean isInDom(Player p) {
        if (!NoBuildPlus.isDominionEnabled() || !Config.isDominionEnabled()) {
            return false;
        }

        Location loc = p.getLocation();
        try {
            DominionAPI dominionAPI = DominionAPI.getInstance();
            DominionDTO d = dominionAPI.getDominionByLoc(loc);
            return d != null;
        } catch (Exception e) {
            Bukkit.getLogger().info("There is some error");
        }

        return false;
    }

    public static boolean isInDom(Block b) {
        if (!NoBuildPlus.isDominionEnabled() || !Config.isDominionEnabled()) {
            return false;
        }

        Location loc = b.getLocation();
        try {
            DominionAPI dominionAPI = DominionAPI.getInstance();
            DominionDTO d = dominionAPI.getDominionByLoc(loc);
            return d != null;
        } catch (Exception e) {
            Bukkit.getLogger().info("There is some error");
        }

        return false;
    }

    public static boolean isInDom(Entity e) {
        if (!NoBuildPlus.isDominionEnabled() || !Config.isDominionEnabled()) {
            return false;
        }

        Location loc = e.getLocation();
        try {
            DominionAPI dominionAPI = DominionAPI.getInstance();
            DominionDTO d = dominionAPI.getDominionByLoc(loc);
            return d != null;
        } catch (Exception ex) {
            Bukkit.getLogger().info("There is some error");
        }

        return false;
    }


}
