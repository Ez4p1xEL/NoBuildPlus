package p1xel.nobuildplus.Hook;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import p1xel.nobuildplus.NoBuildPlus;
import p1xel.nobuildplus.Storage.Config;

public class HRes extends Hooks{

    public static Residence get() {
        return NoBuildPlus.getRes();
    }

    private boolean enabled = Config.isResidenceEnabled();

    @Override
    public boolean cancel(Player p) {
        Location loc = p.getLocation();
        ClaimedResidence res = get().getResidenceManager().getByLoc(loc);
        return res != null && enabled;
    }

    @Override
    public boolean cancel(Entity e) {
        Location loc = e.getLocation();
        ClaimedResidence res = get().getResidenceManager().getByLoc(loc);
        return res != null && enabled;
    }

    @Override
    public boolean cancel(Block b) {
        Location loc = b.getLocation();
        ClaimedResidence res = get().getResidenceManager().getByLoc(loc);
        return res != null && enabled;
    }

    @Override
    public boolean cancel(Location loc) {
        ClaimedResidence res = get().getResidenceManager().getByLoc(loc);
        return res != null && enabled;
    }

}
