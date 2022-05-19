package p1xel.nobuildplus.Storage;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import p1xel.nobuildplus.NoBuildPlus;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Settings {

    public static void createWorldsFile() {

        File file = new File(NoBuildPlus.getInstance().getDataFolder(), "settings.yml");

        if (!file.exists()) {
            NoBuildPlus.getInstance().saveResource("settings.yml", false);
        }

    }

    public static FileConfiguration get() {
        File file = new File(NoBuildPlus.getInstance().getDataFolder(), "settings.yml");
        return YamlConfiguration.loadConfiguration(file);
    }

    public static void set(String path, Object value) {
        File file = new File(NoBuildPlus.getInstance().getDataFolder(), "settings.yml");
        FileConfiguration yaml = YamlConfiguration.loadConfiguration(file);

        yaml.set(path,value);
        try {
            yaml.save(file);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

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
        return get().getBoolean("global-settings.flags." + flag);
    }

    public static String getPermission() { return get().getString("global-settings.permission"); }

    public static String getDenyMessageString() {
        return get().getString("global-settings.deny-message");
    }

}
