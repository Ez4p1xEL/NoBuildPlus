package p1xel.nobuildplus.Command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import p1xel.nobuildplus.Listener.GUIManager;
import p1xel.nobuildplus.NoBuildPlus;
import p1xel.nobuildplus.Storage.*;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;

public class Cmd implements CommandExecutor {

    @Override
    @ParametersAreNonnullByDefault
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!sender.hasPermission("nobuildplus.use")) {
            sender.sendMessage(Locale.getMessage("no-perm"));
            return true;
        }

        if (args.length == 0) {

            sender.sendMessage(Locale.getCmdMessage("commands.help"));
            return true;

        }

        if (args.length == 1) {

            if (args[0].equalsIgnoreCase("help")) {

                for (String m : Locale.yaml.getConfigurationSection("commands").getKeys(false)) {
                    sender.sendMessage(Locale.getCmdMessage("commands." + m));
                }
                return true;

            }

            if (args[0].equalsIgnoreCase("list")) {

                ArrayList<String> list = (ArrayList<String>) Settings.getEnableWorldList();

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
                if (NoBuildPlus.getInstance().getBukkitVersion() >= 15) {
                    GUIManager.instance.reloadGUIs();
                }
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

                if (!Settings.getEnableWorldList().contains(world)) {
                    sender.sendMessage(Locale.getMessage("not-in-list"));
                    return true;
                }

                Worlds.setSpawnLocation(world, p.getLocation());
                sender.sendMessage(Locale.getMessage("loc-set-success").replaceAll("%world%", world));
                return true;

            }

            if (args[0].equalsIgnoreCase("tp")) {

                if (!(sender instanceof Player)) {
                    sender.sendMessage(Locale.getMessage("not-player"));
                    return true;
                }

                Player p = (Player) sender;
                String world = p.getWorld().getName();

                if (!Settings.getEnableWorldList().contains(world)) {
                    sender.sendMessage(Locale.getMessage("not-in-list"));
                    return true;
                }

                if (!Worlds.isSpawnLocationSet(world)) {
                    sender.sendMessage(Locale.getMessage("loc-not-set"));
                    return true;
                }

                sender.sendMessage(Locale.getMessage("tp-success").replaceAll("%world%",world));
                p.teleport(Worlds.getSpawnLocation(world));
                return true;
            }

            sender.sendMessage(Locale.getMessage("wrong-arg"));
            return true;

        }

        if (args.length == 2) {

            if (args[0].equalsIgnoreCase("add")) {

                if (!Settings.getEnableWorldList().contains(args[1])) {

                    Worlds.createWorld(args[1]);
                    GUIManager.instance.createInv(args[1]);
                    sender.sendMessage(Locale.getMessage("add-success").replaceAll("%world%", args[1]));
                    return true;

                } else {
                    sender.sendMessage(Locale.getMessage("already-exists"));
                    return true;
                }

            }

            if (args[0].equalsIgnoreCase("remove")) {

                if (Settings.getEnableWorldList().contains(args[1])) {

                    Worlds.removeWorld(args[1]);
                    GUIManager.instance.removeWorld(args[1]);
                    sender.sendMessage(Locale.getMessage("remove-success").replaceAll("%world%", args[1]));
                    return true;

                } else {
                    sender.sendMessage(Locale.getMessage("not-in-list"));
                    return true;
                }

            }

            if (args[0].equalsIgnoreCase("flag")) {

                if (args[1].equalsIgnoreCase("list")) {

                    sender.sendMessage(FlagsManager.getFlags().toString());
                    return true;

                }

            }

            if (args[0].equalsIgnoreCase("open")) {

                if (!(sender instanceof Player)) {
                    sender.sendMessage(Locale.getMessage("not-player"));
                    return true;
                }

                if (!sender.hasPermission("nobuildplus.gui")) {
                    sender.sendMessage(Locale.getMessage("no-perm"));
                    return true;
                }

                String name = args[1];


                if (!Worlds.yaml.getKeys(false).contains(name)) {
                    sender.sendMessage(Locale.getMessage("not-in-list"));
                    return true;
                }

                if (args[1].contains("_page2")) {
                    sender.sendMessage(Locale.getMessage("name-not-allow"));
                    return true;

                }

                Player p = (Player) sender;

                p.openInventory(GUIManager.instance.getGUI(name+"_page1"));
                return true;

            }

            sender.sendMessage(Locale.getMessage("wrong-arg"));
            return true;

        }

        if (args.length == 4) {

            if (args[0].equalsIgnoreCase("flag")) {

                if (!Settings.getEnableWorldList().contains(args[1])) {
                    sender.sendMessage(Locale.getMessage("cant-find-world"));
                    return true;
                }

                if (!FlagsManager.isInTheFlagsList(args[2])) {
                    sender.sendMessage(Locale.getMessage("flags-list"));
                    return true;
                }

                if (args[3].equalsIgnoreCase("true") || args[3].equalsIgnoreCase("t")) {

                    Worlds.setFlag(args[1], args[2], true);
                    sender.sendMessage(Locale.getMessage("flag-set-success").replaceAll("%world%",args[1]).replaceAll("%flag%",args[2]).replaceAll("%boolean%", args[3]));
                    return true;

                }

                if (args[3].equalsIgnoreCase("false") || args[3].equalsIgnoreCase("f")) {

                    Worlds.setFlag(args[1], args[2], false);
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
