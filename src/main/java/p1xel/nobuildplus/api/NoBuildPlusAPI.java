package p1xel.nobuildplus.api;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;
import p1xel.nobuildplus.Flag;
import p1xel.nobuildplus.Flags;
import p1xel.nobuildplus.world.ProtectedWorld;

public interface NoBuildPlusAPI {

    /**
     * @return version of api
     */
     String getVersion();

    /**
     * Register extra flags from your own plugin.
     * Refresh the flag list after registration
     * @param flag the class with implementing p1xel.nobuildplus.Flag
     */
    void registerFlag(Flag flag);

    /**
     * Return the bool whether the plugin does protection with flag.
     * This method includes isWorldEnabled()
     * @param world name of the world
     * @param flag object Flags
     * @return true if NoBuildPlus executes the protection.
     **/
     boolean canExecute(String world, Flags flag);

    /**
     * Return the bool whether the plugin does protection with flag.
     * This method includes isWorldEnabled()
     * @param world name of the world
     * @param flag enum value of implementing FlagInterface
     * @return true if NoBuildPlus executes the protection.
     **/
    boolean canExecute(String world, Flag flag);

    /**
     * Return the bool whether the plugin does protection with flag.
     * This method is including the area protection plugins checking.
     * If the player is in the area they create, the protection would be cancelled.
     * @param world name of the world
     * @param flag enum value of implementing FlagInterface
     * @param player the player
     * @return true if NoBuildPlus executes the protection.
     **/
    boolean canExecute(String world, Flag flag, Player player);

    /**
     * Return the bool whether the plugin does protection with flag.
     * This method is including the area protection plugins checking.
     * If the location is in the area they create, the protection would be cancelled.
     * @param world name of the world
     * @param flag enum value of implementing FlagInterface
     * @param location the location
     * @return true if NoBuildPlus executes the protection.
     **/
    boolean canExecute(String world, Flag flag, Location location);

    /**
     * Return the bool whether the plugin does protection with flag.
     * This method is including the area protection plugins checking.
     * If the entity is in the area they create, the protection would be cancelled.
     * @param world name of the world
     * @param flag enum value of implementing FlagInterface
     * @param entity the entity
     * @return true if NoBuildPlus executes the protection.
     **/
    boolean canExecute(String world, Flag flag, Entity entity);

    /**
     * Return the bool whether the plugin does protection with flag.
     * This method is including the area protection plugins checking.
     * If the block is in the area they create, the protection would be cancelled.
     * @param world name of the world
     * @param flag enum value of implementing FlagInterface
     * @param block the block
     * @return true if NoBuildPlus executes the protection.
     **/
    boolean canExecute(String world, Flag flag, Block block);

    /**
     * You can get the flag, deny message and bypass permission
     * through the ProtectedWorld, see usage at p1xel.nobuildplus.world.ProtectedWorld
     * @param worldName name of the world
     * @return instance of ProtectedWorld
     **/
    @Nullable
    ProtectedWorld getWorld(String worldName);

    /**
     * @return the language that plugin selects
     */
    String getLang();

    /**
     * Get whether the world is enabled for NoBuildPlus protection or not.
     * canExecute() also include this code.
     * @param world name of the world
     * @return true if the world is protected by NoBuildPlus
     **/
    boolean isWorldEnabled(String world);

    /**
     * This is used to check whether the flag is loaded or not.
     * This is a deprecated method because enabling flag is not used by NoBuildPlus.
     * @param flag name of the flag
     * @return true if NoBuildPlus has the flag
     **/
    @Deprecated
    boolean isFlagEnabled(String flag);

    // Get flag boolean for the enabled world
    @Deprecated
    boolean getFlag(String world, String flag);



}
