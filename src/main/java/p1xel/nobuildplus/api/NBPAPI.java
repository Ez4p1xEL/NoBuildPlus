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
import p1xel.nobuildplus.storage.Worlds;
import p1xel.nobuildplus.world.ProtectedWorld;
import p1xel.nobuildplus.world.WorldManager;

public class NBPAPI implements NoBuildPlusAPI {

    private final String version;

    public NBPAPI() {
        this.version = "v0.3";
    }

    @Override
    public String getVersion() {
        return this.version;
    }

    @Override
    public void registerFlag(Flag flag) {
        FlagRegistry.registerFlag(flag);
        FlagsManager.defaultFlagList();
    }

    @Override
    public boolean canExecute(String world, Flags flag) {
        return flag.isEnabled(world);
    }

    @Override
    public boolean canExecute(String world, Flag flag) {
        return flag.isEnabled(world);
    }

    @Override
    public boolean canExecute(String world, Flag flag, Player player) {
        return !HookedPlugins.cancel(player) && flag.isEnabled(world);
    }

    @Override
    public boolean canExecute(String world, Flag flag, Location location) {
        return !HookedPlugins.cancel(location) && flag.isEnabled(world);
    }

    @Override
    public boolean canExecute(String world, Flag flag, Entity entity) {
        return !HookedPlugins.cancel(entity) && flag.isEnabled(world);
    }

    @Override
    public boolean canExecute(String world, Flag flag, Block block) {
        return !HookedPlugins.cancel(block) && flag.isEnabled(world);
    }

    @Override
    public ProtectedWorld getWorld(String worldName) {
        return WorldManager.getWorld(worldName);
    }

    @Override
    public String getLang() {
        return Config.getLanguage();
    }

    @Override
    public boolean isWorldEnabled(String world) {
        return WorldManager.getWorld(world) != null;
    }

    @Override
    public boolean isFlagEnabled(String flag) {
        return FlagsManager.isInTheFlagsList(flag) || FlagsManager.getFlagsIsEnabled(flag);
    }

    @Deprecated
    @Override
    public boolean getFlag(String world, String flag) {
        return Worlds.getFlag(world, flag);
    }



}
