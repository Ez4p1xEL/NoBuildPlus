package p1xel.nobuildplus.listener.version;

import org.bukkit.event.Event;
import org.bukkit.event.EventException;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityEvent;
import org.bukkit.plugin.EventExecutor;
import org.jspecify.annotations.NonNull;
import p1xel.nobuildplus.listener.version.feature.PaperCushionBreakListener;

public class PaperEventExecutor implements EventExecutor {
    @Override
    public void execute(@NonNull Listener listener, @NonNull Event event) throws EventException {
        if (listener instanceof PaperCushionBreakListener) {
            ((PaperCushionBreakListener) listener).onBreakingCushion((EntityEvent) event);
        }
    }
}
