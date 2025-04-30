package p1xel.nobuildplus.Hook;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public abstract class Hooks {

    // true to cancel
    public abstract boolean cancel(Location loc);

    // true to cancel
    public abstract boolean cancel(Block b);

    // true to cancel
    public abstract boolean cancel(Player p);

    // true to cancel
    public abstract boolean cancel(Entity e);

}
