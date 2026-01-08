package p1xel.nobuildplus.hook.loader;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import p1xel.nobuildplus.hook.*;
import p1xel.nobuildplus.storage.Config;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class AreaProtectionLoader {

    private static final Map<String, Supplier<Hooks>> AREA_PROTECTION_HOOKS =
            new HashMap<String, Supplier<Hooks>>() {{
                put("Residence", HRes::new);
                put("Dominion", HDom::new);
                put("BlockRegen", HBlockRegen::new);
            }};


    public static void load(JavaPlugin plugin) {
        for (Map.Entry<String, Supplier<Hooks>> entry : AREA_PROTECTION_HOOKS.entrySet()) {
            if (Config.getBool("hook." + entry.getKey())) {
                if (Bukkit.getPluginManager().getPlugin(entry.getKey()) != null) {
                    Hooks hook = entry.getValue().get();
                    HookedPlugins.addHookPlugin(hook);
                    for (Listener listener : hook.getListeners()) {
                        plugin.getServer().getPluginManager().registerEvents(listener, plugin);
                    }
                    plugin.getLogger().info("Loaded AreaProtection hook: " + entry.getKey());
                }
            }
        }
    }

}
