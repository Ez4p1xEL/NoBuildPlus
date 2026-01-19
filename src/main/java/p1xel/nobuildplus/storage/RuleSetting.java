package p1xel.nobuildplus.storage;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import p1xel.nobuildplus.NoBuildPlus;

import java.io.File;
import java.io.IOException;

public class RuleSetting {

    public static File file;
    public static FileConfiguration yaml;

    public static void createFile() {

        File file = new File(NoBuildPlus.getInstance().getDataFolder(), "gamerules.yml");

        if (!file.exists()) {
            NoBuildPlus.getInstance().saveResource("gamerules.yml", false);
        }

        upload(file);

    }

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

    public static String getMaterialString(String gamerule) {
        String material = "";
        try {
            material = yaml.getString(gamerule);
        } catch (IllegalArgumentException exception) {
            NoBuildPlus.getInstance().getLogger().warning("Couldn't find " + gamerule);
        }
        Logger.debug("Menu item of " + gamerule + ": " + material);
        return material;
    }

}
