package p1xel.nobuildplus.Hook;

import cn.lunadeer.dominion.api.dtos.DominionDTO;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import p1xel.nobuildplus.NoBuildPlus;
import p1xel.nobuildplus.Storage.Config;


public class HDom extends Hooks {

    @Override
    public boolean cancel(Location loc) {
        if (!Config.isDominionEnabled()) {
            return false;
        }

        try {
            DominionDTO d = NoBuildPlus.getDominionAPI().getDominion(loc);
            return d != null;
        } catch (Exception e) {
            Bukkit.getLogger().info("There is some error");
        }

        return false;
    }

    @Override
    public boolean cancel(Player p) {
        if (!Config.isDominionEnabled()) {
            return false;
        }

        Location loc = p.getLocation();
        try {
            DominionDTO d = NoBuildPlus.getDominionAPI().getDominion(loc);
            return d != null;
        } catch (Exception e) {
            Bukkit.getLogger().info("There is some error");
        }

        return false;
    }

    @Override
    public boolean cancel(Block b) {
        if (!Config.isDominionEnabled()) {
            return false;
        }

        Location loc = b.getLocation();
        try {
            DominionDTO d = NoBuildPlus.getDominionAPI().getDominion(loc);
            return d != null;
        } catch (Exception e) {
            Bukkit.getLogger().info("There is some error");
        }

        return false;
    }

    @Override
    public boolean cancel(Entity e) {
        if (!Config.isDominionEnabled()) {
            return false;
        }

        Location loc = e.getLocation();
        try {
            DominionDTO d = NoBuildPlus.getDominionAPI().getDominion(loc);
            return d != null;
        } catch (Exception ex) {
            Bukkit.getLogger().info("There is some error");
        }

        return false;
    }


}
