package p1xel.nobuildplus.hook;

import nl.aurorion.blockregen.BlockRegenPlugin;
import nl.aurorion.blockregen.region.RegionManager;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;

public class HBlockRegen extends Hooks{

    private final RegionManager regionManager = BlockRegenPlugin.getInstance().getRegionManager();

    @Override
    public List<Listener> getListeners() {
        return new ArrayList<>();
    }

    @Override
    public boolean cancel(Location loc) {
        return false;
    }

    @Override
    public boolean cancel(Block b) {
        return regionManager.getArea(b) != null;
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
