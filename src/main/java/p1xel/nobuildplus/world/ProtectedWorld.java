package p1xel.nobuildplus.world;

import org.bukkit.Location;
import org.jetbrains.annotations.Nullable;
import p1xel.nobuildplus.Flag;

public interface ProtectedWorld {

    // Info
    String getWorldName();
    String getPermission();
    @Nullable
    String getDenyMessage();
    @Nullable
    Location getLocation();

    // flag
    boolean getFlag(Flag flag);

    // gamerule
    Object getGameRule(String ruleName);

}
