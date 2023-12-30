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

    public static List<String> list = new java.util.ArrayList<>(Collections.emptyList());

    // 初始化
    public static void defaultList() {
        list.addAll(Worlds.get().getKeys(false));
    }

    public static List<String> getEnableWorldList() {
        return list;
    }

    public static void addWorld(String world) {
        list.add(world);
    }

    public static void removeWorld(String world) {
        list.remove(world);
    }

    public static boolean getDefaultFlag(String flag) {
        return get().getBoolean("global-settings.flags." + flag);
    }

    public static String getPermission() { return get().getString("global-settings.permission"); }

    public static String getDenyMessageString() {
        return get().getString("global-settings.deny-message");
    }


    // 是否可以执行flag监测
    // 这个方法包含了 FlagsManager.getFlagsIeEnabled() 和 Settings.getEnableWorldList().contains() 的功能
    public static boolean canExecute(String world, String flag) {
        return FlagsManager.get().getBoolean("flags." + flag + ".enable") && Settings.getEnableWorldList().contains(world);
    }

}
