package p1xel.nobuildplus.world;

import org.bukkit.Location;
import org.jetbrains.annotations.Nullable;
import p1xel.nobuildplus.Flag;
import p1xel.nobuildplus.FlagRegistry;
import p1xel.nobuildplus.storage.Worlds;

import java.util.HashMap;

public class NBPWorld implements ProtectedWorld {

    private final String worldName;
    private String permission;
    private String denyMessage;
    private Location location;
    private final HashMap<Flag, Boolean> flags = new HashMap<>();

    // If you want to get a gamerule by string value, please use GameRuleRegistry.getWorldGameRule()

    // Initialization
    public NBPWorld(String worldName) {
        this.worldName = worldName;
        this.permission = Worlds.getPermission(worldName);
        this.denyMessage = Worlds.getDenyMessage(worldName);
        this.location = Worlds.getSpawnLocation(worldName);

        for (Flag flag : FlagRegistry.getAllFlags()) {
            flags.put(flag, Worlds.getFlag(worldName, flag.getName()));
        }
    }


    @Override
    public String getWorldName() {
        return worldName;
    }

    @Override
    public String getPermission() {
        return permission;
    }

    @Nullable
    @Override
    public String getDenyMessage() {
        return denyMessage;
    }

    @Nullable
    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public boolean getFlag(Flag flag) {
        return flags.get(flag);
    }

    void updateFlag(Flag flag, boolean bool) {
        flags.put(flag, bool);
    }

    void updatePermission(String permission) {
        this.permission = permission;
    }

    void updateDenyMessage(String denyMessage) {
        this.denyMessage = denyMessage;
    }

    void updateLocation(Location location) {
        this.location = location;
    }
}
