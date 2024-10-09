package p1xel.nobuildplus;

import com.bekvon.bukkit.residence.Residence;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import p1xel.nobuildplus.API.NBPAPI;
import p1xel.nobuildplus.Command.Cmd;
import p1xel.nobuildplus.Command.TabList;
import p1xel.nobuildplus.Listener.*;
import p1xel.nobuildplus.Storage.*;
import p1xel.nobuildplus.bStats.Metrics;
import p1xel.nobuildplus.spigotmc.UpdateChecker;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class NoBuildPlus extends JavaPlugin {

    private static NoBuildPlus instance;

    public static NoBuildPlus getInstance() {
        return instance;
    }

    private NBPAPI api;
    public NBPAPI getAPI() {
        return api;
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

    public static boolean isDominionEnabled() {
        return Bukkit.getServer().getPluginManager().isPluginEnabled("Dominion");
    }


    public static boolean isOraxenEnabled() {
        return Bukkit.getServer().getPluginManager().isPluginEnabled("Oraxen");
    }

    @Override
    public void onLoad() {
        instance = this;
        api = new NBPAPI();
        saveDefaultConfig();
        saveOtherConfigs();
        Config.update();
        Locale.createLocaleFile();
        FlagsManager.createFlagsManagerFile();
        Settings.createSettingsFile();
        Worlds.createWorldsFile();
        Settings.defaultList();

    }

    @Override
    public void onEnable() {

        updateFlags();

        if (Config.getConfigurationVersion() < 3) {
            getConfig().set("Configuration", 3);
            getConfig().set("hook.Dominion", true);
            try {
                getConfig().save("config.yml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


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

        updateVersion();
        getLogger().info("Plugin loaded! Version: " + Config.getVersion());

        NBPAPI api = new NBPAPI();
        String message = api.getInfo() + api.getVersion();
        getLogger().info("NBP API is loaded! " + message);

        // Text from https://tools.miku.ac/taag/ (Font: Slant)
        getLogger().info("    _   __      ____        _ __    ______  __          ");
        getLogger().info("   / | / /___  / __ )__  __(_) /___/ / __ \\\\/ /_  _______");
        getLogger().info("  /  |/ / __ \\\\/ __  / / / / / / __  / /_/ / / / / / ___/");
        getLogger().info(" / /|  / /_/ / /_/ / /_/ / / / /_/ / ____/ / /_/ (__  ) ");
        getLogger().info("/_/ |_/\\____/_____/\\__,_/_/_/\\__,_/_/   /_/\\__,_/____/  ");

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

        if (Config.getBool("hook.Oraxen")) {
            Plugin oraxen = getServer().getPluginManager().getPlugin("Oraxen");
            if (oraxen != null) {
                getServer().getPluginManager().enablePlugin(oraxen);
                getLogger().info("Oraxen is enabled by NoBuildPlus.");
                getServer().getPluginManager().registerEvents(new OraxenListener(), this);
            }
        }
    }

    void updateFlags() {

        for (Map.Entry<String, Flags> entry : Flags.getMaps().entrySet()) {

            String key = entry.getKey();

            if (!FlagsManager.yaml.getConfigurationSection("flags").getKeys(false).contains(key)) {

                Flags flag = entry.getValue();

                FlagsManager.yaml.set("flags." + key + ".enable", flag.getDefaultFlagEnabled());
                FlagsManager.yaml.set("flags." + key + ".show-item", flag.getDefaultShowItem());
                FlagsManager.yaml.set("flags." + key + ".slot", flag.getDefaultSlot());
                FlagsManager.yaml.set("flags." + key + ".type", flag.getDefaultType());
                FlagsManager.yaml.set("flags." + key + ".list", flag.getDefaultList());
                getLogger().info("Flag " + key.toUpperCase() + " is updated to flags.yml!");

                Settings.yaml.set("global-settings.flags."+key, true);
                getLogger().info("Flag " + key.toUpperCase() + " is updated to settings.yml!");

                for (String world : Settings.getEnableWorldList()) {

                    Worlds.yaml.set(world + ".flags." + key, true);
                    getLogger().info("Flag " + key.toUpperCase() + " is updated to " + world + " in worlds.yml!");

                }

            }

        }

        File flags = new File(getDataFolder(), "flags.yml");
        File settings = new File(getDataFolder(), "settings.yml");
        File worlds = new File(getDataFolder(), "worlds.yml");

        try {
            FlagsManager.yaml.save(flags);
            Settings.yaml.save(settings);
            Worlds.yaml.save(worlds);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        FlagsManager.upload(flags);
        Settings.upload(settings);
        Worlds.upload(worlds);

        FlagsManager.defaultFlagList();
        Settings.defaultList();
        GUIManager.instance.initialization();

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

//    // API? (unfinished)
//    private NBPAPI nbp;
//    public NBPAPI getNBPAPI() {
//        return nbp;
//    }
//
//    // The code you need to write into your onEnable()
//    if (getServer().getPluginManager().getPlugin("NoBuildPlus") != null) {
//        nbp = new NBPAPI();
//    }

}
