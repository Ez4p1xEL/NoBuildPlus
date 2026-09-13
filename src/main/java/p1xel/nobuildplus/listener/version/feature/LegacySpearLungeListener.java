package p1xel.nobuildplus.listener.version.feature;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import p1xel.nobuildplus.Flags;
import p1xel.nobuildplus.NoBuildPlus;
import p1xel.nobuildplus.hook.HookedPlugins;
import p1xel.nobuildplus.listener.version.FeatureListener;
import p1xel.nobuildplus.world.ProtectedWorld;
import p1xel.nobuildplus.world.WorldManager;

public class LegacySpearLungeListener implements FeatureListener {

    @Override
    public String getName() {
        return "LegacySpearLungeListener 1.21.11+";
    }

    @Override
    public boolean matchRequirement(int[] version) {
        //return (version[0] == 1 && version[1] >= 21 && version[2] >= 11);
        return (version[0] == 1 && version[1] >= 21 && version[2] >= 11) || version[0] > 1;
    }

    /* 由於我並沒有在 1.21.11 版本中找到EntityLungeEvent
     所以聽從AI的建議，用了PlayerAnimationEvent修改突進時的加速度 */

    @EventHandler
    public void onLunge(PlayerAnimationEvent event) {

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

        ItemStack item = player.getInventory().getItemInMainHand();
        Material type = item.getType();

        if (type == Material.AIR || !Flags.spear.getList().contains(type.toString().toUpperCase())) {
            return;
        }

        if (player.hasPermission(world.getPermission())) {
            return;
        }

        if (NoBuildPlus.getFoliaLib().isFolia()) {
            NoBuildPlus.getFoliaLib().getScheduler().runNextTick(wrappedTask -> {
                player.setVelocity(new Vector(0, player.getVelocity().getY(), 0));
            });
        } else {
            Bukkit.getScheduler().runTask(NoBuildPlus.getInstance(), task -> {
                player.setVelocity(new Vector(0, player.getVelocity().getY(), 0));
            });
        }

    }

}
