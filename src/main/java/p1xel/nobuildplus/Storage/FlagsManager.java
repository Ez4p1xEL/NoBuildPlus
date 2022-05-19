package p1xel.nobuildplus.Storage;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import p1xel.nobuildplus.NoBuildPlus;

import java.io.File;
import java.io.IOException;
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

    public static boolean isInTheFlagsList(String flag) {

        if (get().getStringList("FlagsList").contains(flag)) {
            return true;
        } else {
            return false;
        }

    }

    public static List<String> getTheFlagsList() {
        return get().getStringList("FlagsList");
    }

    public static void addToTheFlagsList(String flag) {

        List<String> flags = getTheFlagsList();
        flags.add(flag);
        set("FlagsList", flags);

    }

    public static boolean FrameIsIncludingGlowFrame() {
        return get().getBoolean("flags.frame.include-glow-frame");
    }

    public static void checkFlag() {

        if (Config.getInt("Id") < 6) {
            if (!isInTheFlagsList("mob-explode")) {
                set("flags.mob-explode.enable", true);
                set("flags.mob-explode.type", "all");
                set("flags.mob-explode.list", Collections.singletonList("CREEPER"));
                addToTheFlagsList("mob-explode");
            }

            if (!isInTheFlagsList("tnt")) {
                set("flags.tnt.enable", true);
                addToTheFlagsList("tnt");
            }

            if (!isInTheFlagsList("frame")) {
                set("flags.frame.enable", true);
                set("flags.frame.include-glow-frame", false);
                addToTheFlagsList("frame");
            }

            if (!isInTheFlagsList("bed")) {
                set("flags.bed.enable", true);
                addToTheFlagsList("bed");
            }

            if (!isInTheFlagsList("villager")) {
                set("flags.villager.enable", true);
                addToTheFlagsList("villager");
            }

            if (!isInTheFlagsList("command")) {
                set("flags.command.enable", true);
                set("flags.command.type", "all");
                set("flags.command.list", "spawn");
                addToTheFlagsList("command");
            }

        }

    }

}
