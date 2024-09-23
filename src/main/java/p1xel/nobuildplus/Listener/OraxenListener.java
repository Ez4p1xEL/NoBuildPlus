package p1xel.nobuildplus.Listener;

import io.th0rgal.oraxen.api.OraxenBlocks;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import p1xel.nobuildplus.Flags;
import p1xel.nobuildplus.NoBuildPlus;
import p1xel.nobuildplus.Storage.Config;
import p1xel.nobuildplus.Storage.FlagsManager;
import p1xel.nobuildplus.Storage.Settings;
import p1xel.nobuildplus.Storage.Worlds;

public class OraxenListener implements Listener {


    // Flag: break (Oraxen support)
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onBreak(final BlockBreakEvent e) {

        if (!Config.isOraxenEnabled() || !NoBuildPlus.isOraxenEnabled()) {
            return;
        }
        Block block = e.getBlock();


        if (!OraxenBlocks.isOraxenBlock(block)) {
            return;
        }

        String world = block.getWorld().getName();

        if (!Flags.destroy.isEnabled(world)) {
            return;
        }

        Player p = e.getPlayer();

        if (p.hasPermission(Worlds.getPermission(world))) {
            return;
        }

        // When type is "all"
        if (Flags.destroy.getType().equalsIgnoreCase("all")) {

            if (Worlds.isDenyMessageExist(world)) {
                p.sendMessage(Worlds.getDenyMessage(world));
            }

            e.setCancelled(true);
            return;

        }


        // When type is "list"

       if (Flags.destroy.getType().equalsIgnoreCase("list")) {
           for (String item : Flags.destroy.getList()) {

               if (!item.startsWith("oraxen:")) {
                   break;
               }

               item = item.replaceAll("oraxen:", "");

               if (OraxenBlocks.getOraxenBlock(block.getLocation()).getItemID().equalsIgnoreCase(item)) {
                   if (Worlds.isDenyMessageExist(world)) {
                       p.sendMessage(Worlds.getDenyMessage(world));
                   }

                   e.setCancelled(true);
                   return;
               }

           }
       }

    }
}
