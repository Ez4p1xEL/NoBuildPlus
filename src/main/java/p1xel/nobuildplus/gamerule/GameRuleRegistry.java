package p1xel.nobuildplus.gamerule;

import p1xel.nobuildplus.NoBuildPlus;

import java.util.ArrayList;
import java.util.List;


public class GameRuleRegistry {

    private static boolean legacy = false;
    private static List<String> gamerules = new ArrayList<>();
    private static InterfaceGameRuleRegistry registry;

    public static void init() {
        int[] version = NoBuildPlus.getInstance().getBukkitVersion();
        int main = version[0];
        int parent = version[1];
        int child = version[2];
        if ((main == 1 && parent >= 21 && child >= 11) || main > 1) {
            registry = new ModernGameRule();
        } else {
            legacy = true;
            registry = new LegacyGameRule();
        }

        try {
            gamerules = registry.getGameRules();
        } catch (IncompatibleClassChangeError error) {
            registry = new LegacyGameRule();
            gamerules = registry.getGameRules();
            legacy = true;
        }
        gamerules.sort(null);

    }

    public static boolean isInLegacy() {
        return legacy;
    }

    public static List<String> getRegisteredGameRules() {
        return gamerules;
    }

//    @Nullable
//    public static GameRule<?> getByName(String name) {
//        return registry.getByName(name);
//    }

    public static void setWorldGameRule(String worldName, String rule, Object value) {
        if (NoBuildPlus.getFoliaLib().isFolia()) {
            NoBuildPlus.getFoliaLib().getScheduler().runNextTick(task -> registry.setWorldGameRule(worldName, rule, value));
            return;
        }
        registry.setWorldGameRule(worldName, rule, value);
    }

    public static String getWorldGameRule(String worldName, String rule) {
        return registry.getWorldGameRule(worldName, rule);
    }


}
