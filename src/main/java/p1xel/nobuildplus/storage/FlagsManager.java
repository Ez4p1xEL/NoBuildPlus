package p1xel.nobuildplus.storage;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import p1xel.nobuildplus.NoBuildPlus;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FlagsManager {

    public static File file;
    public static FileConfiguration yaml;
    public static void createFlagsManagerFile() {

        File file = new File(NoBuildPlus.getInstance().getDataFolder(), "flags.yml");

        if (!file.exists()) {
            NoBuildPlus.getInstance().saveResource("flags.yml", false);
        }
        
        upload(file);
    }

    public static void upload(File flags) {
        file = flags;
        yaml = YamlConfiguration.loadConfiguration(flags);
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

    @Deprecated
    public static boolean getFlagsIsEnabled(String flag) {
        if (!yaml.isSet("flags." + flag + ".enable")) {
            return false;
        }
        return yaml.getBoolean("flags." + flag + ".enable");
    }

    public static String getFlagsType(String flag) {
        return yaml.getString("flags." + flag + ".type");
    }

    public static List<String> getFlagsList(String flag) {
        return yaml.getStringList("flags."+flag+".list");
    }

    static List<String> flagList = new ArrayList<>();

    public static void defaultFlagList() {
        flagList.addAll(yaml.getConfigurationSection("flags").getKeys(false));
    }

    public static List<String> getFlags() {
        return flagList;
    }

    public static boolean isInTheFlagsList(String flag) {

        if (getFlags().contains(flag)) {
            return true;
        } else {
            return false;
        }

    }

    public static boolean getBoolInFlag(String flag, String path) {
        return yaml.getBoolean("flags." + flag + "." + path);
    }

    public static boolean FrameIsIncludingGlowFrame() {
        return yaml.getBoolean("flags.frame.include-glow-frame");
    }

    public static boolean BoatIsIncludingChestBoat() {
        return yaml.getBoolean("flags.boat.chestBoatEnable");
    }

    @Deprecated
    public static String getShowedItem(String flag) {
        return yaml.getString("flags." + flag + ".show-item");
    }

    @Deprecated
    public static int getSlot(String flag) {
        return yaml.getInt("flags." + flag + ".slot");
    }

    public static int getMaxPage(List<String> flags) {
        return (int) Math.ceil(((double) flags.size() / 28));
    }


}
