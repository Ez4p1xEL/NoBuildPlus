package p1xel.nobuildplus.listener.version;

import org.bukkit.event.Listener;

public interface FeatureListener extends Listener {

    String getName();
    boolean matchRequirement(int[] version);

}
