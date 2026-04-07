package p1xel.nobuildplus.listener.version;

import org.bukkit.plugin.java.JavaPlugin;
import p1xel.nobuildplus.NoBuildPlus;
import p1xel.nobuildplus.listener.gui.GUIListener;
import p1xel.nobuildplus.listener.version.feature.WindChargeListener;

import java.util.Arrays;
import java.util.List;

public class FeatureListenerLoader {

    private static final List<FeatureListener> listeners = Arrays.asList(
            new GUIListener(),
            new NBPBlockListener_1_8(),
            new NBPPlayerListener_1_8(),
            new NBPEntityListener_1_9(),
            new NBPPortalListener_1_12(),
            new NBPEntityListener_1_13(),
            new NBPBlockListener_1_17(),
            new NBPPlayerListener_1_20(),
            new WindChargeListener()
            );


    public static void loadListeners(JavaPlugin plugin) {

        int[] version = NoBuildPlus.getInstance().getBukkitVersion();
        for (FeatureListener listener : listeners) {
            if (listener.matchRequirement(version)) {
                plugin.getServer().getPluginManager().registerEvents(listener, plugin);
                plugin.getLogger().info("NBP Listener " + listener.getName() + " has been registered.");
            }
        }

    }

}
