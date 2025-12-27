package p1xel.nobuildplus.hook;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import com.bekvon.bukkit.residence.protection.ResidenceManager;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import p1xel.nobuildplus.listener.hookedplugins.ResidenceListener;
import p1xel.nobuildplus.storage.Config;

import java.util.Collections;
import java.util.List;

public class HRes extends Hooks{

    private final Residence plugin = Residence.getInstance();
    private final ResidenceManager manager = plugin.getResidenceManager();
    private final boolean enabled = Config.isResidenceEnabled();

    @Override
    public List<Listener> getListeners() { return Collections.singletonList(new ResidenceListener()); }

    @Override
    public boolean cancel(Player p) {
        Location loc = p.getLocation();
        ClaimedResidence res = manager.getByLoc(loc);
        return res != null && enabled;
    }

    @Override
    public boolean cancel(Entity e) {
        Location loc = e.getLocation();
        ClaimedResidence res = manager.getByLoc(loc);
        return res != null && enabled;
    }

    @Override
    public boolean cancel(Block b) {
        Location loc = b.getLocation();
        ClaimedResidence res = manager.getByLoc(loc);
        return res != null && enabled;
    }

    @Override
    public boolean cancel(Location loc) {
        ClaimedResidence res = manager.getByLoc(loc);
        return res != null && enabled;
    }
}
