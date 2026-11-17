package p1xel.nobuildplus.gamerule;

import org.bukkit.Bukkit;
import p1xel.nobuildplus.NoBuildPlus;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;


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

    // gamerule 是世界级状态：Folia 上只能交给全局区域线程，Paper/Spigot 上必须在主线程。
    // 返回的 future 在写入真正生效后完成，调用方可以接着渲染界面（否则会读到旧值）。
    public static CompletableFuture<Void> setWorldGameRule(String worldName, String rule, Object value) {
        if (NoBuildPlus.getFoliaLib().isFolia()) {
            return NoBuildPlus.getFoliaLib().getScheduler().runNextTick(task -> registry.setWorldGameRule(worldName, rule, value));
        }

        if (!Bukkit.isPrimaryThread()) {
            CompletableFuture<Void> future = new CompletableFuture<>();
            Bukkit.getScheduler().runTask(NoBuildPlus.getInstance(), () -> {
                registry.setWorldGameRule(worldName, rule, value);
                future.complete(null);
            });
            return future;
        }

        registry.setWorldGameRule(worldName, rule, value);
        return CompletableFuture.completedFuture(null);
    }

    public static String getWorldGameRule(String worldName, String rule) {
        return registry.getWorldGameRule(worldName, rule);
    }


}
