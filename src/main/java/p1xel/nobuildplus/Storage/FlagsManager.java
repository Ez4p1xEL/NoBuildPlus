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

            set("FlagsList", Arrays.asList("break","build","use","container","move","mob-damage","pvp"));
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
        set("flags.pvp.enable", true);
    }

}
