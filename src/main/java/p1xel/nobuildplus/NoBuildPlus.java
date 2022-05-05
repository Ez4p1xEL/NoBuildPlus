package p1xel.nobuildplus;

import org.bukkit.plugin.java.JavaPlugin;
import p1xel.nobuildplus.Command.Cmd;
import p1xel.nobuildplus.Listener.WorldProtect;
import p1xel.nobuildplus.Storage.*;
import p1xel.nobuildplus.bStats.Metrics;
import p1xel.nobuildplus.spigotmc.UpdateChecker;

public class NoBuildPlus extends JavaPlugin {

    private static NoBuildPlus instance;

    public static NoBuildPlus getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        Locale.createLocaleFile();
        Settings.createWorldsFile();
        Worlds.createWorldsFile();
        FlagsManager.createLocaleFile();
        FlagsManager.checkFlag();

        getServer().getPluginCommand("NoBuildPlus").setExecutor(new Cmd());
        getServer().getPluginManager().registerEvents(new WorldProtect(), this);

        getLogger().info("Plugin loaded! Version: " + Config.getVersion());

        int pluginId = 15126;
        Metrics metrics = new Metrics(this, pluginId);

        if (Config.getBool("check-update")) {
            new UpdateChecker(this, 101815).getVersion(version -> {
                if (this.getDescription().getVersion().equals(version)) {
                    getLogger().info("There is not a new update available.");
                } else {
                    getLogger().info("There is a new update available.");
                }
            });
        }

    }


}
