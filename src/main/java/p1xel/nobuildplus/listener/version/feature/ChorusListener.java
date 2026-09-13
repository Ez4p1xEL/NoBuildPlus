package p1xel.nobuildplus.listener.version.feature;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import p1xel.nobuildplus.Flags;
import p1xel.nobuildplus.hook.HookedPlugins;
import p1xel.nobuildplus.listener.version.FeatureListener;
import p1xel.nobuildplus.world.ProtectedWorld;
import p1xel.nobuildplus.world.WorldManager;

public class ChorusListener implements FeatureListener {

    @Override
    public String getName() {
        return "ChorusListener 1.9+";
    }

    @Override
    public boolean matchRequirement(int[] version) {
        return (version[0] == 1 && version[1] >= 9) || version[0] > 1;
    }

    @EventHandler
    public void onConsumeChorusFruit(PlayerItemConsumeEvent event) {

        Player player = event.getPlayer();

        if (HookedPlugins.cancel(player)) {
            return;
        }

        ItemStack item = event.getItem();
        Material type = item.getType();

        if (type != Material.CHORUS_FRUIT) {
            return;
        }

        String worldName = player.getWorld().getName();
        ProtectedWorld world = WorldManager.getWorld(worldName);
        if (!Flags.chorus_teleport.isEnabled(world)) {
            return;
        }

        if (player.hasPermission(world.getPermission())) {
            return;
        }

        WorldManager.sendMessage(player, world);
        event.setCancelled(true);

    }

}
