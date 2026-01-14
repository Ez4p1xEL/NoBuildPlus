package p1xel.nobuildplus;

import cn.lunadeer.dominion.api.DominionAPI;
import com.bekvon.bukkit.residence.Residence;
import com.tcoded.folialib.FoliaLib;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import p1xel.nobuildplus.api.NBPAPI;
import p1xel.nobuildplus.command.Cmd;
import p1xel.nobuildplus.command.TabList;
import p1xel.nobuildplus.hook.loader.AreaProtectionLoader;
import p1xel.nobuildplus.hook.loader.FlagRegistrationLoader;
import p1xel.nobuildplus.listener.*;
import p1xel.nobuildplus.listener.gui.GUIListener;
import p1xel.nobuildplus.listener.text.TextEditMode;
import p1xel.nobuildplus.storage.*;
import p1xel.nobuildplus.tool.bstats.Metrics;
import p1xel.nobuildplus.tool.spigotmc.UpdateChecker;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class NoBuildPlus extends JavaPlugin {

    private static NoBuildPlus instance;
    private static FoliaLib foliaLib;
    private static DominionAPI dominionAPI;
    private static TextEditMode textEditMode;

    public static NoBuildPlus getInstance() {
        return instance;
    }

    private NBPAPI api;
    public NBPAPI getAPI() {
        return api;
    }
    public static FoliaLib getFoliaLib() {return foliaLib;}
    public static TextEditMode getTextEditMode() { return textEditMode; }

    @Override
    public void onLoad() {
        instance = this;
        foliaLib = new FoliaLib(this);
        api = new NBPAPI();
        saveDefaultConfig();
        Locale.createLocaleFile();
        FlagsManager.createFlagsManagerFile();
        Settings.createSettingsFile();
        Worlds.createWorldsFile();
        Settings.defaultList();
        FlagRegistry.refreshMap();

    }

    @Override
    public void onEnable() {

        updateConfig();
        textEditMode = new TextEditMode();

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
        getServer().getPluginManager().registerEvents(textEditMode, this);

        if (getBukkitVersion() >= 9) {
            getServer().getPluginManager().registerEvents(new NBPEntityListener_1_9(), this);
            getLogger().info("NBP Player Listener for 1.9+ is registered!");
        }

        if (getBukkitVersion() < 13) {
            getServer().getPluginManager().registerEvents(new NBPPlayerListener_1_8(), this);
            getLogger().info("NBP Player Listener for 1.8-1.12 is registered!");
        } else {
            getServer().getPluginManager().registerEvents(new NBPEntityListener_1_13(), this);
            getLogger().info("NBP Entity Listener for 1.13+ is registered!");
        }

        if (getBukkitVersion() > 15) {
            getLogger().info("In-game menu is enabled at 1.16+.");
        } else {
            getLogger().info("The version of your server does not support in-game menu.");
        }

        if (getBukkitVersion() >= 17) {
            getServer().getPluginManager().registerEvents(new NBPBlockListener_1_17(), this);
            getLogger().info("NBP Block Listener for 1.17+ is registered!");
        } else {
            getServer().getPluginManager().registerEvents(new NBPBlockListener_1_8(), this);
            getLogger().info("NBP Block Listener for 1.8-1.16 is registered!");
        }

        if (getBukkitVersion() >= 20) {
            getServer().getPluginManager().registerEvents(new NBPPlayerListener_1_20(), this);
            getLogger().info("NBP Player Listener for 1.20+ is registered!");
        }

        if (getBukkitVersion() >= 15) {
            getServer().getPluginManager().registerEvents(new GUIListener(), this);
        }
        getLogger().info("[NBP] LISTENERS LOADED.");

        AreaProtectionLoader.load(this);
        FlagRegistrationLoader.load(this);
        updateFlags();

        getLogger().info("[NBP] HOOKED FUNCTIONS LOADED.");

        updateVersion();
        getLogger().info("Plugin loaded! Version: " + Config.getVersion());

        NBPAPI api = new NBPAPI();
        String message = "NoBuildPlusAPI is running at " + api.getVersion();
        getLogger().info("NBP API is loaded! " + message);

        // Text from https://tools.miku.ac/taag/ (Font: Slant)
        getLogger().info("    _   __      ____        _ __    ______  __          ");
        getLogger().info("   / | / /___  / __ )__  __(_) /___/ / __ \\/ /_  _______");
        getLogger().info("  /  |/ / __ \\/ __  / / / / / / __  / /_/ / / / / / ___/");
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

    public int getBukkitVersion() {
        String v = Bukkit.getServer().getBukkitVersion().split("-")[0].split("\\.")[1];
        return Integer.parseInt(v);
    }

    void updateVersion() {
        getConfig().set("Version", getDescription().getVersion());
    }

    void updateFlags() {

        for (Map.Entry<String, Flag> entry : FlagRegistry.getMap().entrySet()) {

            String key = entry.getKey();

            if (!FlagsManager.yaml.getConfigurationSection("flags").getKeys(false).contains(key)) {

                Flag flag = entry.getValue();
                if (flag.inLocalFile()) {
                    FlagsManager.yaml.set("flags." + key + ".enable", true);
                    //FlagsManager.yaml.set("flags." + key + ".show-item", flag.getShowItem());
                    //FlagsManager.yaml.set("flags." + key + ".slot", flag.getSlot());
                    FlagsManager.yaml.set("flags." + key + ".type", flag.getDefaultType());
                    FlagsManager.yaml.set("flags." + key + ".list", flag.getDefaultList());
                    getLogger().info("Flag " + key.toUpperCase() + " is updated to flags.yml!");
                }

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

    }

    void updateConfig() {
        int v = Config.getConfigurationVersion();

        if (v < 7) {
            getConfig().set("Configuration", 7);
            getConfig().set("hook.BlockRegen", true);
            if (v < 6) {
                getConfig().set("hook.ItemsAdder", true);
                if (v < 5) {
                    getConfig().set("text-edit-mode.cancel", "cancel");
                    if (v < 4) {
                        if (v < 3) {
                            getConfig().set("hook.Dominion", true);
                        }
                        getConfig().set("deny-message-type", "MESSAGE");
                        getConfig().set("deny-message-sound.name", "ENTITY_VILLAGER_NO");
                    }
                }
            }
        }
        try {
            getConfig().save(new File(getDataFolder(), "config.yml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
