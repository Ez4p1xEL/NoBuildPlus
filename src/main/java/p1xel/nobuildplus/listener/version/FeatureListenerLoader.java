package p1xel.nobuildplus.listener.version;

import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.plugin.java.JavaPlugin;
import p1xel.nobuildplus.NoBuildPlus;
import p1xel.nobuildplus.listener.gui.GUIListener;
import p1xel.nobuildplus.listener.version.feature.*;

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
            new WindChargeListener(),
            new CrystalListener(),
            new ChorusListener(),
            new SpearListener(),
            new LegacySpearLungeListener(),
            new SpearLungeListener(),
            new CushionListener()
            );


    public static void loadListeners(JavaPlugin plugin) {

        int[] version = NoBuildPlus.getInstance().getBukkitVersion();
        for (FeatureListener listener : listeners) {
            if (listener.matchRequirement(version)) {
                plugin.getServer().getPluginManager().registerEvents(listener, plugin);
                plugin.getLogger().info("NBP Listener " + listener.getName() + " has been registered.");
            }
        }

        if (NoBuildPlus.getFoliaLib().isPaper()) {
            try {
                plugin.getServer().getPluginManager().registerEvent((Class<? extends Event>) Class.forName("io.papermc.paper.event.entity.EntityBreakByEntityEvent"), new PaperCushionBreakListener(), EventPriority.NORMAL, new PaperEventExecutor(), plugin);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

    }

}
