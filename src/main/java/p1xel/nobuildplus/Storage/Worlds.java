package p1xel.nobuildplus.Storage;

import org.bukkit.ChatColor;
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

    public static void createWorld(String world) {

        set(world + ".flags.break", Settings.getDefaultFlag("break"));
        set(world + ".flags.build", Settings.getDefaultFlag("build"));
        set(world + ".flags.use", Settings.getDefaultFlag("use"));
        set(world + ".flags.container", Settings.getDefaultFlag("container"));
        set(world + ".flags.move", Settings.getDefaultFlag("move"));
        set(world + ".flags.mob-damage", Settings.getDefaultFlag("mob-damage"));
        set(world + ".flags.pvp", Settings.getDefaultFlag("pvp"));
        set(world + ".deny-message", Settings.getDenyMessageString());
        Settings.addEnableWorldToList(world);

    }

    public static void removeWorld(String world) {

        set(world, null);
        Settings.removeEnableWorldFromList(world);

    }

    public static void setFlag(String world, String flag, boolean bool) {

        if (flag.equalsIgnoreCase("break")) {
            set(world + ".flags.break", bool);
        }

        if (flag.equalsIgnoreCase("build")) {
            set(world + ".flags.build", bool);
        }

        if (flag.equalsIgnoreCase("use")) {
            set(world + ".flags.use", bool);
        }

        if (flag.equalsIgnoreCase("container")) {
            set(world + ".flags.container", bool);
        }

        if (flag.equalsIgnoreCase("move")) {
            set(world + ".flags.move", bool);
        }

        if (flag.equalsIgnoreCase("mob-damage")) {
            set(world + ".flags.mob-damage", bool);
        }

        if (flag.equalsIgnoreCase("pvp")) {
            set(world + ".flags.pvp", bool);
        }

    }

    public static boolean getFlag(String world, String flag) {

        if (flag.equalsIgnoreCase("break")) {
            return get().getBoolean(world + ".flags.break");
        }

        if (flag.equalsIgnoreCase("build")) {
            return get().getBoolean(world + ".flags.build");
        }

        if (flag.equalsIgnoreCase("use")) {
            return get().getBoolean(world + ".flags.use");
        }

        if (flag.equalsIgnoreCase("container")) {
            return get().getBoolean(world + ".flags.container");
        }

        if (flag.equalsIgnoreCase("move")) {
            return get().getBoolean(world + ".flags.move");
        }

        if (flag.equalsIgnoreCase("mob-damage")) {
            return get().getBoolean(world + ".flags.mob-damage");
        }

        if (flag.equalsIgnoreCase("pvp")) {
            return get().getBoolean(world + ".flags.pvp");
        }

        return false;

    }



}
