package p1xel.nobuildplus.Storage;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import p1xel.nobuildplus.NoBuildPlus;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Locale {

    public static File file;
    public static FileConfiguration yaml;

    public static void createLocaleFile() {

        List<String> lang = Arrays.asList("en","zh_CN","zh_TW");
        for (String l : lang) {
            File file = new File(NoBuildPlus.getInstance().getDataFolder() + "/lang", l + ".yml");
            if (!file.exists()) {
                NoBuildPlus.getInstance().saveResource("lang/" + l + ".yml", false);
            }
        }
        
        upload(new File(NoBuildPlus.getInstance().getDataFolder() + "/lang", Config.getLanguage() + ".yml"));
    }

    public static void upload(File locale) {
        file = locale;
        yaml = YamlConfiguration.loadConfiguration(locale);
    }

    public static void set(String path, Object value) {
        yaml.set(path,value);
        try {
            yaml.save(file);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }

    public static String getMessage(String path) {
        return ChatColor.translateAlternateColorCodes('&', yaml.getString(path).replaceAll("%prefix%", yaml.getString("plugin-name")).replaceAll("%version%", Config.getVersion()));
    }

    public static String getCmdMessage(String path) {
        return ChatColor.translateAlternateColorCodes('&', yaml.getString(path).replaceAll("%prefix%", yaml.getString("commands-plugin-name")).replaceAll("%version%", Config.getVersion()));
    }

    public static String translate(String message) {
        return ChatColor.translateAlternateColorCodes('&', message.replaceAll("%prefix%", yaml.getString("plugin-name")).replaceAll("%version%", Config.getVersion()));
    }



}
