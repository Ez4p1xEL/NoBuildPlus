package p1xel.nobuildplus.api;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import p1xel.nobuildplus.Flag;
import p1xel.nobuildplus.FlagRegistry;
import p1xel.nobuildplus.Flags;
import p1xel.nobuildplus.hook.HookedPlugins;
import p1xel.nobuildplus.storage.Config;
import p1xel.nobuildplus.storage.FlagsManager;
import p1xel.nobuildplus.storage.Settings;
import p1xel.nobuildplus.storage.Worlds;

public class NBPAPI {

    private final String version;

    public NBPAPI() {
        this.version = "v0.3";
    }

    /**
     * @return version of api
     */
    public String getVersion() {
        return this.version;
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
     * This method includes isWorldEnabled()
     * @param world name of the world
     * @param flag object Flags
     * @return TRUE means protect, FALSE means no protect.
     **/
    public boolean canExecute(String world, Flags flag) {
        return flag.isEnabled(world);
    }

    /**
     * Return the bool whether the plugin does protection with flag.
     * This method includes isWorldEnabled()
     * @param world name of the world
     * @param flag enum value of implementing FlagInterface
     * @return TRUE means protect, FALSE means no protect.
     **/
    public boolean canExecute(String world, Flag flag) {
        return flag.isEnabled(world);
    }

    /**
     * Return the bool whether the plugin does protection with flag.
     * This method is including the area protection plugins checking.
     * If the player is in the area they create, the protection would be cancelled.
     * @param world name of the world
     * @param flag enum value of implementing FlagInterface
     * @param player the player
     * @return TRUE means protect, FALSE means no protect.
     **/
    public boolean canExecute(String world, Flag flag, Player player) {
        return !HookedPlugins.cancel(player) && flag.isEnabled(world);
    }

    /**
     * Return the bool whether the plugin does protection with flag.
     * This method is including the area protection plugins checking.
     * If the location is in the area they create, the protection would be cancelled.
     * @param world name of the world
     * @param flag enum value of implementing FlagInterface
     * @param location the location
     * @return TRUE means protect, FALSE means no protect.
     **/
    public boolean canExecute(String world, Flag flag, Location location) {
        return !HookedPlugins.cancel(location) && flag.isEnabled(world);
    }

    /**
     * Return the bool whether the plugin does protection with flag.
     * This method is including the area protection plugins checking.
     * If the entity is in the area they create, the protection would be cancelled.
     * @param world name of the world
     * @param flag enum value of implementing FlagInterface
     * @param entity the entity
     * @return TRUE means protect, FALSE means no protect.
     **/
    public boolean canExecute(String world, Flag flag, Entity entity) {
        return !HookedPlugins.cancel(entity) && flag.isEnabled(world);
    }

    /**
     * Return the bool whether the plugin does protection with flag.
     * This method is including the area protection plugins checking.
     * If the block is in the area they create, the protection would be cancelled.
     * @param world name of the world
     * @param flag enum value of implementing FlagInterface
     * @param block the block
     * @return TRUE means protect, FALSE means no protect.
     **/
    public boolean canExecute(String world, Flag flag, Block block) {
        return !HookedPlugins.cancel(block) && flag.isEnabled(world);
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
        return FlagsManager.isInTheFlagsList(flag) || FlagsManager.getFlagsIsEnabled(flag);
    }

    // Get flag boolean for the enabled world
    public boolean getFlag(String world, String flag) {
        return Worlds.getFlag(world, flag);
    }

}
