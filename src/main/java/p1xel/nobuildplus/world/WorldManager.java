package p1xel.nobuildplus.world;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;
import p1xel.nobuildplus.Flag;
import p1xel.nobuildplus.NoBuildPlus;
import p1xel.nobuildplus.gamerule.GameRuleRegistry;
import p1xel.nobuildplus.storage.Config;
import p1xel.nobuildplus.storage.Logger;
import p1xel.nobuildplus.storage.Settings;
import p1xel.nobuildplus.storage.Worlds;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WorldManager {

    private static final HashMap<String, ProtectedWorld> enabled_worlds = new HashMap<>();
    private static String SOUND_NAME;
    private static boolean SOUND_ENABLED;
    private static boolean IS_ACTIONBAR;

    public static void init() {
        enabled_worlds.clear();
        for (String worldName : Worlds.yaml.getKeys(false)) {

            NBPWorld world = new NBPWorld(worldName);
            enabled_worlds.put(worldName, world);
            Logger.debug("Cache of world " + worldName + " has been uploaded to caches!");

        }
        SOUND_NAME = Config.getString("deny-message-sound.name").replace("_", ".").toLowerCase();
        SOUND_ENABLED = Config.getBool("deny-message-sound.enable");
        IS_ACTIONBAR = Config.getString("deny-message-type").equalsIgnoreCase("actionbar") && NoBuildPlus.getInstance().getBukkitVersion() >= 12;
    }

    @Nullable
    public static ProtectedWorld getWorld(String worldName) {
        return enabled_worlds.get(worldName);
    }

    public static List<String> getWorldsInName() {
        return new ArrayList<>(enabled_worlds.keySet());
    }

    public static List<ProtectedWorld> getWorlds() {
        return new ArrayList<>(enabled_worlds.values());
    }

    // create ProtectedWorld
    public static void enableWorld(String worldName) {
        Worlds.createWorld(worldName);
        NBPWorld world = new NBPWorld(worldName);
        enabled_worlds.put(worldName, world);

        for (String gameruleName : GameRuleRegistry.getRegisteredGameRules()) {
            GameRuleRegistry.setWorldGameRule(worldName, gameruleName, Settings.getDefaultGameRule(gameruleName));
        }

        Logger.debug("A new enabled world " + worldName + " has been uploaded to caches!");
    }

    public static void removeWorld(String worldName) {
        Worlds.removeWorld(worldName);
        enabled_worlds.remove(worldName);
        Logger.debug("An enabled world " + worldName + " has been removed from caches!");
    }

    public static void setFlag(NBPWorld world, Flag flag, boolean bool) {
        world.updateFlag(flag, bool);
        Worlds.setFlag(world.getWorldName(), flag.getName(), bool);
    }

    public static void setFlag(String worldName, Flag flag, boolean bool) {
        NBPWorld world = (NBPWorld) enabled_worlds.get(worldName);
        setFlag(world, flag, bool);
    }

    public static void setGameRule(NBPWorld world, String ruleName, Object value) {
        String worldName = world.getWorldName();
        setGameRule(worldName, ruleName, value);
    }

    public static void setGameRule(String worldName, String ruleName, Object value) {
        GameRuleRegistry.setWorldGameRule(worldName, ruleName, value);
    }

    public static void setPermission(NBPWorld world, String permission) {
        world.updatePermission(permission);
        Worlds.setPermission(world.getWorldName(), permission);
    }

    public static void setPermission(String worldName, String permission) {
        NBPWorld world = (NBPWorld) enabled_worlds.get(worldName);
        setPermission(world, permission);
    }

    public static void setDenyMessage(NBPWorld world, String denyMessage) {
        world.updateDenyMessage(denyMessage);
        Worlds.setDenyMessage(world.getWorldName(), denyMessage);
    }

    public static void setDenyMessage(String worldName, String denyMessage) {
        NBPWorld world = (NBPWorld) enabled_worlds.get(worldName);
        setDenyMessage(world, denyMessage);
    }

    public static void setLocation(NBPWorld world, Location location) {
        world.updateLocation(location);
        Worlds.setSpawnLocation(world.getWorldName(), location);
    }

    public static void setLocation(String worldName, Location location) {
        NBPWorld world = (NBPWorld) enabled_worlds.get(worldName);
        setLocation(world, location);
    }

    public static void sendMessage(Player player, ProtectedWorld world) {
        if (world.getDenyMessage() == null) {
            return;
        }

        if (IS_ACTIONBAR) {
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(world.getDenyMessage()));
            if (SOUND_ENABLED && SOUND_NAME != null) {
                player.playSound(player.getLocation(), SOUND_NAME, 1f, 1f);
            }
            return;
        }

        player.sendMessage(world.getDenyMessage());
        if (SOUND_ENABLED && SOUND_NAME != null) {
            player.playSound(player.getLocation(), SOUND_NAME, 1f, 1f);
        }
    }

    public static void sendMessage(Player player, String worldName) {
        ProtectedWorld world = enabled_worlds.get(worldName);
        if (world == null) { return; }
        sendMessage(player, world);
    }

}
