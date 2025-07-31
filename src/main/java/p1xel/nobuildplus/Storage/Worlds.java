package p1xel.nobuildplus.Storage;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import p1xel.nobuildplus.Listener.GUIManager;
import p1xel.nobuildplus.NoBuildPlus;

import javax.annotation.Nullable;
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
        String perm = yaml.getString(world + ".permission");
        if (perm==null) { return "";}
        return perm;
    }

    public static boolean isSpawnLocationSet(String world) {
        return yaml.get(world + ".spawn-loc") != null;
    }

    // This is for voidtp or other flag that getting location from the worlds.yml
    @Nullable
    public static Location getSpawnLocation(String w) {

        World world;
        double x,y,z;
        float pitch,yaw;

        try {
            world = Bukkit.getWorld(yaml.getString(w + ".spawn-loc.world"));
            x = yaml.getDouble(w + ".spawn-loc.x");
            y = yaml.getDouble(w + ".spawn-loc.y");
            z = yaml.getDouble(w + ".spawn-loc.z");
            yaw = (Float) yaml.get(w + ".spawn-loc.yaw");
            pitch = (Float) yaml.get(w + ".spawn-loc.pitch");
        } catch (NullPointerException e) {
            return null;
        }

        return new Location(world,x,y,z,yaw,pitch);
    }

    public static void setSpawnLocation(String world, Location loc) {
        set(world + ".spawn-loc.world", loc.getWorld().getName());
        set(world + ".spawn-loc.x", loc.getX());
        set(world + ".spawn-loc.y", loc.getY());
        set(world + ".spawn-loc.z", loc.getZ());
        set(world + ".spawn-loc.pitch", loc.getPitch());
        set(world + ".spawn-loc.yaw", loc.getYaw());
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

    public static void sendMessage(Player player, String world) {

        if (!Worlds.isDenyMessageExist(world)) {
            return;
        }

        String sound = Config.getString("deny-message-sound.name");

        if (Config.getString("deny-message-type").equalsIgnoreCase("ACTIONBAR")) {
            if (NoBuildPlus.getInstance().getBukkitVersion() < 12) {
                return;
            }
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(Worlds.getDenyMessage(world)));
            if (sound != null) {
                player.playSound(player.getLocation(), Sound.valueOf(sound), 1f, 1f);
            }
            return;
        }

        player.sendMessage(Worlds.getDenyMessage(world));
        if (sound != null) {
            player.playSound(player, Sound.valueOf(sound), 1f, 1f);
        }

    }





}
