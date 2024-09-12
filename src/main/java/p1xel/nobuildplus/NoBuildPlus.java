package p1xel.nobuildplus;

import com.bekvon.bukkit.residence.Residence;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import p1xel.nobuildplus.Command.Cmd;
import p1xel.nobuildplus.Command.TabList;
import p1xel.nobuildplus.Listener.*;
import p1xel.nobuildplus.Storage.*;
import p1xel.nobuildplus.bStats.Metrics;
import p1xel.nobuildplus.spigotmc.UpdateChecker;

import java.io.File;

public class NoBuildPlus extends JavaPlugin {

    private static NoBuildPlus instance;

    public static NoBuildPlus getInstance() {
        return instance;
    }

    // Will be adjusted
    private void saveOtherConfigs() {
        File file = new File(this.getDataFolder(), "config_zh_CN.yml");
        if (!file.exists()) {
            saveResource("config_zh_CN.yml", false);
        }
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

    public static boolean isOraxenEnabled() {
        return Bukkit.getServer().getPluginManager().isPluginEnabled("Oraxen");
    }

    @Override
    public void onLoad() {
        instance = this;
        saveDefaultConfig();
        saveOtherConfigs();
        Config.update();
        Locale.createLocaleFile();
        FlagsManager.createFlagsManagerFile();
        Settings.createSettingsFile();
        Worlds.createWorldsFile();

        GUIManager.instance.initialization();

    }

    @Override
    public void onEnable() {
        Settings.updateFromFlagsManager();
        Worlds.updateFromFlagsManager();

        getLogger().info("[NBP] START LOADING ...");
        getServer().getPluginCommand("NoBuildPlus").setExecutor(new Cmd());
        getServer().getPluginCommand("NoBuildPlus").setTabCompleter(new TabList());
        getLogger().info("[NBP] CMD LOADED.");
        getServer().getPluginManager().registerEvents(new NoBuildPlusBlockListener(), this);
        getServer().getPluginManager().registerEvents(new NoBuildPlusEntityListener(), this);
        getServer().getPluginManager().registerEvents(new NoBuildPlusHangingListener(), this);
        getServer().getPluginManager().registerEvents(new NoBuildPlusPlayerListener(), this);
        getServer().getPluginManager().registerEvents(new NoBuildPlusServerListener(), this);
        getServer().getPluginManager().registerEvents(new NoBuildPlusVehicleListener(), this);
        getServer().getPluginManager().registerEvents(new GUIManager(), this);
        getLogger().info("[NBP] LISTENERS LOADED.");

        checkHookPlugins();

        getLogger().info("[NBP] HOOKED FUNCTIONS LOADED.");

        Settings.defaultList();
        updateVersion();
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

    void updateVersion() {
        getConfig().set("Version", getDescription().getVersion());
    }

    void checkHookPlugins() {

        if (Config.getBool("hook.Residence")) {
            Plugin res = getServer().getPluginManager().getPlugin("Residence");
            Plugin cmilib = getServer().getPluginManager().getPlugin("CMILib");
            if (res != null && cmilib != null) {
                if (!cmilib.isEnabled()) {
                    getServer().getPluginManager().enablePlugin(cmilib);
                    getServer().getPluginManager().enablePlugin(res);
                    getLogger().info("Residence is enabled by NoBuildPlus.");
                }
                getServer().getPluginManager().registerEvents(new ResidenceListener(), this);
            }
        }

//        if (Config.getBool("hook.Oraxen")) {
//            Plugin oraxen = getServer().getPluginManager().getPlugin("Oraxen");
//            if (oraxen != null) {
//                getServer().getPluginManager().enablePlugin(oraxen);
//                getLogger().info("Oraxen is enabled by NoBuildPlus.");
//                getServer().getPluginManager().registerEvents(new OraxenListener(), this);
//            }
//        }
    }


    // bruh

    /* void updateConfig() {
        updateDefaultConfig(getResource("config.yml"), new File(getDataFolder(), "config.yml"), "config.yml");
        updateDefaultConfig(getResource("flags.yml"), new File(getDataFolder(), "flags.yml"), "flags.yml");
        updateDefaultConfig(getResource("settings.yml"), new File(getDataFolder(), "settings.yml"), "settings.yml");
        String langFile = Config.getLanguage() + ".yml";
        updateDefaultConfig(getResource("lang/" + langFile), new File(getDataFolder() + "/lang", langFile), langFile);
        int oldVersion = 1;
        new ConfigUpdater(this).checkUpdate(oldVersion);
    }

    void updateDefaultConfig(InputStream input, File actual, String name) {
        try (FileOutputStream output = new FileOutputStream(actual)) {
            byte[] buf = new byte[8192];
            int length;
            while ((length = input.read(buf)) > 0) {
                output.write(buf, 0, length);
            }
            getLogger().info("Updated to the latest: " + name);
        } catch (IOException e) {
            getLogger().log(Level.WARNING, "Failed to update the latest configuration!", e);
        }
    }
    */

}
