package p1xel.nobuildplus.listener.version.feature;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import p1xel.nobuildplus.Flags;
import p1xel.nobuildplus.hook.HookedPlugins;
import p1xel.nobuildplus.listener.version.FeatureListener;
import p1xel.nobuildplus.world.ProtectedWorld;
import p1xel.nobuildplus.world.WorldManager;

public class SpearListener implements FeatureListener {

    @Override
    public String getName() {
        return "SpearListener 1.21.11+";
    }

    @Override
    public boolean matchRequirement(int[] version) {
        return (version[0] == 1 && version[1] >= 21 && version[2] >= 11) || version[0] > 1;
    }

    @EventHandler
    public void onUsingSpear(PlayerInteractEvent event) {

        Player player = event.getPlayer();

        if (HookedPlugins.cancel(player)) {
            return;
        }

        String worldName = player.getWorld().getName();
        ProtectedWorld world = WorldManager.getWorld(worldName);
        if (!Flags.spear.isEnabled(world)) {
            return;
        }

//        Action action = event.getAction();
//        if (!(action == Action.RIGHT_CLICK_BLOCK || action == Action.RIGHT_CLICK_AIR)) {
//            return;
//        }

        ItemStack item = event.getItem();
        if (item == null) {
            return;
        }

        Material type = item.getType();

        if (type == Material.AIR || !Flags.spear.getList().contains(type.toString().toUpperCase())) {
            return;
        }

        if (player.hasPermission(world.getPermission())) {
            return;
        }

        WorldManager.sendMessage(player, world);
        event.setCancelled(true);

    }

}
