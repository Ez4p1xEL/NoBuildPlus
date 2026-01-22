package p1xel.nobuildplus.storage;

import org.bukkit.configuration.Configuration;
import p1xel.nobuildplus.NoBuildPlus;
import p1xel.nobuildplus.world.WorldManager;

public class Config {

    static Configuration config = NoBuildPlus.getInstance().getConfig();

    public static String getString(String path) {
        return config.getString(path);
    }

    public static boolean getBool(String path) {
        return config.getBoolean(path);
    }

    public static String getLanguage() {
        return config.getString("Language");
    }

    public static void reloadConfig() {

        NoBuildPlus.getInstance().reloadConfig();
        config = NoBuildPlus.getInstance().getConfig();
        NoBuildPlus.getTextEditMode().cancelWord = Config.getString("text-edit-mode.cancel");
        RuleSetting.createFile();
        Logger.setEnabled(Config.getBool("debug"));
        MenuConfig.initialization();

    }

    public static String getVersion() {
        return getString("Version");
    }

    public static int getConfigurationVersion() {return getInt("Configuration");}

    public static int getInt(String path) {
        return config.getInt(path);
    }

    public static boolean isResidenceEnabled() {
        return getBool("hook.Residence");
    }
    public static boolean isDominionEnabled() {
        return getBool("hook.Dominion");
    }
    public static boolean isBlockRegenEnabled() {
        return getBool("hook.BlockRegen");
    }

    public static boolean isOraxenEnabled() { return getBool("hook.Oraxen"); }

}
