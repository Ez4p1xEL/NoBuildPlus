package p1xel.nobuildplus.storage;

import p1xel.nobuildplus.NoBuildPlus;

import java.util.logging.Level;

public class Logger {

    // Debug Tool, Default for true
    private static boolean enabled = true;

    public static void setEnabled(boolean bool) {
        enabled = bool;
    }

    public static boolean isEnabled() {
        return enabled;
    }

    public static void log(Level level, String message) {
        NoBuildPlus.getInstance().getServer().getLogger().log(level, "[NBP] " + message);
    }

    public static void debug(String message) {
        if (enabled) {
            NoBuildPlus.getInstance().getServer().getLogger().info("[NBP_DEBUG] " + message);
        }
    }

}
