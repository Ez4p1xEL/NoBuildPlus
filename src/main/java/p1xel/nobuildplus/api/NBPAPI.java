package p1xel.nobuildplus.api;

import p1xel.nobuildplus.Flag;
import p1xel.nobuildplus.FlagRegistry;
import p1xel.nobuildplus.Flags;
import p1xel.nobuildplus.storage.Config;
import p1xel.nobuildplus.storage.FlagsManager;
import p1xel.nobuildplus.storage.Settings;
import p1xel.nobuildplus.storage.Worlds;

import java.util.List;

public class NBPAPI {

    private final String version;

    public NBPAPI() {
        this.version = "v0.2";
    }

    /**
     * @return version of api
     */
    public String getVersion() {
        return this.version;
    }

    // Add flag to NoBuildPlus (with no type: option)
    @Deprecated
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
    @Deprecated
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

    /**
     * Register extra flags from your own plugin.
     * Refresh the flag list after registration
     * @param flag the class with implementing p1xel.nobuildplus.Flag
     */
    public void registerFlag(Flag flag) {
        FlagRegistry.registerFlag(flag);
        FlagsManager.defaultFlagList();
    }

    /**
     * Return the bool whether the plugin does protection with flag.
     * @param world name of the world
     * @param flag object Flag
     * @return TRUE means protect, FALSE means no protect.
     **/
    public boolean canExecute(String world, Flags flag) {
        return flag.isEnabled(world);
    }

    /**
     * @return language of files
     */
    public String getLang() {
        return Config.getLanguage();
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

}
