package p1xel.nobuildplus.Storage;

import org.bukkit.ChatColor;
import org.bukkit.configuration.Configuration;
import p1xel.nobuildplus.NoBuildPlus;

import java.util.List;

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

    }

    public static String getVersion() {
        return getString("Version");
    }

    public static int getInt(String path) {
        return config.getInt(path);
    }

    public static boolean isResidenceEnabled() {
        return getBool("hook.Residence");
    }

}
