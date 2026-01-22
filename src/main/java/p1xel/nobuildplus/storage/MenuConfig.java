package p1xel.nobuildplus.storage;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import p1xel.nobuildplus.NoBuildPlus;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class MenuConfig {

    public static void initialization() {

        File file = new File(NoBuildPlus.getInstance().getDataFolder(), "menus.yml");
        YamlConfiguration exist_file = YamlConfiguration.loadConfiguration(file);

        if (!file.exists()) {
            NoBuildPlus.getInstance().saveResource("menus.yml", false);
        } else {
            InputStreamReader newFile = new InputStreamReader(NoBuildPlus.getInstance().getResource("menus.yml"), StandardCharsets.UTF_8);
            YamlConfiguration latest_file = YamlConfiguration.loadConfiguration(newFile);

            // Gets all the keys inside the internal file and iterates through all of it's key pairs
            for (String string : latest_file.getKeys(true)) {
                // Checks if the external file contains the key already.
                if (!exist_file.contains(string)) {
                    // If it doesn't contain the key, we set the key based off what was found inside the plugin jar
                    exist_file.set(string, latest_file.get(string));
                }
            }

            try {
                exist_file.save(file);
            } catch (IOException io) {
                io.printStackTrace();
            }
        }

        FileConfiguration yaml = YamlConfiguration.loadConfiguration(file);

        GLOBAL_BACK_TO_MAIN = yaml.getString("global.back-to-main");
        GLOBAL_NEXT_PAGE = yaml.getString("global.next-page");
        GLOBAL_PREVIOUS_PAGE = yaml.getString("global.previous-page");
        GLOBAL_EMPTY_SLOT = yaml.getString("global.empty-slot");
        WORLD_SETTING_TYPE_BUTTON_FLAG = yaml.getString("world-setting.type-button.flag");
        WORLD_SETTING_TYPE_BUTTON_GAMERULE = yaml.getString("world-setting.type-button.gamerule");
        WORLD_SETTING_EDIT_PERMISSION = yaml.getString("world-setting.edit-permission");
        WORLD_SETTING_EDIT_DENY_MESSAGE = yaml.getString("world-setting.edit-deny-message");
        MAIN_DEFAULT_TEMPLATE_ENTRY = yaml.getString("main.default-template-entry");
        MAIN_ADD_WORLD = yaml.getString("main.add-world");
        MAIN_CLOSE = yaml.getString("main.close");
        BUTTON_WIKI = yaml.getString("button.wiki");


    }

    public static String GLOBAL_BACK_TO_MAIN;
    public static String GLOBAL_NEXT_PAGE;
    public static String GLOBAL_PREVIOUS_PAGE;
    public static String GLOBAL_EMPTY_SLOT;
    public static String WORLD_SETTING_TYPE_BUTTON_FLAG;
    public static String WORLD_SETTING_TYPE_BUTTON_GAMERULE;
    public static String WORLD_SETTING_EDIT_PERMISSION;
    public static String WORLD_SETTING_EDIT_DENY_MESSAGE;
    public static String MAIN_DEFAULT_TEMPLATE_ENTRY;
    public static String MAIN_ADD_WORLD;
    public static String MAIN_CLOSE;
    public static String BUTTON_WIKI;

}
