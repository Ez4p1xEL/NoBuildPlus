package p1xel.nobuildplus.hook;

import cn.lunadeer.dominion.api.dtos.DominionDTO;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import p1xel.nobuildplus.NoBuildPlus;
import p1xel.nobuildplus.storage.Config;


public class HDom extends Hooks {

    private boolean enabled = Config.isDominionEnabled();

    @Override
    public boolean cancel(Location loc) {

        DominionDTO d = NoBuildPlus.getDominionAPI().getDominion(loc);
        return d != null && enabled;
    }

    @Override
    public boolean cancel(Player p) {

        Location loc = p.getLocation();
        DominionDTO d = NoBuildPlus.getDominionAPI().getDominion(loc);
        return d != null && enabled;
    }

    @Override
    public boolean cancel(Block b) {
        Location loc = b.getLocation();
        DominionDTO d = NoBuildPlus.getDominionAPI().getDominion(loc);
        return d != null && enabled;
    }

    @Override
    public boolean cancel(Entity e) {

        Location loc = e.getLocation();
        DominionDTO d = NoBuildPlus.getDominionAPI().getDominion(loc);
        return d != null && enabled;
    }


}
