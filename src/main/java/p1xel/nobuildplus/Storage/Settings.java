package p1xel.nobuildplus.Storage;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import p1xel.nobuildplus.NoBuildPlus;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
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
        List<String> list = new java.util.ArrayList<>(Collections.emptyList());
        list.addAll(Worlds.get().getKeys(false));
        return list;
    }

    public static boolean getDefaultFlag(String flag) {
        return get().getBoolean("global-settings.flags." + flag);
    }

    public static String getPermission() { return get().getString("global-settings.permission"); }

    public static String getDenyMessageString() {
        return get().getString("global-settings.deny-message");
    }

}
