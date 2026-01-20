package p1xel.nobuildplus.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import p1xel.nobuildplus.FlagRegistry;
import p1xel.nobuildplus.listener.gui.GUIMain;
import p1xel.nobuildplus.NoBuildPlus;
import p1xel.nobuildplus.listener.gui.GUIType;
import p1xel.nobuildplus.listener.gui.GUIWorld;
import p1xel.nobuildplus.storage.*;
import p1xel.nobuildplus.world.NBPWorld;
import p1xel.nobuildplus.world.ProtectedWorld;
import p1xel.nobuildplus.world.WorldManager;

import java.util.List;

public class Cmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {

        if (!sender.hasPermission("nobuildplus.use")) {
            sender.sendMessage(Locale.getMessage("no-perm"));
            return true;
        }

        if (args.length == 0) {

            if (sender instanceof Player && NoBuildPlus.getInstance().getBukkitVersion() >= 15 && sender.hasPermission("nobuildplus.gui")) {
                Player p = (Player) sender;
                p.openInventory(new GUIMain(1).getInventory());
                return true;
            }

            sender.sendMessage(Locale.getCmdMessage("commands.help"));
            return true;

        }

        if (args.length <= 2) {

            if (args[0].equalsIgnoreCase("open")) {

                if (!(sender instanceof Player)) {
                    sender.sendMessage(Locale.getMessage("not-player"));
                    return true;
                }

                if (NoBuildPlus.getInstance().getBukkitVersion() < 15) {
                    sender.sendMessage(Locale.getMessage("gui-not-support"));
                    return true;
                }

                if (!sender.hasPermission("nobuildplus.gui")) {
                    sender.sendMessage(Locale.getMessage("no-perm"));
                    return true;
                }

                Player p = (Player) sender;

                if (args.length == 2) {

                    String worldName = args[1];

                    if (WorldManager.getWorld(worldName) == null) {
                        sender.sendMessage(Locale.getMessage("not-in-list"));
                        return true;
                    }

//                    if (args[1].contains("_page")) {
//                        sender.sendMessage(Locale.getMessage("name-not-allow"));
//                        return true;
//
//                    }

                    p.openInventory(new GUIWorld(worldName, 1, GUIType.FLAG).getInventory());
                    return true;

                }

                p.openInventory(new GUIMain(1).getInventory());
                return true;

            }
        }

        if (args.length == 1) {

            if (args[0].equalsIgnoreCase("help")) {

                for (String m : Locale.yaml.getConfigurationSection("commands").getKeys(false)) {
                    sender.sendMessage(Locale.getCmdMessage("commands." + m));
                }
                return true;

            }

            if (args[0].equalsIgnoreCase("list")) {

                List<String> list = WorldManager.getWorldsInName();

                sender.sendMessage(Locale.getMessage("list-1"));
                sender.sendMessage(Locale.getMessage("list-2").replaceAll("%list%", list.toString()));
                return true;

            }

            if (args[0].equalsIgnoreCase("clear")) {

                for (String key : Worlds.yaml.getKeys(false)) {
                    Worlds.set(key, null);
                }
                sender.sendMessage(Locale.getMessage("clear-success"));
                return true;

            }

            if (args[0].equalsIgnoreCase("reload")) {

                Config.reloadConfig();
                FlagsManager.createFlagsManagerFile();
                Locale.createLocaleFile();
                Settings.createSettingsFile();
                Worlds.createWorldsFile();
                FlagRegistry.refreshMap();
                WorldManager.init();
                sender.sendMessage(Locale.getMessage("reload-success"));
                return true;

            }

            if (args[0].equalsIgnoreCase("setspawn")) {

                if (!(sender instanceof Player)) {
                    sender.sendMessage(Locale.getMessage("not-player"));
                    return true;
                }

                Player p = (Player) sender;
                String world = p.getWorld().getName();

                if (WorldManager.getWorld(world) == null) {
                    sender.sendMessage(Locale.getMessage("not-in-list"));
                    return true;
                }

                WorldManager.setLocation(world, p.getLocation());
                sender.sendMessage(Locale.getMessage("loc-set-success").replaceAll("%world%", world));
                return true;

            }

            if (args[0].equalsIgnoreCase("tp")) {

                if (!(sender instanceof Player)) {
                    sender.sendMessage(Locale.getMessage("not-player"));
                    return true;
                }

                Player p = (Player) sender;
                String worldName = p.getWorld().getName();
                ProtectedWorld world = WorldManager.getWorld(worldName);

                if (WorldManager.getWorld(worldName) == null) {
                    sender.sendMessage(Locale.getMessage("not-in-list"));
                    return true;
                }

                if (world.getLocation() == null) {
                    sender.sendMessage(Locale.getMessage("loc-not-set"));
                    return true;
                }

                sender.sendMessage(Locale.getMessage("tp-success").replaceAll("%world%", worldName));
                p.teleport(world.getLocation());
                return true;
            }

            sender.sendMessage(Locale.getMessage("wrong-arg"));
            return true;

        }

        if (args.length == 2) {

            if (args[0].equalsIgnoreCase("add")) {

                if (WorldManager.getWorld(args[1]) == null) {

                    //Worlds.createWorld(args[1]);
                    WorldManager.enableWorld(args[1]);
                    sender.sendMessage(Locale.getMessage("add-success").replaceAll("%world%", args[1]));

                } else {
                    sender.sendMessage(Locale.getMessage("already-exists"));
                }
                return true;

            }

            if (args[0].equalsIgnoreCase("remove")) {

                if (WorldManager.getWorld(args[1]) != null) {

                    //Worlds.removeWorld(args[1]);
                    WorldManager.removeWorld(args[1]);
                    sender.sendMessage(Locale.getMessage("remove-success").replaceAll("%world%", args[1]));

                } else {
                    sender.sendMessage(Locale.getMessage("not-in-list"));
                }
                return true;

            }

            if (args[0].equalsIgnoreCase("flag")) {

                if (args[1].equalsIgnoreCase("list")) {

                    sender.sendMessage(FlagsManager.getFlags().toString());
                    return true;

                }

            }

            sender.sendMessage(Locale.getMessage("wrong-arg"));
            return true;

        }

        if (args.length == 4) {

            if (args[0].equalsIgnoreCase("flag")) {

                if (WorldManager.getWorld(args[1]) == null) {
                    sender.sendMessage(Locale.getMessage("cant-find-world"));
                    return true;
                }

                if (FlagRegistry.matchFlag(args[2]) == null) {
                    sender.sendMessage(Locale.getMessage("flags-list"));
                    return true;
                }

                if (args[3].equalsIgnoreCase("true") || args[3].equalsIgnoreCase("t")) {

                    //Worlds.setFlag(args[1], args[2], true);
                    WorldManager.setFlag(args[1], FlagRegistry.matchFlag(args[2]), true);
                    sender.sendMessage(Locale.getMessage("flag-set-success").replaceAll("%world%",args[1]).replaceAll("%flag%",args[2]).replaceAll("%boolean%", args[3]));
                    return true;

                }

                if (args[3].equalsIgnoreCase("false") || args[3].equalsIgnoreCase("f")) {

                    WorldManager.setFlag(args[1], FlagRegistry.matchFlag(args[2]), false);
                    sender.sendMessage(Locale.getMessage("flag-set-success").replaceAll("%world%",args[1]).replaceAll("%flag%",args[2]).replaceAll("%boolean%", args[3]));
                    return true;

                }

                sender.sendMessage(Locale.getMessage("invalid-boolean"));
                return true;

            }

            sender.sendMessage(Locale.getMessage("wrong-arg"));
            return true;

        }

        return false;
    }

}
