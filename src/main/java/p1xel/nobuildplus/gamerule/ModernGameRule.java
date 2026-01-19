package p1xel.nobuildplus.gamerule;

import org.bukkit.*;
import p1xel.nobuildplus.storage.Logger;

import java.util.ArrayList;
import java.util.List;

public class ModernGameRule implements InterfaceGameRuleRegistry {


    @Override
    public List<String> getGameRules() {
        List<String> list = new ArrayList<>();

        for (GameRule<?> rule : Registry.GAME_RULE) {
            list.add(rule.getKeyOrNull().getKey());
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
}
