package p1xel.nobuildplus.Storage;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import p1xel.nobuildplus.NoBuildPlus;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Settings {

    public static void createWorldsFile() {

        File file = new File(NoBuildPlus.getInstance().getDataFolder(), Config.getString("settings-file"));

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

            defaultGlobalSettings();

        }

    }

    public static FileConfiguration get() {
        File file = new File(NoBuildPlus.getInstance().getDataFolder(), Config.getString("settings-file"));
        return YamlConfiguration.loadConfiguration(file);
    }

    public static void set(String path, Object value) {
        File file = new File(NoBuildPlus.getInstance().getDataFolder(), Config.getString("settings-file"));
        FileConfiguration yaml = YamlConfiguration.loadConfiguration(file);

        yaml.set(path,value);
        try {
            yaml.save(file);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }

    public static void defaultGlobalSettings() {

        set("global-settings.flags.break", false);
        set("global-settings.flags.build", false);
        set("global-settings.flags.use", true);
        set("global-settings.flags.container", false);
        set("global-settings.flags.move", true);
        set("global-settings.flags.mob-damage", true);
        set("globa-settings.flags.mob-explode", false);
        set("global-settings.flags.pvp", false);
        set("global-settings.flags.tnt", false);
        set("global-settings.flags.frame",false);
        set("global-settings.permission", "nobuildplus.bypass");
        set("global-settings.deny-message", Locale.getMessage("not-allow"));

        set("enable-worlds", "[]");

    }

    public static List<String> getEnableWorldList() {
        return get().getStringList("enable-worlds");
    }

    public static void addEnableWorldToList(String world) {
        List<String> list = getEnableWorldList();
        list.add(world);
        set("enable-worlds", list);
    }

    public static void removeEnableWorldFromList(String world) {
        List<String> list = getEnableWorldList();
        list.remove(world);
        set("enable-worlds", list);
    }

    public static void clearEnableWorldsList() {
        List<String> list = getEnableWorldList();
        list.clear();
        set("enable-worlds", list);
        for (String key : Worlds.get().getKeys(false)) {
            Worlds.set(key,null);
        }
    }

    public static boolean getDefaultFlag(String flag) {

        if (flag.equalsIgnoreCase("break")) {
            return get().getBoolean("global-settings.flags.break");
        }

        if (flag.equalsIgnoreCase("build")) {
            return get().getBoolean("global-settings.flags.build");
        }

        if (flag.equalsIgnoreCase("use")) {
            return get().getBoolean("global-settings.flags.use");
        }

        if (flag.equalsIgnoreCase("container")) {
            return get().getBoolean("global-settings.flags.container");
        }

        if (flag.equalsIgnoreCase("move")) {
            return get().getBoolean("global-settings.flags.move");
        }

        if (flag.equalsIgnoreCase("mob-damage")) {
            return get().getBoolean("global-settings.flags.mob-damage");
        }

        if (flag.equalsIgnoreCase("mob-explode")) {
            return get().getBoolean("global-settings.flags.mob-explode");
        }

        if (flag.equalsIgnoreCase("pvp")) {
            return get().getBoolean("global-settings.flags.pvp");
        }

        if (flag.equalsIgnoreCase("tnt")) {
            return get().getBoolean("global-settings.flags.tnt");
        }

        if (flag.equalsIgnoreCase("frame")) {
            return get().getBoolean("global-settings.flags.frame");
        }

        return false;

    }

    public static String getPermission() { return get().getString("global-settings.permission"); }

    public static String getDenyMessageString() {
        return get().getString("global-settings.deny-message");
    }

}
