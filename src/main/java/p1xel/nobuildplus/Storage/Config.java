package p1xel.nobuildplus.Storage;

import org.bukkit.ChatColor;
import p1xel.nobuildplus.NoBuildPlus;

import java.util.List;

public class Config {

    public static String getString(String path) {
        return NoBuildPlus.getInstance().getConfig().getString(path);
    }

    public static boolean getBool(String path) {
        return NoBuildPlus.getInstance().getConfig().getBoolean(path);
    }

    public static String getLanguage() {
        return NoBuildPlus.getInstance().getConfig().getString("Language");
    }

    public static void reloadConfig() {

        NoBuildPlus.getInstance().reloadConfig();

    }

    public static String getVersion() {
        return getString("Version");
    }

    public static int getInt(String path) {
        return NoBuildPlus.getInstance().getConfig().getInt(path);
    }

    public static boolean isResidenceEnabled() {
        return getBool("hook.Residence");
    }

}
