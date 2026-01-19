package p1xel.nobuildplus.gamerule;

import org.bukkit.Bukkit;
import org.bukkit.World;
import p1xel.nobuildplus.NoBuildPlus;
import p1xel.nobuildplus.storage.Logger;

import java.util.ArrayList;
import java.util.List;

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
            for (Object gamerule : (Object[]) values) {
                String key = (String) gamerule.getClass().getMethod("getName").invoke(gamerule);
                list.add(key.replace("minecraft:", ""));
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
            NoBuildPlus.getInstance().getLogger().warning("Error of LegacyGameRule when getGameRules");
        }

    }
}
