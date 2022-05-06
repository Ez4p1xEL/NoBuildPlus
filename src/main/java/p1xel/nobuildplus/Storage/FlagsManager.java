package p1xel.nobuildplus.Storage;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import p1xel.nobuildplus.NoBuildPlus;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FlagsManager {

    public static void createLocaleFile() {

        File file = new File(NoBuildPlus.getInstance().getDataFolder(), Config.getString("flags-file"));

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

            set("FlagsList", Arrays.asList("break","build","use","container","move","mob-damage","mob-explode","pvp","tnt","frame"));
            setDefaultFlags();

        }
    }

    public static FileConfiguration get() {
        File file = new File(NoBuildPlus.getInstance().getDataFolder(), Config.getString("flags-file"));
        return YamlConfiguration.loadConfiguration(file);
    }

    public static void set(String path, Object value) {
        File file = new File(NoBuildPlus.getInstance().getDataFolder(), Config.getString("flags-file"));
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

    public static void setDefaultFlags() {
        set("flags.break.enable", true);
        set("flags.break.type", "all");
        set("flags.break.list", Collections.singletonList("GRASS_BLOCK"));
        set("flags.build.enable", true);
        set("flags.build.type", "all");
        set("flags.build.list", Collections.singletonList("GRASS_BLOCK"));
        set("flags.use.enable", true);
        set("flags.use.type", "list");
        set("flags.use.list", Arrays.asList("ENCHANTING_TABLE","CRAFTING_TABLE","ANVIL","CHIPPED_ANVIL","DAMAGED_ANVIL"));
        set("flags.container.enable", true);
        set("flags.container.type", "list");
        set("flags.container.list", Arrays.asList("CHEST","ENDER_CHEST","TRAPPED_CHEST","FURNACE"));
        set("flags.move.enable", true);
        set("flags.move.list", Collections.singletonList("s1mple"));
        set("flags.mob-damage.enable", true);
        set("flags.mob-damage.type", "all");
        set("flags.mob-damage.list", Collections.singletonList("ZOMBIE"));
        set("flags.mob-explode.enable", true);
        set("flags.mob-explode.type", "all");
        set("flags.mob-explode.list", Collections.singletonList("CREEPER"));
        set("flags.pvp.enable", true);
        set("flags.tnt.enable", false);
        set("flags.frame.enable", true);
        set("flags.frame.include-glow-frame", false);
    }

    public static boolean FrameIsIncludingGlowFrame() {
        return get().getBoolean("flags.frame.include-glow-frame");
    }

    public static void checkFlag() {

        if (Config.getInt("Id") <= 2) {
            if (!isInTheFlagsList("mob-explode")) {
                set("flags.mob-explode.enable", true);
                set("flags.mob-explode.type", "all");
                set("flags.mob-explode.list", Collections.singletonList("CREEPER"));
                addToTheFlagsList("mob-explode");
            }

            if (!isInTheFlagsList("tnt")) {
                set("flags.tnt.enable", false);
                addToTheFlagsList("tnt");
            }

            if (!isInTheFlagsList("frame")) {
                set("flags.frame.enable", false);
                set("flags.frame.include-glow-frame", true);
                addToTheFlagsList("frame");
            }
        }

    }

}
