package p1xel.nobuildplus.Storage;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import p1xel.nobuildplus.NoBuildPlus;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class Settings {
    
    public static File file;
    public static FileConfiguration yaml;

    public static void createSettingsFile() {

        File file = new File(NoBuildPlus.getInstance().getDataFolder(), "settings.yml");

        if (!file.exists()) {
            NoBuildPlus.getInstance().saveResource("settings.yml", false);
        }
        
        upload(file);

    }

    // Upload the settings.yml file
    public static void upload(File settings) {
        file = settings;
        yaml = YamlConfiguration.loadConfiguration(settings);
    }

    public static void set(String path, Object value) {

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
        list.addAll(Worlds.yaml.getKeys(false));
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
        return yaml.getBoolean("global-settings.flags." + flag);
    }

    public static String getPermission() { return yaml.getString("global-settings.permission"); }

    public static String getDenyMessageString() {
        return yaml.getString("global-settings.deny-message");
    }


    // 是否可以执行flag监测
    // 这个方法包含了 FlagsManager.getFlagsIeEnabled() 和 Settings.getEnableWorldList().contains() 的功能
    public static boolean canExecute(String world, String flag) {
        return FlagsManager.yaml.getBoolean("flags." + flag + ".enable") && Settings.getEnableWorldList().contains(world);
    }

}
