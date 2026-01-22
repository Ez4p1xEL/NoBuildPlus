package p1xel.nobuildplus.gamerule;

import org.bukkit.*;
import org.jetbrains.annotations.Nullable;
import p1xel.nobuildplus.storage.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class ModernGameRule implements InterfaceGameRuleRegistry {


    @Override
    public List<String> getGameRules() {
        List<String> list = new ArrayList<>();

        World world = Bukkit.getWorlds().getFirst();

        for (GameRule<?> rule : Registry.GAME_RULE) {
            try {
                Object value = world.getGameRuleValue(rule);
                list.add(rule.getKeyOrNull().getKey());
            } catch (Exception exception) {
                Logger.log(Level.INFO, "A gamerule that might be experimental '" + rule.getKeyOrNull().getKey() + "' has been ignored!");
            }
        }

        Logger.debug("GameRule list: " + list);
        return list;

    }

    @Override
    public void setWorldGameRule(String worldName, String rule, Object value) {
        World world = Bukkit.getWorld(worldName);
        GameRule<?> gamerule = Registry.GAME_RULE.get(NamespacedKey.fromString(rule));
        if (gamerule.getType() == Boolean.class && value instanceof Boolean) {
            world.setGameRule((GameRule<Boolean>) gamerule, (boolean) value);
            return;
        }

        if (gamerule.getType() == Integer.class && value instanceof Integer) {
            world.setGameRule((GameRule<Integer>) gamerule, (int) value);
        }
    }

    @Override
    @Nullable
    public String getWorldGameRule(String worldName, String rule) {
        World world = Bukkit.getWorld(worldName);
        GameRule<?> gamerule = Registry.GAME_RULE.get(NamespacedKey.fromString(rule));
        return String.valueOf(world.getGameRuleValue(gamerule));
    }
}
