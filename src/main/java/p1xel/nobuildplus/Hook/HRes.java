package p1xel.nobuildplus.Hook;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.api.ResidenceApi;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import p1xel.nobuildplus.NoBuildPlus;
import p1xel.nobuildplus.Storage.Config;

public class HRes {

    public static Residence get() {
        return NoBuildPlus.getRes();
    }

    public static boolean isInRes(Player p) {
        if (!NoBuildPlus.isResidenceEnabled() || !Config.isResidenceEnabled()) {
            return false;
        }
        Location loc = p.getLocation();
        ClaimedResidence res = get().getResidenceManager().getByLoc(loc);
        if (res != null) {
            return true;
        }
        return false;
    }

    public static boolean isInRes(Entity e) {
        if (!NoBuildPlus.isResidenceEnabled() || !Config.isResidenceEnabled()) {
            return false;
        }
        Location loc = e.getLocation();
        ClaimedResidence res = get().getResidenceManager().getByLoc(loc);
        if (res != null) {
            return true;
        }
        return false;
    }

    public static boolean isInRes(Block b) {
        if (!NoBuildPlus.isResidenceEnabled() || !Config.isResidenceEnabled()) {
            return false;
        }
        Location loc = b.getLocation();
        ClaimedResidence res = get().getResidenceManager().getByLoc(loc);
        if (res != null) {
            return true;
        }
        return false;
    }

    public static boolean isInRes(Location loc) {
        if (!NoBuildPlus.isResidenceEnabled() || !Config.isResidenceEnabled()) {
            return false;
        }
        ClaimedResidence res = get().getResidenceManager().getByLoc(loc);
        if (res != null) {
            return true;
        }
        return false;
    }

}
