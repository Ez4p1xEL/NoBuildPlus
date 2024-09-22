package p1xel.nobuildplus.Storage;

import org.bukkit.configuration.Configuration;
import p1xel.nobuildplus.NoBuildPlus;

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

    public static boolean isOraxenEnabled() { return getBool("hook.Oraxen"); }

    public static void update() {
        // Update the config according to the configuration file version.
        if (config.getInt("Configuration") < 2) {
            config.set("hook.Oraxen", true);
        }
    }

}
