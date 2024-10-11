package p1xel.nobuildplus.Storage;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import p1xel.nobuildplus.Listener.GUIManager;
import p1xel.nobuildplus.NoBuildPlus;

import java.io.File;
import java.io.IOException;

public class Worlds {

    public static File file;
    public static FileConfiguration yaml;
    public static void createWorldsFile() {

        File file = new File(NoBuildPlus.getInstance().getDataFolder(), "worlds.yml");

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

        }

        upload(file);
    }

    public static void upload(File worlds) {
        file = worlds;
        yaml = YamlConfiguration.loadConfiguration(worlds);
    }

    public static void set(String path, Object value) {
        yaml.set(path,value);
        try {
            yaml.save(file);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }

    public static boolean isDenyMessageExist(String world) {
        if (!Config.getBool("deny-message-enable")) {
            return false;
        }
        return yaml.getString(world + ".deny-message") != null;
    }

    public static String getDenyMessage(String world) {
        String m = yaml.getString(world + ".deny-message");
        if (m==null) {return null;}
        return ChatColor.translateAlternateColorCodes('&', m);
    }

    public static String getPermission(String world) {
        return yaml.getString(world + ".permission");
    }

    public static boolean isSpawnLocationSet(String world) {
        if (yaml.get(world + ".spawn-loc") != null) {
            return true;
        }
        return false;
    }

    public static Location getSpawnLocation(String world) {
        return (Location) yaml.get(world + ".spawn-loc");
    }

    public static void setSpawnLocation(String world, Location loc) {
        set(world + ".spawn-loc", loc);
    }

    public static void createWorld(String world) {

        for (String flagname : FlagsManager.getFlags()) {
            set(world + ".flags." + flagname, Settings.getDefaultFlag(flagname));
        }

        set(world + ".permission", Settings.getPermission());
        set(world + ".deny-message", Settings.getDenyMessageString());
        Settings.addWorld(world);

    }

    public static void removeWorld(String world) {

        set(world, null);
        Settings.removeWorld(world);

    }

    public static void setFlag(String world, String flag, boolean bool) {
        set(world + ".flags." + flag, bool);
        if (NoBuildPlus.getInstance().getBukkitVersion() >= 15) {
            GUIManager.instance.updateFlag(world, flag, bool);
        }
    }

    public static boolean getFlag(String world, String flag) {
        return yaml.getBoolean(world + ".flags." + flag);
    }

    @Deprecated
    public static void updateFromFlagsManager() {

        for (String world : Worlds.yaml.getKeys(false)) {

            for (String flag : FlagsManager.getFlags()) {

                if (!yaml.isSet(world + ".flags." + flag)) {
                    setFlag(world, flag, Settings.getDefaultFlag(flag));
                }

            }

        }

    }



}
