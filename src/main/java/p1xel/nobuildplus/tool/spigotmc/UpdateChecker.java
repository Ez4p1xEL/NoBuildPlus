package p1xel.nobuildplus.tool.spigotmc;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import p1xel.nobuildplus.NoBuildPlus;
import p1xel.nobuildplus.storage.Locale;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;
import java.util.function.Consumer;

public class UpdateChecker {

    private final JavaPlugin plugin;
    private final int resourceId;

    public UpdateChecker(JavaPlugin plugin, int resourceId) {
        this.plugin = plugin;
        this.resourceId = resourceId;
    }

    public void getVersion(final Consumer<String> consumer) {
        Runnable runnable = () -> {
            try (InputStream inputStream = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + this.resourceId).openStream(); Scanner scanner = new Scanner(inputStream)) {
                if (scanner.hasNext()) {
                    consumer.accept(scanner.next());
                }
            } catch (IOException exception) {
                plugin.getLogger().info(Locale.getMessage("update-check.invalid") + exception.getMessage());
            }
        };
        if (NoBuildPlus.getFoliaLib().isFolia()) {
            NoBuildPlus.getFoliaLib().getScheduler().runAsync(wrappedTask -> runnable.run());
        } else {
            Bukkit.getScheduler().runTaskAsynchronously(this.plugin, runnable);
        }
    }
}
