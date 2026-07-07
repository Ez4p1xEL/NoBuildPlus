package p1xel.nobuildplus.tool;

import org.bukkit.ChatColor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ColorUtil {

    public static boolean hexColorEnabled = false;

    private static final Pattern HEX_PATTERN = Pattern.compile("&#([A-Fa-f0-9]{6})");

    public static String translateHexColorCodes(String message) {
        if (hexColorEnabled) {
            // HEX COLOR
            Matcher matcher = HEX_PATTERN.matcher(message);
            StringBuffer buffer = new StringBuffer();
            while (matcher.find()) {
                String color = matcher.group(1);
                matcher.appendReplacement(buffer, net.md_5.bungee.api.ChatColor.of("#" + color).toString());
            }
            matcher.appendTail(buffer);
            message = buffer.toString();
        }

        // TRADITIONAL COLOR
        return ChatColor.translateAlternateColorCodes('&', message);
    }

}
