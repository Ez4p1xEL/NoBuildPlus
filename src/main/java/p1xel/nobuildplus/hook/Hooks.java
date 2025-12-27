package p1xel.nobuildplus.hook;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.List;

public abstract class Hooks {

    public abstract List<Listener> getListeners();

    // true to cancel
    public abstract boolean cancel(Location loc);

    // true to cancel
    public abstract boolean cancel(Block b);

    // true to cancel
    public abstract boolean cancel(Player p);

    // true to cancel
    public abstract boolean cancel(Entity e);

}
