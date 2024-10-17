package p1xel.nobuildplus.API;

import p1xel.nobuildplus.Flags;
import p1xel.nobuildplus.Storage.FlagsManager;
import p1xel.nobuildplus.Storage.Settings;
import p1xel.nobuildplus.Storage.Worlds;

import java.util.List;

public class NBPAPI {

    private String info;
    private String version;

    public NBPAPI() {
        this.info = "This is a testing API at ";
        this.version = "v0.1";
    }

    public String getInfo() {
        return this.info;
    }

    public String getVersion() {
        return this.version;
    }

    // Add flag to NoBuildPlus (with no type: option)
    public void addFlag(String flag, String item, int slot, boolean def) {
        if (FlagsManager.yaml.get("flags." + flag + ".enable") == null) {
            FlagsManager.yaml.set("flags." + flag + ".enable", true);
            FlagsManager.yaml.set("flags." + flag + ".show-item", item);
            FlagsManager.yaml.set("flags." + flag + ".slot", slot);
            FlagsManager.defaultFlagList();
        }
        if (Settings.yaml.get("global-settings." + flag) == null) {
            Settings.yaml.set("global-settings." + flag, def);
            Settings.defaultList();
        }
        for (String world : Settings.getEnableWorldList()) {

            if (Worlds.yaml.get(world + ".flags." + flag) == null) {

                Worlds.yaml.set(world + ".flags." + flag, def);

            }

        }

    }

    // Add flag to NoBuildPlus (with type all/list option)
    public void addFlag(String flag, String item, int slot, String type, List<String> list, boolean def) {

        if (FlagsManager.yaml.get("flags." + flag + ".enable") == null) {
            FlagsManager.yaml.set("flags." + flag + ".enable", true);
            FlagsManager.yaml.set("flags." + flag + ".show-item", item);
            FlagsManager.yaml.set("flags." + flag + ".slot", slot);
            FlagsManager.yaml.set("flags." + flag + ".type", type);
            FlagsManager.yaml.set("flags." + flag + ".list", list);
            FlagsManager.defaultFlagList();
        }
        if (Settings.yaml.get("global-settings." + flag) == null) {
            Settings.yaml.set("global-settings." + flag, def);
            Settings.defaultList();
        }
        for (String world : Settings.getEnableWorldList()) {

            if (Worlds.yaml.get(world + ".flags." + flag) == null) {

                Worlds.yaml.set(world + ".flags." + flag, def);

            }

        }

    }

    // Get if the world is enabled for NoBuildPlus
    public boolean isWorldEnabled(String world) {
        return Settings.getEnableWorldList().contains(world);
    }

    // Get if the flag is enabled in Flags Manager for NoBuildPlus.
    public boolean isFlagEnabled(String flag) {
        if ( FlagsManager.isInTheFlagsList(flag) || FlagsManager.getFlagsIsEnabled(flag)) {
            return true;
        }
        return false;
    }

    // Get flag boolean for the enabled world
    public boolean getFlag(String world, String flag) {
        return Worlds.getFlag(world, flag);
    }

    // Get if the flag is able to be executed in the world.
    // TRUE == start to prevent
    // FALSE == no need to prevent
    public boolean canExecute(String world, Flags flag) {
        return flag.isEnabled(world);
    }

}
