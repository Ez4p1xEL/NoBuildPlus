package p1xel.nobuildplus.Storage;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import p1xel.nobuildplus.NoBuildPlus;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FlagsManager {

    public static void createLocaleFile() {

        File file = new File(NoBuildPlus.getInstance().getDataFolder(), "flags.yml");

        if (!file.exists()) {
            NoBuildPlus.getInstance().saveResource("flags.yml", false);
        }
    }

    public static FileConfiguration get() {
        File file = new File(NoBuildPlus.getInstance().getDataFolder(), "flags.yml");
        return YamlConfiguration.loadConfiguration(file);
    }

    public static void set(String path, Object value) {
        File file = new File(NoBuildPlus.getInstance().getDataFolder(), "flags.yml");
        FileConfiguration yaml = YamlConfiguration.loadConfiguration(file);

        yaml.set(path,value);
        try {
            yaml.save(file);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }

    public static boolean getFlagsIsEnabled(String flag) {
        return get().getBoolean("flags." + flag + ".enable");
    }

    public static String getFlagsType(String flag) {
        return get().getString("flags." + flag + ".type");
    }

    public static List<String> getFlagsList(String flag) {
        return get().getStringList("flags."+flag+".list");
    }

    public static List<String> getFlags() {
        List<String> list = new java.util.ArrayList<>(Collections.emptyList());
        list.addAll(get().getConfigurationSection("flags").getKeys(false));
        return list;
    }

    public static boolean isInTheFlagsList(String flag) {

        if (getFlags().contains(flag)) {
            return true;
        } else {
            return false;
        }

    }

    public static boolean getBoolInFlag(String flag, String path) {
        return get().getBoolean("flags." + flag + "." + path);
    }

    public static boolean FrameIsIncludingGlowFrame() {
        return get().getBoolean("flags.frame.include-glow-frame");
    }

    public static boolean BoatIsIncludingChestBoat() {
        return get().getBoolean("flags.boat.chestBoatEnable");
    }

}
