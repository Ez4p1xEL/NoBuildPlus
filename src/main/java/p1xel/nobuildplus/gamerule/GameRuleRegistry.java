package p1xel.nobuildplus.gamerule;

import java.util.ArrayList;
import java.util.List;


public class GameRuleRegistry {

    private static boolean legacy = false;
    private static List<String> gamerules = new ArrayList<>();
    private static InterfaceGameRuleRegistry registry;

    public static void init(String minecraft_version) {
        String[] version = minecraft_version.split("-")[0].split("\\.");
        int parent = Integer.parseInt(version[1]);
        int child = Integer.parseInt(version[2]);
        if (parent >= 21 && child >= 11) {
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
        registry.setWorldGameRule(worldName, rule, value);
    }


}
