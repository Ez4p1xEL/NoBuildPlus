package p1xel.nobuildplus.gamerule;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.jetbrains.annotations.Nullable;
import p1xel.nobuildplus.NoBuildPlus;
import p1xel.nobuildplus.storage.Logger;

import java.util.*;
import java.util.logging.Level;

public class LegacyGameRule implements InterfaceGameRuleRegistry {

    private Class<?> gameRuleClass;

    public LegacyGameRule() {
        try {
            // find the class directly
            this.gameRuleClass = Class.forName("org.bukkit.GameRule");
        } catch (Exception exception) {
            NoBuildPlus.getInstance().getLogger().warning("Error of LegacyGameRule");
        }

    }

    @Override
    public List<String> getGameRules() {
        List<String> list = new ArrayList<>();
        try {
            // find the method directly
            Object values = gameRuleClass.getMethod("values").invoke(null);
            World world = Bukkit.getWorlds().getFirst();

            for (Object gamerule : (Object[]) values) {
                String key = (String) gamerule.getClass().getMethod("getName").invoke(gamerule);
                key = key.replace("minecraft:", "");
                if (world.isGameRule(key)) {
                    list.add(key);
                } else {
                    Logger.log(Level.INFO, "A gamerule that might be experimental '" + key + "' has been ignored!");
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            NoBuildPlus.getInstance().getLogger().warning("Error of LegacyGameRule when getGameRules");
        }
        Logger.debug("GameRule list: " + list);
        return list;

    }

    @Override
    public void setWorldGameRule(String worldName, String rule, Object value) {
        World world = Bukkit.getWorld(worldName);
        try {
            world.getClass().getMethod("setGameRuleValue", String.class, String.class).invoke(world, rule, String.valueOf(value));
        } catch (Exception exception) {
            exception.printStackTrace();
            NoBuildPlus.getInstance().getLogger().warning("Error of LegacyGameRule when setGameRuleValue");
        }

    }

    @Override
    @Nullable
    public String getWorldGameRule(String worldName, String rule) {
        World world = Bukkit.getWorld(worldName);
        String value = null;
        try {
            value = (String) world.getClass().getMethod("getGameRuleValue", String.class).invoke(world, rule);
        } catch (Exception exception) {
            exception.printStackTrace();
            NoBuildPlus.getInstance().getLogger().warning("Error of LegacyGameRule when getGameRuleValue");
        }
        return value;
    }
}
