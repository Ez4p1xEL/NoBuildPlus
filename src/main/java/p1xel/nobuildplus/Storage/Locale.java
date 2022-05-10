package p1xel.nobuildplus.Storage;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import p1xel.nobuildplus.NoBuildPlus;

import java.io.File;
import java.io.IOException;

public class Locale {

    public static void createLocaleFile() {

        File file = new File(NoBuildPlus.getInstance().getDataFolder(), Config.getString("lang-file"));

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

            if (Config.getLanguage().equalsIgnoreCase("en")) {
                setEnglish();
            }

            if (Config.getLanguage().equalsIgnoreCase("zh_CN")) {
                setZH_CN();
            }

        }
    }

    public static FileConfiguration get() {
        File file = new File(NoBuildPlus.getInstance().getDataFolder(), Config.getString("lang-file"));
        return YamlConfiguration.loadConfiguration(file);
    }

    public static void set(String path, Object value) {
        File file = new File(NoBuildPlus.getInstance().getDataFolder(), Config.getString("lang-file"));
        FileConfiguration yaml = YamlConfiguration.loadConfiguration(file);

        yaml.set(path,value);
        try {
            yaml.save(file);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }

    public static void checkLanguage() {

        if (!getLocaleLanguage().equalsIgnoreCase(Config.getLanguage())) {

            if (Config.getLanguage().equalsIgnoreCase("en")) {
                setLocaleLanguage("en");
            }

            if (Config.getLanguage().equalsIgnoreCase("zh_CN")) {
                setLocaleLanguage("zh_CN");
            }

        }

    }

    public static String getMessage(String path) {
        return ChatColor.translateAlternateColorCodes('&', get().getString(path).replaceAll("%plugin%", get().getString("plugin-name")));
    }

    public static String getCmdMessage(String path) {
        return ChatColor.translateAlternateColorCodes('&', get().getString(path).replaceAll("%prefix%", get().getString("commands-plugin-name")));
    }

    public static String getLocaleLanguage() {
        return get().getString("Language");
    }

    public static void setLocaleLanguage(String lang) {

        if (lang.equalsIgnoreCase("en")) {
            setEnglish();
        }

        if (lang.equalsIgnoreCase("zh_CN")) {
            setZH_CN();
        }

    }

    public static void setEnglish() {

        set("Language", "en");
        set("plugin-name", "NoBuildPlus");
        set("commands-plugin-name", "nbp");
        set("commands.top", "&8&m                              ");
        set("commands.plugin", "         &2&lNoBuildPlus");
        set("commands.space-1", "&f");
        set("commands.help", "&7/%prefix% help");
        set("commands.help-2", "&7View help");
        set("commands.list", "&7/%prefix% list");
        set("commands.list-2", "&7List of the enable worlds");
        set("commands.add", "&7/%prefix% add <world>");
        set("commands.add-2", "&7Add world name to list");
        set("commands.remove", "&7/%prefix% remove <world>");
        set("commands.remove-2", "&7Remove world name from list");
        set("commands.clear", "&7/%prefix% clear");
        set("commands.clear-2", "&7Clear worlds from list");
        set("commands.flag", "&7/%prefix% flag <world> <flag> <true/false>");
        set("commands.flag-2", "&7Set world's flag");
        set("commands.reload", "&7/%prefix% reload");
        set("commands.reload-2", "&7Reload the config");
        set("commands.flaglist", "&7/%prefix% flag list");
        set("commands.flaglist-2", "&7View flags list");
        set("commands.setspawn", "&7/%prefix% setspawn");
        set("commands.setspawn-2", "&7Set the spawn location of current world");
        set("commands.tp", "&7/%prefix% tp");
        set("commands.tp-2", "&7Teleport to the spawn of current world");
        set("commands.space-8", "&f");
        set("commands.bottom", "&8&m                              ");

        set("update-check.invalid", "Unable to check for updates: ");
        set("update-check.latest", "There is not a new update available.");
        set("update-check.outdate", "There is a new update available.");

        set("list-1", "&7Enable worlds:");
        set("list-2", "&9%list%");

        set("no-perm", "&cYou do not have permission to do that!");
        set("not-allow", "&c&l! &7You cannot do that here.");
        set("already-exists", "&cThe world is already exists!");
        set("not-in-list", "&cThe world is not in the list!");
        set("clear-success", "&aYou cleared the enable worlds list!");
        set("reload-success", "&aConfiguration reloaded successful!");
        set("add-success", "&aYou added &9%world% &ato the list.");
        set("remove-success", "&aYou removed &9%world% &afrom the list.");
        set("cant-find-world", "&cCan't find world. (Or the world is disabled)");
        set("flags-list", "&cInvalid flag. Check /nbp flag list To view flags.");
        set("invalid-boolean", "&cInvalid, please type true or false for the flag");
        set("flag-set-success", "&aYou set &9%world% &a's flag &e%flag% &ato &b%boolean%&a.");
        set("loc-not-set", "&cYou didn't set the spawn location for your world!");
        set("loc-set-success", "&aYou set the spawn location for &9%world%&a !");
        set("not-player", "&cYou must be a player to do that.");
        set("tp-success", "&aYou teleport to spawn of &9%world%&a.");

        set("flag.break", "Break");
        set("flag.build", "Build");
        set("flag.use", "Use");
        set("flag.container", "Container");
        set("flag.move", "Move");
        set("flag.mob-damage", "Mob Damage");
        set("flag.mob-explode", "Mob Explode");
        set("flag.pvp", "PVP");
        set("flag.tnt", "TNT");
        set("flag.frame", "Frame");
        set("flag.bed", "Bed");

    }

    public static void setZH_CN() {

        set("Language", "zh_CN");
        set("plugin-name", "NoBuildPlus");
        set("commands-plugin-name", "nbp");
        set("commands.top", "&8&m                              ");
        set("commands.plugin", "         &2&lNoBuildPlus");
        set("commands.space-1", "&f");
        set("commands.help", "&7/%prefix% help");
        set("commands.help-2", "&7查看帮助");
        set("commands.list", "&7/%prefix% list");
        set("commands.list-2", "&7查看启用本插件的世界列表");
        set("commands.add", "&7/%prefix% add <world>");
        set("commands.add-2", "&7将世界加至列表中");
        set("commands.remove", "&7/%prefix% remove <world>");
        set("commands.remove-2", "&7从列表中移除世界");
        set("commands.clear", "&7/%prefix% clear");
        set("commands.clear-2", "&7清空列表");
        set("commands.flag", "&7/%prefix% flag <world> <flag> <true/false>");
        set("commands.flag-2", "&7设置世界的属性");
        set("commands.flaglist", "&7/%prefix% flag list");
        set("commands.flaglist-2", "&7查看属性列表");
        set("commands.setspawn", "&7/%prefix% setspawn");
        set("commands.setspawn-2", "&7设置世界的出生点");
        set("commands.tp", "&7/%prefix% tp");
        set("commands.tp-2", "&7前往当前世界的出生点");
        set("commands.reload", "&7/%prefix% reload");
        set("commands.reload-2", "&7重载配置文件");
        set("commands.space-8", "&f");
        set("commands.bottom", "&8&m                              ");

        set("update-check.invalid", "无法检查更新: ");
        set("update-check.latest", "暂时没有任何更新 你的版本为最新版!");
        set("update-check.outdate", "有版本更新 请前往MCBBS或SpigotMC下载!");

        set("list-1", "&7启用本插件的世界:");
        set("list-2", "&9%list%");

        set("no-perm", "&c你没有权限这么做!");
        set("not-allow", "&c&l! &7你不能在这个世界这么做");
        set("already-exists", "&c这个世界已经在列表里了!");
        set("not-in-list", "&c这个世界不在列表里!");
        set("clear-success", "&a你清空了启用本插件的世界列表!");
        set("reload-success", "&a配置文件重载成功!");
        set("add-success", "&a你将 &9%world% &a加入至列表中");
        set("remove-success", "&a你将 &9%world% &a移除至列表中");
        set("cant-find-world", "&c无法找到世界 (有可能是世界未启用本插件)");
        set("flags-list", "&c错误属性, /nbp flag list 查看所有属性");
        set("invalid-boolean", "&c错误, 请输入true或false代表是否开启");
        set("flag-set-success", "&a你成功将 &9%world% &a的属性 &e%flag% &a设置为 &b%boolean%");
        set("loc-not-set", "&c你没有为你的世界设置出生点");
        set("loc-set-success", "&a你成功为 &9%world%&a 设置了出生点!");
        set("not-player", "&c你必须为一名玩家");
        set("tp-success", "&a你传送了 &9%world% &a的出生点!");

        set("flag.break", "破坏");
        set("flag.build", "建造");
        set("flag.use", "使用");
        set("flag.container", "容器");
        set("flag.move", "移动");
        set("flag.mob-damage", "生物伤害");
        set("flag.mob-explode", "生物爆炸");
        set("flag.pvp", "PVP");
        set("flag.tnt", "TNT");
        set("flag.frame", "展示框保护");
        set("flag.bed", "床");

    }

}
