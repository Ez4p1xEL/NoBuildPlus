package p1xel.nobuildplus;

import com.bekvon.bukkit.residence.Residence;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import p1xel.nobuildplus.Command.Cmd;
import p1xel.nobuildplus.Listener.*;
import p1xel.nobuildplus.Storage.*;
import p1xel.nobuildplus.bStats.Metrics;
import p1xel.nobuildplus.spigotmc.UpdateChecker;

public class NoBuildPlus extends JavaPlugin {

    private static NoBuildPlus instance;

    public static NoBuildPlus getInstance() {
        return instance;
    }

    public static Residence getRes() {
        Plugin resPlug = Bukkit.getServer().getPluginManager().getPlugin("Residence");
        if (resPlug != null) {
            return Residence.getInstance();
        } else {
            return null;
        }
    }

    public static boolean isResidenceEnabled() {
        return Bukkit.getServer().getPluginManager().isPluginEnabled("Residence");
    }

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        Locale.createLocaleFile();
        Settings.createWorldsFile();
        Worlds.createWorldsFile();
        FlagsManager.createLocaleFile();

        getServer().getPluginCommand("NoBuildPlus").setExecutor(new Cmd());
        getServer().getPluginManager().registerEvents(new NoBuildPlusBlockListener(), this);
        getServer().getPluginManager().registerEvents(new NoBuildPlusEntityListener(), this);
        getServer().getPluginManager().registerEvents(new NoBuildPlusHangingListener(), this);
        getServer().getPluginManager().registerEvents(new NoBuildPlusPlayerListener(), this);
        getServer().getPluginManager().registerEvents(new NoBuildPlusServerListener(), this);
        getServer().getPluginManager().registerEvents(new NoBuildPlusVehicleListener(), this);




        getLogger().info("Plugin loaded! Version: " + Config.getVersion());

        int pluginId = 15126;
        new Metrics(this, pluginId);

        if (Config.getBool("check-update")) {
            new UpdateChecker(this, 101815).getVersion(version -> {
                if (this.getDescription().getVersion().equals(version)) {
                    getLogger().info(Locale.getMessage("update-check.latest"));
                } else {
                    getLogger().info(Locale.getMessage("update-check.outdate"));
                }
            });
        }

    }

}
