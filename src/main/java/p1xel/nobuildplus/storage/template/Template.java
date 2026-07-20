package p1xel.nobuildplus.storage.template;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.Nullable;
import p1xel.nobuildplus.Flag;
import p1xel.nobuildplus.FlagRegistry;
import p1xel.nobuildplus.NoBuildPlus;
import p1xel.nobuildplus.gamerule.GameRuleRegistry;
import p1xel.nobuildplus.storage.Locale;
import p1xel.nobuildplus.storage.Logger;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

public class Template {

    private String name;
    private String label;
    private String description;
    private @Nullable String bypassPermission;
    private @Nullable String denyMessage;
    private HashMap<Flag, Boolean> flags = new HashMap<>();
    private HashMap<String, Object> gamerules = new HashMap<>();
    private final File file;
    private final FileConfiguration yaml;

    public Template(String name) {
        this.name = name;

        file = new File(NoBuildPlus.getInstance().getDataFolder(), "templates/" + name + ".yml");
        yaml = YamlConfiguration.loadConfiguration(file);

        label = yaml.getString("label", name);
        description = yaml.getString("description", Locale.yaml.getString("no-description", "No description provided."));
        bypassPermission = yaml.getString("permission", null);
        denyMessage = yaml.getString("deny-message", null);


        for (String flagName : yaml.getConfigurationSection("flags").getKeys(false)) {
            Flag flag = FlagRegistry.matchFlag(flagName);
            if (flag != null) {
                flags.put(flag, yaml.getBoolean("flags." + flagName));
            } else {
                Logger.log(Level.WARNING, "Template " + name + " contains an unknown flag: " + flagName);
            }
        }

        List<String> gameruleList = GameRuleRegistry.getRegisteredGameRules();
        for (String gameruleName : yaml.getConfigurationSection("gamerules").getKeys(false)) {
            if (!gameruleList.contains(gameruleName)) {
                //Logger.debug("Template " + name + " contains an unknown gamerule: " + gameruleName);
                continue;
            }
            gamerules.put(gameruleName, yaml.getBoolean("gamerules." + gameruleName));
        }

    }

    public String getName() {
        return name;
    }

    public String getLabel() {
        return label;
    }

    public String getDescription() {
        return description;
    }

    public HashMap<Flag, Boolean> getFlags() {
        return flags;
    }

    public HashMap<String, Object> getGameRules() {
        return gamerules;
    }

    public boolean getFlag(Flag flag) {
        return flags.get(flag);
    }

    public Object getGameRule(String ruleName) {
        return gamerules.get(ruleName);
    }

    public String getPermission() {
        return bypassPermission;
    }

    public String getDenyMessage() {
        return denyMessage;
    }

    void setFlag(Flag flag, boolean value) {
        flags.put(flag, value);
        yaml.set("flags." + flag.getName(), value);
        try {
            yaml.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    void setGameRule(String ruleName, Object value) {
        gamerules.put(ruleName, value);
        yaml.set("gamerules." + ruleName, value);
        try {
            yaml.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    void setPermission(String permission) {
        this.bypassPermission = permission;
        yaml.set("permission", permission);
        try {
            yaml.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    void setDenyMessage(String denyMessage) {
        this.denyMessage = denyMessage;
        yaml.set("deny-message", denyMessage);
        try {
            yaml.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    void setLabel(String label) {
        this.label = label;
        yaml.set("label", label);
        try {
            yaml.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    void setDescription(String description) {
        this.description = description;
        yaml.set("description", description);
        try {
            yaml.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
