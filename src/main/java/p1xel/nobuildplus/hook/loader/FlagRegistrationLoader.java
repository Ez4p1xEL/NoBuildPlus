package p1xel.nobuildplus.hook.loader;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import p1xel.nobuildplus.Flag;
import p1xel.nobuildplus.FlagRegistry;
import p1xel.nobuildplus.flag.ItemsAdderFlags;
import p1xel.nobuildplus.listener.hookedplugins.ItemsAdderListener;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FlagRegistrationLoader {

    private static final Map<String, Flag[]> FLAG_REGISTRATION_HOOKS = new HashMap<String, Flag[]>() {{
        put("ItemsAdder", ItemsAdderFlags.values());
    }};

    private static final Map<String, List<Listener>> LISTENER_REGISTRATION = new HashMap<String, List<Listener>>() {{
        put("ItemsAdder", Collections.singletonList(new ItemsAdderListener()));
    }};

    public static void load(JavaPlugin plugin) {
        FLAG_REGISTRATION_HOOKS.forEach((pluginName, flags) -> {
            if (Bukkit.getPluginManager().getPlugin(pluginName) != null) {
                for (Flag flag : flags) {
                    FlagRegistry.registerFlag(flag);
                }
                for (Listener listener : LISTENER_REGISTRATION.get(pluginName)) {
                    plugin.getServer().getPluginManager().registerEvents(listener, plugin);
                }
                plugin.getLogger().info("Registered flags from: " + pluginName);
            }
        });

    }
}
