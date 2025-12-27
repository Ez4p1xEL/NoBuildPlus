package p1xel.nobuildplus.hook;

import cn.lunadeer.dominion.api.DominionAPI;
import cn.lunadeer.dominion.api.dtos.DominionDTO;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import p1xel.nobuildplus.NoBuildPlus;
import p1xel.nobuildplus.listener.hookedplugins.DominionListener;
import p1xel.nobuildplus.storage.Config;

import java.util.Collections;
import java.util.List;


public class HDom extends Hooks {

    private DominionAPI dominionAPI = null;
    private final boolean enabled = Config.isDominionEnabled();

    public HDom() {
        try {
            dominionAPI = DominionAPI.getInstance();
        } catch (Exception e) {
            NoBuildPlus.getInstance().getLogger().info("Dominion function loading failure.");
        }
    }

    @Override
    public List<Listener> getListeners() {
        return Collections.singletonList(new DominionListener());
    }

    @Override
    public boolean cancel(Location loc) {

        DominionDTO d = dominionAPI.getDominion(loc);
        return d != null && enabled;
    }

    @Override
    public boolean cancel(Player p) {

        Location loc = p.getLocation();
        DominionDTO d = dominionAPI.getDominion(loc);
        return d != null && enabled;
    }

    @Override
    public boolean cancel(Block b) {
        Location loc = b.getLocation();
        DominionDTO d = dominionAPI.getDominion(loc);
        return d != null && enabled;
    }

    @Override
    public boolean cancel(Entity e) {

        Location loc = e.getLocation();
        DominionDTO d = dominionAPI.getDominion(loc);
        return d != null && enabled;
    }


}
