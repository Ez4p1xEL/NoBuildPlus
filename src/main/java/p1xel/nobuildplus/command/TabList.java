package p1xel.nobuildplus.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import p1xel.nobuildplus.storage.FlagsManager;
import p1xel.nobuildplus.storage.Settings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TabList implements TabCompleter {

    List<String> args0 = new ArrayList<>();
    List<String> flags = new ArrayList<>();
    List<String> bool = new ArrayList<>(Arrays.asList("true", "false"));
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {

        if (args0.isEmpty()) {
            args0.add("help"); args0.add("list"); args0.add("add"); args0.add("remove"); args0.add("clear"); args0.add("flag");
            args0.add("setspawn"); args0.add("tp"); args0.add("open"); args0.add("reload");
        }

        if (flags.isEmpty()) {
            flags.add("list");
            flags.addAll(FlagsManager.getFlags());
        }

        List<String> result0 = new ArrayList<>();
        if (args.length == 1) {
            for (String a : args0) {
                if (a.toLowerCase().startsWith(args[0].toLowerCase())) {
                    result0.add(a);
                }
            }
            return result0;
        }

        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("add") || args[0].equalsIgnoreCase("remove") || args[0].equalsIgnoreCase("flag") || args[0].equalsIgnoreCase("open")) {
                List<String> result = new ArrayList<>();
                for (String a : Settings.getEnableWorldList()) {
                    if (a.toLowerCase().startsWith(args[1].toLowerCase())) {
                        result.add(a);
                    }
                }
                return result;
            }


        }

        if (args.length == 3) {
            if (args[0].equalsIgnoreCase("flag")) {
                List<String> result = new ArrayList<>();
                for (String a : flags) {
                    if (a.toLowerCase().startsWith(args[2].toLowerCase())) {
                        result.add(a);
                    }
                }
                return result;
            }
        }

        if (args.length == 4) {
            if (args[0].equalsIgnoreCase("flag")) {
                List<String> result = new ArrayList<>();
                for (String a : bool) {
                    if (a.toLowerCase().startsWith(args[3].toLowerCase())) {
                        result.add(a);
                    }
                }
                return result;
            }
        }

        return new ArrayList<>();
    }

}
