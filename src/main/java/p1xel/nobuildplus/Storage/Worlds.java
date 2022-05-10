package p1xel.nobuildplus.Storage;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import p1xel.nobuildplus.NoBuildPlus;

import java.io.File;
import java.io.IOException;

public class Worlds {

    public static void createWorldsFile() {

        File file = new File(NoBuildPlus.getInstance().getDataFolder(), Config.getString("worlds-data-file"));

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    public static FileConfiguration get() {
        File file = new File(NoBuildPlus.getInstance().getDataFolder(), Config.getString("worlds-data-file"));
        return YamlConfiguration.loadConfiguration(file);
    }

    public static void set(String path, Object value) {
        File file = new File(NoBuildPlus.getInstance().getDataFolder(), Config.getString("worlds-data-file"));
        FileConfiguration yaml = YamlConfiguration.loadConfiguration(file);

        yaml.set(path,value);
        try {
            yaml.save(file);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }

    public static String getDenyMessage(String world) {
        return ChatColor.translateAlternateColorCodes('&', get().getString(world + ".deny-message"));
    }

    public static String getPermission(String world) {
        return get().getString(world + ".permission");
    }

    public static boolean isSpawnLocationSet(String world) {
        if (get().get(world + ".spawn-loc") != null) {
            return true;
        }
        return false;
    }

    public static Location getSpawnLocation(String world) {
        return (Location) get().get(world + ".spawn-loc");
    }

    public static void setSpawnLocation(String world, Location loc) {
        set(world + ".spawn-loc", loc);
    }

    public static void createWorld(String world) {

        set(world + ".flags.break", Settings.getDefaultFlag("break"));
        set(world + ".flags.build", Settings.getDefaultFlag("build"));
        set(world + ".flags.use", Settings.getDefaultFlag("use"));
        set(world + ".flags.container", Settings.getDefaultFlag("container"));
        set(world + ".flags.move", Settings.getDefaultFlag("move"));
        set(world + ".flags.mob-damage", Settings.getDefaultFlag("mob-damage"));
        set(world + ".flags.mob-explode", Settings.getDefaultFlag("mob-explode"));
        set(world + ".flags.pvp", Settings.getDefaultFlag("pvp"));
        set(world + ".flags.tnt", Settings.getDefaultFlag("tnt"));
        set(world + ".flags.frame", Settings.getDefaultFlag("frame"));
        set(world + ".flags.bed", Settings.getDefaultFlag("bed"));
        set(world + ".flags.voidtp", Settings.getDefaultFlag("voidtp"));
        set(world + ".permission", Settings.getPermission());
        set(world + ".deny-message", Settings.getDenyMessageString());
        Settings.addEnableWorldToList(world);

    }

    public static void removeWorld(String world) {

        set(world, null);
        Settings.removeEnableWorldFromList(world);

    }

    public static void setFlag(String world, String flag, boolean bool) {
        set(world + ".flags." + flag, bool);
    }

    public static boolean getFlag(String world, String flag) {
        return get().getBoolean(world + ".flags." + flag);
    }



}
