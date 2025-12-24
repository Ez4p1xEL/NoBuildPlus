package p1xel.nobuildplus.hook;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class HDefault extends Hooks {
    @Override
    public boolean cancel(Location loc) {
        return false;
    }

    @Override
    public boolean cancel(Block b) {
        return false;
    }

    @Override
    public boolean cancel(Player p) {
        return false;
    }

    @Override
    public boolean cancel(Entity e) {
        return false;
    }
}
