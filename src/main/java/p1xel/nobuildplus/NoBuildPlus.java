package p1xel.nobuildplus;

import com.tcoded.folialib.FoliaLib;
import org.bukkit.plugin.java.JavaPlugin;
import p1xel.nobuildplus.api.NBPAPI;
import p1xel.nobuildplus.command.Cmd;
import p1xel.nobuildplus.command.TabList;
import p1xel.nobuildplus.gamerule.GameRuleRegistry;
import p1xel.nobuildplus.hook.loader.AreaProtectionLoader;
import p1xel.nobuildplus.hook.loader.FlagRegistrationLoader;
import p1xel.nobuildplus.listener.*;
import p1xel.nobuildplus.listener.gui.GUIListener;
import p1xel.nobuildplus.listener.text.TextEditMode;
import p1xel.nobuildplus.listener.version.*;
import p1xel.nobuildplus.storage.*;
import p1xel.nobuildplus.tool.ColorUtil;
import p1xel.nobuildplus.tool.bstats.Metrics;
import p1xel.nobuildplus.tool.spigotmc.UpdateChecker;
import p1xel.nobuildplus.world.WorldManager;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class NoBuildPlus extends JavaPlugin {

    private int[] version;
    private static NoBuildPlus instance;
    private static FoliaLib foliaLib;
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

    private boolean firstLoad = false;

    @Override
    public void onLoad() {

        // Check if the plugin is loaded for the first time
        // 检查插件是否第一次被服务器加载
        File dataFolder = getDataFolder();
        if (!dataFolder.exists()) {
            firstLoad = true;
        }

        instance = this;
        foliaLib = new FoliaLib(this);
        api = new NBPAPI();
        saveDefaultConfig();

    }

    @Override
    public void onEnable() {

        String[] stringVersion = getServer().getBukkitVersion().split("-")[0].split("\\.");
        
        // --- 修复版本号解析越界问题 ---
        int major = Integer.parseInt(stringVersion[0]);
        int minor = Integer.parseInt(stringVersion[1]);
        // 如果版本号只有两段 (如 1.21)，则第三段默认为 0
        int patch = stringVersion.length > 2 ? Integer.parseInt(stringVersion[2]) : 0;
        version = new int[]{major, minor, patch};
        // ------------------------------

        // 加载 ColorUtil (Hex color support for 1.16.1+)
        if (((version[0] == 1 && version[1] > 16) || (version[0] == 1 && version[1] == 16 && version[2] >= 1)) || version[0] >= 26) {
            ColorUtil.hexColorEnabled = true;
        }

        // 更改语言
        if (firstLoad) {
            checkSystemLanguage();
        }

        Locale.createLocaleFile();
        FlagsManager.createFlagsManagerFile();
        Settings.createSettingsFile();
        Worlds.createWorldsFile();
        WorldManager.init();
        Settings.defaultList();
        FlagRegistry.refreshMap();
        RuleSetting.createFile();

        updateConfig();
        Logger.setEnabled(Config.getBool("debug"));
        GameRuleRegistry.init();
        textEditMode = new TextEditMode();
        MenuConfig.initialization();

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
        getServer().getPluginManager().registerEvents(new NBPListener(), this);
        getServer().getPluginManager().registerEvents(textEditMode, this);

        FeatureListenerLoader.loadListeners(this);

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
                    getLogger().info(Locale.getMessage("update-check.outdate").replace("%new_version%", version));
                }
            });
        }

    }

    // Only for old version, below 1.21.11;
    public int[] getBukkitVersion() {
        return version;
    }

    public boolean isLegacyVersion() {
        return version[0] <= 1;
    }

    public boolean isMenuFunctionEnabled() {
        if (!isLegacyVersion()) {
            return true;
        }
        return version[0] != 1 || version[1] >= 15;
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

                for (String world : WorldManager.getWorldsInName()) {

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

        switch (v) {
            case 0: // v < 3 的情况
            case 1:
            case 2:
                getConfig().set("hook.Dominion", true);
                // fall through
            case 3:
                getConfig().set("deny-message-type", "MESSAGE");
                getConfig().set("deny-message-sound.name", "ENTITY_VILLAGER_NO");
                // fall through
            case 4:
                getConfig().set("text-edit-mode.cancel", "cancel");
                // fall through
            case 5:
                getConfig().set("hook.ItemsAdder", true);
                // fall through
            case 6:
                getConfig().set("hook.BlockRegen", true);
                // fall through
            case 7:
                getConfig().set("debug", false);
                // fall through
            case 8:
                getConfig().set("open-menu-if-gamerule.enable", true);
                getConfig().set("open-menu-if-gamerule.notification", true);
                getConfig().set("open-menu-if-gamerule.disable-in-non-protected-world", false);
                getConfig().set("Configuration", 9);
                break;
            default:
                // latest version
                break;
        }

        try {
            getConfig().save(new File(getDataFolder(), "config.yml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void checkSystemLanguage() {

        java.util.Locale currentLocale = java.util.Locale.getDefault();
        String language = currentLocale.getLanguage();
        String country = currentLocale.getCountry();
        String lang = language + "_" + country;

        List<String> languages = Locale.getLanguages();

        if (lang.equals("zh_HK")) {
            lang = "zh_TW";
        }

        if (!languages.contains(language)) {
            // 如果插件没有这个语言, 则默认配置文件的语言(英文)
            if (!languages.contains(lang)) {
                return;
            }
        } else {
            lang = language;
        }

        getConfig().set("Language", lang);
        saveConfig();

    }

}
